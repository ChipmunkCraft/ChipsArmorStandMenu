package com.deoxservices.chipsarmorstand.menu;

import com.deoxservices.chipsarmorstand.data.ArmorStandData;
import com.deoxservices.chipsarmorstand.server.config.ServerConfig;
import com.deoxservices.chipsarmorstand.utils.Constants;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class MenuTicker {
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (player.containerMenu instanceof ArmorStandMenu menu) {
                menu.ticksSinceInteraction++;
                int timeoutTicks = ServerConfig.CONFIG.TIMEOUT_SECONDS.get() * 20;
                if (menu.ticksSinceInteraction >= timeoutTicks || !menu.stillValid(player)) {
                    ArmorStandData.setArmorStandInUse(menu.getArmorStand(), player.getUUID(), false);
                    player.closeContainer();
                }
            }
        }
    }
}