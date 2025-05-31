package org.solstice.aristotlesComedy.content.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.solstice.aristotlesComedy.content.screen.handler.MetallurgyScreenHandler;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;

public class SabikaBlockEntity extends AbstractMetallurgyBlockEntity {

	@Override
	protected Text getContainerName() {
		return Text.translatable("container.aristotles_comedy.metallurgy");
	}

	public SabikaBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.SABIKA, pos, state);
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		builder
			.add(AristotlesComponentTypes.LIT_TIME_REMAINING, this.litTimeRemaining)
			.add(AristotlesComponentTypes.LIT_TIME_TOTAL, this.litTotalTime)
			.add(AristotlesComponentTypes.COOKING_TIME_SPENT, this.cookingTimeSpent)
			.add(AristotlesComponentTypes.COOKING_TIME_TOTAL, this.cookingTotalTime);
		super.addComponents(builder);
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.LIT_TIME_REMAINING, this.litTimeRemaining);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.LIT_TIME_TOTAL, this.litTotalTime);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.COOKING_TIME_SPENT, this.cookingTimeSpent);
		this.litTimeRemaining = components.getOrDefault(AristotlesComponentTypes.COOKING_TIME_TOTAL, this.cookingTotalTime);
	}

	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new MetallurgyScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

}
