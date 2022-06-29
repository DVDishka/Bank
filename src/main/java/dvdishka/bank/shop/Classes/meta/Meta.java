package dvdishka.bank.shop.Classes.meta;

import dvdishka.bank.shop.Classes.meta.book.ShopItemBook;
import dvdishka.bank.shop.Classes.meta.enchantment.ShopItemEnchantment;
import dvdishka.bank.shop.Classes.meta.potion.MainPotionEffect;
import dvdishka.bank.shop.Classes.meta.potion.ShopItemPotionEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meta implements Serializable {
    private String name;
    private List<String> lore;
    private ArrayList<ShopItemEnchantment> enchantments;
    private ArrayList<ShopItemEnchantment> storedEnchantments;
    MainPotionEffect mainPotionEffect;
    private ArrayList<ShopItemPotionEffect> potionEffects;
    private ShopItemBook shopItemBook;

    public Meta(String name, List<String> lore,
                    ArrayList<ShopItemEnchantment> enchantments, ArrayList<ShopItemEnchantment> storedEnchantments,
                MainPotionEffect mainPotionEffect, ArrayList<ShopItemPotionEffect> potionEffects, ShopItemBook shopItemBook) {
        this.name = name;
        this.lore = lore;
        this.enchantments = enchantments;
        this.storedEnchantments = storedEnchantments;
        this.mainPotionEffect = mainPotionEffect;
        this.potionEffects = potionEffects;
        this.shopItemBook = shopItemBook;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ArrayList<ShopItemEnchantment> getEnchantments() {
        return this.enchantments;
    }

    public ArrayList<ShopItemEnchantment> getStoredEnchantments() {
        return this.storedEnchantments;
    }

    public MainPotionEffect getMainPotionEffect() {
        return this.mainPotionEffect;
    }

    public ArrayList<ShopItemPotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public ShopItemBook getShopItemBook() {
        return this.shopItemBook;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void setEnchantments(ArrayList<ShopItemEnchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public void setStoredEnchantments(ArrayList<ShopItemEnchantment> storedEnchantments) {
        this.storedEnchantments = storedEnchantments;
    }

    public void setMainPotionEffect(MainPotionEffect mainPotionEffect) {
        this.mainPotionEffect = mainPotionEffect;
    }

    public void setPotionEffects(ArrayList<ShopItemPotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public void setShopItemBook(ShopItemBook shopItemBook) {
        this.shopItemBook = shopItemBook;
    }
}
