package org.solstice.aristotlesComedy.content.item;

import com.google.common.collect.Iterators;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;
import org.solstice.aristotlesComedy.registry.AristotlesFluids;

import java.util.Iterator;

public class HumorVialItem extends Item {

	public HumorVialItem(Settings settings) {
		super(settings);
	}

	public static class HumorVialStorage implements Storage<FluidVariant>, StorageView<FluidVariant> {

		private final FluidVariant fluid;
		private final long amount;

		public HumorVialStorage(ItemStack stack, ContainerItemContext ignored) {
			this.fluid = stack.getOrDefault(AristotlesComponentTypes.FLUID_VARIANT, FluidVariant.blank());
			this.amount = stack.getOrDefault(AristotlesComponentTypes.FLUID_AMOUNT, 0L);
		}

		@Override
		public long insert(FluidVariant variant, long value, TransactionContext transactionContext) {
			if (!variant.isOf(AristotlesFluids.HUMOR)) return 0;

			StoragePreconditions.notBlankNotNegative(variant, value);
			return value;
		}

		@Override
		public long extract(FluidVariant variant, long value, TransactionContext transactionContext) {
			StoragePreconditions.notBlankNotNegative(variant, value);
			return value;
		}

		@Override
		public FluidVariant getResource() {
			return this.fluid;
		}

		@Override
		public boolean isResourceBlank() {
			return getResource().isBlank();
		}

		@Override
		public long getCapacity() {
			return FluidConstants.BUCKET * 8;
		}

		@Override
		public long getAmount() {
			return this.amount;
		}

		@Override
		@NotNull
		public Iterator<StorageView<FluidVariant>> iterator() {
			return Iterators.singletonIterator(this);
		}

	}

}
