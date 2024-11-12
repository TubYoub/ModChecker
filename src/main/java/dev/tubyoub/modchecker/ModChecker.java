package dev.tubyoub.modchecker;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import java.util.List;

public class ModChecker implements ModInitializer {
    public static final String MOD_ID = "mod-checker";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static boolean unexpectedModsDetected = false;
    private static final ModListFetcher modListFetcher = new ModListFetcher();

    @Override
    public void onInitialize() {
        LOGGER.info("[ModChecker] Initializing ModChecker" );
        checkForUnexpectedMods();
    }

    private void checkForUnexpectedMods() {
        List<String> allowedMods = modListFetcher.getAllowedMods();

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            String modId = mod.getMetadata().getId();
            if (!allowedMods.contains(modId) && !modId.startsWith("fabric-" )) {
                unexpectedModsDetected = true;
                LOGGER.info("[ModChecker] found unexpected mod: " + modId);
            }
        }
    }
}