package dev.apollointhehouse.worlddownloader.event;

import net.minecraft.client.input.InputDevice;

public record KeyPressEvent(InputDevice currentInputDevice) {}
