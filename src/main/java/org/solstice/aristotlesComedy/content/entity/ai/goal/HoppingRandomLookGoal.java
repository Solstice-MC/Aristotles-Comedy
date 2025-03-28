package org.solstice.aristotlesComedy.content.entity.ai.goal;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import org.solstice.aristotlesComedy.content.entity.HoppingEntity;
import org.solstice.aristotlesComedy.content.entity.controls.HoppingMoveControl;

import java.util.EnumSet;

public class HoppingRandomLookGoal<T extends MobEntity & HoppingEntity> extends Goal {

	private final T entity;
	private float targetYaw;
	private int timer;

	public HoppingRandomLookGoal(T entity) {
		this.entity = entity;
		this.setControls(EnumSet.of(Control.LOOK));
	}

	@Override
	public boolean canStart() {
		return this.entity.getTarget() == null && (this.entity.isOnGround() || this.entity.isTouchingWater() || this.entity.isInLava() || this.entity.hasStatusEffect(StatusEffects.LEVITATION)) && this.entity.getMoveControl() instanceof HoppingMoveControl<?>;
	}

	@Override
	public void tick() {
		if (--this.timer <= 0) {
			this.timer = this.getTickCount(40 + this.entity.getRandom().nextInt(60));
			this.targetYaw = (float)this.entity.getRandom().nextInt(360);
		}

		MoveControl control = this.entity.getMoveControl();
		if (control instanceof HoppingMoveControl<?> hoppingControl) hoppingControl.look(this.targetYaw);
	}

}
