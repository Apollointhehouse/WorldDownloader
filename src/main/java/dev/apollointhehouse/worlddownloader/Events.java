package dev.apollointhehouse.worlddownloader;

import dev.apollointhehouse.worlddownloader.events.BlockChangeEvent;
import dev.apollointhehouse.worlddownloader.events.ChunkSectionDataEvent;
import dev.apollointhehouse.worlddownloader.events.KeyPressEvent;
import dev.apollointhehouse.worlddownloader.events.ToggleDownloadEvent;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

import java.util.function.Consumer;

public final class Events {
	private Events() {}

	public static final SortedBaseEvent<Consumer<BlockChangeEvent>> BLOCK_CHANGE_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<ChunkSectionDataEvent>> CHUNK_SECTION_DATA_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<ToggleDownloadEvent>> TOGGLE_DOWNLOAD_EVENT = new SortedBaseEvent<>();
	public static final SortedBaseEvent<Consumer<KeyPressEvent>> KEY_PRESS_EVENT = new SortedBaseEvent<>();
}
