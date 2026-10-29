package dev.apollointhehouse.worlddownloader.event;

import net.minecraft.core.world.chunk.Chunk;

public record BlockChangeEvent(Chunk chunk) {}
