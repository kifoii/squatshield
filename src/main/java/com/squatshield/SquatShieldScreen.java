package com.squatshield;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SquatShieldScreen extends Screen {
    private final Screen parent;

    public SquatShieldScreen(Screen parent) {
        super(Component.translatable("screen.squatshield.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int top = this.height / 2 - 10;

        this.addRenderableWidget(Button.builder(enabledText(), b -> {
            SquatShieldConfig.enabled = !SquatShieldConfig.enabled;
            SquatShieldConfig.save();
            b.setMessage(enabledText());
        }).bounds(center - 100, top, 200, 20).build());

        this.addRenderableWidget(Button.builder(
            Component.translatable("gui.done"),
            b -> this.onClose()
        ).bounds(center - 100, top + 32, 200, 20).build());
    }

    private Component enabledText() {
        return Component.translatable(
            "option.squatshield.enabled",
            SquatShieldConfig.enabled
                ? Component.translatable("option.squatshield.enabled.on")
                : Component.translatable("option.squatshield.enabled.off")
        );
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }
}
