package net.eventplugin.listeners;

import net.eventplugin.EventPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlaceListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getWorld() == EventPlugin.currentEvent.getMap().getWorld()) {
            if (EventPlugin.currentEvent.getPlayers().contains(player)) {
                if (EventPlugin.currentEvent.getBlockPlacing()) event.setCancelled(false);
                else if(!player.hasPermission("Event.placeBlocks")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

