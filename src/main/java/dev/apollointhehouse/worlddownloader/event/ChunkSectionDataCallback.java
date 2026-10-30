package dev.apollointhehouse.worlddownloader.event;

import net.minecraft.core.world.chunk.ChunkSection;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

public interface ChunkSectionDataCallback {
	SortedBaseEvent<ChunkSectionDataCallback> EVENT = new SortedBaseEvent<>();
	record ChunkSectionDataEvent(ChunkSection section) {}

	void handle(ChunkSectionDataEvent event);
}
