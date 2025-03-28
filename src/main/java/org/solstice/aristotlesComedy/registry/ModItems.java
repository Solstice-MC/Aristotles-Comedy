package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModItems {

	public static final DeferredRegister.Items REGISTRY = DeferredRegister.Items.createItems(AristotlesComedy.MOD_ID);

	public static final FoodComponent ASH_SOUP_FOOD_COMPONENT = new FoodComponent.Builder()
		.nutrition(-2).saturationModifier(-0.2f).alwaysEdible().usingConvertsTo(Items.BOWL).build();

	public static final DeferredItem<Item> RAW_TIN = register("raw_tin");
	public static final DeferredItem<Item> TIN_INGOT = register("tin_ingot");
	public static final DeferredItem<Item> TIN_NUGGET = register("tin_nugget");
	public static final DeferredItem<Item> RAW_LEAD = register("raw_lead");
	public static final DeferredItem<Item> LEAD_INGOT = register("lead_ingot");
	public static final DeferredItem<Item> LEAD_NUGGET = register("lead_nugget");
	public static final DeferredItem<Item> SILVER_INGOT = register("silver_ingot");
	public static final DeferredItem<Item> PLATINUM_INGOT = register("platinum_ingot");
	public static final DeferredItem<Item> MERCURY_INGOT = register("mercury_ingot");
	public static final DeferredItem<Item> BRONZE_INGOT = register("bronze_ingot");

	public static final DeferredItem<Item> ALKAHEST = register("alkahest");

	public static final DeferredItem<Item> ASH_SOUP = register("ash_soup", Item::new, new Item.Settings()
		.food(ASH_SOUP_FOOD_COMPONENT)
	);

	public static DeferredItem<Item> register(String name) {
		return register(name, Item::new);
	}

	public static DeferredItem<Item> register(String name, Function<Item.Settings, Item> function) {
		return register(name, function, new Item.Settings());
	}

	public static DeferredItem<Item> register(String name, Item.Settings settings) {
		return register(name, Item::new, settings);
	}

	public static DeferredItem<Item> register(String name, Function<Item.Settings, Item> function, Item.Settings settings) {
//        Identifier id = AristotlesComedy.of(name);
//        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
//        Item item = function.apply(settings);
        return REGISTRY.registerItem(name, function, settings);
    }

}
