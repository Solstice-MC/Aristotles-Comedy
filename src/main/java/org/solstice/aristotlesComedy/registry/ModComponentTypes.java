package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;

public class ModComponentTypes {

    public static final DeferredRegister<ComponentType<?>> REGISTRY = DeferredRegister
            .create(Registries.DATA_COMPONENT_TYPE, AristotlesComedy.MOD_ID);

//    public static final ComponentType<ItemUpgradesComponent> UPGRADES = register("upgrades", ItemUpgradesComponent.CODEC);

    private static <T> ComponentType<T> register(String name, Codec<T> codec) {
        return register(name, codec, PacketCodecs.registryCodec(codec));
    }

    private static <T> ComponentType<T> register(String name, Codec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
        ComponentType<T> component = ComponentType.<T>builder()
                .codec(codec)
                .packetCodec(packetCodec)
                .cache()
                .build();
        REGISTRY.register(name, () -> component);
        return component;
    }

}
