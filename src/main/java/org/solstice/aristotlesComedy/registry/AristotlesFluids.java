package org.solstice.aristotlesComedy.registry;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.fluid.HumorFluid;

public class AristotlesFluids {

	public static void init() {
//		FluidStorage.combinedItemApiProvider(AristotlesItems.HUMOR_VIAL).register(context ->
//			new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(HONEY), HONEY_BOTTLE_AMOUNT)
//		);
//		FluidStorage.combinedItemApiProvider(AristotlesItems.HUMOR_VIAL).register(context ->
//			new EmptyItemFluidStorage(context, bottle -> ItemVariant.of(Items.HONEY_BOTTLE), HONEY, HONEY_BOTTLE_AMOUNT)
//		);
	}

	public static final Fluid HUMOR = register("humor", new HumorFluid());

	public static Fluid register(String name, Fluid fluid) {
		return Registry.register(Registries.FLUID, AristotlesComedy.of(name), fluid);
	}

}
