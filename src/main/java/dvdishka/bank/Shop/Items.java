package dvdishka.bank.Shop;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SerializableAs("Items")
public class Items implements ConfigurationSerializable {

    ArrayList<ItemStack> items = new ArrayList<>(27);

    public Items(ArrayList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (ItemStack item : items) {
            map.put(String.valueOf(i), item.serialize());
            i++;
        }
        return map;
    }

    public ItemStack get(int index) {
        if (items.size() > index) {
            return items.get(index);
        } else {
            return null;
        }
    }

    public static Items deserialize(Map<String, Object> map) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            items.set(Integer.parseInt(entry.getKey()), ItemStack.deserialize((Map<String, Object>) entry.getValue()));
        }
        return new Items(items);
    }
}
