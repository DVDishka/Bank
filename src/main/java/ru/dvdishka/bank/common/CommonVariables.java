package ru.dvdishka.bank.common;

import ru.dvdishka.bank.shop.Classes.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class CommonVariables {

    public static Logger logger = Bukkit.getLogger();
    public static HashSet<Shop> shops = new HashSet<>();
    public static HashMap<String, Inventory> shopsInventories = new HashMap<>();
}
