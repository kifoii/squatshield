package com.squatshield;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class SquatShieldClient implements ClientModInitializer {
    private static KeyMapping openMenu;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
            SquatShield.MOD_ID + ":main"
        );

        openMenu = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.squatshield.open_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.gui.screen() == null) {
                    client.gui.setScreen(new SquatShieldScreen(null));
                }
            }
        });
    }
}
