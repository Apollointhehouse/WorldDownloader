package dev.apollointhehouse.worlddownloader.mixin;

import dev.apollointhehouse.worlddownloader.Events;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(method = "onLivingUpdate", at = @At("RETURN"))
	public void onTick(CallbackInfo ci) {
		Events.TICK_EVENT.emit(Runnable::run);
	}
}
