package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.events.BlockChangeEvent;
import dev.apollointhehouse.worlddownloader.events.ChunkSectionDataEvent;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

import java.util.function.Consumer;

public final class Events {
	private Events() {}

	public static final SortedBaseEvent<Consumer<BlockChangeEvent>> BLOCK_CHANGE_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<ChunkSectionDataEvent>> CHUNK_SECTION_DATA_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Runnable> TICK_EVENT = new SortedBaseEvent<>();
}
