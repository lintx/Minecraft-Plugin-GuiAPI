package org.lintx.plugins.modules.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class GuiPlayerHelper {
    private static Map<UUID, RenameCallback> renameHandlers = new HashMap<UUID, RenameCallback>();
    private static Map<UUID, ChestGui> chestGuiHandlers = new HashMap<UUID, ChestGui>();
    private static Map<UUID, ChestGui> chestGuiHandlers2 = new HashMap<UUID, ChestGui>();
    static void addRenameHandler(HumanEntity player,RenameCallback callback){
        removeRenameHandler(player);
        renameHandlers.put(player.getUniqueId(),callback);
    }

    static void removeRenameHandler(HumanEntity player){
        if (isHandlerRename(player)){
            renameHandlers.remove(player.getUniqueId());
        }
    }

    static RenameCallback getRenameCallback(HumanEntity player){
        return renameHandlers.get(player.getUniqueId());
    }

    static boolean isHandlerRename(HumanEntity player){
        return renameHandlers.containsKey(player.getUniqueId());
    }


    static void addChestGuiHandler(HumanEntity player,ChestGui gui){
        removeChestGuiHandler(player);
        chestGuiHandlers.put(player.getUniqueId(),gui);
    }

    static void removeChestGuiHandler(HumanEntity player){
        if (isHandlerChestGui(player)){
            chestGuiHandlers.remove(player.getUniqueId());
        }
    }

    static ChestGui getChestGui(HumanEntity player){
        return chestGuiHandlers.get(player.getUniqueId());
    }

    static boolean isHandlerChestGui(HumanEntity player){
        return chestGuiHandlers.containsKey(player.getUniqueId());
    }

    static void clear(){
        for (Map.Entry<UUID,RenameCallback> map:renameHandlers.entrySet()){
            Player player = Bukkit.getPlayer(map.getKey());
            if (player!=null) player.closeInventory();
        }
        for (Map.Entry<UUID,ChestGui> map:chestGuiHandlers.entrySet()){
            Player player = Bukkit.getPlayer(map.getKey());
            if (player!=null) player.closeInventory();
        }
        for (Map.Entry<UUID,ChestGui> map:chestGuiHandlers2.entrySet()){
            Player player = Bukkit.getPlayer(map.getKey());
            if (player!=null) player.closeInventory();
        }
        renameHandlers.clear();
        chestGuiHandlers.clear();
        chestGuiHandlers2.clear();
    }

    static void addChestGuiHandler2(HumanEntity player,ChestGui gui){
        removeChestGuiHandler2(player);
        chestGuiHandlers2.put(player.getUniqueId(),gui);
    }

    static void removeChestGuiHandler2(HumanEntity player){
        if (isHandlerChestGui2(player)){
            chestGuiHandlers2.remove(player.getUniqueId());
        }
    }

    static ChestGui getChestGui2(HumanEntity player){
        return chestGuiHandlers2.get(player.getUniqueId());
    }

    static boolean isHandlerChestGui2(HumanEntity player){
        return chestGuiHandlers2.containsKey(player.getUniqueId());
    }
}
