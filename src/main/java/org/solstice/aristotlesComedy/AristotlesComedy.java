package org.solstice.aristotlesComedy;

import org.solstice.aristotlesComedy.content.recipe.MetallurgyRecipe;
import org.solstice.aristotlesComedy.registry.AristotlesHumors;
import org.solstice.aristotlesComedy.registry.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solstice.euclidsElements.content.api.event.EuclidsServerEvents;

public class AristotlesComedy implements ModInitializer {

	public static final String MOD_ID = "aristotles_comedy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String path) {
		return Identifier.of(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		AristotlesHumors.init();
		AristotlesResearchContentTypes.init();

		AristotlesTags.init();

		AristotlesScreenHandlers.init();
		AristotlesSoundEvents.init();
		AristotlesJukeboxSongs.init();

		AristotlesRegistryKeys.init();
		AristotlesRegistries.init();
		AristotlesPackets.init();

		AristotlesComponentTypes.init();

		AristotlesRecipeTypes.init();
		AristotlesRecipeSerializers.init();

		AristotlesFluids.init();
		AristotlesBlocks.init();
		AristotlesItems.init();
		AristotlesEntityTypes.init();
		AristotlesBlockEntities.init();

		AristotlesItemGroups.init();

		AristotlesPlacedFeatures.init();
		AristotlesBiomes.init();

		EuclidsServerEvents.AFTER_RESOURCES_LOADED.register(MetallurgyRecipe::populateIngredients);
	}

}
