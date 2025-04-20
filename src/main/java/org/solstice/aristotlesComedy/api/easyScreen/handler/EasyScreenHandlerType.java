package org.solstice.aristotlesComedy.api.easyScreen.handler;

import net.minecraft.entity.player.PlayerInventory;

public class EasyScreenHandlerType<T extends EasyScreenHandler> {

	private final EasyScreenHandlerType.Factory<T> factory;

	public EasyScreenHandlerType(EasyScreenHandlerType.Factory<T> factory) {
		this.factory = factory;
	}

	public T create(PlayerInventory playerInventory) {
		return this.factory.create(playerInventory);
	}

	public interface Factory<T extends EasyScreenHandler> {
		T create(PlayerInventory playerInventory);
	}

}
