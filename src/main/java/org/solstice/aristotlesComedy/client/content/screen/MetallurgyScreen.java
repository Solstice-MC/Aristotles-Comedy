package org.solstice.aristotlesComedy.client.content.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.screen.handler.MetallurgyScreenHandler;

public class MetallurgyScreen extends HandledScreen<MetallurgyScreenHandler> {

	private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = AristotlesComedy.of("item/empty_slot_ingot");
	private static final Identifier LIT_PROGRESS_TEXTURE = AristotlesComedy.of("container/metallurgy/lit_progress");
	private static final Identifier BURN_PROGRESS_TEXTURE = AristotlesComedy.of("container/metallurgy/burn_progress");
	private static final Identifier BACKGROUND_TEXTURE = AristotlesComedy.of("textures/gui/container/metallurgy.png");

	public MetallurgyScreen(MetallurgyScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void init() {
		super.init();
		this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		context.drawTexture(BACKGROUND_TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		Slot slot = this.handler.getSlot(0);
		if (!slot.hasStack()) {
			context.drawGuiTexture(EMPTY_SLOT_INGOT_TEXTURE, this.x + slot.x, this.y + slot.y, 16, 16);
		}
		if (this.handler.isBurning()) {
			int l = MathHelper.ceil(this.handler.getFuelProgress() * 13) + 1;
			context.drawGuiTexture(LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - l, this.x + 56, this.y + 36 + 14 - l, 14, l);
		}
		int l = MathHelper.ceil(this.handler.getCookProgress() * 24);
		context.drawGuiTexture(BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, this.x + 79, this.y + 34, l, 16);
	}

}
