package org.solstice.aristotlesComedy.datagen.mixin;

import net.minecraft.registry.RegistryBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

// this is such a monkey patch lmao

@Mixin(RegistryBuilder.class)
public class RegistryBuilderMixin {

	@Redirect(
		method = "createWrapperLookup(Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/registry/RegistryBuilder$Registries;checkUnreferencedKeys()V"
		)
	)
	private void ignoreReferenceErrors(@Coerce Object instance) {}

}
