package net.eventplugin;

import net.eventplugin.commands.EventCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class SettingsMenu implements Listener {

    private Inventory inv = Bukkit.createInventory(null, 4*9, "§e§lSETTINGS");

    private ItemStack settings = new ItemStack(Material.REDSTONE, 1);
    private ItemMeta settingsMeta = settings.getItemMeta();

    private ItemStack blocksetting = new ItemStack(Material.GRASS_BLOCK, 1);
    private ItemMeta blocksettingMeta = blocksetting.getItemMeta();

    private ItemStack blockplace = new ItemStack(Material.BRICKS, 1);
    private ItemMeta blockplaceMeta = blockplace.getItemMeta();

    private EventCommand eventCommand;



    public SettingsMenu(Player player, EventCommand eventCommand) {

        // pvp option
        if (!EventPlugin.currentEvent.getPvP()) {
            this.settingsMeta.setDisplayName("§cPVP §aOff");
            this.settingsMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.settingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.settingsMeta.setDisplayName("§cPVP §cOn");
            this.settingsMeta.removeEnchantments();
        }

        this.settings.setItemMeta(settingsMeta);
        this.inv.setItem(10, settings);

        //block breaking
        if(!EventPlugin.currentEvent.getBlockBreaking()) {
            this.blocksettingMeta.setDisplayName("§2Block Breaking §cOff");
            this.blocksettingMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.settingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.blocksettingMeta.setDisplayName("§2Block Breaking §aOn");
            this.blocksettingMeta.removeEnchantments();
        }

        this.blocksetting.setItemMeta(blocksettingMeta);
        this.inv.setItem(16, blocksetting);

        //block placing
        if(!EventPlugin.currentEvent.getBlockPlacing()) {
            this.blockplaceMeta.setDisplayName("§2Block Placing §cOff");
            this.blockplaceMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.blockplaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.blockplaceMeta.setDisplayName("§2Block Placing §aOn");
            this.blockplaceMeta.removeEnchantments();
        }

        this.blockplace.setItemMeta(blockplaceMeta);
        this.inv.setItem(13, blockplace);

    }

    public SettingsMenu() {

    }

    public void updateGui(Player player) {
        //pvp
        if (!EventPlugin.currentEvent.getPvP()) {
            this.settingsMeta.setDisplayName("§cPVP Off");
            this.settingsMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.settingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.settingsMeta.setDisplayName("§aPVP On");
            this.settingsMeta.removeEnchantments();
        }

        settings.setItemMeta(settingsMeta);
        inv.setItem(10, settings);

        //block breaking
        if(!EventPlugin.currentEvent.getBlockBreaking()) {
            this.blocksettingMeta.setDisplayName("§2Block Breaking §cOff");
            this.blocksettingMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.blocksettingMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.blocksettingMeta.setDisplayName("§2Block Breaking §aOn");
            this.blocksettingMeta.removeEnchantments();
        }

        //block placing
        this.blocksetting.setItemMeta(blocksettingMeta);
        this.inv.setItem(16, blocksetting);

        if(!EventPlugin.currentEvent.getBlockPlacing()) {
            this.blockplaceMeta.setDisplayName("§2Block Placing §cOff");
            this.blockplaceMeta.addEnchant(Enchantment.MENDING, 1, false);
            this.blockplaceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            this.blockplaceMeta.setDisplayName("§2Block Placing §aOn");
            this.blockplaceMeta.removeEnchantments();
        }

        this.blockplace.setItemMeta(blockplaceMeta);
        this.inv.setItem(13, blockplace);

        openInventory(player);

    }

    public void openInventory(Player player) {
        player.openInventory(inv);
    }


    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§e§lSETTINGS")) {
            event.setCancelled(true);
        }
    }
    @EventHandler(ignoreCancelled = false)
    public void onPvPSettingClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() != 10) return;
        if (!event.getView().getTitle().equals("§e§lSETTINGS")) return;
        if (event.getCurrentItem().getType() == Material.REDSTONE) {
            EventPlugin.currentEvent.setPvP(!EventPlugin.currentEvent.getPvP());
            if (!EventPlugin.currentEvent.getPvP()) {
                player.sendMessage("§cPVP Off");
                updateGui(player);
            }else {
                player.sendMessage("§aPVP On");
                updateGui(player);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreaking(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() != 16) return;
        if (!event.getView().getTitle().equals("§e§lSETTINGS")) return;
        if (event.getCurrentItem().getType() == blocksetting.getType()) {
            EventPlugin.currentEvent.setBlockBreaking(!EventPlugin.currentEvent.getBlockBreaking());
            if (!EventPlugin.currentEvent.getBlockBreaking()) {
                player.sendMessage("§cBlockBreaking Off");
                updateGui(player);
            }else {
                player.sendMessage("§aBlockBreaking On");
                updateGui(player);
            }
        }
    }

    @EventHandler
    public void onBlockPlacing(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() != 13) return;
        if (!event.getView().getTitle().equals("§e§lSETTINGS")) return;
        if (event.getCurrentItem().getType() == blockplace.getType()) {
            EventPlugin.currentEvent.setBlockPlacing(!EventPlugin.currentEvent.getBlockPlacing());
            if (!EventPlugin.currentEvent.getBlockPlacing()) {
                player.sendMessage("§cBlockPlacing Off");
                updateGui(player);
            }else {
                player.sendMessage("§aBlockPlacing On");
                updateGui(player);
            }
        }
    }
}
