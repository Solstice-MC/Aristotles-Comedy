package org.solstice.aristotlesComedy.content.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.solstice.aristotlesComedy.registry.AristotlesSoundEvents;
import org.solstice.euclidsElements.content.api.item.*;

public class PlatinumGloveItem extends ToolItem implements PoseableItem, GenericAttackingItem, CollapsableItem {

	public PlatinumGloveItem(ToolMaterial material, Settings settings) {
		super(material, settings);
	}

	public static double getCarryingWeight(LivingEntity entity) {
		double result = 0;
		for (Entity passenger : entity.getPassengersDeep()) {
			if (passenger instanceof FallingBlockEntity) result += 0.5;
			else result += entity.getBoundingBox().getAverageSideLength();
		}
		return result;
	}

	public ActionResult carryEntity(PlayerEntity player, Entity entity) {
		Entity currentEntity = player;
		while (currentEntity != null && currentEntity.hasPassengers()) {
			currentEntity = currentEntity.getFirstPassenger();
		}
		entity.startRiding(currentEntity, true);

		double multiplier = getCarryingWeight(player);
		player.getItemCollapseManager().set(this, (int) Math.round(multiplier * 200));
		return ActionResult.SUCCESS;
	}

	public FallingBlockEntity createCarriableBlock(World world, BlockPos pos, BlockState state) {
		FallingBlockEntity entity = new FallingBlockEntity(world,
			0, Integer.MAX_VALUE, 0,
			state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state
		);
		entity.timeFalling = Integer.MIN_VALUE;

		if (world.isClient) return entity;

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null) {
			entity.blockEntityData = blockEntity.createNbt(world.getRegistryManager().toImmutable());
			blockEntity.markRemoved();
		}

		BlockSoundGroup sound = state.getSoundGroup();
		world.playSoundAtBlockCenter(pos,
			sound.getBreakSound(),
			SoundCategory.BLOCKS,
			(sound.getVolume() + 1.0F) / 2.0F,
			sound.getPitch() * 0.8F,
			false
		);

		world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
		world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(null, state));

		return entity;
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		return this.carryEntity(player, entity);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (world.isClient()) return ActionResult.PASS;

		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);

		FallingBlockEntity entity = createCarriableBlock(world, pos, state);

		ActionResult result = this.carryEntity(context.getPlayer(), entity);
		world.spawnEntity(entity);
		return result;
	}

	@Override
	public void genericAttack(World world, PlayerEntity player, ItemStack stack) {
		if (player.isSneaking()) return;
		double multiplier = getCarryingWeight(player);

		Entity currentEntity = player;
		while (currentEntity != null && currentEntity.hasPassengers()) {
			currentEntity = currentEntity.getFirstPassenger();
			if (currentEntity != null) this.throwEntity(world, player, currentEntity, multiplier);
			multiplier *= 1.5;
		}
	}

	public void throwEntity(World world, PlayerEntity player, Entity entity, double multiplier) {
		entity.stopRiding();
		world.playSound(null, player.getX(), player.getY(), player.getZ(), AristotlesSoundEvents.ITEM_PLATINUM_GLOVE_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!world.isClient) this.throwEntity(player, entity, multiplier);
	}

	public void throwEntity(PlayerEntity user, Entity entity, double multiplier) {
		Vec3d rotation = new Vec3d(user.getPitch(), user.getYaw(), 0);
		Vec3d velocity = new Vec3d(
			-MathHelper.sin((float) (rotation.y * ((float)Math.PI / 180F))) * MathHelper.cos((float) (rotation.x * ((float)Math.PI / 180F))),
			-MathHelper.sin((float) ((rotation.x + rotation.z) * ((float)Math.PI / 180F))),
			MathHelper.cos((float) (rotation.y * ((float)Math.PI / 180F))) * MathHelper.cos((float) (rotation.x * ((float)Math.PI / 180F)))
		);

		double length = velocity.horizontalLength();
		entity.setYaw((float)(MathHelper.atan2(velocity.x, velocity.z) * (double)(180F / (float)Math.PI)));
		entity.setPitch((float)(MathHelper.atan2(velocity.y, length) * (double)(180F / (float)Math.PI)));
		entity.prevYaw = entity.getYaw();
		entity.prevPitch = entity.getPitch();

		Random random = user.getRandom();
		velocity = velocity.normalize().add(
			random.nextTriangular(0, 0.0172275),
			random.nextTriangular(0, 0.0172275),
			random.nextTriangular(0, 0.0172275)
		).multiply(1 / multiplier);

		Vec3d movement = user.getMovement();
		velocity = velocity.add(movement.x, user.isOnGround() ? (double)0.0F : movement.y, movement.z);
		entity.setVelocity(velocity);
		entity.velocityModified = true;
	}

	@Override
	public void onCollapse(World world, PlayerEntity user) {
		Entity currentEntity = user;
		while (currentEntity != null && currentEntity.hasPassengers()) {
			currentEntity = currentEntity.getFirstPassenger();
			if (currentEntity != null) currentEntity.stopRiding();
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void poseModel(ModelPart root, LivingEntity entity, ItemStack stack, Arm arm) {
		if (!entity.hasPassengers()) return;
		ModelPart handModel = PoseableItem.getArmModel(root, arm);
		handModel.pitch += 60;
	}

}
