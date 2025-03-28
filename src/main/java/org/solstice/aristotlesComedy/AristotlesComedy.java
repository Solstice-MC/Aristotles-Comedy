package org.solstice.aristotlesComedy;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solstice.aristotlesComedy.registry.*;

@Mod(AristotlesComedy.MOD_ID)
public class AristotlesComedy {

	public static final String MOD_ID = "aristotles_comedy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public AristotlesComedy(IEventBus bus) {
		ModItems.REGISTRY.register(bus);
		ModItemGroups.REGISTRY.register(bus);
		ModBlocks.REGISTRY.register(bus);
		ModEntityTypes.REGISTRY.register(bus);
		ModComponentTypes.REGISTRY.register(bus);
		ModRecipeTypes.REGISTRY.register(bus);
		ModRecipeSerializers.REGISTRY.register(bus);
	}

}
