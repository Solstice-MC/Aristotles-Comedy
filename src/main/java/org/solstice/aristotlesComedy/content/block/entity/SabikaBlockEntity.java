package org.solstice.aristotlesComedy.content.block.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.recipe.MetallurgyRecipe;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeTypes;

import java.util.List;
import java.util.Objects;

public class SabikaBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

	protected DefaultedList<ItemStack> inventory;

	protected int litTimeRemaining = 0;
	protected int litTotalTime = 0;
	protected int cookingTimeSpent = 0;
	protected int cookingTotalTime = 0;

	public final Object2IntOpenHashMap<Identifier> recipesUsed;
	public final RecipeManager.MatchGetter<MetallurgyRecipeInput, MetallurgyRecipe> matchGetter;

	@Override
	protected Text getContainerName() {
		return null;
	}

	public SabikaBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.SABIKA, pos, state);
		this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
		this.recipesUsed = new Object2IntOpenHashMap<>();
		this.matchGetter = RecipeManager.createCachedMatchGetter(AristotlesRecipeTypes.METALLURGY);
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		builder
			.add(AristotlesComponentTypes.LIT_TIME_REMAINING, this.litTimeRemaining)
			.add(AristotlesComponentTypes.LIT_TIME_TOTAL, this.litTotalTime)
			.add(AristotlesComponentTypes.COOKING_TIME_SPENT, this.cookingTimeSpent)
			.add(AristotlesComponentTypes.COOKING_TIME_TOTAL, this.cookingTotalTime);
		super.addComponents(builder);
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.LIT_TIME_REMAINING, this.litTimeRemaining);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.LIT_TIME_TOTAL, this.litTotalTime);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.COOKING_TIME_SPENT, this.cookingTimeSpent);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.COOKING_TIME_TOTAL, this.cookingTotalTime);
	}

	//	public int getRemainingLitTime() {
//		return this.getComponents().get(AristotlesComponentTypes.LIT_TIME_REMAINING);
//	}

//	public int getTotalLitTime() {
//		return this.getComponents().get(AristotlesComponentTypes.LIT_TIME_TOTAL);
//	}

//	public int getSpentCookingTime() {
//		return this.getComponents().get(AristotlesComponentTypes.COOKING_TIME_SPENT);
//	}

//	public int getTotalCookingTime() {
//		return this.getComponents().get(AristotlesComponentTypes.COOKING_TIME_TOTAL);
//	}
















//	public static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
//		Registries.ITEM.iterateEntries(tag).forEach(entry ->
//			fuelTimes.put(entry.value(), fuelTime)
//		);
//	}

	public boolean isBurning() {
		return this.litTimeRemaining > 0;
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory, registryLookup);
//		this.litTimeRemaining = nbt.getShort("BurnTime");
//		this.cookingTimeSpent = nbt.getShort("CookTime");
//		this.cookingTotalTime = nbt.getShort("CookTimeTotal");
//		this.litTotalTime = this.inventory.get(1).getFuelTime();
		NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
		for(String string : nbtCompound.getKeys()) {
			this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
		}
	}

	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
