package org.solstice.aristotlesComedy.client.content.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.content.research.content.ImageContent;
import org.solstice.aristotlesComedy.util.Vec2i;

import java.util.*;

public class ResearchableScreen extends Screen {


//	private static final int MAX_TEXT_WIDTH = 114;
//	private static final int MAX_TEXT_HEIGHT = 128;
//	private static final int WIDTH = 192;
//	private static final int HEIGHT = 192;
//	private static final Text EDIT_TITLE_TEXT = Text.translatable("book.editTitle");
//	private static final Text FINALIZE_WARNING_TEXT = Text.translatable("book.finalizeWarning");
//	private static final OrderedText BLACK_CURSOR_TEXT;
//	private static final OrderedText GRAY_CURSOR_TEXT;

//	private boolean signing;
//	private int tickCounter;
//	private int currentPage;
//	private final List<String> pages = Lists.newArrayList();
//	private String title = "";
//	private final SelectionManager currentPageSelectionManager = new SelectionManager(this::getCurrentPageContent, this::setPageContent, this::getClipboard, this::setClipboard, (string) -> string.length() < 1024 && this.textRenderer.getWrappedLinesHeight(string, 114) <= 128);
//	private final SelectionManager bookTitleSelectionManager = new SelectionManager(() -> this.title, (title) -> this.title = title, this::getClipboard, this::setClipboard, (string) -> string.length() < 16);
//	private long lastClickTime;
//	private int lastClickIndex = -1;
//	private PageTurnWidget nextPageButton;
//	private PageTurnWidget previousPageButton;
//	private ButtonWidget doneButton;
//	private ButtonWidget signButton;
//	private ButtonWidget finalizeButton;
//	private ButtonWidget cancelButton;
//	private final Hand hand;
//	@Nullable
//	private BookEditScreen.PageContent pageContent;
//	private Text pageIndicatorText;
//	private final Text signedByText;

//	private final PlayerEntity player;
//	private final ItemStack itemStack;
//	private boolean dirty;

	private final PlayerEntity player;
	private final RegistryEntry<Researchable> entry;

	public ResearchableScreen(PlayerEntity player, RegistryEntry<Researchable> entry) {
		super(NarratorManager.EMPTY);
		this.player = player;
		this.entry = entry;
//		Vec2i size = entry.value().size();
//		this.width = size.x;
//		this.height = size.y;

//		this.pageContent = BookEditScreen.PageContent.EMPTY;
//		this.pageIndicatorText = ScreenTexts.EMPTY;
//		this.player = player;
//		this.itemStack = itemStack;
//		this.hand = hand;
//		WritableBookContentComponent writableBookContentComponent = (WritableBookContentComponent)itemStack.get(DataComponentTypes.WRITABLE_BOOK_CONTENT);
//		if (writableBookContentComponent != null) {
//			Stream var10000 = writableBookContentComponent.stream(MinecraftClient.getInstance().shouldFilterText());
//			List var10001 = this.pages;
//			Objects.requireNonNull(var10001);
//			var10000.forEach(var10001::add);
//		}

//		if (this.pages.isEmpty()) {
//			this.pages.add("");
//		}

//		this.signedByText = Text.translatable("book.byAuthor", new Object[]{player.getName()}).formatted(Formatting.DARK_GRAY);
	}

//	private void setClipboard(String clipboard) {
//		if (this.client != null) {
//			SelectionManager.setClipboard(this.client, clipboard);
//		}
//	}

//	private String getClipboard() {
//		return this.client != null ? SelectionManager.getClipboard(this.client) : "";
//	}

//	private int countPages() {
//		return this.pages.size();
//	}

//	@Override
//	public void tick() {
//		super.tick();
//		++this.tickCounter;
//	}

//	protected void init() {
//		this.invalidatePageContent();
//		this.signButton = (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("book.signButton"), (button) -> {
//			this.signing = true;
//			this.updateButtons();
//		}).dimensions(this.width / 2 - 100, 196, 98, 20).build());
//		this.doneButton = (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
//			this.client.setScreen((Screen)null);
//			this.finalizeBook(false);
//		}).dimensions(this.width / 2 + 2, 196, 98, 20).build());
//		this.finalizeButton = (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("book.finalizeButton"), (button) -> {
//			if (this.signing) {
//				this.finalizeBook(true);
//				this.client.setScreen((Screen)null);
//			}
//
//		}).dimensions(this.width / 2 - 100, 196, 98, 20).build());
//		this.cancelButton = (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, (button) -> {
//			if (this.signing) {
//				this.signing = false;
//			}
//
//			this.updateButtons();
//		}).dimensions(this.width / 2 + 2, 196, 98, 20).build());
//		int i = (this.width - 192) / 2;
//		int j = 2;
//		this.nextPageButton = (PageTurnWidget)this.addDrawableChild(new PageTurnWidget(i + 116, 159, true, (button) -> this.openNextPage(), true));
//		this.previousPageButton = (PageTurnWidget)this.addDrawableChild(new PageTurnWidget(i + 43, 159, false, (button) -> this.openPreviousPage(), true));
//		this.updateButtons();
//	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		Researchable researchable = this.entry.value();

