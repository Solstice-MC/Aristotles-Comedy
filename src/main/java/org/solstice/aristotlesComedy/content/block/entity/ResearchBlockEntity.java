package org.solstice.aristotlesComedy.content.block.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;
import org.solstice.aristotlesComedy.content.research.PositionedResearchable;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;

import java.util.ArrayList;
import java.util.List;

public class ResearchBlockEntity extends BlockEntity {

	public final List<PositionedResearchable> researchables = new ArrayList<>();

	public ResearchBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.RESEARCH, pos, state);
	}

	public void openScreen(World world, PlayerEntity player) {
//		if (!world.isClient) ServerPlayNetworking.send((ServerPlayerEntity)player, new OpenResearchableScreenPacket(stack, entry));
	}

}
