package org.solstice.aristotlesComedy.registry;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import org.solstice.aristotlesComedy.content.block.entity.AlembicBlockEntity;

public class ModBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
		DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AristotlesComedy.MOD_ID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlembicBlockEntity>> ALEMBIC = register("alembic",
		BlockEntityType.Builder.create(AlembicBlockEntity::new, ModBlocks.ALEMBIC.get()).build(null)
	);

	public static <T extends BlockEntityType<?>> DeferredHolder<BlockEntityType<?>, T> register(String name, T type) {
		return REGISTRY.register(name, () -> type);
	}

}
