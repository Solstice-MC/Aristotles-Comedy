package org.solstice.aristotlesComedy.api.easyScreen.handler;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class EasyScreenHandler {

//	private static final Logger LOGGER = LogUtils.getLogger();
//	public static final int EMPTY_SPACE_SLOT_INDEX = -999;
//	public static final int field_30731 = 0;
//	public static final int field_30732 = 1;
//	public static final int field_30733 = 2;
//	public static final int field_30734 = 0;
//	public static final int field_30735 = 1;
//	public static final int field_30736 = 2;
//	public static final int field_30737 = Integer.MAX_VALUE;

	private final Map<Identifier, Slot> slots;
	private ItemStack cursorStack = ItemStack.EMPTY;

	private final DefaultedList<ItemStack> trackedStacks = DefaultedList.of();
	public final DefaultedList<Slot> oldSlots = DefaultedList.of();
	private final List<Property> properties = Lists.newArrayList();
	private final DefaultedList<ItemStack> previousTrackedStacks;
	private final IntList trackedPropertyValues;
	private ItemStack previousCursorStack;
	private int revision;
	@Nullable
	private final ScreenHandlerType<?> type;
	private int quickCraftButton;
	private int quickCraftStage;
	private final Set<Slot> quickCraftSlots;
	private final List<ScreenHandlerListener> listeners;
	@Nullable
	private ScreenHandlerSyncHandler syncHandler;
	private boolean disableSync;

	public abstract int getExpectedSize();

	public EasyScreenHandler(@Nullable ScreenHandlerType<?> type) {
		this.slots = new HashMap<>();

		this.previousTrackedStacks = DefaultedList.of();
		this.trackedPropertyValues = new IntArrayList();
		this.quickCraftButton = -1;
		this.quickCraftSlots = Sets.newHashSet();
		this.listeners = Lists.newArrayList();
		this.type = type;

//		if (inventory.size() < this.getExpectedSize()) {
//			throw new IllegalArgumentException("Container is smaller than expected " + this.getExpectedSize());
//		}
	}

	protected static boolean canUse(ScreenHandlerContext context, PlayerEntity player, Block block) {
		return context.get((world, pos) ->
			world.getBlockState(pos).isOf(block) && player.canInteractWithBlockAt(pos, 4.0F),
			true
		);
	}

	public ScreenHandlerType<?> getType() {
		if (this.type != null) return this.type;
		throw new UnsupportedOperationException("Unable to construct this menu by type");
	}

	public boolean isValid(int slot) {
		return slot == -1 || slot == -999 || slot < this.slots.size();
	}

	protected Slot addSlot(Identifier id, Slot slot) {
//		this.slots.
//		slot.id = this.oldSlots.size();
//		this.oldSlots.add(slot);
//		this.trackedStacks.add(ItemStack.EMPTY);
//		this.previousTrackedStacks.add(ItemStack.EMPTY);
		return slot;
	}

//	protected Property addProperty(Property property) {
//		this.properties.add(property);
//		this.trackedPropertyValues.add(0);
//		return property;
//	}

//	protected void addProperties(PropertyDelegate propertyDelegate) {
//		for(int i = 0; i < propertyDelegate.size(); ++i) {
//			this.addProperty(Property.create(propertyDelegate, i));
//		}
//
//	}

//	public void addListener(ScreenHandlerListener listener) {
//		if (!this.listeners.contains(listener)) {
//			this.listeners.add(listener);
//			this.sendContentUpdates();
//		}
//	}

//	public void updateSyncHandler(ScreenHandlerSyncHandler handler) {
//		this.syncHandler = handler;
//		this.syncState();
//	}

//	public void syncState() {
//		int i = 0;
//
//		for(int j = this.oldSlots.size(); i < j; ++i) {
//			this.previousTrackedStacks.set(i, ((Slot)this.oldSlots.get(i)).getStack().copy());
//		}
//
//		this.previousCursorStack = this.getCursorStack().copy();
//		i = 0;
//
//		for(int j = this.properties.size(); i < j; ++i) {
//			this.trackedPropertyValues.set(i, ((Property)this.properties.get(i)).get());
//		}
//
//		if (this.syncHandler != null) {
//			this.syncHandler.updateState(this, this.previousTrackedStacks, this.previousCursorStack, this.trackedPropertyValues.toIntArray());
//		}
//
//	}

//	public void removeListener(ScreenHandlerListener listener) {
//		this.listeners.remove(listener);
//	}

	public DefaultedList<ItemStack> getStacks() {
		DefaultedList<ItemStack> result = DefaultedList.of();

		this.slots.values()
			.stream()
			.map(Slot::getStack)
			.forEach(result::add);

		return result;
	}

//	public void sendContentUpdates() {
//		for(int i = 0; i < this.oldSlots.size(); ++i) {
//			ItemStack itemStack = ((Slot)this.oldSlots.get(i)).getStack();
//			Objects.requireNonNull(itemStack);
//			Supplier<ItemStack> supplier = Suppliers.memoize(itemStack::copy);
//			this.updateTrackedSlot(i, itemStack, supplier);
//			this.checkSlotUpdates(i, itemStack, supplier);
//		}
//
//		this.checkCursorStackUpdates();
//
//		for(int i = 0; i < this.properties.size(); ++i) {
//			Property property = (Property)this.properties.get(i);
//			int j = property.get();
//			if (property.hasChanged()) {
//				this.notifyPropertyUpdate(i, j);
//			}
//
//			this.checkPropertyUpdates(i, j);
//		}
//
//	}

//	public void updateToClient() {
//		for(int i = 0; i < this.oldSlots.size(); ++i) {
//			ItemStack itemStack = ((Slot)this.oldSlots.get(i)).getStack();
//			Objects.requireNonNull(itemStack);
//			this.updateTrackedSlot(i, itemStack, itemStack::copy);
//		}
//
//		for(int i = 0; i < this.properties.size(); ++i) {
//			Property property = (Property)this.properties.get(i);
//			if (property.hasChanged()) {
//				this.notifyPropertyUpdate(i, property.get());
//			}
//		}
//
//		this.syncState();
//	}

//	private void notifyPropertyUpdate(int index, int value) {
//		for(ScreenHandlerListener screenHandlerListener : this.listeners) {
//			screenHandlerListener.onPropertyUpdate(this, index, value);
//		}
//
//	}

//	private void updateTrackedSlot(int slot, ItemStack stack, Supplier<ItemStack> copySupplier) {
//		ItemStack itemStack = (ItemStack)this.trackedStacks.get(slot);
//		if (!ItemStack.areEqual(itemStack, stack)) {
//			ItemStack itemStack2 = (ItemStack)copySupplier.get();
//			this.trackedStacks.set(slot, itemStack2);
//
//			for(ScreenHandlerListener screenHandlerListener : this.listeners) {
//				screenHandlerListener.onSlotUpdate(this, slot, itemStack2);
//			}
//		}
//
//	}

//	private void checkSlotUpdates(int slot, ItemStack stack, Supplier<ItemStack> copySupplier) {
//		if (!this.disableSync) {
//			ItemStack itemStack = (ItemStack)this.previousTrackedStacks.get(slot);
//			if (!ItemStack.areEqual(itemStack, stack)) {
//				ItemStack itemStack2 = (ItemStack)copySupplier.get();
//				this.previousTrackedStacks.set(slot, itemStack2);
//				if (this.syncHandler != null) {
//					this.syncHandler.updateSlot(this, slot, itemStack2);
//				}
//			}
//
//		}
//	}

//	private void checkPropertyUpdates(int id, int value) {
//		if (!this.disableSync) {
//			int i = this.trackedPropertyValues.getInt(id);
//			if (i != value) {
//				this.trackedPropertyValues.set(id, value);
//				if (this.syncHandler != null) {
//					this.syncHandler.updateProperty(this, id, value);
//				}
//			}
//
//		}
//	}

//	private void checkCursorStackUpdates() {
//		if (!this.disableSync) {
//			if (!ItemStack.areEqual(this.getCursorStack(), this.previousCursorStack)) {
//				this.previousCursorStack = this.getCursorStack().copy();
//				if (this.syncHandler != null) {
//					this.syncHandler.updateCursorStack(this, this.previousCursorStack);
//				}
//			}
//
//		}
//	}

//	public void setPreviousTrackedSlot(int slot, ItemStack stack) {
//		this.previousTrackedStacks.set(slot, stack.copy());
//	}

//	public void setPreviousTrackedSlotMutable(int slot, ItemStack stack) {
//		if (slot >= 0 && slot < this.previousTrackedStacks.size()) {
//			this.previousTrackedStacks.set(slot, stack);
//		} else {
//			LOGGER.debug("Incorrect slot index: {} available slots: {}", slot, this.previousTrackedStacks.size());
//		}
//	}

//	public void setPreviousCursorStack(ItemStack stack) {
//		this.previousCursorStack = stack.copy();
//	}

//	public boolean onButtonClick(PlayerEntity player, int id) {
//		return false;
//	}

	public Slot getSlot(int index) {
		return this.slots.entrySet().stream().toList().get(index).getValue();
	}

	public Slot getSlot(Identifier id) {
		return this.slots.get(id);
	}

//	public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
//		try {
//			this.internalOnSlotClick(slotIndex, button, actionType, player);
//		} catch (Exception exception) {
//			CrashReport crashReport = CrashReport.create(exception, "Container click");
//			CrashReportSection crashReportSection = crashReport.addElement("Click info");
//			crashReportSection.add("Menu Type", () -> this.type != null ? Registries.SCREEN_HANDLER.getId(this.type).toString() : "<no type>");
//			crashReportSection.add("Menu Class", () -> this.getClass().getCanonicalName());
//			crashReportSection.add("Slot Count", this.oldSlots.size());
//			crashReportSection.add("Slot", slotIndex);
//			crashReportSection.add("Button", button);
//			crashReportSection.add("Type", actionType);
//			throw new CrashException(crashReport);
//		}
//	}

	private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
		PlayerInventory playerInventory = player.getInventory();
		if (actionType == SlotActionType.QUICK_CRAFT) {
			int i = this.quickCraftStage;
			this.quickCraftStage = unpackQuickCraftStage(button);
			if ((i != 1 || this.quickCraftStage != 2) && i != this.quickCraftStage) {
				this.endQuickCraft();
			} else if (this.getCursorStack().isEmpty()) {
				this.endQuickCraft();
			} else if (this.quickCraftStage == 0) {
				this.quickCraftButton = unpackQuickCraftButton(button);
				if (shouldQuickCraftContinue(this.quickCraftButton, player)) {
					this.quickCraftStage = 1;
					this.quickCraftSlots.clear();
				} else {
					this.endQuickCraft();
				}
			} else if (this.quickCraftStage == 1) {
				Slot slot = (Slot)this.oldSlots.get(slotIndex);
				ItemStack itemStack = this.getCursorStack();
//				if (canInsertItemIntoSlot(slot, itemStack, true) && slot.canInsert(itemStack) && (this.quickCraftButton == 2 || itemStack.getCount() > this.quickCraftSlots.size()) && this.canInsertIntoSlot(slot)) {
//					this.quickCraftSlots.add(slot);
//				}
			} else if (this.quickCraftStage == 2) {
				if (!this.quickCraftSlots.isEmpty()) {
					if (this.quickCraftSlots.size() == 1) {
						int j = ((Slot)this.quickCraftSlots.iterator().next()).id;
						this.endQuickCraft();
						this.internalOnSlotClick(j, this.quickCraftButton, SlotActionType.PICKUP, player);
						return;
					}

					ItemStack itemStack2 = this.getCursorStack().copy();
					if (itemStack2.isEmpty()) {
						this.endQuickCraft();
						return;
					}

					int k = this.getCursorStack().getCount();

					for(Slot slot2 : this.quickCraftSlots) {
						ItemStack itemStack3 = this.getCursorStack();
//						if (slot2 != null && canInsertItemIntoSlot(slot2, itemStack3, true) && slot2.canInsert(itemStack3) && (this.quickCraftButton == 2 || itemStack3.getCount() >= this.quickCraftSlots.size()) && this.canInsertIntoSlot(slot2)) {
//							int l = slot2.hasStack() ? slot2.getStack().getCount() : 0;
//							int m = Math.min(itemStack2.getMaxCount(), slot2.getMaxItemCount(itemStack2));
//							int n = Math.min(calculateStackSize(this.quickCraftSlots, this.quickCraftButton, itemStack2) + l, m);
//							k -= n - l;
//							slot2.setStack(itemStack2.copyWithCount(n));
//						}
					}

					itemStack2.setCount(k);
					this.setCursorStack(itemStack2);
				}

				this.endQuickCraft();
			} else {
				this.endQuickCraft();
			}
		} else if (this.quickCraftStage != 0) {
			this.endQuickCraft();
		} else if ((actionType == SlotActionType.PICKUP || actionType == SlotActionType.QUICK_MOVE) && (button == 0 || button == 1)) {
			ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;
			if (slotIndex == -999) {
				if (!this.getCursorStack().isEmpty()) {
					if (clickType == ClickType.LEFT) {
						player.dropItem(this.getCursorStack(), true);
						this.setCursorStack(ItemStack.EMPTY);
					} else {
						player.dropItem(this.getCursorStack().split(1), true);
					}
				}
			} else if (actionType == SlotActionType.QUICK_MOVE) {
				if (slotIndex < 0) {
					return;
				}

				Slot slot = (Slot)this.oldSlots.get(slotIndex);
				if (!slot.canTakeItems(player)) {
					return;
				}

//				for(ItemStack itemStack = this.quickMove(player, slotIndex); !itemStack.isEmpty() && ItemStack.areItemsEqual(slot.getStack(), itemStack); itemStack = this.quickMove(player, slotIndex)) {
//				}
			} else {
				if (slotIndex < 0) {
					return;
				}

				Slot slot = (Slot)this.oldSlots.get(slotIndex);
				ItemStack itemStack = slot.getStack();
				ItemStack itemStack4 = this.getCursorStack();
				player.onPickupSlotClick(itemStack4, slot.getStack(), clickType);
				if (!this.handleSlotClick(player, clickType, slot, itemStack, itemStack4)) {
					if (itemStack.isEmpty()) {
						if (!itemStack4.isEmpty()) {
							int o = clickType == ClickType.LEFT ? itemStack4.getCount() : 1;
							this.setCursorStack(slot.insertStack(itemStack4, o));
						}
					} else if (slot.canTakeItems(player)) {
						if (itemStack4.isEmpty()) {
							int o = clickType == ClickType.LEFT ? itemStack.getCount() : (itemStack.getCount() + 1) / 2;
							Optional<ItemStack> optional = slot.tryTakeStackRange(o, Integer.MAX_VALUE, player);
							optional.ifPresent((stack) -> {
								this.setCursorStack(stack);
								slot.onTakeItem(player, stack);
							});
						} else if (slot.canInsert(itemStack4)) {
							if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack4)) {
								int o = clickType == ClickType.LEFT ? itemStack4.getCount() : 1;
								this.setCursorStack(slot.insertStack(itemStack4, o));
							} else if (itemStack4.getCount() <= slot.getMaxItemCount(itemStack4)) {
								this.setCursorStack(itemStack);
								slot.setStack(itemStack4);
							}
						} else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack4)) {
							Optional<ItemStack> optional2 = slot.tryTakeStackRange(itemStack.getCount(), itemStack4.getMaxCount() - itemStack4.getCount(), player);
							optional2.ifPresent((stack) -> {
								itemStack4.increment(stack.getCount());
								slot.onTakeItem(player, stack);
							});
						}
					}
				}

				slot.markDirty();
			}
		} else if (actionType == SlotActionType.SWAP && (button >= 0 && button < 9 || button == 40)) {
			ItemStack itemStack5 = playerInventory.getStack(button);
			Slot slot = (Slot)this.oldSlots.get(slotIndex);
			ItemStack itemStack = slot.getStack();
			if (!itemStack5.isEmpty() || !itemStack.isEmpty()) {
				if (itemStack5.isEmpty()) {
					if (slot.canTakeItems(player)) {
						playerInventory.setStack(button, itemStack);
//						slot.onTake(itemStack.getCount());
						slot.setStack(ItemStack.EMPTY);
						slot.onTakeItem(player, itemStack);
					}
				} else if (itemStack.isEmpty()) {
					if (slot.canInsert(itemStack5)) {
						int p = slot.getMaxItemCount(itemStack5);
						if (itemStack5.getCount() > p) {
							slot.setStack(itemStack5.split(p));
						} else {
							playerInventory.setStack(button, ItemStack.EMPTY);
							slot.setStack(itemStack5);
						}
					}
				} else if (slot.canTakeItems(player) && slot.canInsert(itemStack5)) {
					int p = slot.getMaxItemCount(itemStack5);
					if (itemStack5.getCount() > p) {
						slot.setStack(itemStack5.split(p));
						slot.onTakeItem(player, itemStack);
						if (!playerInventory.insertStack(itemStack)) {
							player.dropItem(itemStack, true);
						}
					} else {
						playerInventory.setStack(button, itemStack);
						slot.setStack(itemStack5);
						slot.onTakeItem(player, itemStack);
					}
				}
			}
		} else if (actionType == SlotActionType.CLONE && player.isInCreativeMode() && this.getCursorStack().isEmpty() && slotIndex >= 0) {
			Slot slot3 = (Slot)this.oldSlots.get(slotIndex);
			if (slot3.hasStack()) {
				ItemStack itemStack2 = slot3.getStack();
				this.setCursorStack(itemStack2.copyWithCount(itemStack2.getMaxCount()));
			}
		} else if (actionType == SlotActionType.THROW && this.getCursorStack().isEmpty() && slotIndex >= 0) {
			Slot slot3 = (Slot)this.oldSlots.get(slotIndex);
			int j = button == 0 ? 1 : slot3.getStack().getCount();
			ItemStack itemStack = slot3.takeStackRange(j, Integer.MAX_VALUE, player);
			player.dropItem(itemStack, true);
		} else if (actionType == SlotActionType.PICKUP_ALL && slotIndex >= 0) {
			Slot slot3 = (Slot)this.oldSlots.get(slotIndex);
			ItemStack itemStack2 = this.getCursorStack();
			if (!itemStack2.isEmpty() && (!slot3.hasStack() || !slot3.canTakeItems(player))) {
				int k = button == 0 ? 0 : this.oldSlots.size() - 1;
				int p = button == 0 ? 1 : -1;

				for(int o = 0; o < 2; ++o) {
					for(int q = k; q >= 0 && q < this.oldSlots.size() && itemStack2.getCount() < itemStack2.getMaxCount(); q += p) {
						Slot slot4 = (Slot)this.oldSlots.get(q);
						if (slot4.hasStack() && canInsertItemIntoSlot(slot4, itemStack2, true) && slot4.canTakeItems(player) && this.canInsertIntoSlot(itemStack2, slot4)) {
							ItemStack itemStack6 = slot4.getStack();
							if (o != 0 || itemStack6.getCount() != itemStack6.getMaxCount()) {
								ItemStack itemStack7 = slot4.takeStackRange(itemStack6.getCount(), itemStack2.getMaxCount() - itemStack2.getCount(), player);
								itemStack2.increment(itemStack7.getCount());
							}
						}
					}
				}
			}
		}

	}

	private boolean handleSlotClick(PlayerEntity player, ClickType clickType, Slot slot, ItemStack stack, ItemStack cursorStack) {
		FeatureSet featureSet = player.getWorld().getEnabledFeatures();
		if (cursorStack.isItemEnabled(featureSet) && cursorStack.onStackClicked(slot, clickType, player)) {
			return true;
		}
		return false;
//		} else {
//			return stack.isItemEnabled(featureSet) && stack.onClicked(cursorStack, slot, clickType, player, this.getCursorStackReference());
//		}
	}

