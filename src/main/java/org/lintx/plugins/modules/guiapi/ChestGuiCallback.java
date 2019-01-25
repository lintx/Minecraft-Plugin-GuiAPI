package org.lintx.plugins.modules.guiapi;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface ChestGuiCallback {
    void onClick(ChestGui gui, ItemStack item, ClickType clickType);
    void onClose(ChestGui gui);
}
