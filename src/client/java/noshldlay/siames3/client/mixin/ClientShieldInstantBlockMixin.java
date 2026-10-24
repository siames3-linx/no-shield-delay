package noshldlay.siames3.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ClientShieldInstantBlockMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void forceInstantShieldUse(CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.player != null && client.gameMode != null) {
            // Check if the use key is down and the player isn't already using an item
            if (client.options.keyUse.isDown() && !client.player.isUsingItem()) {
                for (InteractionHand hand : InteractionHand.values()) {
                    if (client.player.getItemInHand(hand).getItem() instanceof ShieldItem) {
                        // Instantly trigger the item use packet on tick 0
                        client.gameMode.useItem(client.player, hand);
                        break;
                    }
                }
            }
        }
    }
}