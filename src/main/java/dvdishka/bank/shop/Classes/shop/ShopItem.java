package dvdishka.bank.shop.Classes.shop;

import dvdishka.bank.shop.Classes.meta.Meta;
import org.bukkit.Material;

import java.io.Serializable;

public class ShopItem implements Serializable {

    private String material;
    private int amount;
    private int price;
    private Meta meta;

    public ShopItem(String material, int amount, int price, Meta meta) {
        this.material = material;
        this.amount = amount;
        this.price = price;
        this.meta = meta;
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

    public Meta getMeta() {
        return this.meta;
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

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
