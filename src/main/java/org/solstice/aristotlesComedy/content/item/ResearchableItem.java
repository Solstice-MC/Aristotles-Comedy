package org.solstice.aristotlesComedy.content.item;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.packet.OpenResearchableScreenPacket;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;

public class ResearchableItem extends Item {

	public ResearchableItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		RegistryEntry<Researchable> entry = stack.getOrDefault(AristotlesComponentTypes.RESEARCHABLE, null);
		if (entry == null) return TypedActionResult.pass(stack);

		if (!world.isClient) ServerPlayNetworking.send((ServerPlayerEntity)player, new OpenResearchableScreenPacket(stack, entry));
		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return TypedActionResult.success(stack, world.isClient());
	}

}
