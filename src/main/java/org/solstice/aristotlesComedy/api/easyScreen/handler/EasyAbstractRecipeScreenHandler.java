package org.solstice.aristotlesComedy.api.easyScreen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class EasyAbstractRecipeScreenHandler<I extends RecipeInput, R extends Recipe<I>> extends EasyScreenHandler {

	public EasyAbstractRecipeScreenHandler(@Nullable ScreenHandlerType<?> type) {
		super(type);
	}

	@Override
	public int getExpectedSize() {
		return 0;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return false;
	}

}
