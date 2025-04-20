package org.solstice.aristotlesComedy.api.easyScreen.test;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.api.easyScreen.handler.EasyScreenHandler;

import java.util.Optional;

public class EasyCraftingScreenHandler extends EasyScreenHandler {

//	public static final int RESULT_ID = 0;
//	private static final int INPUT_START = 1;
//	private static final int INPUT_END = 10;
//	private static final int INVENTORY_START = 10;
//	private static final int INVENTORY_END = 37;
//	private static final int HOTBAR_START = 37;
//	private static final int HOTBAR_END = 46;

	private final RecipeInputInventory input;
	private final CraftingResultInventory result;
//	private final ScreenHandlerContext context;
//	private final PlayerEntity player;
//	private boolean filling;

	public EasyCraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public EasyCraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ScreenHandlerType.CRAFTING);
		this.input = new EasyCraftingInventory(this, 3, 3);
		this.result = new CraftingResultInventory();
//		this.context = context;
//		this.player = playerInventory.player;
		this.addSlot(
			Identifier.of("crafting_result"),
			new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35)
		);

//		for(int i = 0; i < 3; ++i) {
//			for(int j = 0; j < 3; ++j) {
//				this.addSlot(new Slot(this.input, j + i * 3, 30 + j * 18, 17 + i * 18));
//			}
//		}

//		for(int i = 0; i < 3; ++i) {
//			for(int j = 0; j < 9; ++j) {
//				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
//			}
//		}

//		for(int i = 0; i < 9; ++i) {
//			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
//		}
	}

	protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, @Nullable RecipeEntry<CraftingRecipe> recipe) {
		if (!world.isClient) {
			CraftingRecipeInput craftingRecipeInput = craftingInventory.createRecipeInput();
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<RecipeEntry<CraftingRecipe>> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, world, recipe);
			if (optional.isPresent()) {
				RecipeEntry<CraftingRecipe> recipeEntry = (RecipeEntry)optional.get();
				CraftingRecipe craftingRecipe = (CraftingRecipe)recipeEntry.value();
				if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipeEntry)) {
					ItemStack itemStack2 = craftingRecipe.craft(craftingRecipeInput, world.getRegistryManager());
					if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
						itemStack = itemStack2;
					}
				}
			}

			resultInventory.setStack(0, itemStack);
			handler.setPreviousTrackedSlot(0, itemStack);
			serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
		}
	}

	public void onContentChanged(Inventory inventory) {
//		if (!this.filling) {
//			this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result, (RecipeEntry)null));
//		}

	}

	public void onInputSlotFillStart() {
//		this.filling = true;
	}

	public void onInputSlotFillFinish(RecipeEntry<CraftingRecipe> recipe) {
//		this.filling = false;
//		this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result, recipe));
	}

	public void populateRecipeFinder(RecipeMatcher finder) {
		this.input.provideRecipeInputs(finder);
	}

	public void clearCraftingSlots() {
		this.input.clear();
		this.result.clear();
	}

	public boolean matches(RecipeEntry<CraftingRecipe> recipe) {
		return false;
//		return ((CraftingRecipe)recipe.value()).matches(this.input.createRecipeInput(), this.player.getWorld());
	}

	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
//		this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}

	public boolean canUse(PlayerEntity player) {
		return false;
//		return canUse(this.context, player, Blocks.CRAFTING_TABLE);
	}

	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
//		Slot slot2 = (Slot)this.slots.get(slot);
//		if (slot2 != null && slot2.hasStack()) {
//			ItemStack itemStack2 = slot2.getStack();
//			itemStack = itemStack2.copy();
//			if (slot == 0) {
//				this.context.run((world, pos) -> itemStack2.getItem().onCraftByPlayer(itemStack2, world, player));
//				if (!this.insertItem(itemStack2, 10, 46, true)) {
//					return ItemStack.EMPTY;
//				}
//
//				slot2.onQuickTransfer(itemStack2, itemStack);
//			} else if (slot >= 10 && slot < 46) {
//				if (!this.insertItem(itemStack2, 1, 10, false)) {
//					if (slot < 37) {
//						if (!this.insertItem(itemStack2, 37, 46, false)) {
//							return ItemStack.EMPTY;
//						}
//					} else if (!this.insertItem(itemStack2, 10, 37, false)) {
//						return ItemStack.EMPTY;
//					}
//				}
//			} else if (!this.insertItem(itemStack2, 10, 46, false)) {
//				return ItemStack.EMPTY;
//			}
//
//			if (itemStack2.isEmpty()) {
//				slot2.setStack(ItemStack.EMPTY);
//			} else {
//				slot2.markDirty();
//			}
//
//			if (itemStack2.getCount() == itemStack.getCount()) {
//				return ItemStack.EMPTY;
//			}
//
//			slot2.onTakeItem(player, itemStack2);
//			if (slot == 0) {
//				player.dropItem(itemStack2, false);
//			}
//		}

		return itemStack;
	}

	@Override
	public int getExpectedSize() {
		return 0;
	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
	}

	public int getCraftingResultSlotIndex() {
		return 0;
	}

	public int getCraftingWidth() {
		return this.input.getWidth();
	}

	public int getCraftingHeight() {
		return this.input.getHeight();
	}

	public int getCraftingSlotCount() {
		return 10;
	}

	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	public boolean canInsertIntoSlot(int index) {
		return index != this.getCraftingResultSlotIndex();
	}

}
