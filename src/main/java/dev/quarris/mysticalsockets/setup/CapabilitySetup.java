package dev.quarris.mysticalsockets.setup;

import dev.quarris.mysticalsockets.ModRef;
import dev.quarris.mysticalsockets.capability.ISocketHolder;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilitySetup {

    public static final Capability<ISocketHolder> SOCKET_HOLDER = CapabilityManager.get(new CapabilityToken<>() {});


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ISocketHolder.class);
    }
}
