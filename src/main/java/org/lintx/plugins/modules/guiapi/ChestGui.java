package org.lintx.plugins.modules.guiapi;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestGui {
    private Player player;
    private Inventory inventory;
    private GuiType type;
    private boolean canMoveItem = false;
    private ChestGuiCallback callback;
    ChestGui(Player player, GuiType type, String title){
        Validate.notNull(type, "type cannot be null");
        Validate.notNull(title, "title cannot be null");
        this.type = type;
        this.player = player;
        switch (type){
            case ROW_1_COL_5:
            case ROW_3_COL_3:
                inventory = Bukkit.createInventory(null,type.getType(),title);
                break;
            default:
                inventory = Bukkit.createInventory(null,type.getSize(),title);
        }
    }

    public int getMaxSize(){
        switch (type){
            case ROW_1_COL_5:
                return 5;
            case ROW_3_COL_3:
                return 9;
            default:
                return type.getSize();
        }
    }

    public void clear(){
        inventory.clear();
    }

    public void clearRow(int row){
        int rowcount = inventory.getType()== InventoryType.DROPPER ? 3 : 9;
        int min = (row-1)*rowcount;
        int max = row*rowcount;
        for (int i = 0;i<getMaxSize();i++){
            if (i>=min && i<max){
                inventory.clear(i);
            }
        }
    }

    public void clearCol(int col){
        int rowcount = inventory.getType()== InventoryType.DROPPER ? 3 : 9;
        for (int i = 0;i<getMaxSize();i++){
            int mod = (i+1)%rowcount;
            if (mod==col || (mod==0 && col==rowcount)){
                inventory.clear(i);
            }
        }
    }

    public void fill(ItemStack item){
        Validate.notNull(item, "item cannot be null");
        for (int i = 0;i<getMaxSize();i++){
            inventory.setItem(i,item.clone());
        }
    }

    public void fillRow(ItemStack item,int row){
        Validate.notNull(item, "item cannot be null");
        int rowcount = inventory.getType()== InventoryType.DROPPER ? 3 : 9;
        Validate.notNull(item, "item cannot be null");
        int min = (row-1)*rowcount;
        int max = row*rowcount;
        for (int i = 0;i<getMaxSize();i++){
            if (i>=min && i<max){
                inventory.setItem(i,item.clone());
            }
        }
    }

    public void fillCol(ItemStack item,int col){
        Validate.notNull(item, "item cannot be null");
        int rowcount = inventory.getType()== InventoryType.DROPPER ? 3 : 9;
        for (int i = 0;i<getMaxSize();i++){
            int mod = (i+1)%rowcount;
            if (mod==col || (mod==0 && col==rowcount)){
                inventory.setItem(i,item.clone());
            }
        }
    }

    public Inventory getInventory(){
        return inventory;
    }

    public HumanEntity getPlayer(){
        return player;
    }

    public void setCanMoveItem(boolean bool){
        canMoveItem = bool;
    }

    public boolean isCanMoveItem(){
        return canMoveItem;
    }

    ChestGuiCallback getCallback(){
        return callback;
    }

    public void open(ChestGuiCallback callback){
        Validate.notNull(callback, "callback cannot be null");
        GuiPlayerHelper.addChestGuiHandler(player,this);
        this.callback = callback;
        player.openInventory(inventory);
    }
}
