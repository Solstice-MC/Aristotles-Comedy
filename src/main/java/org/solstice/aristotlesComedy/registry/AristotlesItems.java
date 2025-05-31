package org.solstice.aristotlesComedy.registry;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.item.SwordItem;
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
import org.solstice.aristotlesComedy.content.item.*;
import org.solstice.euclidsElements.content.registry.EuclidsComponentTypes;

import java.util.function.Function;

public class AristotlesItems {

    public static void init() {
		FluidStorage.ITEM.registerForItems(HumorVialItem.HumorVialStorage::new, HUMOR_VIAL);
	}

	public static final FoodComponent ASH_SOUP_FOOD_COMPONENT = new FoodComponent.Builder()
		.nutrition(-2).saturationModifier(-0.2f).alwaysEdible().usingConvertsTo(Items.BOWL).build();

	public static final Item BRONZE_WRENCH = register("bronze_wrench", WrenchItem::new);
	public static final Item HUMOR_VIAL = register("humor_vial", HumorVialItem::new);

	public static final Item PLATINUM_GLOVE = register("platinum_glove",
		settings -> new PlatinumGloveItem(AristotlesToolMaterials.PLATINUM, settings),
		new Item.Settings()
			.maxDamage(2048)
			.component(EuclidsComponentTypes.INVENTORY_ITEM_MODEL, AristotlesComedy.of("icon/platinum_glove"))
			.attributeModifiers(
				SwordItem.createAttributeModifiers(AristotlesToolMaterials.PLATINUM, 3, -1.6F)
			)
	);

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
