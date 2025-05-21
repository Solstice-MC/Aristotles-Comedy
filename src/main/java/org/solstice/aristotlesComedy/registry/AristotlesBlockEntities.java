package org.solstice.aristotlesComedy.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.block.entity.AlembicBlockEntity;
import org.solstice.aristotlesComedy.content.block.entity.SabikaBlockEntity;
import org.solstice.aristotlesComedy.content.block.entity.SacredHeartBlockEntity;

public class AristotlesBlockEntities {

	public static void init() {}

	public static final BlockEntityType<SabikaBlockEntity> SABIKA = register("sabika",
		BlockEntityType.Builder.create(SabikaBlockEntity::new, AristotlesBlocks.SABIKA).build()
	);
	public static final BlockEntityType<AlembicBlockEntity> ALEMBIC = register("alembic",
		BlockEntityType.Builder.create(AlembicBlockEntity::new, AristotlesBlocks.ALEMBIC).build()
	);
	public static final BlockEntityType<SacredHeartBlockEntity> SACRED_HEART = register("sacred_heart",
		BlockEntityType.Builder.create(SacredHeartBlockEntity::new, AristotlesBlocks.SACRED_HEART, AristotlesBlocks.PHILOSOPHERS_STONE).build()
	);

	public static <T extends BlockEntityType<?>> T register(String name, T type) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, type);
	}

}
