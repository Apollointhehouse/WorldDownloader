package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.events.BlockChangeEvent;
import dev.apollointhehouse.worlddownloader.events.ChunkSectionDataEvent;
import dev.apollointhehouse.worlddownloader.events.ToggleDownloadEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.ChunkPos;
import turniplabs.halplibe.util.dependency.Key;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static dev.apollointhehouse.worlddownloader.Events.*;
import static dev.apollointhehouse.worlddownloader.WorldDownloader.LOGGER;
import static dev.apollointhehouse.worlddownloader.WorldDownloader.MOD_ID;

public class Downloader {
	private World world;
	private final Player player;
	private final Minecraft mc = Minecraft.getMinecraft();
	private WorldSaver saver;
	private boolean enabled;

	public Downloader(Player player, World world) {
		this.world = world;
		this.player = player;

		this.saver = new WorldSaver(mc, world);
		this.enabled = false;
	}

	private final Map<Integer, DimData> dimensions = new ConcurrentHashMap<>();

	private DimData dim() {
		return dimensions.computeIfAbsent(player.dimension, id -> new DimData(world.dimension));
	}

	public void toggle() {
		enabled = !enabled;

		ToggleDownloadEvent event = new ToggleDownloadEvent(this);
		TOGGLE_DOWNLOAD_EVENT.emit(c -> c.accept(event));

		if (enabled) onEnable(); else onDisable();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int downloadSize() {
		int chunks = 0;

		for (var data : dimensions.values()) {
			chunks += data.getChunks().size();
		}

		return chunks;
	}

	private void onEnable() {
		LOGGER.info("Downloading chunks!");

		BLOCK_CHANGE_EVENT.listen(Key.of(MOD_ID), this::onBlockChange);
		CHUNK_SECTION_DATA_EVENT.listen(Key.of(MOD_ID), this::onChunkSectionData);

		world = mc.currentWorld;

		saver = new WorldSaver(mc, world);

		dimensions.clear();
		if (mc.currentWorld != null) scanLoadedChunks();
	}

	private void onDisable() {
		BLOCK_CHANGE_EVENT.remove(this::onBlockChange);
		CHUNK_SECTION_DATA_EVENT.remove(this::onChunkSectionData);

		final var snapshot = Map.copyOf(dimensions);
		dimensions.clear();

		if (snapshot.isEmpty()) return;

		final List<DimData> data = snapshot.values().stream().toList();

		saver.save(data, (int)player.x, (int)player.y, (int)player.z);

		LOGGER.info("Saved chunks to local world!");
	}

	private void onBlockChange(BlockChangeEvent event) {
		dim().addChunk(event.chunk().pos, event.chunk());
	}

	private void onChunkSectionData(ChunkSectionDataEvent event) {
		dim().addChunk(event.section().chunk.pos, event.section().chunk);
	}

	private void scanLoadedChunks() {
		final var rd = GameSettings.RENDER_DISTANCE.value + 1;
		final var px = (int)player.x >> 4;
		final var pz = (int)player.z >> 4;
		for (int x = -rd; x <= rd; x++) {
			for (int z = -rd; z <= rd; z++) {
				final ChunkPos pos = new ChunkPos(px + x, pz + z);
				if (world.isChunkLoaded(pos)) dim().addChunk(pos, world.getChunk(pos));
			}
		}
	}
}

