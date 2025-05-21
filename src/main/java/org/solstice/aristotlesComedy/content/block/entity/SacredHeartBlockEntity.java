package org.solstice.aristotlesComedy.content.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;
import org.solstice.aristotlesComedy.registry.AristotlesSoundEvents;

public class SacredHeartBlockEntity extends BlockEntity {

	public static int MAX_DELAY = 40;
	private int beatDelay = 40;
	private int cooldown = 0;

	public SacredHeartBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.SACRED_HEART, pos, state);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.beatDelay = nbt.getInt("beat_delay");
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.putInt("beat_delay", this.beatDelay);
	}

	public static void tick(World world, BlockPos pos, BlockState state, SacredHeartBlockEntity entity) {
		entity.cooldown++;
		if (entity.cooldown >= entity.beatDelay) {
			float pitch = 1 + 1 - (float) entity.beatDelay / MAX_DELAY;
			pitch = MathHelper.clamp(pitch, 1, 1.5F);
			world.playSound(null,
				pos.getX(), pos.getY(), pos.getZ(),
				AristotlesSoundEvents.BLOCK_SACRED_HEART_HEARTBEAT,
				SoundCategory.PLAYERS,
				0.5F, pitch
			);
			entity.cooldown = 0;
		}
	}

}
