package dvdishka.bank.shop.Classes.shop;

import dvdishka.bank.shop.Classes.enchantment.ShopItemEnchantment;
import dvdishka.bank.shop.Classes.potion.MainPotionEffect;
import dvdishka.bank.shop.Classes.potion.ShopItemPotionEffect;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopItem implements Serializable {

    private String name;
    private String material;
    private int amount;
    private int price;
    private List<String> lore;
    private ArrayList<ShopItemEnchantment> enchantments;
    MainPotionEffect mainPotionEffect;
    private ArrayList<ShopItemPotionEffect> potionEffects;

    public ShopItem(String name, String material, int amount, int price, List<String> lore,
                    ArrayList<ShopItemEnchantment> enchantments, MainPotionEffect mainPotionEffect,
                    ArrayList<ShopItemPotionEffect> potionEffects) {
        this.name = name;
        this.material = material;
        this.amount = amount;
        this.price = price;
        this.lore = lore;
        this.enchantments = enchantments;
        this.mainPotionEffect = mainPotionEffect;
        this.potionEffects = potionEffects;
    }

    public String getName() {
        return this.name;
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

    public MainPotionEffect getMainPotionEffect() {
        return this.mainPotionEffect;
    }

    public ArrayList<ShopItemPotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setMainPotionEffect(MainPotionEffect mainPotionEffect) {
        this.mainPotionEffect = mainPotionEffect;
    }

    public void setPotionEffects(ArrayList<ShopItemPotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }
}
