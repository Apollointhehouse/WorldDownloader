package dev.apollointhehouse.worlddownloader.io.impl;

import dev.apollointhehouse.worlddownloader.io.LevelDataSource;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.world.*;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkLoader;
import net.minecraft.core.world.pos.ChunkPosc;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.WeatherManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class LevelDataSourceImpl implements LevelDataSource {
	private final World world;

	public LevelDataSourceImpl(World world) {
		this.world = world;
	}

	@Override
	public List<Player> getPlayers() {
		return world.players;
	}

	@Override
	public DimensionData getDimensionData(Dimension dim) {
		return dim.getDimensionData(world);
	}

	@Override
	public void saveChunk(ChunkLoader loader, Chunk chunk) throws IOException {
		loader.saveChunk(world, chunk);
	}

	@Override
	public LevelData getLevelData() {
		return world.getLevelData();
	}

	@Override
	public boolean isChunkLoaded(ChunkPosc chunkPos) {
		return world.isChunkLoaded(chunkPos);
	}

	@Override
	public Chunk getChunk(ChunkPosc chunkPos) {
		return world.getChunk(chunkPos);
	}

	@Override
	public int getHeightBlocks() {
		return world.getHeightBlocks();
	}

	@Override
	public @NotNull Block<?> getBlockType(@NotNull TilePosc tilePosc) {
		return world.getBlockType(tilePosc);
	}

	@Override
	public @Nullable TileEntity getTileEntity(@NotNull TilePosc tilePosc) {
		return world.getTileEntity(tilePosc);
	}

	@Override
	public float getBrightness(@NotNull TilePosc tilePosc, int i) {
		return world.getBrightness(tilePosc, i);
	}

	@Override
	public byte getLightIndex(@NotNull TilePosc tilePosc, int i) {
		return world.getLightIndex(tilePosc, i);
	}

	@Override
	public float getLightBrightness(@NotNull TilePosc tilePosc) {
		return world.getLightBrightness(tilePosc);
	}

	@Override
	public int getBlockData(@NotNull TilePosc tilePosc) {
		return world.getBlockData(tilePosc);
	}

	@Override
	public @NotNull Material getBlockMaterial(@NotNull TilePosc tilePosc) {
		return world.getBlockMaterial(tilePosc);
	}

	@Override
	public boolean isBlockOpaqueCube(@NotNull TilePosc tilePosc) {
		return world.isBlockOpaqueCube(tilePosc);
	}

	@Override
	public boolean isBlockNormalCube(@NotNull TilePosc tilePosc) {
		return world.isBlockNormalCube(tilePosc);
	}

	@Override
	public boolean shouldBlockSuffocateEntities(@NotNull TilePosc tilePos, @NotNull Class<? extends Entity> entity) {
		return LevelDataSource.super.shouldBlockSuffocateEntities(tilePos, entity);
	}

	@Override
	public boolean shouldBlockPushEntities(@NotNull TilePosc tilePos, @NotNull Class<? extends Entity> entity) {
		return LevelDataSource.super.shouldBlockPushEntities(tilePos, entity);
	}

	@Override
	public boolean canBlockBoostMinecarts(@NotNull TilePosc tilePos) {
		return LevelDataSource.super.canBlockBoostMinecarts(tilePos);
	}

	@Override
	public double getBlockTemperature(@NotNull TilePosc tilePosc) {
		return world.getBlockTemperature(tilePosc);
	}

	@Override
	public double getBlockHumidity(@NotNull TilePosc tilePosc) {
		return world.getBlockHumidity(tilePosc);
	}

	@Override
	public double getBlockVariety(@NotNull TilePosc tilePosc) {
		return world.getBlockVariety(tilePosc);
	}

	@Override
	public @NotNull SeasonManager getSeasonManager() {
		return world.getSeasonManager();
	}

	@Override
	public @NotNull WeatherManager getWeatherManager() {
		return world.getWeatherManager();
	}

	@Override
	public @NotNull Biome getBlockBiome(@NotNull TilePosc tilePosc) {
		return world.getBlockBiome(tilePosc);
	}

	@Override
	public int getSavedLightValue(@NotNull LightLayer lightLayer, @NotNull TilePosc tilePosc) {
		return world.getSavedLightValue(lightLayer, tilePosc);
	}

	@Override
	public byte getSavedLightIndex(@NotNull TilePosc tilePosc) {
		return world.getSavedLightIndex(tilePosc);
	}

	@Override
	public @NotNull Dimension getDimension() {
		return world.getDimension();
	}

	@Override
	public @NotNull WorldType getWorldType() {
		return world.getWorldType();
	}

	@Override
	public boolean setBlockType(@NotNull TilePosc tilePosc, @NotNull Block<?> block) {
		return world.setBlockType(tilePosc, block);
	}

	@Override
	public boolean setBlockData(@NotNull TilePosc tilePosc, int i) {
		return world.setBlockData(tilePosc, i);
	}

	@Override
	public boolean setBlockTypeData(@NotNull TilePosc tilePosc, @NotNull Block<?> block, int i) {
		return world.setBlockTypeData(tilePosc, block, i);
	}

	@Override
	public void setLightValue(@NotNull LightLayer lightLayer, @NotNull TilePosc tilePosc, int i) {
		world.setLightValue(lightLayer, tilePosc, i);
	}

	@Override
	public void setTileEntity(@NotNull TilePosc tilePosc, @NotNull TileEntity tileEntity) {
		world.setTileEntity(tilePosc, tileEntity);
	}

	@Override
	public void removeTileEntity(@NotNull TilePosc tilePosc) {
		world.removeTileEntity(tilePosc);
	}

	@Override
	public void replaceTileEntity(@NotNull TilePosc tilePos, @NotNull TileEntity tileEntity) {
		LevelDataSource.super.replaceTileEntity(tilePos, tileEntity);
	}
}
