package org.solstice.aristotlesComedy.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.screen.handler.SabikaScreenHandler;

public class AristotlesScreenHandlers {

	public static void init() {}

	public static final ScreenHandlerType<SabikaScreenHandler> SABIKA = register("sabika", SabikaScreenHandler::new);

	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String path, ScreenHandlerType.Factory<T> factory) {
		return Registry.register(
			Registries.SCREEN_HANDLER,
			AristotlesComedy.of(path),
			new ScreenHandlerType<>(factory, FeatureFlags.DEFAULT_ENABLED_FEATURES)
		);
	}

}
