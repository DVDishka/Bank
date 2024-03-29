package ru.dvdishka.bank.shops.Classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.dvdishka.bank.shops.backwardsCompatibility.ShopItem;
import ru.dvdishka.bank.shops.common.CommonVariables;
import ru.dvdishka.bank.shops.common.ConfigVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("Shop")
public class Shop implements ConfigurationSerializable {

    private String name;
    private String owner;
    private ArrayList<Inventory> items = new ArrayList<>();
    private ArrayList<Inventory> coffer = new ArrayList<>();
    private ItemStack icon;
    private Upgrade upgrade;
    private boolean infinite;
    private boolean sell;

    public Shop(String name, String owner, ArrayList<Inventory> items, ArrayList<Inventory> coffer, ItemStack icon,
                Upgrade upgrade, boolean infinite, boolean sell) {
        this.name = name;
        this.owner = owner;
        this.items = items;
        this.coffer = coffer;
        this.icon = icon;
        this.upgrade = upgrade;
        this.infinite = infinite;
        this.sell = sell;
    }

    public Shop(String name, String owner, ItemStack icon) {
        this.name = name;
        this.owner = owner;
        this.icon = icon;
        if (ConfigVariables.defaultInventorySize % 9 == 0) {
            this.upgrade = new Upgrade(1, ConfigVariables.defaultInventorySize / 9);
        } else {
            this.upgrade = new Upgrade(1, ConfigVariables.defaultInventorySize / 9 + 1);
        }
        this.infinite = false;
        this.sell = false;
    }

    public Shop(String name, String owner, ItemStack icon, boolean infinite, boolean sell) {
        this.name = name;
        this.owner = owner;
        this.icon = icon;
        if (ConfigVariables.defaultInventorySize % 9 == 0) {
            this.upgrade = new Upgrade(1, ConfigVariables.defaultInventorySize / 9);
        } else {
            this.upgrade = new Upgrade(1, ConfigVariables.defaultInventorySize / 9 + 1);
        }
        this.infinite = infinite;
        this.sell = sell;
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

    public ArrayList<Inventory> getCoffer() {
        return this.coffer;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public Upgrade getUpgrade() {
        return this.upgrade;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    public boolean isSell() {
        return this.sell;
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

    public void setCoffer(ArrayList<Inventory> coffer) {
        this.coffer = coffer;
    }

    public void addCoffer(Inventory coffer) {
        this.coffer.add(coffer);
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public static Shop getShop(String name) {
        for (Shop shop : CommonVariables.shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        for (Shop shop : CommonVariables.infiniteShops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        return null;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        ArrayList<ArrayList<ItemStack>> contents = new ArrayList<>();
        if (!isInfinite()) {

            for (Inventory inventory : CommonVariables.shopsInventories.get(name)) {

                ArrayList<ItemStack> itemStacks = new ArrayList<>();

                for (int i = 0; i < inventory.getSize(); i++) {

                    itemStacks.add(inventory.getItem(i));
                }
                contents.add(itemStacks);
            }
        } else {

            for (Inventory inventory : CommonVariables.infiniteShopsInventories.get(name)) {

                ArrayList<ItemStack> itemStacks = new ArrayList<>();

                for (int i = 0; i < inventory.getSize(); i++) {

                    itemStacks.add(inventory.getItem(i));
                }
                contents.add(itemStacks);
            }
        }
        ArrayList<ArrayList<ItemStack>> coffers = new ArrayList<>();
        for (Inventory inventory : coffer) {
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                itemStacks.add(inventory.getItem(i));
            }
            coffers.add(itemStacks);
        }
        map.put("coffer", coffers);
        map.put("items", contents);
        map.put("icon", icon);
        map.put("upgrade", upgrade);
        map.put("infinite", infinite);
        map.put("sell", sell);
        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {

        String name = (String) map.get("name");
        String owner = (String) map.get("owner");

        boolean infinite = (boolean) map.get("infinite");
        boolean sell = (boolean) map.get("sell");

        ArrayList<ArrayList<ItemStack>> contents = (ArrayList<ArrayList<ItemStack>>) map.get("items");
        ArrayList<ArrayList<ItemStack>> coffers = (ArrayList<ArrayList<ItemStack>>) map.get("coffer");

        ItemStack icon;

        if (map.get("icon") == null) {

            icon = new ItemStack(Material.BARRIER);
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(name);
            icon.setItemMeta(iconMeta);

        } else {

            icon = (ItemStack) map.get("icon");
        }

        Upgrade upgrade = (Upgrade) map.get("upgrade");

        ArrayList<Inventory> coffer = new ArrayList<>();

        int i = 1;

        for (ArrayList<ItemStack> item : coffers) {

            Inventory inventory = Bukkit.createInventory(null, item.size(),
                    ChatColor.GOLD + name + " coffer " + i);

            int index = 0;

            for (ItemStack itemStack : item) {

                if (index != ConfigVariables.defaultPrevPageIndex && index != ConfigVariables.defaultNextPageIndex) {
                    inventory.setItem(index, itemStack);
                }
                index++;
            }

            inventory.setItem(ConfigVariables.defaultPrevPageIndex, CommonVariables.prevPage);
            inventory.setItem(ConfigVariables.defaultNextPageIndex, CommonVariables.nextPage);

            coffer.add(inventory);
            i++;
        }

        try {

            i = 1;

            ArrayList<Inventory> items = new ArrayList<>();

            for (ArrayList<ItemStack> item : contents) {

                Inventory inventory;

                if (!infinite) {

                    inventory = Bukkit.createInventory(null, item.size(),
                            ChatColor.GOLD + name + " " + i);

                } else {

                    if (sell) {

                        inventory = Bukkit.createInventory(null, item.size(),
                                ChatColor.RED + name + " " + i);

                    } else {

                        inventory = Bukkit.createInventory(null, item.size(),
                                ChatColor.GREEN + name + " " + i);
                    }
                }

                int index = 0;

                for (ItemStack itemStack : item) {

                    inventory.setItem(index, itemStack);
                    index++;
                }

                items.add(inventory);
                i++;
            }

            return new Shop(name, owner, items, coffer, icon, upgrade, infinite, sell);

        } catch (Exception e) {

            ArrayList<Inventory> items = new ArrayList<>();
            ArrayList<ShopItem> oldItems = (ArrayList<ShopItem>) map.get("items");

            Inventory inventory = Bukkit.createInventory(null, 27,
                    ChatColor.GOLD + name + " 1");

            i = 0;

            for (ShopItem shopItem : oldItems) {

                if (shopItem != null) {

                    inventory.setItem(i, shopItem.getItem());

                } else {

                    inventory.setItem(i, null);
                }

                i++;
            }

            inventory.setItem(ConfigVariables.defaultPrevPageIndex, CommonVariables.prevPage);
            inventory.setItem(ConfigVariables.defaultNextPageIndex, CommonVariables.nextPage);

            items.add(inventory);

            return new Shop(name, owner, items, coffer, icon, upgrade, infinite, sell);
        }
    }
}