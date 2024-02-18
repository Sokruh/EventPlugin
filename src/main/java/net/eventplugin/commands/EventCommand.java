package net.eventplugin.commands;


import net.eventplugin.EventPlugin;
import net.eventplugin.SettingsMenu;
import net.eventplugin.event.BasicEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EventCommand implements TabExecutor {

    private EventPlugin plugin;

    public SettingsMenu settingsMenu;

    public EventCommand(EventPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase("event") || s.equalsIgnoreCase("e")) {

            if (!(commandSender instanceof Player)) return false;
            Player player = (Player) commandSender;

            if(strings.length >= 1) {
                if(strings[0].equalsIgnoreCase("join")) {
                    if(EventPlugin.currentEvent == null) {
                        player.sendMessage("§cThere is no event running!");
                        return false;
                    }
                    if(EventPlugin.currentEvent.getPlayers().contains(player)) {
                        player.sendMessage("§cYou already are in the event!");
                        return false;
                    }
                    EventPlugin.currentEvent.join(player);
                    player.sendMessage("§aYou have joined the current event!");
                    return true;
                }
                if(strings[0].equalsIgnoreCase("leave")) {
                    if(EventPlugin.currentEvent == null) {
                        player.sendMessage("§cThere is no event running!");
                        return false;
                    }
                    if(!EventPlugin.currentEvent.getPlayers().contains(player)) {
                        player.sendMessage("§cYou are not in the event!");
                        return false;
                    }
                    EventPlugin.currentEvent.leave(player);
                    player.sendMessage("§aYou have left the current event!");
                    return true;
                }
                if (player.hasPermission("Event.admin")) {
                    if(strings[0].equalsIgnoreCase("help")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        EventPlugin.currentEvent.join(player);
                        return true;
                    }

                    if(strings[0].equalsIgnoreCase("create")) {
                        player.sendMessage("§cCreating...");
                        EventPlugin.currentEvent = new BasicEvent(strings[1], true, plugin);
                        player.sendMessage("§c" + strings[1] +  " has been created.");
                        player.teleport(EventPlugin.currentEvent.getMap().getWorld().getSpawnLocation());
                        EventPlugin.currentEvent.join(player);
                        return true;
                    }

                    if(strings[0].equalsIgnoreCase("end")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        player.sendMessage("§c" + EventPlugin.currentEvent.getMap().getWorld().getName() + " has been unloaded.");
                        EventPlugin.currentEvent.end();
                        return true;
                    }


                    if(strings[0].equalsIgnoreCase("start")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        player.sendMessage("§aStarting the event");
                        EventPlugin.currentEvent.start();
                        Component message = Component.text("       §e§lEVENT HAS STARTED \n §8§l----------------------- \n \n    §f§lClick HERE or do §a/e join§r \n          §f§lto join the event").clickEvent(ClickEvent.runCommand("/e join"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(message);
                        }
                        return true;
                    }

                    if(strings[0].equalsIgnoreCase("close")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        player.sendMessage("§cClosing the event.");
                        EventPlugin.currentEvent.close();
                        return true;
                    }

                    if(strings[0].equalsIgnoreCase("open")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        player.sendMessage("§aEvent is now Open");
                        EventPlugin.currentEvent.open();
                        Component message = Component.text("       §e§lEVENT HAS OPENED \n §8§l----------------------- \n \n    §f§lClick HERE or do §a/e join§r \n          §f§lto join the event").clickEvent(ClickEvent.runCommand("/e join"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(message);
                        }
                        return true;
                    }

                    if(strings[0].equalsIgnoreCase("settings")) {
                        if(EventPlugin.currentEvent == null) {
                            player.sendMessage("§cThere is no event running!");
                            return false;
                        }
                        settingsMenu = new SettingsMenu(player, this);
                        settingsMenu.openInventory(player);
                    }

                }




            }
            sendHelpMessage(player);
            return true;


        }if (s.equalsIgnoreCase("joinevent")) {
            if (!(commandSender instanceof Player)) return false;
            Player player = (Player) commandSender;

            if(EventPlugin.currentEvent == null) {
                player.sendMessage("§cThere is no event running!");
                return false;
            }
            EventPlugin.currentEvent.join(player);
            player.sendMessage("§aYou have joined the current event!");
            return true;
        }


        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("help");
            list.add("join");
            list.add("leave");
            list.add("create");
            list.add("end");
            list.add("start");
            list.add("close");
            list.add("open");
            list.add("settings");
        }
        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("create")) {
                if (plugin.eventMapsFolder.list() != null){
                    list.addAll(Arrays.asList(plugin.eventMapsFolder.list()));
                }else list.add("noMapsInFolder");


            }
        }
        return list;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(
                "                §3§lEVENTS - Commands \n"
                + "                  §7by §d§lSokruh \n"
                + "§8§l----------------------------------\n"
                + "§b/e help \n"
                + "§7Help for commands and usage of the plugin \n"
                + "§8§l----------------------------------\n"
                + "§b/e join \n"
                + "§7Joins the current event. \n"
                + "§8§l----------------------------------\n"
                + "§b/e leave \n"
                + "§7Leaves the current event. \n"
                + "§8§l----------------------------------\n"
                + "§b/e create <map name> \n"
                + "§7Creates the event and map. \n"
                + "§8§l----------------------------------\n"
                + "§b/e delete \n"
                + "§7Unloads current event and deletes the temporary map file. \n"
                + "§8§l----------------------------------\n"
                + "§b/e start \n"
                + "§7Starts current event. \n"
                + "§8§l----------------------------------\n"
                + "§b/e close \n"
                + "§7Players won't be able to join the event. \n"
                + "§8§l----------------------------------\n"
                + "§b/e open \n"
                + "§7Players will be able to join the event \n"
                + "§8§l----------------------------------\n"
                + "§b/e stop \n"
                + "§7Stops the current event running. \n"
                + "§8§l----------------------------------\n"
                + "§b/e settings \n"
                + "§7Opens settings menu of the current event. \n"
                + "§8§l----------------------------------\n"
        );
    }
}
