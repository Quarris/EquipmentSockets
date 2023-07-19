package dev.quarris.mysticalsockets.sockets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class SocketSlot {

    public static final SocketSlot EMPTY = new SocketSlot();
    private ItemStack item;

    public SocketSlot() {
        this(ItemStack.EMPTY);
    }

    public SocketSlot(ItemStack item) {
        this.item = item;
    }

    public SocketSlot copy() {
        SocketSlot slot = new SocketSlot();
        slot.deserialize(this.serialize());
        return slot;
    }

    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        if (!this.item.isEmpty()) {
            tag.put("Item", this.item.serializeNBT());
        }
        return tag;
    }

    public void deserialize(CompoundTag tag) {
        if (tag.contains("Item")) {
            this.item = ItemStack.of(tag.getCompound("Item"));
        }
    }

    public ItemStack getItem() {
        return this.item;
    }
}
