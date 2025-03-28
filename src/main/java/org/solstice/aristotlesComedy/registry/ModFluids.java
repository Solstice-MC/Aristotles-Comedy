package org.solstice.aristotlesComedy.registry;

import net.neoforged.neoforge.registries.DeferredHolder;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.content.fluid.ConcoctionFluid;

public class ModFluids {

	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(Registries.FLUID, AristotlesComedy.MOD_ID);

	public static final DeferredHolder<Fluid, Fluid> CONCOCTION = register("concoction",
		new ConcoctionFluid()
	);

	private static DeferredHolder<Fluid, Fluid> register(String name, Fluid fluid) {
		return REGISTRY.register(name, () -> fluid);
	}

}
