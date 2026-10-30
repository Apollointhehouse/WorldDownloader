package dev.apollointhehouse.worlddownloader.io;

import dev.apollointhehouse.worlddownloader.io.impl.PositionSourceImpl;
import net.minecraft.core.entity.player.Player;

public interface PositionSource {
	double getX();
	double getY();
	double getZ();

	int getDimensionID();

	static PositionSource of(Player player) {
		return new PositionSourceImpl(player);
	}
}
