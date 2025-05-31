package org.solstice.aristotlesComedy.content.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.recipe.MetallurgyRecipe;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;
import org.solstice.aristotlesComedy.content.screen.slot.FuelSlot;
import org.solstice.aristotlesComedy.content.screen.slot.SpecializedSlot;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeTypes;
import org.solstice.aristotlesComedy.registry.AristotlesScreenHandlers;

import java.util.*;

public class MetallurgyScreenHandler extends AbstractRecipeScreenHandler<MetallurgyRecipeInput, MetallurgyRecipe> {

	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;
	protected final World world;

	public MetallurgyScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(4));
	}

	public MetallurgyScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(AristotlesScreenHandlers.METALLURGY, syncId);

		checkSize(inventory, 4);
		checkDataCount(propertyDelegate, 4);
		this.inventory = inventory;
		this.propertyDelegate = propertyDelegate;
		this.world = playerInventory.player.getWorld();

		this.addSlot(new SpecializedSlot(inventory, 0, 45, 17, MetallurgyRecipe::isValidIngredient));
		this.addSlot(new SpecializedSlot(inventory, 1, 67, 17, MetallurgyRecipe::isValidAddition));
		this.addSlot(new FuelSlot(inventory, 2, 56, 53));
		this.addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 3, 116, 35));

		for (int height = 0; height < 3; ++height) {
			for (int width = 0; width < 9; ++width) {
				this.addSlot(new Slot(playerInventory, width + height * 9 + 9, 8 + width * 18, 84 + height * 18));
			}
		}

		for (int width = 0; width < 9; ++width) {
			this.addSlot(new Slot(playerInventory, width, 8 + width * 18, 142));
		}

		this.addProperties(propertyDelegate);
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		if (this.inventory instanceof RecipeInputProvider)
			((RecipeInputProvider)this.inventory).provideRecipeInputs(finder);
	}

	@Override
	public void clearCraftingSlots() {
		this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
		this.getSlot(1).setStackNoCallbacks(ItemStack.EMPTY);
		this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
	}



	@Override
	public boolean matches(RecipeEntry<MetallurgyRecipe> recipe) {
		MetallurgyRecipeInput input = new MetallurgyRecipeInput(
			this.inventory.getStack(0),
			this.inventory.getStack(1)
		);
		return recipe.value().matches(input, this.world);
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
		Slot slot = this.slots.get(index);
		if (!slot.hasStack()) return ItemStack.EMPTY;
		ItemStack stack = slot.getStack();
		ItemStack copy = stack.copy();

		if (index == 3) {
			if (!this.insertItem(stack, 4, 40, true)) return ItemStack.EMPTY;
			slot.onQuickTransfer(stack, copy);
		} else if (index != 0 && index != 1 && index != 2) {
			if (MetallurgyRecipe.isValidIngredient(stack)) {
				if (!this.insertItem(stack, 0, 1, false)) return ItemStack.EMPTY;
			}
			if (MetallurgyRecipe.isValidAddition(stack)) {
				if (!this.insertItem(stack, 1, 2, false)) return ItemStack.EMPTY;
			}
			if (stack.isFuel()) {
				if (!this.insertItem(stack, 2, 3, false)) return ItemStack.EMPTY;
			}
			if (index >= 4 && index < 31) {
				if (!this.insertItem(stack, 31, 40, false)) return ItemStack.EMPTY;
			} else if (index >= 31 && index < 40 && !this.insertItem(stack, 4, 31, false)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.insertItem(stack, 4, 40, false)) {
			return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) {
			slot.setStack(ItemStack.EMPTY);
		} else {
			slot.markDirty();
		}

		if (stack.getCount() == copy.getCount()) {
			return ItemStack.EMPTY;
		}

		slot.onTakeItem(player, stack);
		return stack;
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
//		ItemStack stack = index.getStack();
//		stack = index.getStack().copy();
//
//		// output index
//		if (slotI == 3) {
//			if (!this.insertItem(stack, 3, 39, true)) {
//				return ItemStack.EMPTY;
//			}
//			index.onQuickTransfer(stack, stack);
//			return stack;
//		}
//		// no clue
//		if (slotI != 1 && slotI != 0) {
//			if (this.isSmeltable(stack)) {
//				if (!this.insertItem(stack, 0, 1, false)) {
//					return ItemStack.EMPTY;
//				}
//				if (!this.insertItem(stack, 1, 2, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (this.isFuel(stack)) {
//				if (!this.insertItem(stack, 2, 3, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (slotI >= 3 && slotI < 30) {
//				if (!this.insertItem(stack, 30, 39, false)) {
//					return ItemStack.EMPTY;
//				}
//			} else if (slotI >= 30 && slotI < 39 && !this.insertItem(stack, 3, 30, false)) {
//				return ItemStack.EMPTY;
//			}
//		} else if (!this.insertItem(stack, 3, 39, false)) {
//			return ItemStack.EMPTY;
//		}
//
//		if (stack.isEmpty()) {
//			index.setStack(ItemStack.EMPTY);
//		} else {
//			index.markDirty();
//		}
//
//		if (stack.getCount() == stack.getCount()) {
//			return ItemStack.EMPTY;
//		}
//
//		index.onTakeItem(player, stack);
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
