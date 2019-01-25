package org.lintx.plugins.modules.guiapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class RenameGui {
    RenameGui(Player player, String defaultName){
        try {
            Class<?> ContainerAnvil = NMSUtil.getNMSClass("ContainerAnvil");
            Class<?> EntityHuman = NMSUtil.getNMSClass("EntityHuman");
            Class<?> BlockPosition = NMSUtil.getNMSClass("BlockPosition");
            Class<?> PacketPlayOutOpenWindow = NMSUtil.getNMSClass("PacketPlayOutOpenWindow");
            Class<?> ChatMessage = NMSUtil.getNMSClass("ChatMessage");
            Inventory inv;

            Object craftPlayer = NMSUtil.getCraftClass("entity.CraftPlayer").cast(player);
            Method getHandleMethod = craftPlayer.getClass().getMethod("getHandle", new Class<?>[0]);
            Object entityPlayer = getHandleMethod.invoke(craftPlayer, new Object[0]);

            Object container;

            if (NMSUtil.getVersionNumber() == 7) {
                container = ContainerAnvil
                        .getConstructor(
                                new Class[] { NMSUtil.getNMSClass("PlayerInventory"), NMSUtil.getNMSClass("World"),
                                        Integer.TYPE, Integer.TYPE, Integer.TYPE, EntityHuman })
                        .newInstance(new Object[] {
                                NMSUtil.getFieldObject(entityPlayer,
                                        NMSUtil.getField(entityPlayer.getClass(), "inventory", false)),
                                NMSUtil.getFieldObject(entityPlayer,
                                        NMSUtil.getField(entityPlayer.getClass(), "world", false)),
                                Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), entityPlayer });
            } else {
                container = ContainerAnvil
                        .getConstructor(NMSUtil.getNMSClass("PlayerInventory"), NMSUtil.getNMSClass("World"),
                                BlockPosition, EntityHuman)
                        .newInstance(
                                NMSUtil.getFieldObject(entityPlayer,
                                        NMSUtil.getField(entityPlayer.getClass(), "inventory", false)),
                                NMSUtil.getFieldObject(entityPlayer,
                                        NMSUtil.getField(entityPlayer.getClass(), "world", false)),
                                BlockPosition.getConstructor(int.class, int.class, int.class).newInstance(0, 0, 0),
                                entityPlayer);
            }

            NMSUtil.getField(NMSUtil.getNMSClass("Container"), "checkReachable", true).set(container, false);

            Method getBukkitViewMethod = container.getClass().getMethod("getBukkitView", new Class<?>[0]);
            Object bukkitView = getBukkitViewMethod.invoke(container);
            Method getTopInventoryMethod = bukkitView.getClass().getMethod("getTopInventory", new Class<?>[0]);
            inv = (Inventory) getTopInventoryMethod.invoke(bukkitView);


            ItemStack barrier = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = barrier.getItemMeta();
            meta.setDisplayName(defaultName);
            barrier.setItemMeta(meta);
            inv.setItem(0,barrier);

            Method nextContainerCounterMethod = entityPlayer.getClass().getMethod("nextContainerCounter",
                    new Class<?>[0]);
            Object c = nextContainerCounterMethod.invoke(entityPlayer);

            Constructor<?> chatMessageConstructor = ChatMessage.getConstructor(String.class, Object[].class);
            Object packet;

            if (NMSUtil.getVersionNumber() == 7) {
                packet = PacketPlayOutOpenWindow
                        .getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE,
                                Boolean.TYPE, Integer.TYPE })
                        .newInstance(new Object[] { Integer.valueOf((Integer) c), Integer.valueOf(8), "Repairing",
                                Integer.valueOf(0), Boolean.valueOf(true), Integer.valueOf(0) });
            } else {
                packet = PacketPlayOutOpenWindow
                        .getConstructor(int.class, String.class, NMSUtil.getNMSClass("IChatBaseComponent"), int.class)
                        .newInstance(c, "minecraft:anvil",
                                chatMessageConstructor.newInstance("Repairing", new Object[] {}), 0);
            }

            NMSUtil.sendPacket(player, packet);

            Field activeContainerField = NMSUtil.getField(EntityHuman, "activeContainer", true);

            if (activeContainerField != null) {
                activeContainerField.set(entityPlayer, container);
                NMSUtil.getField(NMSUtil.getNMSClass("Container"), "windowId", true)
                        .set(activeContainerField.get(entityPlayer), c);

                Method addSlotListenerMethod = activeContainerField.get(entityPlayer).getClass()
                        .getMethod("addSlotListener", NMSUtil.getNMSClass("ICrafting"));
                addSlotListenerMethod.invoke(activeContainerField.get(entityPlayer), entityPlayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
