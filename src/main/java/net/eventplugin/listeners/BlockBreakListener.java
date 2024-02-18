package net.eventplugin.listeners;

import net.eventplugin.EventPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getWorld() == EventPlugin.currentEvent.getMap().getWorld()) {
            if (EventPlugin.currentEvent.getPlayers().contains(player)) {
                if (EventPlugin.currentEvent.getBlockBreaking()) event.setCancelled(false);
                else if(!player.hasPermission("Event.breakBlocks")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
