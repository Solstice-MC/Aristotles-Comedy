package org.solstice.aristotlesComedy.client.content.entity.renderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.block.entity.MarbleBustBlockEntity;

public class MarbleBustRenderer implements BlockEntityRenderer<MarbleBustBlockEntity> {

	private final BlockRenderManager blockRenderManager;

	public MarbleBustRenderer(BlockEntityRendererFactory.Context ctx) {
		this.blockRenderManager = ctx.getRenderManager();
	}

	@Override
	public void render(MarbleBustBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		World world = entity.getWorld();
		BlockPos pos = entity.getPos();
		BlockState state = world.getBlockState(pos);
		this.blockRenderManager.getModelRenderer().render(
			world,
			this.blockRenderManager.getModel(state),
			state,
			pos,
			matrices,
			vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)),
			false,
			world.getRandom(),
			state.getRenderingSeed(pos),
			OverlayTexture.DEFAULT_UV
		);
	}

}
