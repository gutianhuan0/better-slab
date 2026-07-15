package com.betterslab.client.mixin;

import com.betterslab.client.BetterSlabClient;
import com.betterslab.client.ModKeyBindings;
import com.betterslab.config.BetterSlabConfig;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At("TAIL"))
    private void preventMovementOnAlt(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        if (BetterSlabConfig.get().preventMovement && BetterSlabClient.isAltHeldWithSlab()) {
            this.movementForward = 0.0f;
            this.movementSideways = 0.0f;
            this.pressingForward = false;
            this.pressingBack = false;
            this.pressingLeft = false;
            this.pressingRight = false;

            if (ModKeyBindings.isMovementKeyPressed(ModKeyBindings.KEY_SPACE)) {
                this.jumping = false;
            }

        }
    }
}
