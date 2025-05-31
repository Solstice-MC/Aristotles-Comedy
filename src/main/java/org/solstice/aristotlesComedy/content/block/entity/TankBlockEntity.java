package org.solstice.aristotlesComedy.content.block.entity;

import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class TankBlockEntity extends BlockEntity {

	public final TankStorage storage;

	public TankBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.FLUID_TANK, pos, state);
		this.storage = new TankStorage();
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		this.storage.writeNbt(nbt, registryLookup);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.storage.readNbt(nbt, registryLookup);
	}

	public class TankStorage extends SingleFluidStorage {

		@Override
		protected long getCapacity(FluidVariant fluidVariant) {
			return FluidConstants.BUCKET * 8;
		}

		@Override
		protected void onFinalCommit() {
			TankBlockEntity.super.markDirty();
		}

	}

}
