package ch.exitian.exitiantweaks.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static ch.exitian.exitiantweaks.ExitianTweaks.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue MINECART_SPEED = BUILDER
            .comment("Speed of the vanilla minecart.")
            .comment("\"1.2\" is vanilla default maximum speed. High speeds will probably derail the cart.")
            .comment("Default: 1.2")
            .defineInRange("minecartMaxSpeed", 1.2d, 0.5d, 20d);

    private static final ModConfigSpec.IntValue LEFT_BAR_HEIGHT = BUILDER
            .comment("Height of the heart and armor bar offset.")
            .comment("Default: 0")
            .defineInRange("leftBarHeightOffset", 0, -50, 50);

    private static final ModConfigSpec.IntValue RIGHT_BAR_HEIGHT = BUILDER
            .comment("Height of the food bar offset.")
            .comment("Default: 0")
            .defineInRange("rightBarHeightOffset", 0, -50, 50);

    private static final ModConfigSpec.BooleanValue ALLOW_NETHER_PORTAL = BUILDER
            .worldRestart()
            .comment("Whether to allow the nether portal from forming.")
            .comment("\"Off\" disables forming.")
            .comment("Default: true")
            .define("allowNetherPortalForming", true);

    private static final ModConfigSpec.BooleanValue GENERATE_LARGE_ORE_NODES = BUILDER
            .worldRestart()
            .comment("Whether to generate large ore nodes.")
            .comment("\"Off\" disables the carver-ores.")
            .comment("Default: true")
            .define("generateLargeOreNodes", true);

    private static final ModConfigSpec.BooleanValue DISABLE_NARRATOR = BUILDER
            .comment("Whether to mute and disable the narrator onboarding on first startup.")
            .comment("\"On\" disables the Narrator.")
            .comment("default: true")
            .define("disableAccessibilityOnboardingScreen", true);

    private static final ModConfigSpec.BooleanValue ENABLE_INVENTORY_TOTEM = BUILDER
            .comment("Should any item with the tag #c:death_preventables stop the player from dying in the inventory.")
            .comment("\"On\" enables totem-ability in the inventory.")
            .comment("default: false")
            .define("enableInventoryTotem", false);

    private static final ModConfigSpec.BooleanValue HOT_ITEMS_DAMAGE_PLAYER = BUILDER
            .comment("If any item with the tag c:will_burn_players will.. burn players.")
            .comment("This is the same damage as being hurt by lava. Fire protection does mitigate.")
            .comment("\"On\" enables this.")
            .comment("default: false")
            .define("hotItemsDamagePlayers", false);

    private static final ModConfigSpec.BooleanValue ITEMS_HEAT_IMMUNE = BUILDER
            .comment("Any item with this tag becomes immune to lava and fire.")
            .comment("\"On\" enables the behaviour. If false the tag does nothing.")
            .comment("default: false")
            .define("itemsFireImmunity", false);

    private static final ModConfigSpec.BooleanValue EXPERIENCE_DISABLE = BUILDER
            .worldRestart()
            .comment("How the xp should be disabled.")
            .comment("\"Off\" is vanilla behaviour, \"On\" disables XP entirely.")
            .comment("default: false")
            .define("disableXP", false);

    private static final ModConfigSpec.IntValue OFFSET_APPLESKIN = BUILDER
            .comment("Offset the appleskin overlay")
            .comment("\"33\" by default. If XP is disabled it will apply a default offset of 33 to align it.")
            .defineInRange("offsetAppleskin", 0, 0, 200);

    public static Boolean allowNetherPortalForming;
    public static Double minecartSpeed;
    public static Integer leftBarHeightOffset;
    public static Integer rightBarHeightOffset;
    public static Boolean generateLargeOreNodes;
    public static Boolean disableAccessibilityOnboardingScreen;
    public static Boolean enableInventoryTotem;
    public static Boolean hotItemsDamagePlayers;
    public static Boolean itemsFireImmunity;
    public static Boolean disableXP;
    public static Integer offsetAppleskin;

    public static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(
            final ModConfigEvent event) {
        allowNetherPortalForming = ALLOW_NETHER_PORTAL.get();
        minecartSpeed = MINECART_SPEED.get();
        leftBarHeightOffset = LEFT_BAR_HEIGHT.get();
        rightBarHeightOffset = RIGHT_BAR_HEIGHT.get();
        generateLargeOreNodes = GENERATE_LARGE_ORE_NODES.get();
        disableAccessibilityOnboardingScreen = DISABLE_NARRATOR.get();
        enableInventoryTotem = ENABLE_INVENTORY_TOTEM.get();
        hotItemsDamagePlayers = HOT_ITEMS_DAMAGE_PLAYER.get();
        itemsFireImmunity = ITEMS_HEAT_IMMUNE.get();
        disableXP = EXPERIENCE_DISABLE.get();
        offsetAppleskin = OFFSET_APPLESKIN.get();
    }
}
