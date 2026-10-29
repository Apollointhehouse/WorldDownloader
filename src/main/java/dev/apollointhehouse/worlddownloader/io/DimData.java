package dev.apollointhehouse.worlddownloader.io;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.pos.ChunkPos;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DimData {
	private final Dimension dimension;
	private final Map<ChunkPos, Chunk> data = new ConcurrentHashMap<>();

	public DimData(Dimension dim) {
		this.dimension = dim;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public List<Chunk> getChunks() {
		return data.values().stream().toList();
	}

	public void addChunk(ChunkPos pos, Chunk chunk) {
		data.put(pos, chunk);
	}
}
