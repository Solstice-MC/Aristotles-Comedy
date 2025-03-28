package org.solstice.aristotlesComedy.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AthanorBlock extends AbstractFurnaceBlock {

	public static final MapCodec<AthanorBlock> CODEC = createCodec(AthanorBlock::new);

	public AthanorBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends AbstractFurnaceBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player) {

	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}

}
