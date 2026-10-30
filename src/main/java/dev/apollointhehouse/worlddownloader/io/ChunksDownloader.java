package dev.apollointhehouse.worlddownloader.io;

import dev.apollointhehouse.worlddownloader.event.BlockChangeCallback;
import dev.apollointhehouse.worlddownloader.event.ChunkSectionDataCallback;
import dev.apollointhehouse.worlddownloader.event.StartDownloadCallback;
import dev.apollointhehouse.worlddownloader.event.StartDownloadCallback.StartDownloadEvent;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.pos.ChunkPos;
import net.minecraft.core.world.pos.ChunkPosc;
import turniplabs.halplibe.util.dependency.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static dev.apollointhehouse.worlddownloader.WorldDownloader.LOGGER;
import static dev.apollointhehouse.worlddownloader.WorldDownloader.MOD_ID;

public class ChunksDownloader implements BlockChangeCallback, ChunkSectionDataCallback {
	private final LevelDataSource level;
	private final PositionSource position;
	private final ChunksSaver saver;
	private final Map<Integer, DimData> dimensions = new ConcurrentHashMap<>();
	private boolean running;

	public ChunksDownloader(PositionSource position, LevelDataSource level, ChunksSaver saver) {
		this.level = level;
		this.position = position;
		this.saver = saver;
		this.running = false;
	}

	public void start() {
		if (running) return;

		running = true;

		StartDownloadEvent event = new StartDownloadEvent(this);
		StartDownloadCallback.EVENT.emit(handler -> handler.handle(event));

		LOGGER.info("Downloading chunks!");

		BlockChangeCallback.EVENT.listen(Key.of(MOD_ID), this);
		ChunkSectionDataCallback.EVENT.listen(Key.of(MOD_ID), this);

		dimensions.clear();
		if (level != null) scanLoadedChunks();
	}

	public void stop() {
		if (!running) return;

		running = false;

		BlockChangeCallback.EVENT.remove(this);
		ChunkSectionDataCallback.EVENT.remove(this);

		final var snapshot = Map.copyOf(dimensions);

		if (snapshot.isEmpty()) {
			LOGGER.warn("Zero chunks have been downloaded!");
			return;
		}

		final var data = snapshot.values();

		saver.save(data, (int) position.getX(), (int) position.getY(), (int) position.getZ());

		LOGGER.info("Saved chunks to local world!");
	}

	public boolean isRunning() {
		return running;
	}

	public int size() {
		int chunks = 0;

		for (var data : dimensions.values()) {
			chunks += data.getChunks().size();
		}

		return chunks;
	}

	@Override
	public void handle(BlockChangeEvent event) {
		addChunk(event.chunk().pos, event.chunk());
	}

	@Override
	public void handle(ChunkSectionDataEvent event) {
		LOGGER.info("Adding chunk section data!");
		addChunk(event.section().chunk.pos, event.section().chunk);
	}

	private void scanLoadedChunks() {
		final var rd = GameSettings.RENDER_DISTANCE.value + 1;
		final var px = (int) position.getX() >> 4;
		final var pz = (int) position.getZ() >> 4;
		for (int x = -rd; x <= rd; x++) {
			for (int z = -rd; z <= rd; z++) {
				final ChunkPos pos = new ChunkPos(px + x, pz + z);
				if (level.isChunkLoaded(pos)) addChunk(pos, level.getChunk(pos));
			}
		}
	}

	private void addChunk(ChunkPosc chunkPos, Chunk chunk) {
		dim().addChunk(chunkPos, chunk);
	}

	private DimData dim() {
		return dimensions.computeIfAbsent(position.getDimensionID(), id -> new DimData(level.getDimension()));
	}
}

