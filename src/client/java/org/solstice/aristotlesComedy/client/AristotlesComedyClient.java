package org.solstice.aristotlesComedy.client;

import org.solstice.aristotlesComedy.client.registry.AristotlesEntityRenderers;
import org.solstice.aristotlesComedy.registry.AristotlesBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class AristotlesComedyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		AristotlesEntityRenderers.init();
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
			AristotlesBlocks.YELLOW_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.RED_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.BLUE_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.BLACK_TRISMEGISTITE_CLUSTER,

			AristotlesBlocks.BRAZIER,
			AristotlesBlocks.SOUL_BRAZIER
		);
	}

}
