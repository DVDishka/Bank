package ru.dvdishka.bank.common;

import ru.dvdishka.bank.shop.Classes.Shop;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class CommonVariables {

    public static Logger logger = Bukkit.getLogger();
    public static ArrayList<Shop> shops = new ArrayList<>();
    public static HashMap<String, Inventory> shopsInventories = new HashMap<>();
}
