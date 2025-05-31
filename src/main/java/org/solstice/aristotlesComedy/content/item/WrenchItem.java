package org.solstice.aristotlesComedy.content.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.solstice.euclidsElements.content.api.item.InteractionPreventingItem;

public class WrenchItem extends Item implements InteractionPreventingItem {

	public WrenchItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		return this.rotateBlock(context, world, pos, state);
	}

	public ActionResult rotateBlock(ItemUsageContext context, World world, BlockPos pos, BlockState state) {
		PlayerEntity player = context.getPlayer();
		Direction side = context.getSide();
		if (player != null && player.isSneaking()) side = side.getOpposite();

		Pair<ActionResult, Direction> canRotate = this.canRotate(state, side);
		ActionResult result = canRotate.getLeft();
		if (!result.isAccepted()) return result;

		Hand hand = context.getHand();
		ItemStack stack = context.getStack();

		Direction direction = canRotate.getRight();
		world.setBlockState(pos, state.with(Properties.FACING, direction));
		if (player != null) stack.damage(1, player, LivingEntity.getSlotForHand(hand));
		return ActionResult.SUCCESS;
	}

	public Pair<ActionResult, Direction> canRotate(BlockState state, Direction newDirection) {
		if (!state.contains(Properties.FACING)) return new Pair<>(ActionResult.FAIL, newDirection);
		Direction currentDirection = state.get(Properties.FACING);

		if (currentDirection == newDirection) return new Pair<>(ActionResult.FAIL, currentDirection);
		return new Pair<>(ActionResult.SUCCESS, newDirection);
	}

	@Override
	public boolean doBlockInteractions(LivingEntity user, BlockState state, Direction direction) {
		if (user.isSneaking()) direction = direction.getOpposite();
		return this.canRotate(state, direction).getLeft().isAccepted();
	}

}
