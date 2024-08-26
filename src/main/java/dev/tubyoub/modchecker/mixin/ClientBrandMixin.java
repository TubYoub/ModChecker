package dev.tubyoub.modchecker.mixin;

import dev.tubyoub.modchecker.ModChecker;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandMixin {
    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true, remap = false)
    private static void onGetClientModName(CallbackInfoReturnable<String> cir) {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer("fitnacraft").orElseThrow(IllegalStateException::new).getMetadata();
        String version = metadata.getVersion().getFriendlyString();
        String brand = "Fabric " ;

        if (ModChecker.unexpectedModsDetected) {
            brand += " (Unexpected Mods detected)";
        }else {
            brand += " (No unexpected mods detected)";
        }

        cir.setReturnValue(brand);
    }
}
