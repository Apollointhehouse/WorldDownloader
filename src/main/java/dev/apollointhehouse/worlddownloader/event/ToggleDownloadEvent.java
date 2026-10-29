package dev.apollointhehouse.worlddownloader.event;

import dev.apollointhehouse.worlddownloader.io.Downloader;

public record ToggleDownloadEvent(Downloader downloader) {}
