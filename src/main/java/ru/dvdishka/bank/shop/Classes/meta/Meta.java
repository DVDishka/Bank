package ru.dvdishka.bank.shop.Classes.meta;

import ru.dvdishka.bank.shop.Classes.map.ShopItemMap;
import ru.dvdishka.bank.shop.Classes.meta.banner.ShopItemBanner;
import ru.dvdishka.bank.shop.Classes.meta.book.ShopItemBook;
import ru.dvdishka.bank.shop.Classes.meta.enchantment.ShopItemEnchantment;
import ru.dvdishka.bank.shop.Classes.meta.firework.ShopItemFirework;
import ru.dvdishka.bank.shop.Classes.meta.potion.MainPotionEffect;
import ru.dvdishka.bank.shop.Classes.meta.potion.ShopItemPotionEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meta implements Serializable {
    private final String name;
    private final List<String> lore;
    private final ArrayList<ShopItemEnchantment> enchantments;
    private final ArrayList<ShopItemEnchantment> storedEnchantments;
    private final MainPotionEffect mainPotionEffect;
    private final ArrayList<ShopItemPotionEffect> potionEffects;
    private final ShopItemBook shopItemBook;
    private final ShopItemFirework shopItemFirework;
    private final ShopItemBanner shopItemBanner;
    private final ShopItemMap shopItemMap;

    public Meta(String name, List<String> lore,
                    ArrayList<ShopItemEnchantment> enchantments, ArrayList<ShopItemEnchantment> storedEnchantments,
                MainPotionEffect mainPotionEffect, ArrayList<ShopItemPotionEffect> potionEffects,
                ShopItemBook shopItemBook, ShopItemFirework shopItemFirework, ShopItemBanner shopItemBanner,
                ShopItemMap shopItemMap) {
        this.name = name;
        this.lore = lore;
        this.enchantments = enchantments;
        this.storedEnchantments = storedEnchantments;
        this.mainPotionEffect = mainPotionEffect;
        this.potionEffects = potionEffects;
        this.shopItemBook = shopItemBook;
        this.shopItemFirework = shopItemFirework;
        this.shopItemBanner = shopItemBanner;
        this.shopItemMap = shopItemMap;
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

    public ShopItemFirework getShopItemFirework() {
        return this.shopItemFirework;
    }

    public ShopItemBanner getShopItemBanner() {
        return this.shopItemBanner;
    }

    public ShopItemMap getShopItemMap() {
        return this.shopItemMap;
    }
}
