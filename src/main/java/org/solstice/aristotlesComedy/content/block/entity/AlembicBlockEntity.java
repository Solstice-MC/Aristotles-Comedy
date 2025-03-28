package org.solstice.aristotlesComedy.content.block.entity;

//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
//import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.solstice.aristotlesComedy.registry.ModBlockEntities;
//import org.solstice.aristotlesComedy.registry.ModBlockEntityComponents;

public class AlembicBlockEntity extends BlockEntity {

//	public final SingleVariantStorage<FluidVariant> storage;

	public AlembicBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ALEMBIC.get(), pos, state);
//		this.storage = new AlembicStorage();
	}

//	@Override
//	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
//		nbt.put("fluid", FluidVariant.CODEC.encodeStart(NbtOps.INSTANCE, this.storage.variant).getOrThrow());
//		nbt.putLong("amount", this.storage.amount);
//	}


//	@Override
//	protected void addComponents(ComponentMap.Builder builder) {
//		builder
//			.add(ModBlockEntityComponents.FLUID_VARIANT, this.storage.variant)
//			.add(ModBlockEntityComponents.FLUID_AMOUNT, this.storage.amount);
//	}

//	@Override
//	protected void readComponents(ComponentsAccess components) {
//		this.storage.variant = components.get(ModBlockEntityComponents.FLUID_VARIANT);
//		this.storage.amount = components.get(ModBlockEntityComponents.FLUID_AMOUNT);
//	}

//	public static class AlembicStorage extends SingleVariantStorage<FluidVariant> {
//
//		@Override
//		protected FluidVariant getBlankVariant() {
//			return FluidVariant.blank();
//		}
//
//		@Override
//		protected long getCapacity(FluidVariant fluidVariant) {
//			return (8 * FluidConstants.BUCKET);
//		}
//
//	}

}
