package dev.apollointhehouse.worlddownloader.mixin;

import dev.apollointhehouse.worlddownloader.event.ChunkSectionDataCallback;
import dev.apollointhehouse.worlddownloader.event.ChunkSectionDataCallback.ChunkSectionDataEvent;
import net.minecraft.core.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkSection.class, remap = false)
public class ChunkSectionMixin {
	@Inject(method = "setChunkSectionData", at = @At("RETURN"))
	private void onSetChunkSectionData(byte[] data, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int startIndex, CallbackInfoReturnable<Integer> info) {
		ChunkSection section = (ChunkSection) (Object) this;
		ChunkSectionDataEvent event = new ChunkSectionDataEvent(section);
		ChunkSectionDataCallback.EVENT.emit(handler -> handler.handle(event));
	}
}
