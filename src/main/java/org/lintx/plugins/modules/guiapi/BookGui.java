package org.lintx.plugins.modules.guiapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookGui {
    static void openBook(Player player,String defaultPage){
        player.closeInventory();
        ItemStack hand = player.getInventory().getItemInMainHand();

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        if (defaultPage!=null && !defaultPage.equals("")){
            BookMeta meta = (BookMeta) book.getItemMeta();
            meta.addPage(defaultPage);
            book.setItemMeta(meta);
        }
        player.getInventory().setItemInMainHand(book);

        NMSUtil.openBook(player,book);
        player.getInventory().setItemInMainHand(hand);
    }
}
