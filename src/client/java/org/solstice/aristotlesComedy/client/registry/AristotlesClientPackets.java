package org.solstice.aristotlesComedy.client.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableScreen;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;

public class AristotlesClientPackets {

	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(OpenResearchableScreenPacket.ID, (payload, context) ->
			context.client().setScreen(new ResearchableScreen(context.player(), payload.entry()))
		);
	}

}
