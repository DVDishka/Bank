package ru.dvdishka.bank.shops.common;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
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
}
