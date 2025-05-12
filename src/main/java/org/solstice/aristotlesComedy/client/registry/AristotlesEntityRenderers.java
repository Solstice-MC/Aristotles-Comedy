package org.solstice.aristotlesComedy.client.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.client.content.entity.model.AshBunnyModel;
import org.solstice.aristotlesComedy.client.content.entity.renderer.AshBunnyRenderer;
import org.solstice.aristotlesComedy.registry.AristotlesEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class AristotlesEntityRenderers {

	public static final Identifier ASH_BUNNY_ID = AristotlesComedy.of("ash_bunny");
	public static final EntityModelLayer ASH_BUNNY_LAYER = new EntityModelLayer(ASH_BUNNY_ID, "main");

	public static void init() {
		EntityRendererRegistry.register(
			AristotlesEntityTypes.ASH_BUNNY,
			context -> new AshBunnyRenderer(context, new AshBunnyModel(context.getPart(ASH_BUNNY_LAYER)))
		);
		EntityModelLayerRegistry.registerModelLayer(
			ASH_BUNNY_LAYER,
			AshBunnyModel::getTexturedModelData
		);
	}

}
