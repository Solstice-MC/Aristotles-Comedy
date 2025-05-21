package org.solstice.aristotlesComedy.registry;

import net.minecraft.util.Rarity;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.content.item.ResearchableItem;
import org.solstice.aristotlesComedy.content.item.WrenchItem;

import java.util.function.Function;

public class AristotlesItems {

    public static void init() {}

	public static final FoodComponent ASH_SOUP_FOOD_COMPONENT = new FoodComponent.Builder()
		.nutrition(-2).saturationModifier(-0.2f).alwaysEdible().usingConvertsTo(Items.BOWL).build();

	public static final Item BRONZE_WRENCH = register("bronze_wrench", WrenchItem::new);

	public static final Item ARCHATAME = register("archatame");
	public static final Item RESEARCH_NOTES = register("research_notes", ResearchableItem::new);

	public static final Item RAW_ENXOFA = register("raw_enxofa", new Item.Settings().maxCount(960));
	public static final Item RAW_SANGVIA = register("raw_sangvia", new Item.Settings().maxCount(960));
	public static final Item RAW_MELOFA = register("raw_melofa", new Item.Settings().maxCount(960));
	public static final Item RAW_APHENA = register("raw_aphena", new Item.Settings().maxCount(960));

	public static final Item RAW_TIN = register("raw_tin");
	public static final Item TIN_INGOT = register("tin_ingot");
	public static final Item TIN_NUGGET = register("tin_nugget");
	public static final Item RAW_LEAD = register("raw_lead");
	public static final Item LEAD_INGOT = register("lead_ingot");
	public static final Item LEAD_NUGGET = register("lead_nugget");
	public static final Item SILVER_INGOT = register("silver_ingot");
	public static final Item PLATINUM_INGOT = register("platinum_ingot");
	public static final Item MERCURY_INGOT = register("mercury_ingot");
	public static final Item BRONZE_INGOT = register("bronze_ingot");

	public static final Item ALKAHEST = register("alkahest");

	public static final Item ASH_SOUP = register("ash_soup", Item::new, new Item.Settings()
		.food(ASH_SOUP_FOOD_COMPONENT)
	);

	public static final Item MUSIC_DISC_SCARICO_IN_VINILE = register("music_disc_scarico_in_vinile", Item::new,
		new Item.Settings()
			.maxCount(1)
			.rarity(Rarity.RARE)
			.jukeboxPlayable(AristotlesJukeboxSongs.SCARICO_IN_VINILE)
	);

	public static Item register(String name) {
		return register(name, Item::new);
	}

	public static Item register(String name, Function<Item.Settings, Item> function) {
		return register(name, function, new Item.Settings());
	}

	public static Item register(String name, Item.Settings settings) {
		return register(name, Item::new, settings);
	}

	public static Item register(String name, Function<Item.Settings, Item> function, Item.Settings settings) {
        Identifier id = AristotlesComedy.of(name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = function.apply(settings);
        return Registry.register(Registries.ITEM, key, item);
    }

}
