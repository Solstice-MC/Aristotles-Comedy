package org.solstice.aristotlesComedy.content.entity;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public interface HoppingEntity {

	Random getRandom();

	SoundEvent getJumpSound();

	default int getTicksUntilNextJump() {
		return this.getRandom().nextInt(20) + 10;
	}

	default float getJumpSoundPitch() {
		return ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
	}

}
