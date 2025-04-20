package org.solstice.aristotlesComedy.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BrazierBlock extends Block implements Waterloggable {

	public static final BooleanProperty LIT = Properties.LIT;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	private final boolean emitsParticles;

	public BrazierBlock(boolean emitsParticles, Settings settings) {
		super(settings);
		this.emitsParticles = emitsParticles;
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, true).with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT, WATERLOGGED);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		boolean isInWater = world.getFluidState(pos).getFluid() == Fluids.WATER;
		return this.getDefaultState().with(WATERLOGGED, isInWater).with(LIT, !isInWater);
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED))
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(LIT)) return;

		if (random.nextInt(10) == 0)
			world.playSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);

		if (this.emitsParticles && random.nextInt(5) == 0) {
			for (int i = 0; i < random.nextInt(2) + 1; ++i) {
				world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, (double)(random.nextFloat() / 2.0F), 5.0E-5, (double)(random.nextFloat() / 2.0F));
			}
		}
	}

	@Override
	public boolean tryFillWithFluid(WorldAccess worldAccess, BlockPos pos, BlockState state, FluidState fluidState) {
		World world = (World)worldAccess;
		if (state.get(WATERLOGGED)) return false;
		if (fluidState.getFluid() != Fluids.WATER) return false;

		if (state.get(LIT)) {
			if (!world.isClient())
				world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			extinguish(null, world, pos);
		}

		world.setBlockState(pos, state.with(WATERLOGGED, true).with(LIT, false));
		world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
		return true;
	}

	@Override
	protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		BlockPos pos = hit.getBlockPos();
		if (!world.isClient && projectile.isOnFire() && projectile.canModifyAt(world, pos) && canBeLit(state))
			world.setBlockState(pos, state.with(Properties.LIT, true));
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(true) : super.getFluidState(state);
	}

	public static void spawnSmokeParticle(World world, BlockPos pos) {
		Random random = world.getRandom();
		world.addImportantParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, (double)pos.getX() + (double)0.5F + random.nextDouble() / (double)3.0F * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + (double)0.5F + random.nextDouble() / (double)3.0F * (double)(random.nextBoolean() ? 1 : -1), (double)0.0F, 0.07, (double)0.0F);
	}

	public static boolean canBeLit(BlockState state) {
		return !state.get(LIT) && !state.get(WATERLOGGED);
	}

	public static void extinguish(@Nullable Entity entity, World world, BlockPos pos) {
		if (world.isClient()) for(int i = 0; i < 20; ++i) {
			spawnSmokeParticle(world, pos);
		}
		world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
	}

}
