package ru.dvdishka.bank.shop.Classes.meta.firework;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopItemFirework implements Serializable {

    int power;
    ArrayList<ShopItemFireworkEffect> effects;

    public ShopItemFirework(int power, ArrayList<ShopItemFireworkEffect> effects) {
        this.power = power;
        this.effects = effects;
    }

    public int getPower() {
        return this.power;
    }

    public ArrayList<ShopItemFireworkEffect> getEffects() {
        return this.effects;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setEffects(ArrayList<ShopItemFireworkEffect> effects) {
        this.effects = effects;
    }
}
