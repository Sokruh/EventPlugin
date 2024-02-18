package net.eventplugin.listeners;


import net.eventplugin.EventPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void pvpListener(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        if(!event.getEntity().getType().equals(EntityType.PLAYER)) return;
        if(!EventPlugin.currentEvent.isStarted()) event.setCancelled(true);
    }
}
