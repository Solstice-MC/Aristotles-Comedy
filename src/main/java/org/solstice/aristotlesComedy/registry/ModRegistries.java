package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.solstice.aristotlesComedy.content.humor.Humor;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

	public static final Registry<Humor> HUMOR = new SimpleRegistry<>(ModRegistryKeys.HUMOR, Lifecycle.stable());

	@SubscribeEvent
	public static void registerBuiltinRegistries(NewRegistryEvent event) {
		event.register(HUMOR);
	}

	@SubscribeEvent
	public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
		// TODO
	}

}
