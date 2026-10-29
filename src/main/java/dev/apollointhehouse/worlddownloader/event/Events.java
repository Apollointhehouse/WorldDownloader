package dev.apollointhehouse.worlddownloader.event;

import turniplabs.halplibe.event.impl.SortedBaseEvent;

import java.util.function.Consumer;

public final class Events {
	private Events() {}

	public static final SortedBaseEvent<Consumer<BlockChangeEvent>> BLOCK_CHANGE_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<ChunkSectionDataEvent>> CHUNK_SECTION_DATA_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<ToggleDownloadEvent>> TOGGLE_DOWNLOAD_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<KeyPressEvent>> KEY_PRESS_EVENT = new SortedBaseEvent<>();
}
