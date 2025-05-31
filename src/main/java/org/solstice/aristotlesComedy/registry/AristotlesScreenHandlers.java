package org.solstice.aristotlesComedy.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.client.content.screen.MetallurgyScreen;
import org.solstice.aristotlesComedy.content.screen.handler.MetallurgyScreenHandler;

public class AristotlesScreenHandlers {

	public static void init() {}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		HandledScreens.register(AristotlesScreenHandlers.METALLURGY, MetallurgyScreen::new);
	}

	public static final ScreenHandlerType<MetallurgyScreenHandler> METALLURGY = register("metallurgy", MetallurgyScreenHandler::new);

	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType.Factory<T> factory) {
		return Registry.register(
			Registries.SCREEN_HANDLER,
			AristotlesComedy.of(name),
			new ScreenHandlerType<>(factory, FeatureFlags.DEFAULT_ENABLED_FEATURES)
		);
	}

}
