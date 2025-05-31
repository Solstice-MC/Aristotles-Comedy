package org.solstice.aristotlesComedy.content.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.block.entity.SabikaBlockEntity;

public abstract class AbstractMetallurgyBlock extends BlockWithEntity {

	public AbstractMetallurgyBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SabikaBlockEntity(pos, state);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			this.openScreen(world, pos, player);
			return ActionResult.CONSUME;
		}
	}

	protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
		BlockEntity entity = world.getBlockEntity(pos);
		if (!(entity instanceof SabikaBlockEntity)) return;

		player.openHandledScreen((NamedScreenHandlerFactory)entity);
//		player.incrementStat(JoltedStats.INTERACT_WITH_ALLOY_FORGE);
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.isOf(newState.getBlock())) return;

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SabikaBlockEntity sabikaBlockEntity) {
			if (world instanceof ServerWorld) {
				ItemScatterer.spawn(world, pos, sabikaBlockEntity);
//				sabikaBlockEntity.getRecipesUsedAndDropExperience((ServerWorld)world, Vec3d.ofCenter(pos));
			}

			super.onStateReplaced(state, world, pos, newState, moved);
			world.updateComparators(pos, this);
		} else {
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}


	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<? extends SabikaBlockEntity> expectedType) {
		return world.isClient ? null : validateTicker(givenType, expectedType, SabikaBlockEntity::tick);
	}

}
