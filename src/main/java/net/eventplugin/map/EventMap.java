package net.eventplugin;

import org.bukkit.World;

public interface EventMap {
    boolean load();
    void unload();
    boolean restoreFromSource();

    boolean isLoaded();
    World getWorld();
}
