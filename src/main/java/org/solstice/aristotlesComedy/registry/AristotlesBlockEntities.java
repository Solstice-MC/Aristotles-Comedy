package org.solstice.aristotlesComedy.registry;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.block.entity.*;

public class AristotlesBlockEntities {

	public static void init() {
		FluidStorage.SIDED.registerForBlockEntity(TankBlockEntity::getStorage, FLUID_TANK);
	}

	public static final BlockEntityType<TankBlockEntity> FLUID_TANK = register("fluid_tank",
		BlockEntityType.Builder.create(TankBlockEntity::new, AristotlesBlocks.BRONZE_TANK).build()
	);
	public static final BlockEntityType<SabikaBlockEntity> SABIKA = register("sabika",
		BlockEntityType.Builder.create(SabikaBlockEntity::new, AristotlesBlocks.SABIKA).build()
	);
	public static final BlockEntityType<SacredHeartBlockEntity> SACRED_HEART = register("sacred_heart",
		BlockEntityType.Builder.create(SacredHeartBlockEntity::new, AristotlesBlocks.SACRED_HEART, AristotlesBlocks.PHILOSOPHERS_STONE).build()
	);
	public static final BlockEntityType<ResearchBlockEntity> RESEARCH = register("research",
		BlockEntityType.Builder.create(ResearchBlockEntity::new, AristotlesBlocks.RESEARCH).build()
	);

	public static <T extends BlockEntityType<?>> T register(String name, T type) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, type);
	}

}