//	private StackReference getCursorStackReference() {
//		return new StackReference() {
//			public ItemStack get() {
//				return ScreenHandler.this.getCursorStack();
//			}
//
//			public boolean set(ItemStack stack) {
//				ScreenHandler.this.setCursorStack(stack);
//				return true;
//			}
//		};
//	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.canInsert(stack);
	}

	public void onClosed(PlayerEntity player) {
		if (player instanceof ServerPlayerEntity) {
			ItemStack itemStack = this.getCursorStack();
			if (!itemStack.isEmpty()) {
				if (player.isAlive() && !((ServerPlayerEntity)player).isDisconnected()) {
					player.getInventory().offerOrDrop(itemStack);
				} else {
					player.dropItem(itemStack, false);
				}

				this.setCursorStack(ItemStack.EMPTY);
			}
		}

	}

	protected void dropInventory(PlayerEntity player, Inventory inventory) {
		if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
			for(int i = 0; i < inventory.size(); ++i) {
				player.dropItem(inventory.removeStack(i), false);
			}

		} else {
			for(int i = 0; i < inventory.size(); ++i) {
				PlayerInventory playerInventory = player.getInventory();
				if (playerInventory.player instanceof ServerPlayerEntity) {
					playerInventory.offerOrDrop(inventory.removeStack(i));
				}
			}

		}
	}

	public void onContentChanged(Inventory inventory) {
//		this.sendContentUpdates();
	}

	public void setStackInSlot(int slot, int revision, ItemStack stack) {
		this.getSlot(slot).setStackNoCallbacks(stack);
		this.revision = revision;
	}

	public void updateSlotStacks(int revision, List<ItemStack> stacks, ItemStack cursorStack) {
		for(int i = 0; i < stacks.size(); ++i) {
			this.getSlot(i).setStackNoCallbacks((ItemStack)stacks.get(i));
		}

		this.cursorStack = cursorStack;
		this.revision = revision;
	}

	public void setProperty(int id, int value) {
		((Property)this.properties.get(id)).set(value);
	}

	public abstract boolean canUse(PlayerEntity player);

	protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
		boolean bl = false;
		int i = startIndex;
		if (fromLast) {
			i = endIndex - 1;
		}

		if (stack.isStackable()) {
			while(!stack.isEmpty()) {
				if (fromLast) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				Slot slot = (Slot)this.oldSlots.get(i);
				ItemStack itemStack = slot.getStack();
				if (!itemStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(stack, itemStack)) {
					int j = itemStack.getCount() + stack.getCount();
					int k = slot.getMaxItemCount(itemStack);
					if (j <= k) {
						stack.setCount(0);
						itemStack.setCount(j);
						slot.markDirty();
						bl = true;
					} else if (itemStack.getCount() < k) {
						stack.decrement(k - itemStack.getCount());
						itemStack.setCount(k);
						slot.markDirty();
						bl = true;
					}
				}

				if (fromLast) {
					--i;
				} else {
					++i;
				}
			}
		}

		if (!stack.isEmpty()) {
			if (fromLast) {
				i = endIndex - 1;
			} else {
				i = startIndex;
			}

			while(true) {
				if (fromLast) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				Slot slot = (Slot)this.oldSlots.get(i);
				ItemStack itemStack = slot.getStack();
				if (itemStack.isEmpty() && slot.canInsert(stack)) {
					int j = slot.getMaxItemCount(stack);
					slot.setStack(stack.split(Math.min(stack.getCount(), j)));
					slot.markDirty();
					bl = true;
					break;
				}

				if (fromLast) {
					--i;
				} else {
					++i;
				}
			}
		}

		return bl;
	}

	public static int unpackQuickCraftButton(int quickCraftData) {
		return quickCraftData >> 2 & 3;
	}

	public static int unpackQuickCraftStage(int quickCraftData) {
		return quickCraftData & 3;
	}

	public static int packQuickCraftData(int quickCraftStage, int buttonId) {
		return quickCraftStage & 3 | (buttonId & 3) << 2;
	}

	public static boolean shouldQuickCraftContinue(int stage, PlayerEntity player) {
		if (stage == 0) {
			return true;
		} else if (stage == 1) {
			return true;
		} else {
			return stage == 2 && player.isInCreativeMode();
		}
	}

	protected void endQuickCraft() {
		this.quickCraftStage = 0;
		this.quickCraftSlots.clear();
	}

	public static boolean canInsertItemIntoSlot(@Nullable Slot slot, ItemStack stack, boolean allowOverflow) {
		boolean bl = slot == null || !slot.hasStack();
		if (!bl && ItemStack.areItemsAndComponentsEqual(stack, slot.getStack())) {
			return slot.getStack().getCount() + (allowOverflow ? 0 : stack.getCount()) <= stack.getMaxCount();
		} else {
			return bl;
		}
	}

	public static int calculateStackSize(Set<Slot> slots, int mode, ItemStack stack) {
		int var10000;
		switch (mode) {
			case 0 -> var10000 = MathHelper.floor((float)stack.getCount() / (float)slots.size());
			case 1 -> var10000 = 1;
			case 2 -> var10000 = stack.getMaxCount();
			default -> var10000 = stack.getCount();
		}

		return var10000;
	}

