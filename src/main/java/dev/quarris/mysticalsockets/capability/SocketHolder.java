package dev.quarris.mysticalsockets.capability;

import dev.quarris.mysticalsockets.setup.CapabilitySetup;
import dev.quarris.mysticalsockets.sockets.SocketSlot;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SocketHolder implements ISocketHolder, ICapabilitySerializable<CompoundTag> {

    private final LazyOptional<ISocketHolder> lazyThis = LazyOptional.of(() -> this);

    private final NonNullList<SocketSlot> socketSlots;

    public SocketHolder(int sockets) {
        this.socketSlots = NonNullList.withSize(sockets, SocketSlot.EMPTY);
        this.socketSlots.set(0, new SocketSlot(new ItemStack(Items.DIAMOND)));
        this.socketSlots.set(1, new SocketSlot(new ItemStack(Items.REDSTONE)));
        this.socketSlots.set(2, new SocketSlot(new ItemStack(Items.LAPIS_LAZULI)));
        this.socketSlots.set(3, new SocketSlot(new ItemStack(Items.EMERALD)));
    }

    @Override
    public NonNullList<SocketSlot> getSockets() {
        return this.socketSlots;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag socketsTag = new ListTag();
        for (int i = 0; i < this.socketSlots.size(); i++) {
            SocketSlot slot = this.socketSlots.get(i);
            CompoundTag socketTag = slot.serialize();
            socketTag.putInt("Slot", i);
            socketsTag.add(socketTag);
        }
        tag.put("SocketSlots", socketsTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag socketsTag = tag.getList("SocketSlots", Tag.TAG_COMPOUND);
        socketsTag.stream()
            .map(CompoundTag.class::cast)
            .forEach(socketTag -> {
                this.socketSlots.get(socketTag.getInt("Slot")).deserialize(socketTag);
            });
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilitySetup.SOCKET_HOLDER.orEmpty(cap, this.lazyThis);
    }
}
