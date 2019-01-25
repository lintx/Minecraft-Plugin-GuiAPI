package org.lintx.plugins.modules.guiapi;

import org.bukkit.event.inventory.InventoryType;

public enum GuiType {
    ROW_1_COL_9(InventoryType.CHEST,9),
    ROW_2_COL_9(InventoryType.CHEST,18),
    ROW_3_COL_9(InventoryType.CHEST,27),
    ROW_4_COL_9(InventoryType.CHEST,36),
    ROW_5_COL_9(InventoryType.CHEST,45),
    ROW_6_COL_9(InventoryType.CHEST,54),
    ROW_3_COL_3(InventoryType.DROPPER),
    ROW_1_COL_5(InventoryType.HOPPER);

    private InventoryType type;
    private int size;
    private GuiType(InventoryType type){
        this(type,0);
    }

    private GuiType(InventoryType type,int size){
        this.type = type;
        this.size = size;
    }

    InventoryType getType(){
        return type;
    }

    int getSize(){
        return size;
    }
}
