package org.lintx.plugins.modules.guiapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiAPI extends JavaPlugin {
    private static GuiAPI plugin;

    static GuiAPI getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new GuiListener(),plugin);
    }

    @Override
    public void onDisable() {
        GuiPlayerHelper.clear();
    }

    public static ChestGui createChestGui(Player player, GuiType type, String title){
        return new ChestGui(player,type,title);
    }

    public static void openRenameGui(Player player,String defaultName,RenameCallback callback){
        GuiPlayerHelper.addRenameHandler(player,callback);
        RenameGui gui = new RenameGui(player,defaultName);
    }

    public static void openBook(Player player,String defaultPage){
        BookGui.openBook(player,defaultPage);
    }
}
