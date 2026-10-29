package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.events.BlockChangeEvent;
import dev.apollointhehouse.worlddownloader.events.ChunkSectionDataEvent;
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
	private final World world;
	private final Player player;
	private final Minecraft mc = Minecraft.getMinecraft();
	private final WorldSaver saver;
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

		if (enabled) onEnable(); else onDisable();
	}

	private void onEnable() {
		LOGGER.info("Downloading chunks!");

		BLOCK_CHANGE_EVENT.listen(Key.of(MOD_ID), this::onBlockChange);
		CHUNK_SECTION_DATA_EVENT.listen(Key.of(MOD_ID), this::onChunkSectionData);

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

//	private void onRender(Render2DEvent event) {
//		final var chunksSaved = dimensions.values.map { it.chunks.size }.sum();
//		final var text = "Chunks Saved: $chunksSaved";
//
//		event.renderer.textWithShadow(
//			text,
//			(mc.resolution.getExactScaledHeightScreenCoords() - event.renderer.font.stringWidth(text)) / 2.0,
//			10.0,
//			TextFormatting.Base.Color.GREEN
//			);
//	}

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

