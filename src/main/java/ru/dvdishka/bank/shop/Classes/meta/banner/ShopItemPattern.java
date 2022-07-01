package ru.dvdishka.bank.shop.Classes.meta.banner;

import java.io.Serializable;

public class ShopItemPattern implements Serializable {

    private final String type;
    private final ShopItemPatternColor patternColor;

    public ShopItemPattern(String type, ShopItemPatternColor patternColor) {
        this.type = type;
        this.patternColor = patternColor;
    }

    public String getType() {
        return this.type;
    }

    public ShopItemPatternColor getPatternColor() {
        return this.patternColor;
    }
}