		researchable.contents().stream()
			.filter(content -> content instanceof ImageContent)
			.map(content -> (ImageContent) content)
			.forEach(content -> {
				Identifier texture = content.path().withPrefixedPath("gui/researchable/").withSuffixedPath(".png");
				Vec2i size = this.entry.value().size();
				context.drawTexture(
					texture,
					(this.width - size.x) / 2,
					(this.height - size.y) / 2,
					0, 0, size.x * 2, size.y * 2
				);
			});

//		Identifier background = Researchable.getFrontTexture(this.entry);
//		Vec2i size = this.entry.value().size();
//		context.drawTexture(
//			background,
//			(this.width - size.x) / 2,
//			(this.height - size.y) / 2,
//			0, 0, size.x * 2, size.y * 2
//		);
	}


//	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//		super.render(context, mouseX, mouseY, delta);
//		this.setFocused((Element)null);
//		int i = (this.width - 192) / 2;
//		int j = 2;
//		if (this.signing) {
//			boolean bl = this.tickCounter / 6 % 2 == 0;
//			OrderedText orderedText = OrderedText.concat(OrderedText.styledForwardsVisitedString(this.title, Style.EMPTY), bl ? BLACK_CURSOR_TEXT : GRAY_CURSOR_TEXT);
//			int k = this.textRenderer.getWidth(EDIT_TITLE_TEXT);
//			context.drawText(this.textRenderer, EDIT_TITLE_TEXT, i + 36 + (114 - k) / 2, 34, 0, false);
//			int l = this.textRenderer.getWidth(orderedText);
//			context.drawText(this.textRenderer, orderedText, i + 36 + (114 - l) / 2, 50, 0, false);
//			int m = this.textRenderer.getWidth(this.signedByText);
//			context.drawText(this.textRenderer, this.signedByText, i + 36 + (114 - m) / 2, 60, 0, false);
//			context.drawTextWrapped(this.textRenderer, FINALIZE_WARNING_TEXT, i + 36, 82, 114, 0);
//		} else {
//			int n = this.textRenderer.getWidth(this.pageIndicatorText);
//			context.drawText(this.textRenderer, this.pageIndicatorText, i - n + 192 - 44, 18, 0, false);
//			BookEditScreen.PageContent pageContent = this.getPageContent();
//
//			for(BookEditScreen.Line line : pageContent.lines) {
//				context.drawText(this.textRenderer, line.text, line.x, line.y, -16777216, false);
//			}
//
//			this.drawSelection(context, pageContent.selectionRectangles);
//			this.drawCursor(context, pageContent.position, pageContent.atEnd);
//		}
//
//	}

	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderInGameBackground(context);
		Identifier background = Researchable.getFrontTexture(this.entry);
		Vec2i size = this.entry.value().size();
		context.drawTexture(
			background,
			(this.width - size.x) / 2,
			(this.height - size.y) / 2,
			0, 0, size.x * 2, size.y * 2
		);
	}

