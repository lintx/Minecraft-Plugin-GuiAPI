package org.lintx.plugins.modules.guiapi;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        InventoryType type = event.getInventory().getType();
        if (type==InventoryType.ANVIL){
            HumanEntity player = event.getWhoClicked();
            if (GuiPlayerHelper.isHandlerRename(player)){
                if (event.getRawSlot()!=2){
                    event.setCancelled(true);
                    return;
                }
                ItemStack item = event.getCurrentItem();
                event.setCancelled(true);
                if (item!=null && item.getType()==Material.NAME_TAG){
                    String newName = item.getItemMeta().getDisplayName();
                    GuiPlayerHelper.getRenameCallback(player).onRename(newName);
                }
                return;
            }
            if (event.getInventory().getLocation()==null){
                event.setCancelled(true);
            }
        }
        else if (type==InventoryType.CHEST || type==InventoryType.DROPPER || type==InventoryType.HOPPER){
            HumanEntity player = event.getWhoClicked();
            if (GuiPlayerHelper.isHandlerChestGui(player)){
                ChestGui gui = GuiPlayerHelper.getChestGui(player);
                if (gui.isCanMoveItem()){
                    return;
                }

                if (gui.getInventory().equals(event.getInventory())){
                    if (event.getClickedInventory()!=null && event.getClickedInventory().getType()==InventoryType.PLAYER){
                        event.setCancelled(true);
                        return;
                    }
                    ItemStack item = event.getCurrentItem();
                    event.setCancelled(true);
                    if (item!=null){
                        gui.getCallback().onClick(gui,item,event.getClick());
                    }
                    return;
                }
            }
            if (event.getInventory().getLocation()==null){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event){
        HumanEntity player = event.getPlayer();
        if (GuiPlayerHelper.isHandlerRename(player)){
            event.getInventory().clear();
            GuiPlayerHelper.getRenameCallback(player).onClose();
            GuiPlayerHelper.removeRenameHandler(player);
        }
        if (GuiPlayerHelper.isHandlerChestGui(player)){
            ChestGui gui = GuiPlayerHelper.getChestGui(player);
            if (gui.getInventory().equals(event.getInventory())){
                GuiPlayerHelper.getChestGui(player).getCallback().onClose(GuiPlayerHelper.getChestGui(player));
                event.getInventory().clear();
            }
            GuiPlayerHelper.removeChestGuiHandler(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (GuiPlayerHelper.isHandlerRename(player)){
            GuiPlayerHelper.removeRenameHandler(player);
        }
        if (GuiPlayerHelper.isHandlerChestGui(player)){
            GuiPlayerHelper.removeChestGuiHandler(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPrepareAnvil(PrepareAnvilEvent event){
        if (GuiPlayerHelper.isHandlerRename(event.getView().getPlayer())){
            event.getInventory().setRepairCost(0);
        }
    }
}
