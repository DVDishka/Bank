package dvdishka.bank.Shop;

import dvdishka.bank.common.CommonVariables;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("Shop")
public class Shop implements ConfigurationSerializable {

    private String name;
    private String owner;
    private Items items = new Items(new ArrayList<>(27));

    public Shop(String name, String owner, Items items) {
        this.name = name;
        this.owner = owner;
        this.items = items;
    }

    public Shop(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public Items getItems() {
        return this.items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setItems(Items items) {
        this.items = items;
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
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("owner", this.owner);
        map.put("Items", items.serialize());
        return map;
    }

    public static Shop deserialize(Map<String, Object> map) {
        String name = String.valueOf(map.get("name"));
        String owner = String.valueOf(map.get("owner"));
        Items items = Items.deserialize((Map<String, Object>) map.get("Items"));
        return new Shop(name, owner, items);
    }
}
