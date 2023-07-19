package dev.quarris.mysticalsockets.event;

import dev.quarris.mysticalsockets.ModRef;
import dev.quarris.mysticalsockets.capability.SocketHolder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRef.ID)
public class ItemEvents {

    @SubscribeEvent
    public static void addItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ArmorItem) {
            event.addCapability(ModRef.res("socket_holder"), new SocketHolder(6));
        }
    }
}
