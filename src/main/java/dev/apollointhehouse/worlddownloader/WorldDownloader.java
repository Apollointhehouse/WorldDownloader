package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.gui.DownloadProgressDisplay;
import dev.apollointhehouse.worlddownloader.options.WorldDownloaderSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutAbsolute;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.dependency.Key;

public class WorldDownloader implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("worlddownloader", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static Downloader downloader;

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(Key.of(MOD_ID), this::beforeGameStart);
		CommonEvents.AFTER_GAME_START.listen(Key.of(MOD_ID), this::afterGameStart);
		LOGGER.info("WorldDownloader initialized.");
	}

	public void beforeGameStart() {

	}

	public void afterGameStart() {

		final var layout = new LayoutAbsolute(0.5f, 0f, ComponentAnchor.TOP_CENTER);
		final var progessComponenet = HudComponents.register(
			new DownloadProgressDisplay(
				"progress_display",
				100, 20,
				layout
			)
		);

		OptionsPages.CONTROLS
			.withComponent(
				new OptionsCategory("worlddownloader")
					.withComponent(new KeyBindingComponent(WorldDownloaderSettings.keybind)
				)
			);

		Events.TICK_EVENT.listen(Key.of(MOD_ID), this::onTick);
	}

	private void onTick() {
		Minecraft mc = Minecraft.getMinecraft();

		if (downloader == null) {
			downloader = new Downloader(mc.thePlayer, mc.currentWorld);
		}

		if (WorldDownloaderSettings.keybind.isReleaseEvent()) {
			LOGGER.info("toggle!");
			downloader.toggle();
		}
	}
}
