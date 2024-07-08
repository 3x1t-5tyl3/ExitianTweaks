package ch.exitian.exitiantweaks;

import ch.exitian.exitiantweaks.config.Config;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import squeek.appleskin.api.event.HUDOverlayEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = "appleskin")
public class AppleSkinEventHandler {

    @SubscribeEvent
    public void offsetAppleskin(HUDOverlayEvent event) {
        int XPOffset = 0;
        if (Config.disableXP) {
            XPOffset = 33;
        }
        event.y = event.guiGraphics.guiHeight() - (Config.offsetAppleskin + XPOffset);
    }
}
