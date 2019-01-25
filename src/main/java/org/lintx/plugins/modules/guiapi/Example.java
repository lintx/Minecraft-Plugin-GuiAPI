package org.lintx.plugins.modules.guiapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.lintx.plugins.modules.guiapi.page.PageConfig;
import org.lintx.plugins.modules.guiapi.page.PageHelper;
import org.lintx.plugins.modules.guiapi.page.PageInterface;

import java.util.ArrayList;
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
        gui.setCanMoveItem(true);
        gui.open(new ChestGuiCallback() {
            @Override
            public void onClick(ChestGui gui, ItemStack item, ClickType clickType) {

            }

            @Override
            public void onClose(ChestGui gui) {

            }
        });
    }

    void pageExample(Player player){
        ChestGui gui = GuiAPI.createChestGui(player,GuiType.ROW_6_COL_9,"get page");
        List<ItemStack> items = new ArrayList<>();
        for (int i=0;i<100;i++){
            items.add(ItemStackHelper.getItem(Material.HOPPER,"item "+(i+1)));
        }
        PageConfig config = new PageConfig(6);
        PageHelper helper = new PageHelper(config, new PageInterface() {
            @Override
            public String currentPageTitle(int page) {
                return "当前第"+page+"页";
            }

            @Override
            public String otherPageTitle(int page) {
                return "去往第"+page+"页";
            }

            @Override
            public List<String> currentPageLore(int page) {
                return null;
            }

            @Override
            public List<String> otherPageLore(int page) {
                return null;
            }
        });
        int currnet_page = 1;
        setGuiItems(gui,helper.getPageStart(currnet_page),helper.getPageEnd(currnet_page,items.size()),items,helper,currnet_page);
        ChestGuiCallback callback = new ChestGuiCallback() {
            @Override
            public void onClick(ChestGui gui, ItemStack item, ClickType clickType) {
                int page = helper.getPage(item);
                if (page>0){
                    setGuiItems(gui,helper.getPageStart(page),helper.getPageEnd(page,items.size()),items,helper,page);
                }
            }

            @Override
            public void onClose(ChestGui gui) {

            }
        };
    }

    private void setGuiItems(ChestGui gui,int min,int max,List<ItemStack> list,PageHelper helper,int current_page){
        gui.clear();
        for (int i=min;i<max;i++){
            gui.getInventory().setItem(i-min,list.get(i));
        }
        if (helper.getTotalPage(list.size())>1){
            List<ItemStack> pageItem = helper.getPage(helper.getTotalPage(list.size()),current_page);
            for (int j=0;j<pageItem.size();j++){
                gui.getInventory().setItem(j+45,pageItem.get(j));
            }
        }
    }
}
