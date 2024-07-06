package ch.exitian.exitiantweaks;

import ch.exitian.exitiantweaks.config.Config;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static ch.exitian.exitiantweaks.ExitianTweaks.*;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandler {

    public static int lastTick = 0;

    //These 3 kinda all need to be here to ensure no abuse of items/XP. Also see HotbarMixin as it sets a default -6 to the hotbar renderer.
    @SubscribeEvent
    public static void deleteXPOrbs(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ExperienceOrb && Config.disableXP) {
            //event.getEntity().remove(null);
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void deleteXPFromPlayer(PlayerXpEvent.PickupXp event) {
        event.setCanceled(Config.disableXP);
    }
    @SubscribeEvent
    public static void deleteXPFromCommand(PlayerXpEvent.XpChange event) {
        event.setCanceled(Config.disableXP);
    }

    @SubscribeEvent
    public static void setFireAndLavaImmune(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ItemEntity item && Config.itemsFireImmunity) {
            if (item.getItem().is(heatResistantItems)) {
                item.setInvulnerable(true);
            }
        }
    }


    @SubscribeEvent
    public static void portalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        event.setCanceled(!Config.allowNetherPortalForming);
    }

    @SubscribeEvent
    public static void damagePlayerByHotItem(PlayerTickEvent.Post event) {

        Player player = event.getEntity();
        double randomTime = remap(Math.random(), 60, 90);
        if (!player.getInventory().isEmpty() && lastTick >= randomTime && Config.hotItemsDamagePlayers) {
            for (ItemStack item : event.getEntity().getInventory().items) {
                if (item.is(willBurnPlayers)) {
                    player.lavaHurt();
                    lastTick = 0;
                    break;
                }
            }
        } else {
            lastTick++;
        }
    }

    public static double remap(double x, double targetMin, double targetMax) {
        return targetMin + x * (targetMax - targetMin);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void narratorScreen(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof AccessibilityOnboardingScreen) {
            if (Config.disableAccessibilityOnboardingScreen) {
                Minecraft.getInstance().setScreen(new TitleScreen());
            }
        }
    }

    @SubscribeEvent
    static public void preventDeathTotemInventory(LivingDeathEvent event) {
        if (Config.enableInventoryTotem) {
            Entity entity = event.getEntity();
            ItemStack itemStack = null;
            if (entity instanceof Player) {
                for (ItemStack itemStack1 : ((Player) entity).getInventory().items) {
                    if (itemStack1.is(deathPreventables)) {
                        itemStack = itemStack1.copy();
                        itemStack1.shrink(1);
                        break;
                    }
                }
            }

            if (itemStack != null) {
                if (entity instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                    CriteriaTriggers.USED_TOTEM.trigger(serverplayer, itemStack);
                    entity.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                }

                event.getEntity().setHealth(1.0F);
                event.setCanceled(true);
                event.getEntity().removeEffectsCuredBy(EffectCures.PROTECTED_BY_TOTEM);
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                event.getEntity().level().broadcastEntityEvent(entity, (byte) 35);
            }
        }
    }
}

