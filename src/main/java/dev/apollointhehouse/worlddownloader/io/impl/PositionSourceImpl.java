package dev.apollointhehouse.worlddownloader.io.impl;

import dev.apollointhehouse.worlddownloader.io.PositionSource;
import net.minecraft.core.entity.player.Player;

public class PositionSourceImpl implements PositionSource {
	private final Player player;

	public PositionSourceImpl(Player player) {
		this.player = player;
	}

	@Override
	public double getX() {
		return player.x;
	}

	@Override
	public double getY() {
		return player.y;
	}

	@Override
	public double getZ() {
		return player.z;
	}

	@Override
	public int getDimensionID() {
		return player.dimension;
	}
}
