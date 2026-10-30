package dev.apollointhehouse.worlddownloader.io;

import com.mojang.nbt.NbtIo;
import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.world.chunk.ChunkLoaderRegion;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.save.LevelStorageServer;
import net.minecraft.core.world.save.SaveFormats;
import net.minecraft.core.world.save.mcregion.RegionFileCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import static dev.apollointhehouse.worlddownloader.WorldDownloader.LOGGER;

public class ChunksSaver {
	private final LevelDataSource level;
	private final File saveDir;

	public ChunksSaver(File saveDir, LevelDataSource level) {
		this.saveDir = saveDir;
		this.level = level;
	}

	public void save(
		Collection<DimData> dims,
		int spawnX, int spawnY, int spawnZ
	) {
		final var name = "Downloaded_" + System.currentTimeMillis();
		final var worldDir = new File(saveDir, name);
		if (!worldDir.mkdirs()) return;

		final var source = level.getLevelData();
		final var sizeBefore = source.getSizeOnDisk();

		final var activeFormat = SaveFormats.createSaveFormat(19135, worldDir);
		final var storage = new LevelStorageServer(activeFormat, saveDir, name, true);

		storage.saveLevelDataAndPlayerData(level.getLevelData(), level.getPlayers());

		for (final var data : dims) {
			final var dim = data.getDimension();
			final var chunks = data.getChunks();

			final var dimID = dim.id;

			final var dimDir = new File(worldDir, "dimensions/" + dimID);
			if (!dimDir.mkdirs()) {
				LOGGER.warn("Failed to create dimensions directory");
			}

			storage.saveDimensionData(dimID, level.getDimensionData(dim));

			final var loader = new ChunkLoaderRegion(dimDir);
			for (var chunk : chunks) {
				try {
					level.saveChunk(loader, chunk);
				} catch (IOException e) {
					LOGGER.warn("Failed to save chunk {}", chunk, e);
				}
			}
		}

		source.setSizeOnDisk(sizeBefore);
		RegionFileCache.flushCache();

		writeLevelDat(worldDir, name, source, spawnX, spawnY, spawnZ);
	}

	private void writeLevelDat(File dir, String name, LevelData source, int x, int y, int z) {
		final var levelData = new LevelData(source.getRandomSeed(), name);

		levelData.getSpawnPos().set(x, y, z);
		levelData.setWorldTime(source.getWorldTime());
		levelData.setTotalWorldTime(source.getTotalWorldTime());
		levelData.setSaveVersion(19135);
		levelData.setCheatsEnabled(true);
		levelData.setDifficulty(source.getDifficulty(), true);

		final var data = LevelData.serialize(levelData, new CompoundTag());
		final var root = new CompoundTag();
		root.put("Data", data);

		try (final var out = new FileOutputStream(new File(dir, "level.dat"))) {
			NbtIo.writeCompressed(root, out);
		} catch (IOException e) {
			LOGGER.warn("Failed to save level.dat", e);
			throw new RuntimeException(e);
		}
	}
}
