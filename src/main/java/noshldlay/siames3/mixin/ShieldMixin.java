package noshldlay.siames3.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ShieldMixin {

    @Shadow 
    protected int useItemRemaining;

    @Shadow 
    protected ItemStack useItem;

    @Inject(method = "startUsingItem", at = @At("TAIL"))
    private void fastForwardShieldUse(InteractionHand hand, CallbackInfo ci) {
        // Check if the newly activated item is a shield
        if (!this.useItem.isEmpty() && this.useItem.getItem() instanceof ShieldItem) {
            // Subtract 5 ticks from remaining time so elapsed time immediately equals 5
            this.useItemRemaining -= 5;
        }
    }
}