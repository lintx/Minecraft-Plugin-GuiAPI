package org.lintx.plugins.modules.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class ItemStackHelper {
    public static ItemStack getItem(Material type){
        return getItem(type,null,null);
    }

    public static ItemStack getItem(Material type, String title){
        return getItem(type,title,null);
    }

    public static ItemStack getItem(Material type,String title, List<String> lore){
        ItemStack item = new ItemStack(type);
        setMeta(item,title,lore);
        return item;
    }

    private static void setMeta(ItemStack item,String title, List<String> lore){
        if (title!=null || lore!=null){
            ItemMeta meta = item.getItemMeta();
            if (title!=null){
                meta.setDisplayName(title);
            }
            if (lore!=null){
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }
    }

    public static ItemStack limeWool(String title,List<String> lore){
        return getItem(Material.LIME_WOOL,title,lore);
    }

    public static ItemStack redWool(String title,List<String> lore){
        return getItem(Material.RED_WOOL,title,lore);
    }

    public static ItemStack barrier(String title,List<String> lore){
        return getItem(Material.BARRIER,title,lore);
    }

    public static ItemStack limeStainedGlass(String title,List<String> lore){
        return getItem(Material.LIME_STAINED_GLASS,title,lore);
    }

    public static ItemStack limeConcrete(String title,List<String> lore){
        return getItem(Material.LIME_CONCRETE,title,lore);
    }

    public static ItemStack redConcrete(String title,List<String> lore){
        return getItem(Material.RED_CONCRETE,title,lore);
    }

    public static ItemStack blackStainedGlassPane(String title,List<String> lore){
        return getItem(Material.BLACK_STAINED_GLASS_PANE,title,lore);
    }

    public static ItemStack limeStainedGlassPane(String title,List<String> lore){
        return getItem(Material.LIME_STAINED_GLASS_PANE,title,lore);
    }

    public static ItemStack redStainedGlassPane(String title,List<String> lore){
        return getItem(Material.RED_STAINED_GLASS_PANE,title,lore);
    }

    public static ItemStack arrow(String title,List<String> lore){
        return getItem(Material.ARROW,title,lore);
    }

    public static ItemStack ironDoor(String title,List<String> lore){
        return getItem(Material.IRON_DOOR,title,lore);
    }

    public static ItemStack getPlayerHead(UUID playerUUID,String title,List<String> lore){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        setMeta(head,title,lore);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerUUID));
        head.setItemMeta(meta);
        return head;
    }
}
