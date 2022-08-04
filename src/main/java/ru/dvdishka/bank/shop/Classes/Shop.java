package ru.dvdishka.bank.shop.Classes;

import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.dvdishka.bank.common.CommonVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("Shop")
public class Shop implements ConfigurationSerializable {

    private String name;
    private String owner;
    private ArrayList<ShopItem> items = new ArrayList<>(27);
    private int cardNumber;
    private ItemStack icon;

    public Shop(String name, String owner, ArrayList<ShopItem> items, int cardNumber, ItemStack icon) {
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

    public ArrayList<ShopItem> getItems() {
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

    public void setItems(ArrayList<ShopItem> items) {
        this.items = items;
    }

    public void setItem(int index, ShopItem item) {
        this.items.set(index, item);
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
        map.put("items", items);
        map.put("cardNumber", cardNumber);
        map.put("icon", icon);
        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {
        String name = (String) map.get("name");
        String owner = (String) map.get("owner");
        ArrayList<ShopItem> items = (ArrayList<ShopItem>) map.get("items");
        int cardNumber = (int) map.get("cardNumber");
        ItemStack icon = (ItemStack) map.get("icon");
        return new Shop(name, owner, items, cardNumber, icon);
    }
}
