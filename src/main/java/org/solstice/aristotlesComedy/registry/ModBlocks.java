package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.content.block.*;

import java.util.function.Function;

public class ModBlocks {

	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.Blocks.createBlocks(AristotlesComedy.MOD_ID);


	public static final DeferredBlock<Block> GLASS_TANK = register("glass_tank", settings -> new TankBlock(settings), AbstractBlock.Settings.copy(Blocks.GLASS));
	public static final DeferredBlock<Block> BRONZE_TANK = register("bronze_tank", settings -> new TankBlock(settings), AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
	public static final DeferredBlock<Block> BRONZE_PIPE = register("bronze_pipe", settings -> new PipeBlock(settings), AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

	public static final DeferredBlock<Block> YELLOW_TRISMEGISTITE_BLOCK = register("yellow_trismegistite_block", AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final DeferredBlock<Block> RED_TRISMEGISTITE_BLOCK = register("red_trismegistite_block", AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final DeferredBlock<Block> BLUE_TRISMEGISTITE_BLOCK = register("blue_trismegistite_block", AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final DeferredBlock<Block> BLACK_TRISMEGISTITE_BLOCK = register("black_trismegistite_block", AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));

	public static final DeferredBlock<Block> YELLOW_TRISMEGISTITE_CLUSTER = register("yellow_trismegistite_cluster", ClusterBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER));
	public static final DeferredBlock<Block> RED_TRISMEGISTITE_CLUSTER = register("red_trismegistite_cluster", ClusterBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER));
	public static final DeferredBlock<Block> BLUE_TRISMEGISTITE_CLUSTER = register("blue_trismegistite_cluster", ClusterBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER));
	public static final DeferredBlock<Block> BLACK_TRISMEGISTITE_CLUSTER = register("black_trismegistite_cluster", ClusterBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER));

	public static final DeferredBlock<Block> BUDDING_YELLOW_TRISMEGISTITE = register("budding_yellow_trismegistite",
		settings -> new BuddingClusterBlock(YELLOW_TRISMEGISTITE_CLUSTER.get(), settings),
		AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST)
	);
	public static final DeferredBlock<Block> BUDDING_RED_TRISMEGISTITE = register("budding_red_trismegistite",
		settings -> new BuddingClusterBlock(RED_TRISMEGISTITE_CLUSTER.get(), settings),
		AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST)
	);
	public static final DeferredBlock<Block> BUDDING_BLUE_TRISMEGISTITE = register("budding_blue_trismegistite",
		settings -> new BuddingClusterBlock(BLUE_TRISMEGISTITE_CLUSTER.get(), settings),
		AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST)
	);
	public static final DeferredBlock<Block> BUDDING_BLACK_TRISMEGISTITE = register("budding_black_trismegistite",
		settings -> new BuddingClusterBlock(BLACK_TRISMEGISTITE_CLUSTER.get(), settings),
		AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST)
	);

	public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = register("deepslate_tin_ore", AbstractBlock.Settings.copy(Blocks.DEEPSLATE_COPPER_ORE));
    public static final DeferredBlock<Block> TIN_ORE = register("tin_ore", AbstractBlock.Settings.copy(Blocks.COPPER_ORE));
	public static final DeferredBlock<Block> NETHER_LEAD_ORE = register("nether_lead_ore", AbstractBlock.Settings.copy(Blocks.IRON_ORE));

	public static final DeferredBlock<Block> LEAD_BARREL = register("lead_barrel", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK));
	public static final DeferredBlock<Block> LEAD_BARS = register("lead_bars", PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS));
	public static final DeferredBlock<Block> BRAZIER = register("brazier", settings -> new BrazierBlock(true, settings), AbstractBlock.Settings.copy(Blocks.IRON_BARS));
	public static final DeferredBlock<Block> SOUL_BRAZIER = register("soul_brazier", settings -> new BrazierBlock(false, settings), AbstractBlock.Settings.copy(Blocks.IRON_BARS));

	public static final DeferredBlock<Block> SACRED_HEART = register("sacred_heart", PhilosopherStoneBlock::new, AbstractBlock.Settings.create());
	public static final DeferredBlock<Block> PHILOSOPHERS_STONE = register("philosophers_stone", PhilosopherStoneBlock::new, AbstractBlock.Settings.create());
	public static final DeferredBlock<Block> ASH = register("ash", SnowBlock::new, AbstractBlock.Settings.copy(Blocks.SNOW));
	public static final DeferredBlock<Block> ASH_BLOCK = register("ash_block", Block::new, AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK));
	public static final DeferredBlock<Block> NUMITRON = register("numitron", NumitronBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS).luminance(s -> 2));

	public static final DeferredBlock<Block> SABIKA = register("sabika", AthanorBlock::new, AbstractBlock.Settings.copy(Blocks.FURNACE));
	public static final DeferredBlock<Block> ATHANOR = register("athanor", AthanorBlock::new, AbstractBlock.Settings.copy(Blocks.FURNACE));

	public static final DeferredBlock<Block> ALEMBIC = register("alembic", AlembicBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));


	public static DeferredBlock<Block> register(String name, AbstractBlock.Settings settings) {
		return register(name, Block::new, settings);
	}

	public static DeferredBlock<Block> register(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
		return register(name, function, settings, new Item.Settings());
	}

	public static DeferredBlock<Block> register(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		DeferredBlock<Block> block = REGISTRY.registerBlock(name, function, blockSettings);
		ModItems.REGISTRY.registerSimpleBlockItem(block, itemSettings);
		return block;
    }

}
