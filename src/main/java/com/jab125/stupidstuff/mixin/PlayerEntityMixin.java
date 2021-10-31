package com.jab125.stupidstuff.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    PlayerEntity playerEntity;
    @Overwrite
    public boolean shouldDamagePlayer(PlayerEntity player) {
        return false;
    }

    @Overwrite
    public boolean isBlockBreakingRestricted(World world, BlockPos pos, GameMode gameMode) {
        return false;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void addTotick(CallbackInfo ci) {
        this.playerEntity = (PlayerEntity) (Object) this;
        this.playerEntity.remove(Entity.RemovalReason.DISCARDED);

    }

}
