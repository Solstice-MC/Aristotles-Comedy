package org.solstice.aristotlesComedy.content.block.entity;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.registry.AristotlesEntityTypes;

public class SturdyFallingBlockEntity extends FallingBlockEntity {

	public SturdyFallingBlockEntity(EntityType<? extends FallingBlockEntity> type, World world) {
		super(type, world);
	}

	public SturdyFallingBlockEntity(World world, double x, double y, double z, BlockState state) {
		this(AristotlesEntityTypes.STURDY_FALLING_BLOCK, world);
		this.block = state;
		this.intersectionChecked = true;
		this.setPosition(x, y, z);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
		this.setFallingBlockPos(this.getBlockPos());
	}

	public void tick() {
		if (this.block.isAir()) {
			this.discard();
			return;
		}

		Block block = this.block.getBlock();
		++this.timeFalling;
		this.applyGravity();
		this.move(MovementType.SELF, this.getVelocity());
		this.tickPortalTeleportation();

		if (this.getWorld().isClient) return;
		if (!this.isAlive()) return;

		BlockPos pos = this.getBlockPos();

		if (this.timeFalling > 100 && (pos.getY() <= this.getWorld().getBottomY() || pos.getY() > this.getWorld().getTopY())) {
			this.discard();
			return;
		}

		BlockState state = this.getWorld().getBlockState(pos);
		this.setVelocity(this.getVelocity().multiply(0.7, -0.5F, 0.7));
		// TODO is this related to duping somehow?
//		if (state.isOf(Blocks.MOVING_PISTON)) return;

		boolean canReplace = state.canReplace(new AutomaticItemPlacementContext(this.getWorld(), pos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
		if (!canReplace) return;

		boolean canFallThrough = FallingBlock.canFallThrough(this.getWorld().getBlockState(pos.down()));
		boolean canPlace = this.block.canPlaceAt(this.getWorld(), pos) && !canFallThrough;
		if (!canPlace) return;

		if (this.block.contains(Properties.WATERLOGGED) && this.getWorld().getFluidState(pos).getFluid() == Fluids.WATER)
			this.block = this.block.with(Properties.WATERLOGGED, true);

		boolean placed = this.getWorld().setBlockState(pos, this.block, 3);
		if (!placed) {
			this.discard();
			return;
		}

		((ServerWorld) this.getWorld()).getChunkManager().chunkLoadingManager.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(pos, this.getWorld().getBlockState(pos)));
		this.discard();
		if (block instanceof LandingBlock) {
			((LandingBlock) block).onLanding(this.getWorld(), pos, this.block, state, this);
		}

		if (!this.block.hasBlockEntity() || this.blockEntityData == null) return;

		BlockEntity blockEntity = this.getWorld().getBlockEntity(pos);
		if (blockEntity == null) return;

		NbtCompound nbtCompound = blockEntity.createNbt(this.getWorld().getRegistryManager());

		for (String string : this.blockEntityData.getKeys()) {
			nbtCompound.put(string, this.blockEntityData.get(string).copy());
		}

		try {
			blockEntity.read(nbtCompound, this.getWorld().getRegistryManager());
		} catch (Exception exception) {
			AristotlesComedy.LOGGER.error("Failed to load block entity from falling block", exception);
		}

		blockEntity.markDirty();
	}

}
