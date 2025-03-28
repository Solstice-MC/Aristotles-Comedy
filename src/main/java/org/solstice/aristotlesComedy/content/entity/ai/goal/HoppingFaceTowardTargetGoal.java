package org.solstice.aristotlesComedy.content.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import org.solstice.aristotlesComedy.content.entity.HoppingEntity;
import org.solstice.aristotlesComedy.content.entity.controls.HoppingMoveControl;

import java.util.EnumSet;

public class HoppingFaceTowardTargetGoal<T extends MobEntity & HoppingEntity> extends Goal {

	private final T entity;
	private int ticksLeft;

	public HoppingFaceTowardTargetGoal(T entity) {
		this.entity = entity;
		this.setControls(EnumSet.of(Control.LOOK));
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.entity.getTarget();
		if (livingEntity == null) return false;

		return this.entity.canTarget(livingEntity) && this.entity.getMoveControl() instanceof HoppingMoveControl<?>;
	}

	@Override
	public void start() {
		this.ticksLeft = toGoalTicks(300);
		super.start();
	}

	@Override
	public boolean shouldContinue() {
		LivingEntity livingEntity = this.entity.getTarget();
		if (livingEntity == null) return false;
		if (!this.entity.canTarget(livingEntity)) return false;

		return --this.ticksLeft > 0;
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity livingEntity = this.entity.getTarget();
		if (livingEntity != null) {
			this.entity.lookAtEntity(livingEntity, 10.0F, 10.0F);
		}

		MoveControl control = this.entity.getMoveControl();
		if (control instanceof HoppingMoveControl<?> hoppingControl) hoppingControl.look(this.entity.getYaw());
	}

}