//		nbt.putShort("BurnTime", (short)this.litTimeRemaining);
//		nbt.putShort("CookTime", (short)this.cookingTimeSpent);
//		nbt.putShort("CookTimeTotal", (short)this.cookingTotalTime);
		Inventories.writeNbt(nbt, this.inventory, registryLookup);
		NbtCompound nbtCompound = new NbtCompound();
		this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), count));
		nbt.put("RecipesUsed", nbtCompound);
	}

	public static void tick(World world, BlockPos pos, BlockState state, SabikaBlockEntity blockEntity) {
		boolean isBurning = blockEntity.isBurning();
		boolean dirty = false;
		if (isBurning) --blockEntity.litTimeRemaining;

		ItemStack baseStack = blockEntity.inventory.get(0);
		ItemStack additionStack = blockEntity.inventory.get(1);
		ItemStack fuelStack = blockEntity.inventory.get(2);

		boolean materialsExist = !baseStack.isEmpty() || !additionStack.isEmpty();
		boolean fuelExists = !fuelStack.isEmpty();
		if (blockEntity.isBurning() || fuelExists && materialsExist) {
			RecipeEntry<?> recipeEntry;
			if (materialsExist) {
				MetallurgyRecipeInput input = new MetallurgyRecipeInput(baseStack, additionStack);
				recipeEntry = blockEntity.matchGetter.getFirstMatch(input, world).orElse(null);
			} else {
				recipeEntry = null;
			}

			int i = blockEntity.getMaxCountPerStack();
			if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
				blockEntity.litTimeRemaining = fuelStack.getFuelTime();
				blockEntity.litTotalTime = blockEntity.litTimeRemaining;
				if (blockEntity.isBurning()) {
					dirty = true;
					if (fuelExists) {
						Item item = fuelStack.getItem();
						fuelStack.decrement(1);
						if (fuelStack.isEmpty()) {
							Item item2 = item.getRecipeRemainder();
							blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
						}
					}
				}
			}

			if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
				++blockEntity.cookingTimeSpent;
				if (blockEntity.cookingTimeSpent == blockEntity.cookingTotalTime) {
					blockEntity.cookingTimeSpent = 0;
					blockEntity.cookingTotalTime = getCookTime(world, blockEntity);
					if (craftRecipe(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
						blockEntity.setLastRecipe(recipeEntry);
					}

					dirty = true;
				}
			} else {
				blockEntity.cookingTimeSpent = 0;
			}
		} else if (!blockEntity.isBurning() && blockEntity.cookingTimeSpent > 0) {
			blockEntity.cookingTimeSpent = MathHelper.clamp(blockEntity.cookingTimeSpent - 2, 0, blockEntity.cookingTotalTime);
		}

		if (isBurning != blockEntity.isBurning()) {
			dirty = true;
			state = state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
			world.setBlockState(pos, state);
		}

		if (dirty) {
			markDirty(world, pos, state);
		}

	}

	public static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
		if (!((ItemStack)slots.get(0)).isEmpty() && recipe != null) {
			ItemStack itemStack = recipe.value().getResult(registryManager);
			if (itemStack.isEmpty()) {
				return false;
			} else {
				ItemStack itemStack2 = (ItemStack)slots.get(2);
				if (itemStack2.isEmpty()) {
					return true;
				} else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
					return false;
				} else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
					return true;
				} else {
					return itemStack2.getCount() < itemStack.getMaxCount();
				}
			}
		} else {
			return false;
		}
	}

	public static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
		if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
			ItemStack itemStack = (ItemStack)slots.get(0);
			ItemStack itemStack2 = recipe.value().getResult(registryManager);
			ItemStack itemStack3 = (ItemStack)slots.get(2);
			if (itemStack3.isEmpty()) {
				slots.set(2, itemStack2.copy());
			} else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
				itemStack3.increment(1);
			}

			if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !((ItemStack)slots.get(1)).isEmpty() && ((ItemStack)slots.get(1)).isOf(Items.BUCKET)) {
				slots.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			itemStack.decrement(1);
			return true;
		} else {
			return false;
		}
	}

	public static int getCookTime(World world, SabikaBlockEntity furnace) {
		MetallurgyRecipeInput input = new MetallurgyRecipeInput(
			furnace.getStack(0),
			furnace.getStack(1)
		);
		return furnace.matchGetter.getFirstMatch(input, world)
			.map(RecipeEntry::value)
			.map(MetallurgyRecipe::cookingTime)
			.orElseThrow();
	}

	public int[] getAvailableSlots(Direction side) {
		if (side == Direction.DOWN) {
			return new int[]{2, 1};
		} else {
			return side == Direction.UP ? new int[]{0} : new int[]{1};
		}
	}

	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return this.isValid(slot, stack);
	}

	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		if (dir == Direction.DOWN && slot == 1) {
			return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
		} else {
			return true;
		}
	}

	public int size() {
		return this.inventory.size();
	}

	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}

	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}

	public void setStack(int slot, ItemStack stack) {
		ItemStack slotStack = this.inventory.get(slot);
		boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(slotStack, stack);
		this.inventory.set(slot, stack);
		stack.capCount(this.getMaxCount(stack));
		if (slot == 0 && !bl) {
			this.cookingTotalTime = getCookTime(this.world, this);
			this.cookingTimeSpent = 0;
			this.markDirty();
		}

	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return null;
	}

	public boolean isValid(int slot, ItemStack stack) {
		if (slot == 2) {
			return false;
		} else if (slot != 1) {
			return true;
		} else {
			return stack.isFuel();
		}
	}

	@Override
	public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
		if (recipe == null) return;

		Identifier id = recipe.id();
		this.recipesUsed.addTo(id, 1);
	}

	@Override
	@Nullable
	public RecipeEntry<?> getLastRecipe() {
		return null;
	}

	@Override
	public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
	}

	public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
		List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
		player.unlockRecipes(list);

		list.stream()
			.filter(Objects::nonNull)
			.forEach(entry -> player.onRecipeCrafted(entry, this.inventory));

		this.recipesUsed.clear();
	}

	public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
		List<RecipeEntry<?>> result = Lists.newArrayList();

		this.recipesUsed.object2IntEntrySet().forEach(entry -> {
			world.getRecipeManager().get(entry.getKey()).ifPresent((recipe) -> {
				result.add(recipe);
				dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe.value()).getExperience());
			});
		});

		return result;
	}

	public static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
		int totalExperience = MathHelper.floor((float)multiplier * experience);
		ExperienceOrbEntity.spawn(world, pos, totalExperience);
	}

	@Override
	public void provideRecipeInputs(RecipeMatcher finder) {
		this.inventory.forEach(finder::addInput);
	}

//	public SabikaBlockEntity(BlockPos pos, BlockState state) {
//		super(AristotlesBlockEntities.SABIKA, pos, state, AristotlesRecipeTypes.METALLURGY);
//	}
//
//	@Override
//	public Text getContainerName() {
//		RegistryEntry<BlockEntityType<?>> entry = Registries.BLOCK_ENTITY_TYPE.getEntry(AristotlesBlockEntities.ALEMBIC);
//		String key = entry.getKey().orElseThrow().getValue().toTranslationKey("container");
//		return Text.translatable(key);
//	}
//
//	@Override
//	public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
//		return new SabikaScreenHandler(syncId, playerInventory);
//	}
//
//	@Override
//	public int size() {
//		return 4;
//	}

}