//	private void drawCursor(DrawContext context, Vec2i position, boolean atEnd) {
//		if (this.tickCounter / 6 % 2 == 0) {
//			position = this.absolutePositionToScreenPosition(position);
//			if (!atEnd) {
//				int var10001 = position.x;
//				int var10002 = position.y - 1;
//				int var10003 = position.x + 1;
//				int var10004 = position.y;
//				Objects.requireNonNull(this.textRenderer);
//				context.fill(var10001, var10002, var10003, var10004 + 9, -16777216);
//			} else {
//				context.drawText(this.textRenderer, "_", position.x, position.y, 0, false);
//			}
//		}
//	}

//	private void drawSelection(DrawContext context, Rect2i[] selectionRectangles) {
//		for(Rect2i rect2i : selectionRectangles) {
//			int i = rect2i.getX();
//			int j = rect2i.getY();
//			int k = i + rect2i.getWidth();
//			int l = j + rect2i.getHeight();
//			context.fill(RenderLayer.getGuiTextHighlight(), i, j, k, l, -16776961);
//		}
//	}

//	private Vec2i screenPositionToAbsolutePosition(Vec2i position) {
//		return new Vec2i(position.x - (this.width - 192) / 2 - 36, position.y - 32);
//	}

//	private Vec2i absolutePositionToScreenPosition(Vec2i position) {
//		return new Vec2i(position.x + (this.width - 192) / 2 + 36, position.y + 32);
//	}








	static int getLineFromOffset(int[] lineStarts, int position) {
		int i = Arrays.binarySearch(lineStarts, position);
		return i < 0 ? -(i + 2) : i;
	}





	@Environment(EnvType.CLIENT)
	static class Line {
		final Style style;
		final String content;
		final Text text;
		final int x;
		final int y;

		public Line(Style style, String content, int x, int y) {
			this.style = style;
			this.content = content;
			this.x = x;
			this.y = y;
			this.text = Text.literal(content).setStyle(style);
		}
	}

	@Environment(EnvType.CLIENT)
	static class PageContent {
		private final String pageContent;
		final Vec2i position;
		final boolean atEnd;
		private final int[] lineStarts;
		final Line[] lines;
		final Rect2i[] selectionRectangles;

		public PageContent(String pageContent, Vec2i position, boolean atEnd, int[] lineStarts, Line[] lines, Rect2i[] selectionRectangles) {
			this.pageContent = pageContent;
			this.position = position;
			this.atEnd = atEnd;
			this.lineStarts = lineStarts;
			this.lines = lines;
			this.selectionRectangles = selectionRectangles;
		}

		public int getCursorPosition(TextRenderer renderer, Vec2i position) {
			int var10000 = position.y;
			Objects.requireNonNull(renderer);
			int i = var10000 / 9;
			if (i < 0) {
				return 0;
			} else if (i >= this.lines.length) {
				return this.pageContent.length();
			} else {
				Line line = this.lines[i];
				return this.lineStarts[i] + renderer.getTextHandler().getTrimmedLength(line.content, position.x, line.style);
			}
		}

		public int getVerticalOffset(int position, int lines) {
			int i = getLineFromOffset(this.lineStarts, position);
			int j = i + lines;
			int m;
			if (0 <= j && j < this.lineStarts.length) {
				int k = position - this.lineStarts[i];
				int l = this.lines[j].content.length();
				m = this.lineStarts[j] + Math.min(k, l);
			} else {
				m = position;
			}

			return m;
		}

		public int getLineStart(int position) {
			int i = getLineFromOffset(this.lineStarts, position);
			return this.lineStarts[i];
		}

		public int getLineEnd(int position) {
			int i = getLineFromOffset(this.lineStarts, position);
			return this.lineStarts[i] + this.lines[i].content.length();
		}

	}

}
