package net.eventplugin.event;

import net.eventplugin.EventPlugin;
import net.eventplugin.map.EventMap;
import net.eventplugin.map.LocalEventMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BasicEvent implements Event{
    private EventPlugin plugin;

    private boolean open;
    public List<Player> players = new ArrayList<>();
    private boolean started = false;
    private Location loc;
    private EventMap map;

    private boolean blockBreaking = false;
    private boolean pvp = false;
    private boolean blockPlacing = false;




    public BasicEvent(String mapName, boolean openOnInit, EventPlugin plugin) {
        this.plugin = plugin;
        this.open = openOnInit;
        create(mapName);
    }

    @Override
    public void create(String mapName) {
        if (EventPlugin.currentEvent != null) return;
        map = new LocalEventMap(plugin.eventMapsFolder, mapName, true);
        EventPlugin.currentEvent = this;
        loc = getMap().getWorld().getSpawnLocation();
    }

    @Override
    public boolean isOpen() {
        return this.open;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public boolean start() {
        if(EventPlugin.currentEvent == null) return false;
        if(isStarted()) return false;
        for (Player player : EventPlugin.currentEvent.getPlayers()) {
            player.teleport(loc);
            player.sendTitle("§a§lEVENT STARTED", "§fCAN YOU WIN?", 20, 40, 20);
        }
        this.started = true;
        return true;
    }

    @Override
    public boolean join(Player player) {
        if(!isOpen()) return false;
        this.players.add(player);
        player.teleport(loc);
        return true;
    }

    @Override
    public boolean leave(Player player) {
        this.players.remove(player);
        player.teleport(EventPlugin.spawnLocation);
        return true;
    }

    @Override
    public void end() {
        for(Player player : getPlayers()) {
            player.teleport(EventPlugin.spawnLocation);
            player.sendTitle("§cEVENT ENDED", "", 20, 40, 20);
        }
        EventPlugin.currentEvent.getMap().unload();
        loc = null;
        getPlayers().clear();
        started = false;
        close();
        EventPlugin.currentEvent = null;

    }

    @Override
    public void open() {
        open = true;
    }

    @Override
    public void close() {
        open = false;
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public EventMap getMap() {
        return map;
    }

    @Override
    public boolean getBlockBreaking() {
        return blockBreaking;
    }
    @Override
    public void setBlockBreaking(boolean b) {
        this.blockBreaking = b;
    }

    @Override
    public boolean getBlockPlacing() {
        return blockPlacing;
    }

    @Override
    public void setBlockPlacing(boolean b) {
        this.blockPlacing = b;
    }

    @Override
    public boolean getPvP() {
        return pvp;
    }
    @Override
    public void setPvP(boolean b) {
        this.pvp = b;
    }
}
