package org.solstice.aristotlesComedy.content.item;

import net.minecraft.item.Instrument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MultiGoatHornItem extends Item {

	private final TagKey<Instrument> harmonyTag;
	private final TagKey<Instrument> melodyTag;
	private final TagKey<Instrument> bassTag;

	public MultiGoatHornItem(TagKey<Instrument> harmonyTag, TagKey<Instrument> melodyTag, TagKey<Instrument> bassTag, Settings settings) {
		super(settings);
		this.harmonyTag = harmonyTag;
		this.melodyTag = melodyTag;
		this.bassTag = bassTag;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		Optional<RegistryKey<Instrument>> optional = this.getInstrument(stack).flatMap(RegistryEntry::getKey);
		if (optional.isPresent()) {
			MutableText mutableText = Text.translatable(Util.createTranslationKey("instrument", ((RegistryKey)optional.get()).getValue()));
			tooltip.add(mutableText.formatted(Formatting.GRAY));
		}

	}

	private Optional<RegistryEntry<Instrument>> getInstrument(ItemStack stack) {
		Iterator<RegistryEntry<Instrument>> iterator = Registries.INSTRUMENT.iterateEntries(this.melodyTag).iterator();
		return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
	}

}
