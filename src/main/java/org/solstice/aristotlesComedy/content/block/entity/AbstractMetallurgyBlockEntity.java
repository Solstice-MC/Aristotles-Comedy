package org.solstice.aristotlesComedy.content.block.entity;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.recipe.MetallurgyRecipe;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;
import org.solstice.aristotlesComedy.content.screen.handler.MetallurgyScreenHandler;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeTypes;

import java.util.List;

public abstract class AbstractMetallurgyBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

	protected DefaultedList<ItemStack> inventory;

	protected int litTimeRemaining = 0;
	protected int litTotalTime = 0;
	protected int cookingTimeSpent = 0;
	protected int cookingTotalTime = 0;

	protected final PropertyDelegate propertyDelegate;
	protected final Object2IntOpenHashMap<Identifier> recipesUsed;
	protected final RecipeManager.MatchGetter<MetallurgyRecipeInput, MetallurgyRecipe> matchGetter;

	public AbstractMetallurgyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
		this.propertyDelegate = new PropertyDelegate() {
			public int get(int index) {
				switch (index) {
					case 0 -> {
						return AbstractMetallurgyBlockEntity.this.litTimeRemaining;
					}
					case 1 -> {
						return AbstractMetallurgyBlockEntity.this.litTotalTime;
					}
					case 2 -> {
						return AbstractMetallurgyBlockEntity.this.cookingTimeSpent;
					}
					case 3 -> {
						return AbstractMetallurgyBlockEntity.this.cookingTotalTime;
					}
					default -> {
						return 0;
					}
				}
			}

			public void set(int index, int value) {
				switch (index) {
					case 0 -> AbstractMetallurgyBlockEntity.this.litTimeRemaining = value;
					case 1 -> AbstractMetallurgyBlockEntity.this.litTotalTime = value;
					case 2 -> AbstractMetallurgyBlockEntity.this.cookingTimeSpent = value;
					case 3 -> AbstractMetallurgyBlockEntity.this.cookingTotalTime = value;
				}

			}

			public int size() {
				return 4;
			}
		};
		this.recipesUsed = new Object2IntOpenHashMap<>();
		this.matchGetter = RecipeManager.createCachedMatchGetter(AristotlesRecipeTypes.METALLURGY);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory, registryLookup);
		NbtCompound nbtCompound = nbt.getCompound("recipes_used");
		for (String string : nbtCompound.getKeys()) {
			this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
		}
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		Inventories.writeNbt(nbt, this.inventory, registryLookup);
		NbtCompound nbtCompound = new NbtCompound();
		this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), count));
		nbt.put("recipes_used", nbtCompound);
	}

	public boolean isBurning() {
		return this.litTimeRemaining > 0;
	}

	public static void tick(World world, BlockPos pos, BlockState state, AbstractMetallurgyBlockEntity entity) {
		boolean isBurning = entity.isBurning();
		boolean dirty = false;
		if (isBurning) --entity.litTimeRemaining;

		ItemStack ingredientStack = entity.inventory.get(0);
		ItemStack additionStack = entity.inventory.get(1);
		ItemStack fuelStack = entity.inventory.get(2);

		boolean materialsExist = !ingredientStack.isEmpty() || !additionStack.isEmpty();
		boolean fuelExists = !fuelStack.isEmpty();

		if (entity.isBurning() || fuelExists && materialsExist) {
			RecipeEntry<MetallurgyRecipe> recipeEntry = null;
			if (materialsExist) {
				MetallurgyRecipeInput input = new MetallurgyRecipeInput(ingredientStack, additionStack);
				recipeEntry = entity.matchGetter.getFirstMatch(input, world).orElse(null);
			}

			boolean canProcess = canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, entity.inventory, entity.getMaxCountPerStack());
			if (!entity.isBurning() && canProcess) {
				entity.litTimeRemaining = fuelStack.getFuelTime();
				entity.litTotalTime = entity.litTimeRemaining;

				if (entity.isBurning()) {
					dirty = true;
					if (fuelExists) {
						fuelStack.decrement(1);
						if (fuelStack.isEmpty()) {
							Item remainder = fuelStack.getItem().getRecipeRemainder();
							entity.inventory.set(2, remainder == null ? ItemStack.EMPTY : new ItemStack(remainder));
						}
					}
				}
			}

			if (entity.isBurning() && canProcess) {
				++entity.cookingTimeSpent;
				if (entity.cookingTimeSpent >= entity.cookingTotalTime) {
					entity.cookingTimeSpent = 0;
					entity.cookingTotalTime = getCookTime(world, entity);
					if (craftRecipe(world.getRegistryManager(), recipeEntry, entity.inventory, entity.getMaxCountPerStack())) {
						entity.setLastRecipe(recipeEntry);
					}
					dirty = true;
				}
			} else {
				entity.cookingTimeSpent = 0;
			}
		} else if (!entity.isBurning() && entity.cookingTimeSpent > 0) {
			entity.cookingTimeSpent = MathHelper.clamp(entity.cookingTimeSpent - 2, 0, entity.cookingTotalTime);
		}

		if (isBurning != entity.isBurning()) {
			dirty = true;
			state = state.with(AbstractFurnaceBlock.LIT, entity.isBurning());
			world.setBlockState(pos, state);
		}

		if (dirty) {
			markDirty(world, pos, state);
		}
	}

	public static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
		if (recipe == null) return false;

		ItemStack result = recipe.value().getResult(registryManager);
		if (result.isEmpty()) return false;

		ItemStack output = slots.get(3);
		if (output.isEmpty()) return true;
		if (!ItemStack.areItemsAndComponentsEqual(output, result)) return false;
		if (output.getCount() < count && output.getCount() < output.getMaxCount()) return true;
		return output.getCount() < result.getMaxCount();
	}

	public static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<MetallurgyRecipe> recipe, DefaultedList<ItemStack> slots, int count) {
		if (recipe == null) return false;
		if (!canAcceptRecipeOutput(registryManager, recipe, slots, count)) return false;


		ItemStack ingredient = slots.get(0);
		ItemStack addition = slots.get(1);

		ItemStack result = recipe.value().getResult(registryManager);
		ItemStack output = slots.get(3);
		if (output.isEmpty()) {
			slots.set(3, result.copy());
		} else if (ItemStack.areItemsAndComponentsEqual(output, result)) {
			output.increment(result.getCount());
		}

		ingredient.decrement(1);
		addition.decrement(recipe.value().addition().amount());
		return true;
	}

	public static int getCookTime(World world, AbstractMetallurgyBlockEntity furnace) {
		MetallurgyRecipeInput input = new MetallurgyRecipeInput(
			furnace.getStack(0),
			furnace.getStack(1)
		);
		return furnace.matchGetter.getFirstMatch(input, world)
			.map(RecipeEntry::value)
			.map(MetallurgyRecipe::cookingTime)
			.orElse(200);
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		return switch (side) {
			case DOWN -> new int[]{3};
			case UP -> new int[]{0, 1};
			default -> new int[]{2};
		};
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction direction) {
		if (slot == 0 && direction == Direction.UP && MetallurgyRecipe.isValidIngredient(stack)) return true;
		if (slot == 1 && direction == Direction.UP && MetallurgyRecipe.isValidAddition(stack)) return true;
		if (slot == 2 && (direction != Direction.UP && direction != Direction.DOWN) && stack.isFuel()) return true;
		return false;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	@Override
	public int size() {
		return this.inventory.size();
	}

	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}

	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		ItemStack existingStack = this.inventory.get(slot);
		boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(existingStack, stack);
		this.inventory.set(slot, stack);
		stack.capCount(this.getMaxCount(stack));
		if (slot > 0 || bl) return;

		this.cookingTotalTime = getCookTime(this.world, this);
		this.cookingTimeSpent = 0;
		this.markDirty();
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new MetallurgyScreenHandler(syncId, playerInventory);
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return true;
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

	@Override
	public void provideRecipeInputs(RecipeMatcher finder) {
		this.inventory.forEach(finder::addInput);
	}

}
