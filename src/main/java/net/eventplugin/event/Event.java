package net.eventplugin.event;

import net.eventplugin.map.EventMap;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public interface Event {
    void create(String mapName);
    boolean isOpen();

    boolean isStarted();
    boolean start();
    boolean join(Player player);
    boolean leave(Player player);
    void end();
    void open();
    void close();
    List<Player> getPlayers();
    EventMap getMap();
    boolean getBlockBreaking();
    void setBlockBreaking(boolean b);
    boolean getBlockPlacing();
    void setBlockPlacing(boolean b);
    boolean getPvP();
    void setPvP(boolean b);


}
