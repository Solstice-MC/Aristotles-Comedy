package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AristotlesItemGroups {

	public static void init() {}

	public static final ItemGroup MAIN = register("main", FabricItemGroup.builder()
		.icon(() -> new ItemStack(AristotlesItems.ALKAHEST))
		.entries((context, entries) -> {
			entries.add(AristotlesItems.ALKAHEST);
		})
	);

	public static ItemGroup register(String name, ItemGroup.Builder builder) {
		Identifier id = AristotlesComedy.of(name);
		Text displayName = Text.translatable(id.toTranslationKey("item_group"));
		ItemGroup group = builder
			.displayName(displayName)
			.build();
		return register(id, group);
	}

	public static ItemGroup register(Identifier id, ItemGroup group) {
		return Registry.register(Registries.ITEM_GROUP, id, group);
	}

}
