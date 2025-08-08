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

    // Cache the computed brand so we only build / log it once.
    private static String cachedBrand;

    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true, remap = false)
    private static void onGetClientModName(CallbackInfoReturnable<String> cir) {
        if (cachedBrand == null) {
            Optional<ModMetadata> optionalMetadata = FabricLoader.getInstance()
                    .getModContainer("mod-checker")
                    .map(container -> container.getMetadata());

            if (optionalMetadata.isPresent()) {
                ModMetadata metadata = optionalMetadata.get();
                String version = metadata.getVersion().getFriendlyString();
                cachedBrand = "ModChecker-fabric v" + version;

                if (ModChecker.unexpectedModsDetected) {
                    cachedBrand += " (Unexpected Mods detected)";
                } else {
                    cachedBrand += " (No unexpected mods detected)";
                }
            } else {
                cachedBrand = "ModChecker";
            }

            ModChecker.LOGGER.info("[ModChecker] Successfully changed client brand to: {}", cachedBrand);
        }

        cir.setReturnValue(cachedBrand);
    }
}