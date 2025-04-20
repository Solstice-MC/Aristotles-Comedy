package org.solstice.aristotlesComedy.access;

public interface AristotlesItemStack {

	default boolean isFuel() {
		return false;
	}

	default int getFuelTime() {
		return 0;
	}

}
