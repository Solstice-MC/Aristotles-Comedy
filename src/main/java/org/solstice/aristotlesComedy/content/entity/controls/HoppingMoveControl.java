package org.solstice.aristotlesComedy.content.entity.controls;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import org.solstice.aristotlesComedy.content.entity.HoppingEntity;

public class HoppingMoveControl<T extends MobEntity & HoppingEntity> extends MoveControl {

	private float targetYaw;
	private int ticksUntilJump;
	private final T entity;

	public HoppingMoveControl(T entity) {
		super(entity);
		this.entity = entity;
		this.targetYaw = 180.0F * entity.getYaw() / (float)Math.PI;
	}

	public void look(float targetYaw) {
		this.targetYaw = targetYaw;
	}

	public void move(double speed) {
		this.speed = speed;
		this.state = State.MOVE_TO;
	}

	@Override
	public void tick() {
		this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
		this.entity.headYaw = this.entity.getYaw();
		this.entity.bodyYaw = this.entity.getYaw();
		if (this.state != State.MOVE_TO) {
			this.entity.setForwardSpeed(0.0F);
			return;
		}

		this.state = State.WAIT;
		if (!this.entity.isOnGround()) {
			this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			return;
		}

		this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
		if (this.ticksUntilJump-- <= 0) {
			this.ticksUntilJump = this.entity.getTicksUntilNextJump();
			this.entity.getJumpControl().setActive();
			this.entity.playSound(this.entity.getJumpSound(), this.entity.getSoundVolume(), this.entity.getJumpSoundPitch());
		} else {
			this.entity.sidewaysSpeed = 0.0F;
			this.entity.forwardSpeed = 0.0F;
			this.entity.setMovementSpeed(0.0F);
		}
	}

}
