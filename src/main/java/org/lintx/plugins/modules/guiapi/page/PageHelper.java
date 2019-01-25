package org.lintx.plugins.modules.guiapi.page;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {
    private PageConfig config;
    private PageInterface pageInterface;
    private static final int colCount = 9;
    private int total_page = 1;

    public PageHelper(PageConfig config,PageInterface pageInterface){
        this.config = config;
        this.pageInterface = pageInterface;
    }

    public int getTotalPage(int count){
        if (count<=config.guiRow*colCount){
            total_page = 1;
        }
        else {
            total_page = (int) Math.ceil(count/((config.guiRow-1.0f)*colCount));
        }
        return total_page;
    }

    public int getPageStart(int page){
        if (total_page==1){
            return 0;
        }
        else {
            int page_count = config.guiRow*colCount;
            return (page-1)*page_count;
        }
    }

    public int getPageEnd(int page,int max){
        if (total_page==1){
            return max;
        }
        else {
            int page_count = config.guiRow*colCount;
            return Math.min((page)*page_count,max);
        }
    }

    public List<ItemStack> getPage(int total_page, int current_page){
        List<ItemStack> page = new ArrayList<>();
        if (total_page<=1){
            return page;
        }
        else if (total_page<=colCount){
            int befor = (int) Math.floor((colCount-total_page)/2.0f);
            if (befor>0){
                for (int i=0;i<befor;i++){
                    page.add(null);
                }
            }
            for (int i=0;i<total_page;i++){
                page.add(getPageItem(i+1,current_page));
            }
        }
        else {
            page.add(getPageItem(1,current_page));
            int min = Math.max(current_page-3,2);
            int max = Math.min(min+6,total_page-1);
            if (max-min!=6){
                min = max - 6;
            }
            for (int i=0;i<7;i++){
                page.add(getPageItem(min+i,current_page));
            }
            page.add(getPageItem(total_page,current_page));
        }
        return page;
    }

    public int getPage(ItemStack item){
        if (item.getType().equals(config.currentPageItemType) || item.getType().equals(config.otherPageItemType)){
            return item.getAmount();
        }
        return 0;
    }

    private ItemStack getPageItem(int page,int current_page){
        Material type = page==current_page?config.currentPageItemType:config.otherPageItemType;
        ItemStack item = new ItemStack(type);
        item.setAmount(page);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = null;
        if (page==current_page){
            meta.setDisplayName(pageInterface.currentPageTitle(page));
            lore = pageInterface.currentPageLore(page);
        }
        else {
            meta.setDisplayName(pageInterface.otherPageTitle(page));
            lore = pageInterface.otherPageLore(page);
        }
        if (lore!=null){
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }
}
