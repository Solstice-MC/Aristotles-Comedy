package org.solstice.aristotlesComedy.content.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.recipe.MetallurgyRecipe;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;
import org.solstice.aristotlesComedy.content.screen.slot.FuelSlot;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeTypes;
import org.solstice.aristotlesComedy.registry.AristotlesScreenHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SabikaScreenHandler extends AbstractRecipeScreenHandler<MetallurgyRecipeInput, MetallurgyRecipe> {

	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;
	protected final World world;
	private final List<RecipeEntry<MetallurgyRecipe>> recipes;
	private final List<ItemStack> validIngredients;

	public SabikaScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
	}

	protected SabikaScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(AristotlesScreenHandlers.SABIKA, syncId);
		this.recipes = playerInventory.player.getWorld().getRecipeManager().listAllOfType(AristotlesRecipeTypes.METALLURGY);
		this.validIngredients = new ArrayList<>();
		this.recipes.forEach(entry -> {
			MetallurgyRecipe recipe = entry.value();
			Arrays.stream(recipe.addition().getMatchingStacks()).forEach(stack -> {
				if (!this.validIngredients.contains(stack)) this.validIngredients.add(stack);
			});
			Arrays.stream(recipe.base().getMatchingStacks()).forEach(stack -> {
				if (!this.validIngredients.contains(stack)) this.validIngredients.add(stack);
			});
		});
		checkSize(inventory, 3);
		checkDataCount(propertyDelegate, 4);
		this.inventory = inventory;
		this.propertyDelegate = propertyDelegate;
		this.world = playerInventory.player.getWorld();

		this.addSlot(new Slot(inventory, 0, 47, 17));
		this.addSlot(new Slot(inventory, 1, 65, 17));
		this.addSlot(new FuelSlot(inventory, 1, 56, 53));
		this.addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 2, 116, 35));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

		this.addProperties(propertyDelegate);
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		if (this.inventory instanceof RecipeInputProvider) {
			((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
		}

	}

	@Override
	public void clearCraftingSlots() {
		this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
		this.getSlot(1).setStackNoCallbacks(ItemStack.EMPTY);
		this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
	}

	@Override
	public boolean matches(RecipeEntry<MetallurgyRecipe> recipe) {
		MetallurgyRecipeInput testInput = new MetallurgyRecipeInput(
			this.inventory.getStack(0),
			this.inventory.getStack(1)
		);
		return recipe.value().matches(testInput, this.world);
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 3;
	}

	@Override
	public int getCraftingWidth() {
		return 2;
	}

	@Override
	public int getCraftingHeight() {
		return 1;
	}

	@Override
	public int getCraftingSlotCount() {
		return 4;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasStack()) return stack;

		ItemStack slotStack = slot.getStack();
		stack = slotStack.copy();
		if (index == 2) {
			if (!this.insertItem(slotStack, 3, 39, true)) {
				return ItemStack.EMPTY;
			}

			slot.onQuickTransfer(slotStack, stack);
		} else if (index != 1 && index != 0) {
			if (this.isSmeltable(slotStack)) {
				if (!this.insertItem(slotStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotStack.isFuel()) {
				if (!this.insertItem(slotStack, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 3 && index < 30) {
				if (!this.insertItem(slotStack, 30, 39, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 30 && index < 39 && !this.insertItem(slotStack, 3, 30, false)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.insertItem(slotStack, 3, 39, false)) {
			return ItemStack.EMPTY;
		}

		if (slotStack.isEmpty()) {
			slot.setStack(ItemStack.EMPTY);
		} else {
			slot.markDirty();
		}

		if (slotStack.getCount() == stack.getCount()) {
			return ItemStack.EMPTY;
		}

		slot.onTakeItem(player, slotStack);
		return stack;
	}

	protected boolean isSmeltable(ItemStack stack) {
		return this.validIngredients.contains(stack);
	}

	public float getCookProgress() {
		int i = this.propertyDelegate.get(2);
		int j = this.propertyDelegate.get(3);
		return j != 0 && i != 0 ? MathHelper.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
	}

	public float getFuelProgress() {
		int i = this.propertyDelegate.get(1);
		if (i == 0) {
			i = 200;
		}

		return MathHelper.clamp((float)this.propertyDelegate.get(0) / (float)i, 0.0F, 1.0F);
	}

	public boolean isBurning() {
		return this.propertyDelegate.get(0) > 0;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != 1;
	}

//	public SabikaScreenHandler(int syncId, PlayerInventory playerInventory) {
//		super(AristotlesScreenHandlers.SABIKA, AristotlesRecipeTypes.METALLURGY, RecipeBookCategory.BLAST_FURNACE, syncId, playerInventory);
//	}
//
//	public SabikaScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
//		super(AristotlesScreenHandlers.SABIKA, AristotlesRecipeTypes.METALLURGY, RecipeBookCategory.BLAST_FURNACE, syncId, playerInventory, inventory, propertyDelegate);
//	}
//
//	@Override
//	public void addSlots(PlayerInventory playerInventory, Inventory inventory) {
//		this.addSlot(new Slot(inventory, 0, 47, 17));
//		this.addSlot(new Slot(inventory, 1, 65, 17));
//		this.addSlot(new FurnaceFuelSlot(this, inventory, 2, 56, 53));
//		this.addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 3, 116, 35));
//	}
//
//	@Override
//	public void clearCraftingSlots() {
//		this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
//		this.getSlot(1).setStackNoCallbacks(ItemStack.EMPTY);
//		this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
//	}
//
//	@Override
//	public int getCraftingSlotCount() {
//		return 4;
//	}
//
//	@Override
//	public int getCraftingResultSlotIndex() {
//		return 3;
//	}
//
//	@Override
//	public int getCraftingWidth() {
//		return 2;
//	}


//	@Override
//	public void clearCraftingSlots() {
//		this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
//		this.getSlot(1).setStackNoCallbacks(ItemStack.EMPTY);
//		this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
//	}

//	@Override
//	public int getCraftingWidth() {
//		return 2;
//	}

//	@Override
//	public int getCraftingResultSlotIndex() {
//		return 3;
//	}

//	@Override
//	public int getCraftingSlotCount() {
//		return 4;
//	}

//	public ItemStack quickMove(PlayerEntity player, int slotI) {
//		ItemStack stack = ItemStack.EMPTY;
//		Slot index = this.slots.get(slotI);
//		if (!index.hasStack()) return stack;
//
//		ItemStack slotStack = index.getStack();
//		stack = index.getStack().copy();
//
//		// output index
//		if (slotI == 3) {
//			if (!this.insertItem(slotStack, 3, 39, true)) {
//				return ItemStack.EMPTY;
//			}
//			index.onQuickTransfer(slotStack, stack);
//			return stack;
//		}
//		// no clue
//		if (slotI != 1 && slotI != 0) {
//			if (this.isSmeltable(slotStack)) {
//				if (!this.insertItem(slotStack, 0, 1, false)) {
//					return ItemStack.EMPTY;
//				}
//				if (!this.insertItem(slotStack, 1, 2, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (this.isFuel(slotStack)) {
//				if (!this.insertItem(slotStack, 2, 3, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (slotI >= 3 && slotI < 30) {
//				if (!this.insertItem(slotStack, 30, 39, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (slotI >= 30 && slotI < 39 && !this.insertItem(slotStack, 3, 30, false)) {
//				return ItemStack.EMPTY;
//			}
//		} else if (!this.insertItem(slotStack, 3, 39, false)) {
//			return ItemStack.EMPTY;
//		}
//
//		if (slotStack.isEmpty()) {
//			index.setStack(ItemStack.EMPTY);
//		} else {
//			index.markDirty();
//		}
//
//		if (slotStack.getCount() == stack.getCount()) {
//			return ItemStack.EMPTY;
//		}
//
//		index.onTakeItem(player, slotStack);
//		return stack;
//
//	}

//	@Override
//	protected boolean isSmeltable(ItemStack stack) {
//		return AlloyForgeRecipe.isValidIngredient(stack);
//	}
//
//	@Override
//	protected boolean isFuel(ItemStack stack) {
//		return stack.contains(JoltedComponents.ALLOY_FORGE_FUEL);
//	}

}
