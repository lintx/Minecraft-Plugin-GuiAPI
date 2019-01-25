package org.lintx.plugins.modules.guiapi;

import org.bukkit.entity.HumanEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class GuiPlayerHelper {
    private static Map<UUID, RenameCallback> renameHandlers = new HashMap<UUID, RenameCallback>();
    private static Map<UUID, ChestGui> chestGuiHandlers = new HashMap<UUID, ChestGui>();
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
        renameHandlers.clear();
        chestGuiHandlers.clear();
    }
}
