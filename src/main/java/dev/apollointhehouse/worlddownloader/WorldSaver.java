package dev.apollointhehouse.worlddownloader;

import com.mojang.nbt.NbtIo;
import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkLoaderRegion;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.save.LevelStorageServer;
import net.minecraft.core.world.save.SaveFormats;
import net.minecraft.core.world.save.mcregion.RegionFileCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static dev.apollointhehouse.worlddownloader.WorldDownloader.LOGGER;

public class WorldSaver {
	private final Minecraft mc;
	private final World world;

	public WorldSaver(Minecraft mc, World world) {
		this.mc = mc;
		this.world = world;
	}

	public void save(
		List<DimData> dims,
		int spawnX, int spawnY, int spawnZ
	) {
		final var name = "Downloaded_" + System.currentTimeMillis();
		final var saveDir = new File(mc.getMinecraftDir(), "saves");
		final var worldDir = new File(saveDir, name);
		if (!worldDir.mkdirs()) return;

		final var source = world.getLevelData();
		final var sizeBefore = source.getSizeOnDisk();

		final var activeFormat = SaveFormats.createSaveFormat(19133, worldDir);
		final var storage = new LevelStorageServer(activeFormat, saveDir, name, true);

		storage.saveLevelDataAndPlayerData(world.getLevelData(), world.players);

		for (var data : dims) {
			final var dim = data.getDimension();
			final var chunks = data.getChunks();

			final var dimID = dim.id;

			final var dimDir = (dimID == 0) ? worldDir : new File(worldDir, "dimensions/" + dimID);
			dimDir.mkdirs();

			storage.saveDimensionData(dimID, dim.getDimensionData(world));

			final var loader = new ChunkLoaderRegion(dimDir);
			for (var chunk : chunks) {
				try {
					loader.saveChunk(world, chunk);
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
		levelData.setSaveVersion(source.getSaveVersion());
		levelData.setCheatsEnabled(true);
		levelData.setDifficulty(source.getDifficulty(), true);

		final var data = LevelData.serialize(levelData, new CompoundTag());
		final var root = new CompoundTag();
		root.put("Data", data);

		try (OutputStream out = new FileOutputStream(new File(dir, "level.dat"))) {
			NbtIo.writeCompressed(root, out);
		} catch (IOException e) {
			LOGGER.warn("Failed to save level.dat", e);
			throw new RuntimeException(e);
		}
	}
}
