package org.solstice.aristotlesComedy.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.block.entity.MarbleBustBlockEntity;

public class MarbleBustBlock extends BlockWithEntity {

	public static VoxelShape HEAD_SHAPE = Block.createCuboidShape(4, 10, 4, 12, 18, 12);
	public static VoxelShape BODY_SHAPE = Block.createCuboidShape(4, 4, 6, 12, 10, 10);
	public static VoxelShape LEFT_ARM_SHAPE = Block.createCuboidShape(0, 6, 6, 4, 10, 10);
	public static VoxelShape RIGHT_ARM_SHAPE = Block.createCuboidShape(12, 6, 6, 16, 10, 10);
	public static VoxelShape BUST_SHAPE = VoxelShapes.union(HEAD_SHAPE, BODY_SHAPE, LEFT_ARM_SHAPE, RIGHT_ARM_SHAPE);

	public static VoxelShape STILL_SHAPE = Block.createCuboidShape(7, 2, 7, 9, 4, 9);
	public static VoxelShape PLATE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 2, 14);
	public static VoxelShape BASE_SHAPE = VoxelShapes.union(STILL_SHAPE, PLATE_SHAPE);

	public static VoxelShape SHAPE = VoxelShapes.union(BUST_SHAPE, BASE_SHAPE);

	public MarbleBustBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(MarbleBustBlock::new);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new MarbleBustBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

}
