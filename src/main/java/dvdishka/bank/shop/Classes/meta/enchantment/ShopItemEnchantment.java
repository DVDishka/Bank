package dvdishka.bank.shop.Classes.meta.enchantment;

import java.io.Serializable;

public class ShopItemEnchantment implements Serializable {

    private String enchantment;
    private int Level;

    public ShopItemEnchantment(String enchantment, int level) {
        this.enchantment = enchantment;
        this.Level = level;
    }

    public String getEnchantmentName() {
        return this.enchantment;
    }

    public int getLevel() {
        return this.Level;
    }

    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return org.bukkit.enchantments.Enchantment.getByName(enchantment);
    }

    public void setEnchantment(String enchantment) {
        this.enchantment = enchantment;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
