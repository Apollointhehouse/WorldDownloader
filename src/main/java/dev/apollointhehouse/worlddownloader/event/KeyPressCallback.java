package dev.apollointhehouse.worlddownloader.event;

import net.minecraft.client.input.InputDevice;
import turniplabs.halplibe.event.impl.SortedBaseEvent;

public interface KeyPressCallback {
	SortedBaseEvent<KeyPressCallback> EVENT = new SortedBaseEvent<>();
	record KeyPressEvent(InputDevice currentInputDevice) {}

	void handle(KeyPressEvent event);
}
