package org.solstice.aristotlesComedy.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ItemCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

public class MaxStackSizeMixins {

	public static final int MAX_STACK_SIZE_CAP = 1024;

	@Mixin(DataComponentTypes.class)
	public static class DataComponentTypesMixin {

		@ModifyArg(method = "method_58570",  at = @At(value = "INVOKE", target = "Lnet/minecraft/util/dynamic/Codecs;rangedInt(II)Lcom/mojang/serialization/Codec;"), index = 1)
		private static int changeMaxStackSizeLimit(int original) {
			return MAX_STACK_SIZE_CAP;
		}

	}

	@Mixin(Inventory.class)
	public interface InventoryMixin {

		@ModifyConstant( method = "getMaxCountPerStack", constant = @Constant(intValue = 99))
		private int changeMaxStackSizeLimit(int original) {
			return MAX_STACK_SIZE_CAP;
		}

	}

	@Mixin(ItemCommand.class)
	public abstract static class ItemCommandMixin {

		@ModifyConstant( method = "register", constant = @Constant(intValue = 99))
		private static int changeMaxStackSizeLimit(int original) {
			return MAX_STACK_SIZE_CAP;
		}

	}

	@Mixin(ItemStack.class)
	public abstract static class ItemStackMixin {

		@ModifyArg(method = "method_57371", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/dynamic/Codecs;rangedInt(II)Lcom/mojang/serialization/Codec;"), index = 1)
		private static int changeMaxStackSizeLimit(int original) { return MAX_STACK_SIZE_CAP; }

	}

}
