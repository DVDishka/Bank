package ru.dvdishka.bank.shop.Classes.meta.firework;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopItemFireworkEffect implements Serializable {

    private String type;
    private ArrayList<Integer> colors;
    private ArrayList<Integer> fadeColors;
    private boolean trail;
    private boolean flicker;

    public ShopItemFireworkEffect(String type, ArrayList<Integer> colors, ArrayList<Integer> fadeColors, boolean trail,
                                  boolean flicker) {
        this.type = type;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.trail = trail;
        this.flicker = flicker;
    }

    public String getType() {
        return this.type;
    }

    public ArrayList<Integer> getColors() {
        return this.colors;
    }

    public ArrayList<Integer> getFadeColors() {
        return this.fadeColors;
    }

    public boolean hasTrail() {
        return this.trail;
    }

    public boolean hasFlicker() {
        return this.flicker;
    }
}
