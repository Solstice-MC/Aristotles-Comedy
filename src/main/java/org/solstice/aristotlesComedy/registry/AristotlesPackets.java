package org.solstice.aristotlesComedy.registry;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;

public class AristotlesPackets {

	public static void init() {
		PayloadTypeRegistry.playS2C().register(OpenResearchableScreenPacket.ID, OpenResearchableScreenPacket.PACKET_CODEC);
	}

}
