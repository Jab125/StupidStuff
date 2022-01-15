package com.jab125.stupidstuff.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void inject_damage(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Extremely Modern!");
        cir.cancel();
    }
}
