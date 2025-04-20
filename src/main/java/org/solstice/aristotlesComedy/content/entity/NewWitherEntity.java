package org.solstice.aristotlesComedy.content.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class NewWitherEntity extends HostileEntity implements RangedAttackMob {

	protected NewWitherEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlightMoveControl(this, 10, false);
	}

//	@Override
//	protected EntityNavigation createNavigation(World world) {
//		BirdNavigation navigation = new WitherNavigation(this, world);
//		navigation.setCanPathThroughDoors(false);
//		navigation.setCanSwim(true);
//		navigation.setCanEnterOpenDoors(true);
//		return navigation;
//	}
//
//	@Override
//	protected void initGoals() {
//		this.goalSelector.add(0, new WitherEntity.DescendAtHalfHealthGoal());
//		this.goalSelector.add(2, new ProjectileAttackGoal(this, (double)1.0F, 40, 20.0F));
//		this.goalSelector.add(5, new FlyGoal(this, (double)1.0F));
//		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
//		this.goalSelector.add(7, new LookAroundGoal(this));
//		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
//		this.targetSelector.add(2, new ActiveTargetGoal(this, LivingEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
//	}

	@Override
	public void shootAt(LivingEntity target, float pullProgress) {

	}

//	public static class RecoveryPhase implements Phase {
//
//		@Override
//		public void phaseInitialized(LivingEntity entity) {
//			entity.noClip = true;
//		}
//
//		@Override
//		public void phaseChanged(LivingEntity entity) {
//			entity.noClip = true;
//		}
//
//		@Override
//		public void updateGoals(GoalSelector goalSelector, GoalSelector targetSelector) {
//
//		}
//
//		@Override
//		public Map<Integer, Goal> getGoals(LivingEntity entity) {
//			return Map.of();
//		}
//
//	}
//
//	public interface Phase {
//
//		void phaseInitialized(LivingEntity entity);
//		void phaseChanged(LivingEntity entity);
//
//		void updateGoals(GoalSelector goalSelector, GoalSelector targetSelector);
//		Map<Integer, Goal> getGoals(LivingEntity entity);
//
//	}

}
