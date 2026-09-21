package com.squatshield;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SquatShieldScreen extends Screen {
    private final Screen parent;
    private Button enabledButton;
    private Button blockButton;
    private Button handButton;

    public SquatShieldScreen(Screen parent) {
        super(Component.literal("SquatShield"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int top = this.height / 2 - 70;

        enabledButton = this.addRenderableWidget(Button.builder(enabledText(), b -> {
            SquatShieldConfig.enabled = !SquatShieldConfig.enabled;
            SquatShieldConfig.save();
            b.setMessage(enabledText());
        }).bounds(center - 100, top, 200, 20).build());

        blockButton = this.addRenderableWidget(Button.builder(blockText(), b -> {
            SquatShieldConfig.blockManualShieldUse = !SquatShieldConfig.blockManualShieldUse;
            SquatShieldConfig.save();
            b.setMessage(blockText());
        }).bounds(center - 100, top + 28, 200, 20).build());

        handButton = this.addRenderableWidget(Button.builder(handText(), b -> {
            SquatShieldConfig.offhandFirst = !SquatShieldConfig.offhandFirst;
            SquatShieldConfig.save();
            b.setMessage(handText());
        }).bounds(center - 100, top + 56, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Reset"), b -> {
            SquatShieldConfig.reset();
            enabledButton.setMessage(enabledText());
            blockButton.setMessage(blockText());
            handButton.setMessage(handText());
        }).bounds(center - 100, top + 88, 98, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), b -> this.onClose())
            .bounds(center + 2, top + 88, 98, 20).build());
    }

    private Component enabledText() {
        return Component.literal("SquatShield: " + (SquatShieldConfig.enabled ? "ON" : "OFF"));
    }

    private Component blockText() {
        return Component.literal("Block right-click: " + (SquatShieldConfig.blockManualShieldUse ? "ON" : "OFF"));
    }

    private Component handText() {
        return Component.literal("Preferred hand: " + (SquatShieldConfig.offhandFirst ? "OFFHAND" : "MAIN HAND"));
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }
}
