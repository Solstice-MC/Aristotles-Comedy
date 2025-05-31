package org.solstice.aristotlesComedy.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.solstice.aristotlesComedy.content.packet.OpenResearchScreenPacket;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;

public class AristotlesPackets {

	public static void init() {
		PayloadTypeRegistry.playS2C().register(OpenResearchableScreenPacket.ID, OpenResearchableScreenPacket.PACKET_CODEC);
		PayloadTypeRegistry.playS2C().register(OpenResearchScreenPacket.ID, OpenResearchScreenPacket.PACKET_CODEC);
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ClientPlayNetworking.registerGlobalReceiver(OpenResearchableScreenPacket.ID, OpenResearchableScreenPacket::openResearchableScreen);
		ClientPlayNetworking.registerGlobalReceiver(OpenResearchScreenPacket.ID, OpenResearchScreenPacket::openResearchScreen);
	}

}