//	public boolean canInsertIntoSlot(Slot slot) {
//		return true;
//	}

	public static int calculateComparatorOutput(@Nullable BlockEntity entity) {
		return entity instanceof Inventory ? calculateComparatorOutput((Inventory)entity) : 0;
	}

	public static int calculateComparatorOutput(@Nullable Inventory inventory) {
		if (inventory == null) {
			return 0;
		} else {
			float f = 0.0F;

			for(int i = 0; i < inventory.size(); ++i) {
				ItemStack itemStack = inventory.getStack(i);
				if (!itemStack.isEmpty()) {
					f += (float)itemStack.getCount() / (float)inventory.getMaxCount(itemStack);
				}
			}

			f /= (float)inventory.size();
			return MathHelper.lerpPositive(f, 0, 15);
		}
	}

	public void setCursorStack(ItemStack stack) {
		this.cursorStack = stack;
	}

	public ItemStack getCursorStack() {
		return this.cursorStack;
	}

	public void disableSyncing() {
		this.disableSync = true;
	}

	public void enableSyncing() {
		this.disableSync = false;
	}

	public void copySharedSlots(ScreenHandler handler) {
		Table<Inventory, Integer, Integer> table = HashBasedTable.create();

		for(int i = 0; i < handler.slots.size(); ++i) {
			Slot slot = (Slot)handler.slots.get(i);
			table.put(slot.inventory, slot.getIndex(), i);
		}

//		for(int i = 0; i < this.oldSlots.size(); ++i) {
//			Slot slot = (Slot)this.oldSlots.get(i);
//			Integer integer = (Integer)table.get(slot.inventory, slot.getIndex());
//			if (integer != null) {
//				this.trackedStacks.set(i, (ItemStack)handler.trackedStacks.get(integer));
//				this.previousTrackedStacks.set(i, (ItemStack)handler.previousTrackedStacks.get(integer));
//			}
//		}

	}

	public OptionalInt getSlotIndex(Inventory inventory, int index) {
		for(int i = 0; i < this.oldSlots.size(); ++i) {
			Slot slot = (Slot)this.oldSlots.get(i);
			if (slot.inventory == inventory && index == slot.getIndex()) {
				return OptionalInt.of(i);
			}
		}

		return OptionalInt.empty();
	}

	public int getRevision() {
		return this.revision;
	}

	public int nextRevision() {
		this.revision = this.revision + 1 & 32767;
		return this.revision;
	}

}
