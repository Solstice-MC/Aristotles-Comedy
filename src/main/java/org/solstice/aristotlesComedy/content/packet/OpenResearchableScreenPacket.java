package org.solstice.aristotlesComedy.content.packet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableScreen;
import org.solstice.aristotlesComedy.content.research.Researchable;

public record OpenResearchableScreenPacket (
	ItemStack stack,
	RegistryEntry<Researchable> entry
) implements CustomPayload {

	public static final CustomPayload.Id<OpenResearchableScreenPacket> ID = new CustomPayload.Id<>(AristotlesComedy.of("open_researchable_screen"));

	public static final Codec<OpenResearchableScreenPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemStack.CODEC.fieldOf("stack").forGetter(OpenResearchableScreenPacket::stack),
		Researchable.ENTRY_CODEC.fieldOf("entry").forGetter(OpenResearchableScreenPacket::entry)
	).apply(instance, OpenResearchableScreenPacket::new));

	public static final PacketCodec<RegistryByteBuf, OpenResearchableScreenPacket> PACKET_CODEC = PacketCodecs.unlimitedRegistryCodec(CODEC);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void openResearchableScreen(OpenResearchableScreenPacket packet, ClientPlayNetworking.Context context) {
		context.client().setScreen(new ResearchableScreen(context.player(), packet.stack, packet.entry));
	}

}
