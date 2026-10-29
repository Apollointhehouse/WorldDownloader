package dev.apollointhehouse.worlddownloader.events;

import net.minecraft.client.input.InputDevice;

public record KeyPressEvent(InputDevice currentInputDevice) {}
