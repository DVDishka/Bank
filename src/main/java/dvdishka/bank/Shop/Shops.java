package dvdishka.bank.Shop;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@SerializableAs("Shops")
public class Shops implements ConfigurationSerializable {

    private HashSet<Shop> shops = new HashSet<>();

    public Shops(HashSet<Shop> shops) {
        this.shops = shops;
    }

    public HashSet<Shop> getShops() {
        return this.shops;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (Shop shop : shops) {
            map.put(String.valueOf(i), shop.serialize());
            i++;
        }
        return map;
    }

    public static Shops deserialize(Map<String, Object> map) {
        HashSet<Shop> shops = new HashSet<>();
        for (Map.Entry<String, Object> shop : map.entrySet()) {
            shops.add(Shop.deserialize((Map<String, Object>) shop.getValue()));
        }
        return new Shops(shops);
    }
}
