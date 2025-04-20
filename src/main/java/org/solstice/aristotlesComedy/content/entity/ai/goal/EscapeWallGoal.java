package org.solstice.aristotlesComedy.content.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class EscapeWallGoal extends Goal {

	protected final PathAwareEntity entity;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final World world;

	public EscapeWallGoal(PathAwareEntity entity) {
		this.entity = entity;
		this.world = entity.getWorld();
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return this.entity.isInsideWall();
	}

	@Override
	public boolean shouldContinue() {
		return !this.entity.getNavigation().isIdle();
	}

	@Override
	public void start() {
		Vec3d location = this.locateAirPocket();
		if (location != null)
			this.entity.getNavigation().startMovingTo(location.x, location.y, location.z, 2);
	}

	@Nullable
	protected Vec3d locateAirPocket() {
		Random random = this.entity.getRandom();
		BlockPos pos = this.entity.getBlockPos();

		for (int i = 0; i < 10; ++i) {
			BlockPos randomPos = pos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
			if (!this.world.getBlockState(pos).isAir() && this.entity.getPathfindingFavor(randomPos) < 0.0F) {
				return Vec3d.ofBottomCenter(randomPos);
			}
		}

		return null;
	}

}
