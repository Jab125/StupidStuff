package com.jab125.stupidstuff.mixin;

import com.terraformersmc.modmenu.util.mod.fabric.FabricMod;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(FabricMod.class)
public class FabricModMixin {
    @Inject(method = "getVersion", at = @At("HEAD"), cancellable = true ,remap = false)
    private void inject_version(CallbackInfoReturnable<String> cir) {
        if (((FabricMod)(Object)this).getId().equals("minecraft"))
        cir.setReturnValue("2.15");
    }
}
