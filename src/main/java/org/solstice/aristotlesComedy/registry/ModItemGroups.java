package org.solstice.aristotlesComedy.registry;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class ModItemGroups {

	public static final DeferredRegister<ItemGroup> REGISTRY = DeferredRegister.create(Registries.ITEM_GROUP, AristotlesComedy.MOD_ID);

	public static final DeferredHolder<ItemGroup, ItemGroup> MAIN = register("main", ItemGroup.builder()
		.icon(() -> new ItemStack(ModItems.ALKAHEST.get()))
		.entries((context, entries) -> {
			entries.add(ModItems.ALKAHEST);
		})
	);

	public static DeferredHolder<ItemGroup, ItemGroup> register(String name, ItemGroup.Builder builder) {
		return REGISTRY.register(name, id -> {
			Text displayName = Text.translatable(id.toTranslationKey("item_group"));
			return builder
				.displayName(displayName)
				.build();
		});
	}

}
