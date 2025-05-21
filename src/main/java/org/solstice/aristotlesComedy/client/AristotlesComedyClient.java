package org.solstice.aristotlesComedy.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.client.registry.AristotlesClientPackets;
import org.solstice.aristotlesComedy.client.registry.AristotlesEntityRenderers;
import org.solstice.aristotlesComedy.registry.AristotlesBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class AristotlesComedyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		AristotlesEntityRenderers.init();
		ColorProviderRegistry.BLOCK.register(AristotlesComedyClient::test, AristotlesBlocks.BISMUTH_BLOCK);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
			AristotlesBlocks.YELLOW_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.RED_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.BLUE_TRISMEGISTITE_CLUSTER,
			AristotlesBlocks.BLACK_TRISMEGISTITE_CLUSTER,

			AristotlesBlocks.BRAZIER,
			AristotlesBlocks.SOUL_BRAZIER
		);
		AristotlesClientPackets.init();
	}

	public static int test(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		return 0xff00ff;
	}

}
