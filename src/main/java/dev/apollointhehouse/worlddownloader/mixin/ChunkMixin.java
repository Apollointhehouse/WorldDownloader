package dev.apollointhehouse.worlddownloader.mixin;

import dev.apollointhehouse.worlddownloader.Events;
import dev.apollointhehouse.worlddownloader.events.BlockChangeEvent;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.pos.ChunkTilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Chunk.class, remap = false)
public class ChunkMixin {
	@Inject(method = "setBlockIdData(Lnet/minecraft/core/world/pos/ChunkTilePosc;IIZ)Z", at = @At("HEAD"))
	private void onSetBlockIdData(ChunkTilePosc tilePos, int id, int data, boolean raw, CallbackInfoReturnable<Boolean> info) {
		Chunk chunk = (Chunk) (Object) this;
		BlockChangeEvent event = new BlockChangeEvent(chunk);

		Events.BLOCK_CHANGE_EVENT.emit(consumer -> consumer.accept(event));
	}
}
