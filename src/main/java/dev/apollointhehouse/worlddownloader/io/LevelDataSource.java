package dev.apollointhehouse.worlddownloader.io;

import dev.apollointhehouse.worlddownloader.io.impl.LevelDataSourceImpl;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.MutableWorldSource;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkLoader;
import net.minecraft.core.world.pos.ChunkPosc;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.LevelData;

import java.io.IOException;
import java.util.List;

public interface LevelDataSource extends MutableWorldSource {
	List<Player> getPlayers();
	DimensionData getDimensionData(Dimension dim);
	void saveChunk(ChunkLoader loader, Chunk chunk) throws IOException;
	LevelData getLevelData();

	boolean isChunkLoaded(ChunkPosc chunkPos);
	Chunk getChunk(ChunkPosc chunkPos);

	static LevelDataSource of(World world) {
		return new LevelDataSourceImpl(world);
	}
}
