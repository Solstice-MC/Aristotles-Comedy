package org.solstice.aristotlesComedy.registry;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import org.solstice.aristotlesComedy.content.entity.AshBunnyEntity;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

	@SubscribeEvent
	public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
		event.put(ASH_BUNNY.get(), AshBunnyEntity.createAshBunnyAttributes().build());
	}

	public static final DeferredRegister<EntityType<?>> REGISTRY =
		DeferredRegister.create(Registries.ENTITY_TYPE, AristotlesComedy.MOD_ID);

	public static final DeferredHolder<EntityType<?>, EntityType<AshBunnyEntity>> ASH_BUNNY = register("ash_bunny",
		EntityType.Builder.create(AshBunnyEntity::new, SpawnGroup.CREATURE)
			.dimensions(0.5f, 0.5f)
			.maxTrackingRange(10)
	);

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
		return REGISTRY.register(name, id -> builder.build(id.toString()));
	}

}
