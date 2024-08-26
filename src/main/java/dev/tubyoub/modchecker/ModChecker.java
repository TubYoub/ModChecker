package dev.tubyoub.modchecker;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ModChecker implements ModInitializer {
    public static final String MOD_ID = "fitnacraft";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static boolean unexpectedModsDetected = false;
    List<String> modsToCheck = Arrays.asList(
        "animatica", "appleskin", "betterf3", "bettermounthud", "caffeineconfig", "capes",
        "cloth-basic-math", "cloth-config", "com_akuleshov7_ktoml-core-jvm",
        "com_github_fallen-breath_conditional-mixin", "com_twelvemonkeys_common_common-image",
        "com_twelvemonkeys_common_common-io", "com_twelvemonkeys_common_common-lang",
        "com_twelvemonkeys_imageio_imageio-core", "com_twelvemonkeys_imageio_imageio-metadata",
        "com_twelvemonkeys_imageio_imageio-webp", "continuity", "controlify",
        "cubes-without-borders", "debugify", "dev_isxander_libsdl4j", "distanthorizons",
        "dynamic_fps", "e4mc_minecraft", "entity_model_features", "entity_texture_features",
        "entityculling", "fabric-api", "fabricloader", "fabricskyboxes", "fabrishot",
        "fastquit", "ferritecore", "fitnacraft", "freecam", "fsb-interop", "gammautils",
        "immediatelyfast", "indium", "io_github_douira_glsl-transformer", "iris",
        "isxander-main-menu-credits", "itemswapper", "java", "languagereload", "litematica",
        "lithium", "malilib", "midnightlib", "minecraft", "mixinextras", "mixinsquared",
        "mixintrace", "modelfix", "modernfix", "modmenu", "morechathistory", "moreculling",
        "net_lenni0451_reflect", "net_lostluma_battery", "net_objecthunter_exp4j",
        "nochatreports", "optigui", "org_anarres_jcpp", "org_antlr_antlr4-runtime",
        "org_apache_commons_commons-text", "org_apache_httpcomponents_httpmime",
        "org_hid4java_hid4java", "org_ini4j_ini4j", "org_jetbrains_kotlin_kotlin-reflect",
        "org_jetbrains_kotlin_kotlin-stdlib", "org_jetbrains_kotlin_kotlin-stdlib-jdk7",
        "org_jetbrains_kotlin_kotlin-stdlib-jdk8", "org_jetbrains_kotlinx_atomicfu-jvm",
        "org_jetbrains_kotlinx_kotlinx-coroutines-core-jvm",
        "org_jetbrains_kotlinx_kotlinx-coroutines-jdk8",
        "org_jetbrains_kotlinx_kotlinx-datetime-jvm",
        "org_jetbrains_kotlinx_kotlinx-serialization-cbor-jvm",
        "org_jetbrains_kotlinx_kotlinx-serialization-core-jvm",
        "org_jetbrains_kotlinx_kotlinx-serialization-json-jvm",
        "org_quiltmc_parsers_gson", "org_quiltmc_parsers_json", "paginatedadvancements",
        "placeholder-api", "polytone", "puzzle", "puzzle-base", "puzzle-gui", "puzzle-models",
        "puzzle-splashscreen", "reeses-sodium-options", "rrls", "shulkerboxtooltip", "sodium",
        "sodium-extra", "sound_physics_remastered", "status-effect-bars", "voicechat",
        "xaerominimap", "xaeroworldmap", "yet_another_config_lib_v3", "yosbr", "zoomify"
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing FitnaCraft");
        List<String> installedMods = new ArrayList<>();

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            String modId = mod.getMetadata().getId();
            if (!modId.startsWith("fabric-")) {
                installedMods.add(modId);
            }
        }

        List<String> unexpectedMods = new ArrayList<>(installedMods);
        unexpectedMods.removeAll(modsToCheck);

        if (!unexpectedMods.isEmpty()) {
            LOGGER.warn("The following unexpected mods are installed:");
            for (String mod : unexpectedMods) {
                LOGGER.warn("- {}", mod);
            }
            unexpectedModsDetected = true;
        } else {
            LOGGER.info("No unexpected mods found.");
        }
    }
}