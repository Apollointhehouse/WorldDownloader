package dev.apollointhehouse.worlddownloader.event;

import net.minecraft.core.world.chunk.Chunk;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

public interface BlockChangeCallback {
	SortedBaseEvent<BlockChangeCallback> EVENT = new SortedBaseEvent<>();
	record BlockChangeEvent(Chunk chunk) {}

	void handle(BlockChangeEvent event);
}
