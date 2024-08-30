package dev.tubyoub.modchecker.mixin;

import dev.tubyoub.modchecker.ModChecker;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandMixin {
    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true, remap = false)
    private static void onGetClientModName(CallbackInfoReturnable<String> cir) {
        Optional<ModMetadata> optionalMetadata = FabricLoader.getInstance().getModContainer("mod-checker").map(container -> container.getMetadata());
        String brand;

        if (optionalMetadata.isPresent()) {
            ModMetadata metadata = optionalMetadata.get();
            String version = metadata.getVersion().getFriendlyString();
            brand = "ModChecker-fabric v" + version;

            if (ModChecker.unexpectedModsDetected) {
                brand += " (Unexpected Mods detected)";
            } else {
                brand += " (No unexpected mods detected)";
            }
        } else {
            brand = "ModChecker";
        }

        cir.setReturnValue(brand);
    }
}
