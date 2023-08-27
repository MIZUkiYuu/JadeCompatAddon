package me.mizukiyuu.jadecompataddon.mixin.carpetTIS.largeBarrel;

import carpettisaddition.CarpetTISAdditionSettings;
import carpettisaddition.helpers.rule.largeBarrel.LargeBarrelHelper;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ViewGroup;
import snownee.jade.util.PlatformProxy;

import java.util.List;

@Restriction(require = @Condition("carpet-tis-addition"))
@Mixin(PlatformProxy.class)
public abstract class PlatformProxyMixin {

    @Inject(
            method = "wrapItemStorage(Ljava/lang/Object;Lnet/minecraft/server/network/ServerPlayerEntity;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void JadeCompatAddon$getBarrelInventory(Object target, ServerPlayerEntity player, CallbackInfoReturnable<List<ViewGroup<ItemStack>>> cir){
        if(CarpetTISAdditionSettings.largeBarrel){
            if (target instanceof BarrelBlockEntity be) {
                Inventory inventory = LargeBarrelHelper.getInventory(be.getCachedState(), be.getWorld(), be.getPos());
                if (inventory != null) {
                    cir.setReturnValue(List.of(ItemView.fromContainer(inventory, 54, 0)));
                }
            }
        }
    }

}
