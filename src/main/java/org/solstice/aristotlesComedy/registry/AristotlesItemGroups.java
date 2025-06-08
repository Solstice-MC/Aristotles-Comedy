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
			entries.add(AristotlesBlocks.SABIKA);
			entries.add(AristotlesBlocks.GLASS_TANK);
			entries.add(AristotlesBlocks.GLASS_PIPE);
			entries.add(AristotlesBlocks.BRONZE_TANK);
			entries.add(AristotlesBlocks.BRONZE_PIPE);

			entries.add(AristotlesBlocks.LEAD_BARREL);
			entries.add(AristotlesBlocks.LEAD_BARS);
			entries.add(AristotlesBlocks.BRAZIER);
			entries.add(AristotlesBlocks.SOUL_BRAZIER);

			entries.add(AristotlesBlocks.ASH);
			entries.add(AristotlesBlocks.ASH_BLOCK);

			entries.add(AristotlesBlocks.TIN_ORE);
			entries.add(AristotlesBlocks.DEEPSLATE_ZINC_ORE);
			entries.add(AristotlesBlocks.BLACKSTONE_LEAD_ORE);

			entries.add(AristotlesItems.BRONZE_WRENCH);
			entries.add(AristotlesItems.PLATINUM_GLOVE);

			entries.add(AristotlesItems.ALKAHEST);
			entries.add(AristotlesItems.RESEARCH_NOTES);


			entries.add(AristotlesItems.TIN_INGOT);
			entries.add(AristotlesItems.LEAD_INGOT);
			entries.add(AristotlesItems.BRONZE_INGOT);
			entries.add(AristotlesItems.SILVER_INGOT);
			entries.add(AristotlesItems.MERCURY_INGOT);

			entries.add(AristotlesItems.TIN_NUGGET);
			entries.add(AristotlesItems.LEAD_NUGGET);
			entries.add(AristotlesItems.BRONZE_NUGGET);
			entries.add(AristotlesItems.SILVER_NUGGET);

			entries.add(AristotlesItems.RAW_TIN);
			entries.add(AristotlesItems.RAW_LEAD);

			entries.add(AristotlesItems.ARCHATAME);

			entries.add(AristotlesItems.ASH_SOUP);

			entries.add(AristotlesItems.MUSIC_DISC_SCARICO_IN_VINILE);


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
