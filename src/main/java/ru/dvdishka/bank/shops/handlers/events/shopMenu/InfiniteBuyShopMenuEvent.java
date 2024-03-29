package ru.dvdishka.bank.shops.handlers.events.shopMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dvdishka.bank.shops.common.CommonVariables;
import ru.dvdishka.bank.shops.Classes.Shop;

public class InfiniteBuyShopMenuEvent implements Listener {

    @org.bukkit.event.EventHandler
    public void onInfiniteBuyShopMenuInventoryClick(InventoryClickEvent event) {

        int i = 0;

        for (Inventory shopMenuPage : CommonVariables.infiniteBuyShopMenu) {

            if (shopMenuPage == event.getView().getTopInventory()) {

                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem() != null) {

                    if (event.getCurrentItem().equals(CommonVariables.prevPage)) {

                        if (i > 0) {

                            player.playSound(player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50, 1);
                            event.getWhoClicked().openInventory(CommonVariables.infiniteBuyShopMenu.get(i - 1));
                        } else {

                            player.playSound(player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50, 1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem().equals(CommonVariables.nextPage)) {

                        if (i < CommonVariables.infiniteBuyShopMenu.size() - 1) {

                            player.playSound(player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50, 1);
                            event.getWhoClicked().openInventory(CommonVariables.infiniteBuyShopMenu.get(i + 1));
                        } else {

                            player.playSound(player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50, 1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem().equals(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE))) {

                        player.playSound(player.getLocation(),
                                org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                50, 1);

                        event.setCancelled(true);
                        return;
                    }

                    ItemMeta currentItemMeta = event.getCurrentItem().getItemMeta();

                    player.playSound(player.getLocation(),
                            org.bukkit.Sound.UI_BUTTON_CLICK,
                            50, 1);

                    if (!event.getWhoClicked().getName().equals(Shop.getShop(currentItemMeta.getDisplayName()).getOwner())) {

                        int pageIndex = 0;

                        for (Inventory inventory : CommonVariables.infiniteShopsInventories.get(currentItemMeta.getDisplayName())) {

                            int index = 0;

                            for (ItemStack item : inventory) {

                                if (item == null) {

                                    CommonVariables.infiniteShopsInventories.get(currentItemMeta.getDisplayName()).get(pageIndex)
                                            .setItem(index, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                                }
                                index++;
                            }
                            pageIndex++;
                        }
                    } else {

                        int pageIndex = 0;

                        for (Inventory inventory : CommonVariables.infiniteShopsInventories.get(currentItemMeta.getDisplayName())) {

                            int index = 0;

                            for (ItemStack item : inventory) {

                                if (item != null && item.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {

                                    CommonVariables.infiniteShopsInventories.get(currentItemMeta.getDisplayName()).get(pageIndex)
                                            .setItem(index, null);
                                }
                                index++;
                            }
                            pageIndex++;
                        }
                    }

                    event.getWhoClicked().openInventory(CommonVariables.infiniteShopsInventories
                            .get(currentItemMeta.getDisplayName()).get(0));
                    event.setCancelled(true);
                    return;
                }
                return;
            }
            i++;
        }
    }
}
