package org.solstice.aristotlesComedy.api.easyScreen.test;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import org.solstice.aristotlesComedy.api.easyScreen.handler.EasyScreenHandler;

import java.util.List;

public class EasyCraftingInventory implements RecipeInputInventory {

	private final DefaultedList<ItemStack> stacks;
	private final int width;
	private final int height;
	private final EasyScreenHandler handler;

	public EasyCraftingInventory(EasyScreenHandler handler, int width, int height) {
		this(handler, width, height, DefaultedList.ofSize(width * height, ItemStack.EMPTY));
	}

	public EasyCraftingInventory(EasyScreenHandler handler, int width, int height, DefaultedList<ItemStack> stacks) {
		this.stacks = stacks;
		this.handler = handler;
		this.width = width;
		this.height = height;
	}

	public int size() {
		return this.stacks.size();
	}

	public boolean isEmpty() {
		for(ItemStack itemStack : this.stacks) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public ItemStack getStack(int slot) {
		return slot >= this.size() ? ItemStack.EMPTY : (ItemStack)this.stacks.get(slot);
	}

	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.stacks, slot);
	}

	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
		if (!itemStack.isEmpty()) {
			this.handler.onContentChanged(this);
		}

		return itemStack;
	}

	public void setStack(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		this.handler.onContentChanged(this);
	}

	public void markDirty() {
	}

	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	public void clear() {
		this.stacks.clear();
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public List<ItemStack> getHeldStacks() {
		return List.copyOf(this.stacks);
	}

	public void provideRecipeInputs(RecipeMatcher finder) {
		for(ItemStack itemStack : this.stacks) {
			finder.addUnenchantedInput(itemStack);
		}

	}

}
