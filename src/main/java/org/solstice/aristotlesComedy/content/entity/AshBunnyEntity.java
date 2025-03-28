package org.solstice.aristotlesComedy.content.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.entity.ai.goal.HoppingFaceTowardTargetGoal;
import org.solstice.aristotlesComedy.content.entity.ai.goal.HoppingMoveGoal;
import org.solstice.aristotlesComedy.content.entity.ai.goal.HoppingRandomLookGoal;
import org.solstice.aristotlesComedy.content.entity.controls.HoppingMoveControl;
import org.solstice.aristotlesComedy.registry.ModEntityTypes;

public class AshBunnyEntity extends AbstractAshBunnyEntity {

	public AshBunnyEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new HoppingMoveControl<>(this);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new HoppingFaceTowardTargetGoal<>(this));
		this.goalSelector.add(3, new HoppingRandomLookGoal<>(this));
		this.goalSelector.add(5, new HoppingMoveGoal<>(this));
	}

	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return ModEntityTypes.ASH_BUNNY.get().create(world);
	}

	@Override
	public SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_SLIME_JUMP;
	}

}
