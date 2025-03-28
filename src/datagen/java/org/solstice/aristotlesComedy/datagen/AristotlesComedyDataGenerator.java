package org.solstice.aristotlesComedy.datagen;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.solstice.aristotlesComedy.datagen.provider.*;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AristotlesComedyDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		ModContainer container = event.getModContainer();
		DataGenerator generator = event.getGenerator();
		DataOutput output = generator.getPackOutput();
		CompletableFuture<RegistryWrapper.WrapperLookup> lookup = event.getLookupProvider();

		generator.addProvider(event.includeServer(), new ACRecipeProvider(output, lookup));
		generator.addProvider(event.includeServer(), new ACLanguageProvider(container, output, lookup));
		generator.addProvider(event.includeServer(), new ACLootTableProvider(output, lookup));

//		DataGenerator.PackGenerator microcosmPack = event.getGenerator().getBuiltinDatapack(true, "microcosm", "microcosm");
	}

//	@Override
//	public void onInitializeDataGenerator(FabricDataGenerator generator) {
//		FabricDataGenerator.Pack pack = generator.createPack();
//		pack.addProvider(ACLanguageProvider::new);
//		pack.addProvider(ACModelProvider::new);
//		pack.addProvider(ACBlockLootTableProvider::new);
//		pack.addProvider(ACAdvancementProvider::new);
//		pack.addProvider(ACRecipeProvider::new);
//	}

	public static boolean ownsRegistry(RegistryEntry<?> entry) {
		return entry.getKey().orElseThrow().getValue().getNamespace().equals(AristotlesComedy.MOD_ID);
	}

}
