package dev.apollointhehouse.worlddownloader.gui;

import dev.apollointhehouse.worlddownloader.Events;
import dev.apollointhehouse.worlddownloader.events.ToggleDownloadEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.renderer.GLRenderer;
import turniplabs.halplibe.util.dependency.Key;

import static dev.apollointhehouse.worlddownloader.WorldDownloader.MOD_ID;

public class DownloadProgressDisplay extends HudComponentMovable  {
	private final Minecraft mc = Minecraft.getMinecraft();
	private boolean enabled;

	public DownloadProgressDisplay(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);

		Events.TOGGLE_DOWNLOAD_EVENT.listen(Key.of(MOD_ID), this::toggle);
	}

	private void toggle(ToggleDownloadEvent event) {
		this.enabled = event.enabled();
	}

	@Override
	public boolean isVisible() {
		return enabled;
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
