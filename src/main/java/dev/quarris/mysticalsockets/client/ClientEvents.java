package dev.quarris.mysticalsockets.client;

import com.mojang.datafixers.util.Either;
import dev.quarris.mysticalsockets.ModRef;
import dev.quarris.mysticalsockets.container.SocketTooltip;
import dev.quarris.mysticalsockets.setup.CapabilitySetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ModRef.ID)
public class ClientEvents {

    @SubscribeEvent
    public static void addSocketTooltip(RenderTooltipEvent.GatherComponents event) {
        event.getItemStack().getCapability(CapabilitySetup.SOCKET_HOLDER).ifPresent(sockets -> {
            event.getTooltipElements().add(1, Either.right(new SocketTooltip(event.getItemStack(), sockets)));
        });
    }
}
