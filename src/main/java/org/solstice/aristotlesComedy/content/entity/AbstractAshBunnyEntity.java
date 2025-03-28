package org.solstice.aristotlesComedy.content.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.entity.controls.HoppingMoveControl;
import org.solstice.aristotlesComedy.registry.ModBlocks;
import org.solstice.aristotlesComedy.registry.ModSoundEvents;

public abstract class AbstractAshBunnyEntity extends PassiveEntity implements HoppingEntity {

	public AbstractAshBunnyEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new HoppingMoveControl<>(this);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);
		this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, -1, 0, true, false));
		return result;
	}

	@Override
	public void jump() {
		Vec3d vec3d = this.getVelocity();
		this.setVelocity(vec3d.x, this.getJumpVelocity(), vec3d.z);
		this.velocityDirty = true;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		this.poof();
		return true;
	}

	@Override
	public boolean collidesWith(Entity entity) {
		if (entity instanceof PlayerEntity player && player.isSprinting())
			this.damage(this.getWorld().getDamageSources().generic(), 1);
		return super.collidesWith(entity);
	}

	@Override
	public void tick() {
		super.tick();
		boolean isInWater = this.getFluidHeight(FluidTags.WATER) > 0;
		if (isInWater) this.poof();
	}

	@Override
	public int getTicksUntilNextJump() {
		return this.getRandom().nextInt(80) + 20;
	}

	public void poof() {
		this.remove(RemovalReason.DISCARDED);
		World world = this.getWorld();
		Vec3d pos = this.getPos();

		if (world instanceof ServerWorld serverWorld) serverWorld.spawnParticles(
			ParticleTypes.CLOUD,
			pos.x, pos.y, pos.z,
			32,
			0.25, 0.25, 0.25,
			0.1
		);
		world.playSound(this, this.getBlockPos(), ModSoundEvents.ENTITY_ASH_BUNNY_POOF, SoundCategory.NEUTRAL, 1, 1);
		this.placeAshLayer();
	}

	public void placeAshLayer() {
		World world = this.getWorld();
		BlockPos pos = this.getBlockPos();
		BlockState state = world.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) return;

		if (!state.isOf(ModBlocks.ASH)) {
			state = ModBlocks.ASH.get().getDefaultState();
		} else {
			int layers = state.get(Properties.LAYERS);
			layers = Math.min(layers + 1, SnowBlock.MAX_LAYERS);
			state = state.with(Properties.LAYERS, layers);
		}
		BlockState belowState = world.getBlockState(pos.down());
		boolean canPlace = belowState.hasSolidTopSurface(world, pos.down(), this);
		if (canPlace) world.setBlockState(pos, state);
	}

	public static DefaultAttributeContainer.Builder createAshBunnyAttributes() {
		return PassiveEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 0);
	}

}
