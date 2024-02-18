package net.eventplugin;

import net.eventplugin.commands.EventCommand;
import net.eventplugin.event.Event;
import net.eventplugin.listeners.BlockBreakListener;
import net.eventplugin.listeners.BlockPlaceListener;
import net.eventplugin.listeners.PvPListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class EventPlugin extends JavaPlugin {
    private static final Properties f = new Properties();
    public static Location spawnLocation;
    public static Event currentEvent = null;

    public File eventMapsFolder = new File(getDataFolder(), "eventMaps");

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        File eventMapsFolder = new File(getDataFolder(), "eventMaps");
        if(!eventMapsFolder.exists()) {
            eventMapsFolder.mkdirs();
        }


        getCommand("event").setExecutor(new EventCommand(this));
        getCommand("joinevent").setExecutor(new EventCommand(this));
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new SettingsMenu(), this);
        getServer().getPluginManager().registerEvents(new PvPListener(), this);



        try {
            f.load(Files.newInputStream(Paths.get("server.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        spawnLocation = Bukkit.getWorld(f.getProperty("level-name")).getSpawnLocation();


    }

    @Override
    public void onDisable() {
        if (currentEvent != null) currentEvent.end();
    }
}
