package org.solstice.aristotlesComedy.content.block.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import org.solstice.aristotlesComedy.content.block.entity.TankBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTankBlock extends BlockWithEntity {

	public AbstractTankBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		TankBlockEntity entity = this.getBlockEntity(world, pos);
		if (entity == null) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		boolean result = FluidStorageUtil.interactWithFluidStorage(entity.storage, player, hand);
		return result ? ItemActionResult.SUCCESS : ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
	}

	public TankBlockEntity getBlockEntity(World world, BlockPos pos) {
		return (TankBlockEntity) world.getBlockEntity(pos);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

    public TankBlockEntity.TankStorage getStorage(TankBlockEntity entity, Direction direction) {
		return entity.storage;
    }

}
