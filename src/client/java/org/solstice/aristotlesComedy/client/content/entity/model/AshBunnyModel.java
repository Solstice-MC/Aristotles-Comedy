package org.solstice.aristotlesComedy.client.content.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import org.solstice.aristotlesComedy.content.entity.AshBunnyEntity;

public class AshBunnyModel extends SinglePartEntityModel<AshBunnyEntity> {

	private final ModelPart root;

	public AshBunnyModel(ModelPart root) {
		this.root = root;
	}

	@Override
	public void setAngles(AshBunnyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		return TexturedModelData.of(modelData, 32, 32);
	}

}
