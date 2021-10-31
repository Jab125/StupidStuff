package com.jab125.stupidstuff.mixin;

import com.jab125.stupidstuff.StupidStuff;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @SuppressWarnings("RedundantSuppression")
    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void getName(CallbackInfoReturnable<Text> returnable) {
        //noinspection ConstantConditions
        if (true) {
            Identifier id = Registry.ITEM.getId(getItem());
            if (!id.getNamespace().equals(StupidStuff.MODID)) {
                Text returnVal = returnable.getReturnValue();
                if (returnVal.getStyle().getColor() == null) {
                    returnable.setReturnValue(returnVal.shallowCopy().setStyle(returnVal.getStyle().withColor(Formatting.DARK_GREEN).obfuscated(true)));
                }
            }
        }
    }
}
