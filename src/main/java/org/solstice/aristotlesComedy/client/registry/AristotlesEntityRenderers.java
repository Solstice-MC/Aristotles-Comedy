package org.solstice.aristotlesComedy.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.client.content.entity.model.AshBunnyModel;
import org.solstice.aristotlesComedy.client.content.entity.renderer.AshBunnyRenderer;
import org.solstice.aristotlesComedy.registry.AristotlesEntityTypes;

public class AristotlesEntityRenderers {

	public static final Identifier ASH_BUNNY_ID = AristotlesComedy.of("ash_bunny");
	public static final EntityModelLayer ASH_BUNNY_LAYER = new EntityModelLayer(ASH_BUNNY_ID, "main");

	public static void init() {
		EntityRendererRegistry.register(AristotlesEntityTypes.ASH_BUNNY,
			context -> new AshBunnyRenderer(context, new AshBunnyModel(context.getPart(ASH_BUNNY_LAYER)))
		);
		EntityRendererRegistry.register(AristotlesEntityTypes.STURDY_FALLING_BLOCK, FallingBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(ASH_BUNNY_LAYER, AshBunnyModel::getTexturedModelData);
	}

}
