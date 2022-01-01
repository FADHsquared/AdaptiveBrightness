package me.cominixo.adaptivebrightness.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyVariable(method = "getLeftText", at = @At("RETURN"), ordinal = 0)
    private List<String> addDebugString(List<String> list) {
        list.add("");
        list.add(String.format("[AdaptiveBrightness] Brightness: %f%%", client.options.gamma*100));
        return list;
    }
}
