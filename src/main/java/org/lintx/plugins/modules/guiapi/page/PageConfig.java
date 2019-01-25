package org.lintx.plugins.modules.guiapi.page;

import org.bukkit.Material;

public class PageConfig {
    public int guiRow = 5;
    public Material currentPageItemType = Material.MAP;
    public Material otherPageItemType = Material.PAPER;

    public PageConfig(){ }
    public PageConfig(int row){
        this.guiRow = row;
    }
    public PageConfig(Material currentPageItemType,Material otherPageItemType){
        this.currentPageItemType = currentPageItemType;
        this.otherPageItemType = otherPageItemType;
    }
    public PageConfig(int row,Material currentPageItemType,Material otherPageItemType){
        this.guiRow = row;
        this.currentPageItemType = currentPageItemType;
        this.otherPageItemType = otherPageItemType;
    }
}
