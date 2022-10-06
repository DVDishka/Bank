package ru.dvdishka.bank.shops.common;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConfigVariables {

    public static ItemStack shopCost = new ItemStack(Material.DIAMOND, 10);
    public static ItemStack newPageCost = new ItemStack(Material.DIAMOND, 10);
    public static ItemStack newLineCost = new ItemStack(Material.DIAMOND, 10);
    public static boolean isShopCostBank = false;
    public static boolean isNewPageCostBank = false;
    public static boolean isNewLineCostBank = false;
    public static int shopCostBank = 0;
    public static int newPageCostBank = 0;
    public static int newLineCostBank = 0;
    public static int defaultInventorySize = 27;
    public static int defaultNextPageIndex = 26;
    public static int defaultPrevPageIndex = 18;
    public static String blancvillePath = "";
}
