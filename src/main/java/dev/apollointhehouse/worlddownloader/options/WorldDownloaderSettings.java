package dev.apollointhehouse.worlddownloader.options;

import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;

public class WorldDownloaderSettings {
	private WorldDownloaderSettings() {}

	public static final KeyBinding keybind = GameSettings.register((new KeyBinding("key.worlddownloader"))
		.setDefault(InputDevice.keyboard, Keyboard.KEY_K));
}
