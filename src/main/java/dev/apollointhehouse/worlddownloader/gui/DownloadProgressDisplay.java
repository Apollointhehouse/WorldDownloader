package dev.apollointhehouse.worlddownloader.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.renderer.GLRenderer;

public class DownloadProgressDisplay extends HudComponentMovable  {
	private final Minecraft mc = Minecraft.getMinecraft();

	public DownloadProgressDisplay(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void render(HudIngame hud, int xSizeScreen, int ySizeScreen, float delta) {
		final var x = getDisplayedXSize() + getLayout().getComponentX(this, xSizeScreen);
		final var y = getLayout().getComponentY(this, ySizeScreen);

		GLRenderer.setColor1i(0xFF00FF00);

		final var text = "Saving Chunks...";

		hud.drawStringShadow(
			mc.font,
			text,
			x - mc.font.stringWidth(text),
			y,
			0xFF00FF00
			);
	}

	@Override
	public void renderPreview(Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		final var x = getDisplayedXSize() + getLayout().getComponentX(this, xSizeScreen);
		final var y = getLayout().getComponentY(this, ySizeScreen);

		GLRenderer.setColor1i(0xFF00FF00);

		final var text = "Saving Chunks...";

		gui.drawStringShadow(
			mc.font,
			text,
			x - mc.font.stringWidth(text),
			y,
			0xFF00FF00
		);
	}
}
