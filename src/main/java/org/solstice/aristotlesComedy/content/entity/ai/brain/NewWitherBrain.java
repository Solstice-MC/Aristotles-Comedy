package org.solstice.aristotlesComedy.content.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.solstice.aristotlesComedy.registry.ModEntityActivities;

import java.util.Set;

public class NewWitherBrain {

	public static final Set<Activity> ACTIVITIES = ImmutableSet.of(
		ModEntityActivities.WITHER_RECOVERY,
		Activity.CORE
	);

	protected static Brain<?> create(Brain<AllayEntity> brain) {
		addCoreActivities(brain);
		addIdleActivities(brain);
		brain.setCoreActivities(ACTIVITIES);
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addCoreActivities(Brain<AllayEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
			new StayAboveWaterTask(0.8F)
//			new FleeTask(2.5F)
//			new LookAroundTask(45, 90),
//			new MoveToTargetTask(),
//			new TemptationCooldownTask(MemoryModuleType.LIKED_NOTEBLOCK_COOLDOWN_TICKS),
//			new TemptationCooldownTask(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
		));
	}

	private static void addIdleActivities(Brain<AllayEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(
			Pair.of(0, WalkToNearestVisibleWantedItemTask.create((allay) -> true, 1.75F, true, 32)),
//			Pair.of(1, new GiveInventoryToLookTargetTask(AllayBrain::getLookTarget, 2.25F, 20)),
//			Pair.of(2, WalkTowardsLookTargetTask.create(AllayBrain::getLookTarget, Predicate.not(AllayBrain::hasNearestVisibleWantedItem), 4, 16, 2.25F)),
			Pair.of(3, LookAtMobWithIntervalTask.follow(6.0F, UniformIntProvider.create(30, 60))),
			Pair.of(4, new RandomTask(ImmutableList.of(Pair.of(StrollTask.createSolidTargeting(1.0F), 2),
				Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 2),
				Pair.of(new WaitTask(30, 60), 1)))
			)),
			ImmutableSet.of()
		);
	}

	public static void updateActivities(AllayEntity allay) {
		allay.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
	}

}
