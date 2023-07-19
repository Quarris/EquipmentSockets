package dev.quarris.mysticalsockets.capability;

import dev.quarris.mysticalsockets.sockets.SocketSlot;
import net.minecraft.core.NonNullList;

import java.util.List;

public interface ISocketHolder {

    NonNullList<SocketSlot> getSockets();

    default int socketCount() {
        return this.getSockets().size();
    }

}
