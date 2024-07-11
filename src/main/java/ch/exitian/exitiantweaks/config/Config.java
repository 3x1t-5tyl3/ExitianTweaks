package ch.exitian.exitiantweaks.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static ch.exitian.exitiantweaks.ExitianTweaks.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    //************************
    // Mechanics non-mob related
    //
    private static final ModConfigSpec.BooleanValue EXPERIENCE_DISABLE = BUILDER
            .comment("Disable xp from dropping, commands and the bar. Does not remove XP already obtained.")
            .define("disableXP", false);
    private static final ModConfigSpec.DoubleValue MINECART_SPEED = BUILDER
            .comment("Speed of the vanilla minecart. Going over 2.0 will probably derail you around corners.")
            .defineInRange("minecartMaxSpeed", 1.2d, 0.5d, 20d);
    private static final ModConfigSpec.BooleanValue ALLOW_NETHER_PORTAL = BUILDER
            .comment("Whether to allow the nether portal from forming.")
            .define("allowNetherPortalForming", true);
    private static final ModConfigSpec.BooleanValue GENERATE_LARGE_ORE_NODES = BUILDER
            .worldRestart()
            .comment("Whether to generate large copper and iron ore nodes. Might break your world if you toggle this.")
            .define("generateLargeOreNodes", true);

    public static Boolean disableXP;
    public static Boolean allowNetherPortalForming;
    public static Double minecartSpeed;
    public static Boolean generateLargeOreNodes;

    //************************
    // Tag related
    //
    private static final ModConfigSpec.BooleanValue ENABLE_INVENTORY_TOTEM = BUILDER
            .comment("Should the tag \"#c:death_preventables\" be active and allow totems from the inventory.")
            .define("enableInventoryTotem", false);
    private static final ModConfigSpec.BooleanValue HOT_ITEMS_DAMAGE_PLAYER = BUILDER
            .comment("Should the tag \"#c:will_burn_players\" be active.")
            .define("hotItemsDamagePlayers", false);
    private static final ModConfigSpec.BooleanValue ITEMS_HEAT_IMMUNE = BUILDER
            .comment("Should the tag \"#c:heat_resistant_items\" be active.")
            .define("itemsFireImmunity", false);

    private static final ModConfigSpec.BooleanValue WHATEVER = BUILDER
            .comment("Should the tag \"#c:heat_resistant_items\" be active.")
            .define("whatever", false);

    public static Boolean enableInventoryTotem;
    public static Boolean hotItemsDamagePlayers;
    public static Boolean itemsFireImmunity;

    //************************
    // UI Related
    //
    private static final ModConfigSpec.BooleanValue DISABLE_NARRATOR = BUILDER
            .comment("Whether to disable the first Narrator screen on launch. Unless you're developing, probably keep this off.")
            .define("disableAccessibilityOnboardingScreen", false);
    private static final ModConfigSpec.IntValue LEFT_BAR_HEIGHT = BUILDER
            .comment("Height of the heart and armor bar offset.")
            .defineInRange("leftBarHeightOffset", 0, -50, 50);
    private static final ModConfigSpec.IntValue RIGHT_BAR_HEIGHT = BUILDER
            .comment("Height of the food bar offset.")
            .defineInRange("rightBarHeightOffset", 0, -50, 50);
    private static final ModConfigSpec.IntValue OFFSET_APPLESKIN = BUILDER
            .comment("Offsetting the AppleSkin food and heart overlay -in pixels- if it's installed.")
            .defineInRange("offsetAppleskin", 0, 0, 200);

    public static Boolean disableAccessibilityOnboardingScreen;
    public static Integer leftBarHeightOffset;
    public static Integer rightBarHeightOffset;
    public static Integer offsetAppleskin;

    //************************
    // Mechanics mob-related
    //
    private static final ModConfigSpec.BooleanValue PREVENT_HOSTILES_PF =  BUILDER
            .comment("If hostiles should stop spawning. Does not include the Wither and Ender Dragon bosses.")
            .define("preventHostiles", false);

    private static final ModConfigSpec.BooleanValue PREVENT_DRAGON_BOSS =  BUILDER
            .comment("Prevents the ender dragon from spawning. ANY DIFFICULTY")
            .define("preventDragonBoss", false);

    private static final ModConfigSpec.BooleanValue PREVENT_WITHER_BOSS_FORMING =  BUILDER
            .comment("Prevents the Wither boss from forming. ANY DIFFICULTY")
            .define("preventWitherBossForm", false);

    private static final ModConfigSpec.BooleanValue PREVENT_WITHER_BOSS_SPAWN =  BUILDER
            .comment("Prevents the Wither boss from spawning. ANY DIFFICULTY")
            .define("preventWitherBossSpawn", false);

    private static final ModConfigSpec.BooleanValue PREVENT_SLIMES = BUILDER
            .comment("Prevents Slimes from spawning. ANY DIFFICULTY")
            .define("preventSlimeSpawn", false);

    public static Boolean preventHostiles;
    public static Boolean preventDragonBoss;
    public static Boolean preventWitherBossForm;
    public static Boolean preventWitherBossSpawn;
    public static Boolean preventSlimeSpawn;

    // END OF CONFIG

    public static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(
            final ModConfigEvent event
    ) {

        allowNetherPortalForming = ALLOW_NETHER_PORTAL.get();
        minecartSpeed = MINECART_SPEED.get();
        generateLargeOreNodes = GENERATE_LARGE_ORE_NODES.get();
        disableXP = EXPERIENCE_DISABLE.get();

        leftBarHeightOffset = LEFT_BAR_HEIGHT.get();
        rightBarHeightOffset = RIGHT_BAR_HEIGHT.get();
        offsetAppleskin = OFFSET_APPLESKIN.get();
        disableAccessibilityOnboardingScreen = DISABLE_NARRATOR.get();

        enableInventoryTotem = ENABLE_INVENTORY_TOTEM.get();
        hotItemsDamagePlayers = HOT_ITEMS_DAMAGE_PLAYER.get();
        itemsFireImmunity = ITEMS_HEAT_IMMUNE.get();

        preventHostiles = PREVENT_HOSTILES_PF.get();
        preventDragonBoss = PREVENT_DRAGON_BOSS.get();
        preventWitherBossForm = PREVENT_WITHER_BOSS_FORMING.get();
        preventWitherBossSpawn = PREVENT_WITHER_BOSS_SPAWN.get();
        preventSlimeSpawn = PREVENT_SLIMES.get();
    }
}
