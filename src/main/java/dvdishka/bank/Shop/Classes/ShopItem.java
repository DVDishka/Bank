package dvdishka.bank.Shop.Classes;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopItem implements Serializable {

    private String material;
    private int amount;
    private int price;
    private List<String> lore;
    private ArrayList<ShopItemEnchantment> enchantments = new ArrayList<>();

    public ShopItem(String material, int amount, int price, List<String> lore,
                    ArrayList<ShopItemEnchantment> enchantments ArrayList<>) {
        this.material = material;
        this.amount = amount;
        this.price = price;
        this.lore = lore;
        this.enchantments = enchantments;
    }

    public Material getMaterial() {
        return Material.getMaterial(this.material);
    }

    public int getAmount() {
        return this.amount;
    }

    public int getPrice() {
        return this.price;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ArrayList<ShopItemEnchantment> getEnchantments() {
        return this.enchantments;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void setEnchantments(ArrayList<ShopItemEnchantment> enchantments) {
        this.enchantments = enchantments;
    }
}
