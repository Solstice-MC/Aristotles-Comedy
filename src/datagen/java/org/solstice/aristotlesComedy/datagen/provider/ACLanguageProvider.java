package org.solstice.aristotlesComedy.datagen.provider;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.data.DataOutput;
import net.neoforged.fml.ModContainer;
import org.solstice.aristotlesComedy.datagen.AristotlesComedyDataGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.datagen.api.LanguageProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ACLanguageProvider extends LanguageProvider {

	public static final List<RegistryKey<?>> REGISTRY_BLACKLIST = List.of(
		RegistryKeys.RECIPE_SERIALIZER,
		RegistryKeys.BIOME
	);

	public static boolean keyIsNotBlacklisted(RegistryKey<?> key) {
		return !REGISTRY_BLACKLIST.contains(key);
	}

	public static String toUpperCase(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}


	public ACLanguageProvider(ModContainer container, DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
		super(container, output, lookup);
	}


	@Override
	public void addTranslations(RegistryWrapper.WrapperLookup lookup) {
		generateRegistryTranslations(lookup);
		generateTagTranslations(lookup);
	}

	public void generateRegistryTranslations(RegistryWrapper.WrapperLookup lookup) {
		lookup.streamAllRegistryKeys()
			.filter(ACLanguageProvider::keyIsNotBlacklisted)
			.map(lookup::getOptionalWrapper)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(wrapper -> wrapper.streamEntries()
				.filter(AristotlesComedyDataGenerator::ownsRegistry)
				.forEach(this::generateEntryTranslation)
			);
	}

	public void generateEntryTranslation(RegistryEntry<?> entry) {
		RegistryKey<?> registryKey = entry.getKey().orElse(null);
		if (registryKey == null) return;

		Identifier id = registryKey.getValue();
		if (!id.getNamespace().equals(AristotlesComedy.MOD_ID)) return;
		String dataType = registryKey.getRegistry().getPath();
		if (dataType.endsWith("_type")) dataType = dataType.split("_type")[0];
		String key = id.toTranslationKey(dataType);
		String translation = Arrays
			.stream(id.getPath().split("([_.])"))
			.map(ACLanguageProvider::toUpperCase)
			.collect(Collectors.joining(" "));
		this.add(key, translation);
	}

	public void generateTagTranslations(RegistryWrapper.WrapperLookup lookup) {
		lookup.streamAllRegistryKeys().forEach(sKey ->
			lookup.getWrapperOrThrow(sKey).streamTagKeys().forEach(this::generateTagTranslation)
		);
	}

	public void generateTagTranslation(TagKey<?> tag) {
		Identifier id = tag.id();
		String key = id.toTranslationKey("tag." + tag.registry().getValue().getPath());
		String translation = Arrays
			.stream(id.getPath().split("([_.])"))
			.map(ACLanguageProvider::toUpperCase)
			.collect(Collectors.joining(" "));
		this.add(key, translation);
	}

}
