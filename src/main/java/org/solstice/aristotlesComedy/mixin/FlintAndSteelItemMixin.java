package org.solstice.aristotlesComedy.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {

	/**
	 * @author Sindercube
	 * @reason IDK anymore
	 */
	@Overwrite
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		ItemStack stack = context.getStack();

		boolean isNotLit = state.contains(Properties.LIT) && !state.get(Properties.LIT);
		boolean isNotWaterlogged = state.contains(Properties.WATERLOGGED) && !state.get(Properties.WATERLOGGED);

		ActionResult result;
		if (isNotLit && isNotWaterlogged) result = lightBlock(world, player, pos, state);
		else result = trySetFire(world, player, pos, stack, context.getSide(), context.getHorizontalPlayerFacing());

		if (result.isAccepted() && player != null)
			stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));

		return result;
	}

	@Unique
	private static ActionResult trySetFire(World world, PlayerEntity player, BlockPos pos, ItemStack stack, Direction side, Direction facing) {
		BlockPos offset = pos.offset(side);
		if (!AbstractFireBlock.canPlaceAt(world, offset, facing)) return ActionResult.FAIL;

		world.playSound(player, offset, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
		BlockState blockState2 = AbstractFireBlock.getState(world, offset);
		world.setBlockState(offset, blockState2, 11);
		world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
		if (player instanceof ServerPlayerEntity serverPlayer) {
			Criteria.PLACED_BLOCK.trigger(serverPlayer, offset, stack);
		}

		return ActionResult.success(world.isClient());
	}

	@Unique
	private static ActionResult lightBlock(World world, PlayerEntity player, BlockPos pos, BlockState state) {
		world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
		world.setBlockState(pos, state.with(Properties.LIT, true));
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		return ActionResult.success(world.isClient());
	}

}
