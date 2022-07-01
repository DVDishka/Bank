package ru.dvdishka.bank.shop.Classes.meta.banner;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopItemBanner implements Serializable {

    private final ArrayList<ShopItemPattern> patterns;

    public ShopItemBanner(ArrayList<ShopItemPattern> patterns) {
        this.patterns = patterns;
    }

    public ArrayList<ShopItemPattern> getPatterns() {
        return this.patterns;
    }
}
