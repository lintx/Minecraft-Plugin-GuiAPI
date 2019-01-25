package org.lintx.plugins.modules.guiapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Example {
    void rename(Player player){
        GuiAPI.openRenameGui(player, "test", new RenameCallback() {
            @Override
            public void onRename(String newName) {
                player.sendMessage("you input:"+newName);
            }

            @Override
            public void onClose() {
                player.sendMessage("you close the gui");
            }
        });
    }

    void chestGui(Player player){
        ChestGui gui = GuiAPI.createChestGui(player,GuiType.ROW_6_COL_9,"gui title");
        gui.fillCol(ItemStackHelper.getPlayerHead(player.getUniqueId(),"you hear", null),1);
        gui.getInventory().setItem(9,ItemStackHelper.getItem(Material.HOPPER));
        gui.open(new ChestGuiCallback() {
            @Override
            public void onClick(ChestGui gui, ItemStack item, ClickType clickType) {
                player.sendMessage("you click:"+(item==null?"null item":item.toString()));
                player.closeInventory();
            }

            @Override
            public void onClose(ChestGui gui) {
                player.sendMessage("you close the chest gui");
            }
        });
    }

    void canMove3x3Gui(Player player){
        ChestGui gui = GuiAPI.createChestGui(player,GuiType.ROW_3_COL_3,"can move item");
        gui.getInventory().setItem(4,ItemStackHelper.getItem(Material.HOPPER));
        gui.open(new ChestGuiCallback() {
            @Override
            public void onClick(ChestGui gui, ItemStack item, ClickType clickType) {

            }

            @Override
            public void onClose(ChestGui gui) {

            }
        });
    }
}
