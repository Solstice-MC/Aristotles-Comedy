package org.solstice.aristotlesComedy.api.easyScreen.slot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class EasySlot {

//	protected final Inventory inventory;
	protected ItemStack stack = ItemStack.EMPTY;

//	public EasySlot(Inventory inventory) {
//		this.inventory = inventory;
//	}

	public void onQuickTransfer(ItemStack newItem, ItemStack original) {
		int i = original.getCount() - newItem.getCount();
		if (i > 0) {
			this.onCrafted(original, i);
		}
	}

	public boolean canTakeItems(PlayerEntity playerEntity) {
		return true;
	}

	public boolean canInsert(ItemStack stack) {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean canBeHighlighted() {
		return true;
	}

	public boolean disablesDynamicDisplay() {
		return false;
	}

	public void onCrafted(ItemStack stack, int amount) {}

	public void onTake(int amount) {}

	public void onCrafted(ItemStack stack) {}

	public void onTakeItem(PlayerEntity player, ItemStack stack) {}

	@Nullable
	public Pair<Identifier, Identifier> getBackgroundSprite() {
		return null;
	}


	public boolean hasStack() {
		return !this.getStack().isEmpty();
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public ItemStack takeStack(int amount) {
		int max = this.stack.getCount();
		int taken = Math.min(amount, max);
		ItemStack result = this.stack.copyWithCount(taken);
		this.stack.decrement(taken);
		return result;
	}

	public Optional<ItemStack> tryTakeStackRange(int min, int max, PlayerEntity player) {
		if (!this.canTakeItems(player)) return Optional.empty();
		if (!this.canTakePartial(player) && max < this.getStack().getCount()) return Optional.empty();

		min = Math.min(min, max);
		ItemStack stack = this.takeStack(min);
		if (stack.isEmpty()) return Optional.empty();

		if (this.getStack().isEmpty()) this.setStack(ItemStack.EMPTY);

		return Optional.of(stack);
	}

	public ItemStack takeStackRange(int min, int max, PlayerEntity player) {
		Optional<ItemStack> result = this.tryTakeStackRange(min, max, player);
		result.ifPresent(stack -> this.onTakeItem(player, stack));
		return result.orElse(ItemStack.EMPTY);
	}

	public ItemStack insertStack(ItemStack inputStack) {
		return this.insertStack(inputStack, inputStack.getCount());
	}

	public ItemStack insertStack(ItemStack inputStack, int count) {
		if (!inputStack.isEmpty() && this.canInsert(inputStack)) {
			ItemStack currentStack = this.getStack();
			int test = Math.min(this.stack.getMaxCount(), stack.getMaxCount());
			int i = Math.min(Math.min(test, inputStack.getCount()), count - currentStack.getCount());
			if (currentStack.isEmpty()) {
				this.setStack(inputStack.split(i));
			} else if (ItemStack.areItemsAndComponentsEqual(currentStack, inputStack)) {
				inputStack.decrement(i);
				currentStack.increment(i);
				this.setStack(currentStack);
			}
		}
		return inputStack;
	}

	public boolean canTakePartial(PlayerEntity player) {
		return this.canTakeItems(player) && this.canInsert(this.getStack());
	}

}
