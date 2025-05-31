package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.humor.Humor;
import org.solstice.aristotlesComedy.content.research.Researchable;

import java.util.function.UnaryOperator;

public class AristotlesComponentTypes {

	public static void init() {}

	public static final ComponentType<RegistryEntry<Researchable>> RESEARCHABLE = register("researchable", Researchable.ENTRY_CODEC, Researchable.ENTRY_PACKET_CODEC);

	public static final ComponentType<Integer> LIT_TIME_REMAINING = register("lit_time_remaining", Codec.INT);
	public static final ComponentType<Integer> LIT_TIME_TOTAL = register("lit_time_total", Codec.INT);
	public static final ComponentType<Integer> COOKING_TIME_SPENT = register("cooking_time_spent", Codec.INT);
	public static final ComponentType<Integer> COOKING_TIME_TOTAL = register("cooking_time_total", Codec.INT);

	public static final ComponentType<FluidVariant> FLUID_VARIANT = register("fluid_variant", FluidVariant.CODEC);
	public static final ComponentType<Long> FLUID_AMOUNT = register("fluid_amount", Codec.LONG);

	public static final ComponentType<RegistryEntry<Humor>> HUMOR = register("humor", Humor.ENTRY_CODEC);

	private static <T> ComponentType<T> register(String name, Codec<T> codec) {
		return register(name, codec, PacketCodecs.unlimitedRegistryCodec(codec));
	}

	private static <T> ComponentType<T> register(String name, Codec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
		return register(name, builder -> builder.codec(codec).packetCodec(packetCodec));
	}

	private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
	}

}
