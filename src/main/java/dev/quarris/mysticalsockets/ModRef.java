package dev.quarris.mysticalsockets;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModRef {

    public static final String ID = "mysticalsockets";
    public static final String NAME = "MysticalSockets";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }

}
