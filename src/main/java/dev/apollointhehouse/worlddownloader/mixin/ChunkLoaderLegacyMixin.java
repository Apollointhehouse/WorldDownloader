package dev.apollointhehouse.worlddownloader.mixin;

import dev.apollointhehouse.worlddownloader.Events;
import dev.apollointhehouse.worlddownloader.events.ChunkSectionDataEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.world.chunk.ChunkLoaderLegacy;
import net.minecraft.core.world.chunk.ChunkSection;
import net.minecraft.core.world.chunk.reader.ChunkReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkLoaderLegacy.class, remap = false)
public class ChunkLoaderLegacyMixin {

	@Inject(method = "loadChunkSectionFromCompound", at = @At("RETURN"))
	private static void onLoadChunkSectionFromCompound(ChunkSection section, ChunkReader reader, Int2ObjectMap<String> biomeRegistry, CallbackInfo info) {
		ChunkSectionDataEvent event = new ChunkSectionDataEvent(section);
		Events.CHUNK_SECTION_DATA_EVENT.emit(c -> c.accept(event));
	}
}
