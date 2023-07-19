package dev.quarris.mysticalsockets.container;

import dev.quarris.mysticalsockets.capability.ISocketHolder;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class SocketTooltip implements TooltipComponent {

    private final ItemStack item;
    private final ISocketHolder socketHolder;
    public SocketTooltip(ItemStack item, ISocketHolder socketHolder) {
        this.item = item;
        this.socketHolder = socketHolder;
    }

    public ISocketHolder getSocketHolder() {
        return this.socketHolder;
    }

    public ItemStack getItem() {
        return this.item;
    }
}
