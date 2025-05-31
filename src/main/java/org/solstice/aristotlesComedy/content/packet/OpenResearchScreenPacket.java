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
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.client.content.screen.ResearchScreen;
import org.solstice.aristotlesComedy.content.research.PositionedResearchable;

import java.util.List;

public record OpenResearchScreenPacket(
	ItemStack stack,
	List<PositionedResearchable> entries
) implements CustomPayload {

	public static final Id<OpenResearchScreenPacket> ID = new Id<>(AristotlesComedy.of("open_research_screen"));

	public static final Codec<OpenResearchScreenPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemStack.CODEC.fieldOf("stack").forGetter(OpenResearchScreenPacket::stack),
		PositionedResearchable.CODEC.listOf().fieldOf("entries").forGetter(OpenResearchScreenPacket::entries)
	).apply(instance, OpenResearchScreenPacket::new));

	public static final PacketCodec<RegistryByteBuf, OpenResearchScreenPacket> PACKET_CODEC = PacketCodecs.unlimitedRegistryCodec(CODEC);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void openResearchScreen(OpenResearchScreenPacket packet, ClientPlayNetworking.Context context) {
		context.client().setScreen(new ResearchScreen(context.player(), packet.stack, packet.entries));
	}

}
