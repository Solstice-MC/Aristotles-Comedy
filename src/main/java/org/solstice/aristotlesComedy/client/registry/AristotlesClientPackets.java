package org.solstice.aristotlesComedy.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;

public class AristotlesClientPackets {

	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(OpenResearchableScreenPacket.ID, OpenResearchableScreenPacket::openResearchableScreen);
	}

}
