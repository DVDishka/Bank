package ru.dvdishka.bank.shop.Classes.meta.banner;

import java.io.Serializable;

public class ShopItemPatternColor implements Serializable {

    private final int dyeData;
    private final int woolData;
    private final int color;
    private final int fireworkData;

    public ShopItemPatternColor(int dyeData, int woolData, int color, int fireworkData) {
        this.dyeData = dyeData;
        this.woolData = woolData;
        this.color = color;
        this.fireworkData = fireworkData;
    }

    public int getDyeData() {
        return this.dyeData;
    }

    public int getWoolData() {
        return this.woolData;
    }

    public int getColor() {
        return this.color;
    }

    public int getFireworkData() {
        return this.fireworkData;
    }
}
