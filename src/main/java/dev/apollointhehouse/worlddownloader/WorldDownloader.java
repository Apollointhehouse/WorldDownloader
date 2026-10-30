package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.event.KeyPressCallback;
import dev.apollointhehouse.worlddownloader.gui.DownloadProgressDisplay;
import dev.apollointhehouse.worlddownloader.io.*;
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

import java.io.File;

public class WorldDownloader implements ModInitializer, KeyPressCallback {
	public static final String MOD_ID = HalpLibe.registerMod("worlddownloader", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static ChunksDownloader downloader;

	@Override
	public void onInitialize() {
		CommonEvents.AFTER_GAME_START.listen(Key.of(MOD_ID), this::afterGameStart);
		LOGGER.info("WorldDownloader initialized.");
	}

	public void afterGameStart() {
		final var layout = new LayoutAbsolute(0.5f, 0f, ComponentAnchor.TOP_CENTER);
		HudComponents.register(
			new DownloadProgressDisplay(
				"progress_display",
				100, 20,
				layout
			)
		);

		OptionsPages.CONTROLS
			.withComponent(
				new OptionsCategory("worlddownloader.toggle")
					.withComponent(new KeyBindingComponent(WorldDownloaderSettings.keybind)
				)
			);

		KeyPressCallback.EVENT.listen(Key.of(MOD_ID), this);
	}

	@Override
	public void handle(KeyPressEvent event) {
		final var mc = Minecraft.getMinecraft();

		if (mc.currentWorld == null) return;
		if (!WorldDownloaderSettings.keybind.isPressEvent(event.currentInputDevice())) return;

		if (downloader == null) {
			final var level = LevelDataSource.of(mc.currentWorld);
			final var position = PositionSource.of(mc.thePlayer);

			final var saveDir = new File(mc.getMinecraftDir(), "saves");
			final var saver = new ChunksSaver(saveDir, level);

			downloader = new ChunksDownloader(position, level, saver);
			downloader.start();
			return;
		}

		if (downloader.isRunning()) {
			downloader.stop();
			downloader = null;
		}
	}
}
