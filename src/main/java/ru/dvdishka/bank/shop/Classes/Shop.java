package ru.dvdishka.bank.shop.Classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.dvdishka.bank.backwardsCompatibility.ShopItem;
import ru.dvdishka.bank.common.CommonVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("Shop")
public class Shop implements ConfigurationSerializable {

    private String name;
    private String owner;
    private ArrayList<Inventory> items = new ArrayList<>();
    private int cardNumber;
    private ItemStack icon;

    public Shop(String name, String owner, ArrayList<Inventory> items, int cardNumber, ItemStack icon) {
        this.name = name;
        this.owner = owner;
        this.items = items;
        this.cardNumber = cardNumber;
        this.icon = icon;
    }

    public Shop(String name, String owner, int cardNumber, ItemStack icon) {
        this.name = name;
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public ArrayList<Inventory> getItems() {
        return this.items;
    }

    public int getCardNumber() {
        return this.cardNumber;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setItems(ArrayList<Inventory> items) {
        this.items = items;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public static Shop getShop(String name) {
        for (Shop shop : CommonVariables.shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        return null;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        ArrayList<ArrayList<ItemStack>> contents = new ArrayList<>();
        for (Inventory inventory : CommonVariables.shopsInventories.get(name)) {
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                itemStacks.add(inventory.getItem(i));
            }
            contents.add(itemStacks);
        }
        map.put("items", contents);
        map.put("cardNumber", cardNumber);
        map.put("icon", icon);
        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {
        String name = (String) map.get("name");
        String owner = (String) map.get("owner");
        ArrayList<ArrayList<ItemStack>> contents = (ArrayList<ArrayList<ItemStack>>) map.get("items");
        int cardNumber = (int) map.get("cardNumber");
        ItemStack icon;
        if (map.get("icon") == null) {
            icon = new ItemStack(Material.BARRIER);
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(name);
            icon.setItemMeta(iconMeta);
        } else {
            icon = (ItemStack) map.get("icon");
        }
        try {

            int i = 1;
            ArrayList<Inventory> items = new ArrayList<>();
            for (ArrayList<ItemStack> item : contents) {
                Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + name + " " + i);
                int index = 0;
                for (ItemStack itemStack : item) {
                    inventory.setItem(index, itemStack);
                    index++;
                }
                items.add(inventory);
                i++;
            }
            return new Shop(name, owner, items, cardNumber, icon);
        } catch (Exception e) {
            ArrayList<Inventory> items = new ArrayList<>();
            ArrayList<ShopItem> oldItems = (ArrayList<ShopItem>) map.get("items");
            Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + name + " 1");
            int i = 0;
            for (ShopItem shopItem : oldItems) {
                if (shopItem != null) {
                    inventory.setItem(i, shopItem.getItem());
                } else {
                    inventory.setItem(i, null);
                }
                i++;
            }
            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta prevPageMeta = prevPage.getItemMeta();
            prevPageMeta.setDisplayName("<--");
            prevPage.setItemMeta(prevPageMeta);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName("-->");
            nextPage.setItemMeta(nextPageMeta);
            inventory.setItem(18, prevPage);
            inventory.setItem(26, nextPage);
            items.add(inventory);
            return new Shop(name, owner, items, cardNumber, icon);
        }
    }
}
