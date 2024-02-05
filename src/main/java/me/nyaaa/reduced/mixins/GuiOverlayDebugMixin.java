package me.nyaaa.reduced.mixins;

import me.nyaaa.reduced.utils.MathUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {
    @Shadow @Final public Minecraft mc;
    @Shadow public static long bytesToMb(long l) {return 0;}

    @Inject(method = "call", at = @At("TAIL"), cancellable = true)
    public void debugInfoLeft(CallbackInfoReturnable<List<String>> cir) {
        List<String> debugInfo = new ArrayList<>();

        debugInfo.add(mc.getVersion() + ":" + ClientBrandRetriever.getClientModName());
        debugInfo.add("FPS: " + Minecraft.getDebugFPS());
        debugInfo.add("XYZ: " + MathUtils.round(mc.thePlayer.posX, 2) + ", " + MathUtils.round(mc.thePlayer.posY, 2) + ", " + MathUtils.round(mc.thePlayer.posZ, 2));
        debugInfo.add("Facing: " + mc.getRenderViewEntity().getHorizontalFacing());

        cir.setReturnValue(debugInfo);
    }

    @Inject(method = "getDebugInfoRight", at = @At("TAIL"), cancellable = true)
    public void debugInfoRight(CallbackInfoReturnable<List<String>> cir) {
        List<String> debugInfo = new ArrayList<>();

        long maxMem = Runtime.getRuntime().maxMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        long leftMem = totalMem - freeMem;

        debugInfo.add("Java: " + System.getProperty("java.version") + " " + (mc.isJava64bit() ? 64 : 32) + "bit");
        debugInfo.add("Mem: " + (leftMem * 100L / maxMem) + "% " + bytesToMb(leftMem) + "/" + bytesToMb(maxMem) + "MB");

        cir.setReturnValue(debugInfo);
    }
}
