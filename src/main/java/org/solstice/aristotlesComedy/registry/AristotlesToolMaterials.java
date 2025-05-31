package org.solstice.aristotlesComedy.registry;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import org.solstice.aristotlesComedy.util.ToolMaterialImpl;

public class AristotlesToolMaterials {

	public static final ToolMaterial PLATINUM = new ToolMaterialImpl(
		0, 8.0F, 3.0F,
		ToolMaterials.DIAMOND.getInverseTag(),
		10,
		() -> Ingredient.ofItems(AristotlesItems.PLATINUM_INGOT)
	);

}
