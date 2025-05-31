package org.solstice.aristotlesComedy.content.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.ComponentChanges;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.humor.Humor;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;
import org.solstice.aristotlesComedy.registry.AristotlesFluids;

public class HumorFluid extends EmptyFluid {

	public static FluidVariant withHumor(RegistryEntry<Humor> humor) {
		ComponentChanges changes = ComponentChanges.builder()
			.add(AristotlesComponentTypes.HUMOR, humor)
			.build();
		return FluidVariant.of(AristotlesFluids.HUMOR, changes);
	}

	@Override
	public boolean isStill(FluidState state) {
		return true;
	}

}
