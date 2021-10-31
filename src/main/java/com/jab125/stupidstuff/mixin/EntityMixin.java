package com.jab125.stupidstuff.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public class EntityMixin {
    Entity entity;
    @Inject(method = "tick", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void addTotick(CallbackInfo ci) {
        this.entity = (Entity) (Object) this;
        this.entity.remove(Entity.RemovalReason.DISCARDED);

    }
}
