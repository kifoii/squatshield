package com.squatshield.mixin.client;

import com.squatshield.SquatShieldConfig;
import com.squatshield.input.SneakShieldContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Shadow @Final public Options options;
    @Shadow public LocalPlayer player;
    @Shadow public MultiPlayerGameMode gameMode;

    @Inject(method = "handleKeybinds", at = @At("TAIL"))
    private void squatshield$triggerShieldFromSneak(CallbackInfo ci) {
        if (!SquatShieldConfig.enabled || this.player == null || !this.options.keyShift.isDown() || this.player.isUsingItem()) return;

        InteractionHand hand = this.squatshield$getPreferredShieldHand();
        if (hand == null) return;

        SneakShieldContext.setSneakTriggered(true);
        try {
            InteractionResult result = this.gameMode.useItem(this.player, hand);
            if (result instanceof InteractionResult.Success success
                && success.swingSource() == InteractionResult.SwingSource.CLIENT) {
                this.player.swing(hand);
            }
        } finally {
            SneakShieldContext.setSneakTriggered(false);
        }
    }

    @Redirect(
        method = "handleKeybinds",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;releaseUsingItem(Lnet/minecraft/world/entity/player/Player;)V")
    )
    private void squatshield$keepShieldUsingWhileSneaking(MultiPlayerGameMode gameMode, Player player) {
        if (SquatShieldConfig.enabled && this.options.keyShift.isDown()
            && player.isUsingItem()
            && player.getUseItem().getItem() instanceof ShieldItem) {
            return;
        }

        gameMode.releaseUsingItem(player);
    }

    private InteractionHand squatshield$getPreferredShieldHand() {
        if (this.squatshield$isShield(this.player.getOffhandItem())) return InteractionHand.OFF_HAND;
        if (this.squatshield$isShield(this.player.getMainHandItem())) return InteractionHand.MAIN_HAND;
        return null;
    }

    private boolean squatshield$isShield(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem;
    }
}
