package org.solstice.aristotlesComedy.content.block.tank;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.block.entity.TankBlockEntity;

import java.util.Map;

public class TankBlock extends AbstractTankBlock {

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(TankBlock::new);
	}

	public TankBlock(Settings settings) {
		super(settings);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
	}

}
