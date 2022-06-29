package dvdishka.bank.shop.Classes.meta.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.Serializable;

public class ShopItemPotionEffect implements Serializable {

    private String effect;
    private int duration;
    private int level;

    public ShopItemPotionEffect(String effect, int duration, int level) {
        this.effect = effect;
        this.duration = duration;
        this.level = level;
    }

    public String getEffectName() {
        return this.effect;
    }

    public PotionEffect getEffect() {
        return new PotionEffect(PotionEffectType.getByName(effect), duration, level);
    }

    public int getLevel() {
        return this.level;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
