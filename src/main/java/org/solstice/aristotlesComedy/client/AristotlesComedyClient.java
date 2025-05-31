package org.solstice.aristotlesComedy.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.registry.AristotlesBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.solstice.aristotlesComedy.registry.AristotlesEntityTypes;
import org.solstice.aristotlesComedy.registry.AristotlesPackets;
import org.solstice.aristotlesComedy.registry.AristotlesScreenHandlers;

public class AristotlesComedyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		AristotlesPackets.clientInit();
		AristotlesEntityTypes.clientInit();
		AristotlesScreenHandlers.clientInit();
		AristotlesBlocks.clientInit();
	}

	public static int test(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		return 0xff00ff;
	}

}
