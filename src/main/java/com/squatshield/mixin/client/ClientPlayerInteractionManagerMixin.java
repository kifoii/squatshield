package com.squatshield.mixin.client;

import com.squatshield.SquatShieldConfig;
import com.squatshield.input.SneakShieldContext;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void squatshield$blockRightClickShield(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (SneakShieldContext.isSneakTriggered() || !SquatShieldConfig.enabled || !SquatShieldConfig.blockManualShieldUse) return;

        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ShieldItem) cir.setReturnValue(InteractionResult.PASS);
    }
}
