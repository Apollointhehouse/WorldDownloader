package dev.apollointhehouse.worlddownloader.mixin;

import dev.apollointhehouse.worlddownloader.Events;
import dev.apollointhehouse.worlddownloader.events.KeyPressEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputDevice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Inject(method = "checkBoundInputs", at = @At("HEAD"))
	public void checkBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		KeyPressEvent event = new KeyPressEvent(currentInputDevice);
		Events.KEY_PRESS_EVENT.emit(consumer -> consumer.accept(event));
	}
}
