package dev.apollointhehouse.worlddownloader.event;

import dev.apollointhehouse.worlddownloader.io.ChunksDownloader;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

public interface StartDownloadCallback {
	SortedBaseEvent<StartDownloadCallback> EVENT = new SortedBaseEvent<>();
	record StartDownloadEvent(ChunksDownloader downloader) {}

	void handle(StartDownloadEvent event);
}
