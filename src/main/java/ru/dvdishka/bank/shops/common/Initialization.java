package ru.dvdishka.bank.shops.common;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import ru.dvdishka.bank.shops.Classes.Shop;
import ru.dvdishka.bank.shops.handlers.events.shopCoffer.InfiniteShopCofferEvent;
import ru.dvdishka.bank.shops.handlers.events.shopCoffer.ShopCofferEvent;
import ru.dvdishka.bank.shops.handlers.events.shopInventory.InfiniteBuyShopInventoryEvent;
import ru.dvdishka.bank.shops.handlers.events.shopInventory.InfiniteSellShopInventoryEvent;
import ru.dvdishka.bank.shops.handlers.events.shopInventory.ShopInventoryEvent;
import ru.dvdishka.bank.shops.handlers.events.shopMenu.InfiniteBuyShopMenuEvent;
import ru.dvdishka.bank.shops.handlers.events.shopMenu.InfiniteSellShopMenuEvent;
import ru.dvdishka.bank.shops.handlers.events.shopMenu.ShopMenuEvent;
import ru.dvdishka.bank.shops.handlers.events.shopMenu.shopEdit.IconMenuEvent;
import ru.dvdishka.bank.shops.handlers.events.shopMenu.shopEdit.UpgradeMenuClick;

import java.util.ArrayList;

public class Initialization {

    public static void registerEventHandlers(Plugin plugin) {

        Bukkit.getPluginManager().registerEvents(new ShopMenuEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new InfiniteSellShopMenuEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new InfiniteBuyShopMenuEvent(), plugin);

        Bukkit.getPluginManager().registerEvents(new ShopInventoryEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new InfiniteSellShopInventoryEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new InfiniteBuyShopInventoryEvent(), plugin);

        Bukkit.getPluginManager().registerEvents(new IconMenuEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new UpgradeMenuClick(), plugin);

        Bukkit.getPluginManager().registerEvents(new ShopCofferEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new InfiniteShopCofferEvent(), plugin);
    }

    public static void registerCommands() {
        PluginCommand shopCommand = Bukkit.getPluginCommand("shop");

        assert shopCommand != null;

        ru.dvdishka.bank.shops.handlers.CommandExecutor shopCommandExecutor = new ru.dvdishka.bank.shops.handlers.CommandExecutor();
        ru.dvdishka.bank.shops.handlers.TabCompleter shopTabCompleter = new ru.dvdishka.bank.shops.handlers.TabCompleter();

        shopCommand.setExecutor(shopCommandExecutor);
        shopCommand.setTabCompleter(shopTabCompleter);
    }

    public static void shopMenuCreating() {

        ArrayList<Inventory> shopsIcons = new ArrayList<>();

        int shopsAmount = CommonVariables.shops.size();

        int i = 1;

        while (shopsAmount > 0) {

            Inventory page = Bukkit.createInventory(null, 54,
                    ChatColor.GOLD + "Shops " + i);

            page.setItem(45, CommonVariables.prevPage);
            page.setItem(53, CommonVariables.nextPage);

            shopsIcons.add(page);
            shopsAmount -= 52;
            i++;
        }

        int stackIndex = 0;
        int inventoryIndex = 0;

        for (Shop shop : CommonVariables.shops) {

            if (stackIndex == 45) {

                stackIndex++;
            }
            if (stackIndex == 53) {

                stackIndex = 0;
                inventoryIndex++;
            }

            shopsIcons.get(inventoryIndex).setItem(stackIndex, shop.getIcon());
            stackIndex++;
        }

        stackIndex = 0;

        Inventory lastPage = shopsIcons.get(inventoryIndex);

        for (ItemStack itemStack : lastPage) {

            if (itemStack == null) {

                lastPage.setItem(stackIndex, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
            }
            stackIndex++;
        }

        shopsIcons.set(inventoryIndex, lastPage);
        CommonVariables.shopMenu = shopsIcons;
    }

    public static void infiniteSellShopCreating() {

        ArrayList<Inventory> shopsIcons = new ArrayList<>();
        ArrayList<Shop> infiniteSellShops = new ArrayList<>();

        int shopsAmount = 0;

        for (Shop shop : CommonVariables.infiniteShops) {

            if (shop.isSell()) {

                infiniteSellShops.add(shop);
                shopsAmount++;
            }
        }

        int pageNumber = 1;

        while (shopsAmount > 0) {

            Inventory page = Bukkit.createInventory(null, 54,
                    ChatColor.RED + "Infinite Sell Shops " + pageNumber);

            page.setItem(45, CommonVariables.prevPage);
            page.setItem(53, CommonVariables.nextPage);

            shopsIcons.add(page);
            shopsAmount -= 52;
            pageNumber++;
        }

        int stackIndex = 0;
        int inventoryIndex = 0;

        for (Shop shop : infiniteSellShops) {

            if (stackIndex == 45) {

                stackIndex++;
            }
            if (stackIndex == 53) {

                stackIndex = 0;
                inventoryIndex++;
            }

            shopsIcons.get(inventoryIndex).setItem(stackIndex, shop.getIcon());
            stackIndex++;
        }

        stackIndex = 0;

        Inventory lastPage = shopsIcons.get(inventoryIndex);

        for (ItemStack itemStack : lastPage) {

            if (itemStack == null) {

                lastPage.setItem(stackIndex, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
            }
            stackIndex++;
        }

        shopsIcons.set(inventoryIndex, lastPage);
        CommonVariables.infiniteSellShopMenu = shopsIcons;
    }

    public static void infiniteBuyShopMenuCreating() {

        ArrayList<Inventory> shopsIcons = new ArrayList<>();
        ArrayList<Shop> infiniteBuyShops = new ArrayList<>();

        int shopsAmount = 0;

        for (Shop shop : CommonVariables.infiniteShops) {

            if (!shop.isSell()) {

                infiniteBuyShops.add(shop);
                shopsAmount++;
            }
        }

        int pageNumber = 1;

        while (shopsAmount > 0) {

            Inventory page = Bukkit.createInventory(null, 54,
                    ChatColor.GREEN + "Infinite Buy Shops " + pageNumber);

            page.setItem(45, CommonVariables.prevPage);
            page.setItem(53, CommonVariables.nextPage);

            shopsIcons.add(page);
            shopsAmount -= 52;
            pageNumber++;
        }

        int stackIndex = 0;
        int inventoryIndex = 0;

        for (Shop shop : infiniteBuyShops) {

            if (stackIndex == 45) {

                stackIndex++;
            }
            if (stackIndex == 53) {

                stackIndex = 0;
                inventoryIndex++;
            }

            shopsIcons.get(inventoryIndex).setItem(stackIndex, shop.getIcon());
            stackIndex++;
        }

        stackIndex = 0;

        Inventory lastPage = shopsIcons.get(inventoryIndex);

        for (ItemStack itemStack : lastPage) {

            if (itemStack == null) {

                lastPage.setItem(stackIndex, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
            }
            stackIndex++;
        }

        shopsIcons.set(inventoryIndex, lastPage);
        CommonVariables.infiniteBuyShopMenu = shopsIcons;
    }
}
