package org.solstice.aristotlesComedy.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.block.entity.SabikaBlockEntity;

public class SabikaBlock extends BlockWithEntity {

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(SabikaBlock::new);
	}

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty LIT = Properties.LIT;

	public SabikaBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState()
			.with(FACING, Direction.NORTH)
			.with(LIT, false)
		);
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
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.isOf(newState.getBlock())) return;

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SabikaBlockEntity sabikaBlockEntity) {
			if (world instanceof ServerWorld) {
				ItemScatterer.spawn(world, pos, sabikaBlockEntity);
				sabikaBlockEntity.getRecipesUsedAndDropExperience((ServerWorld)world, Vec3d.ofCenter(pos));
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

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<? extends SabikaBlockEntity> expectedType) {
		return world.isClient ? null : validateTicker(givenType, expectedType, SabikaBlockEntity::tick);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(LIT)) return;

		double d = (double)pos.getX() + 0.5;
		double e = pos.getY();
		double f = (double)pos.getZ() + 0.5;
		if (random.nextDouble() < 0.1) {
			world.playSound(d, e, f, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}

		Direction direction = state.get(FACING);
		Direction.Axis axis = direction.getAxis();
		double g = 0.52;
		double h = random.nextDouble() * 0.6 - 0.3;
		double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
		double j = random.nextDouble() * 9.0 / 16.0;
		double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
		world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
	}

}
