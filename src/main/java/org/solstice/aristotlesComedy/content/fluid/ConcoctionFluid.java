package org.solstice.aristotlesComedy.content.fluid;

import net.neoforged.neoforge.fluids.FluidStack;
import org.solstice.aristotlesComedy.registry.ModFluids;

public class ConcoctionFluid extends EmptyFluid {


	public static FluidStack of(int amount) {
		FluidStack stack = new FluidStack(ModFluids.CONCOCTION, amount);
		return stack;
	}

}
