package dvdishka.bank.shop.Classes.shop;

import dvdishka.bank.common.CommonVariables;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {

    private String name;
    private String owner;
    private ArrayList<ShopItem> items = new ArrayList<>(27);
    private int cardNumber;

    public Shop(String name, String owner, ArrayList<ShopItem> items, int cardNumber) {
        this.name = name;
        this.owner = owner;
        this.items = items;
        this.cardNumber = cardNumber;
    }

    public Shop(String name, String owner, int cardNumber) {
        this.name = name;
        this.owner = owner;
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public ArrayList<ShopItem> getItems() {
        return this.items;
    }

    public int getCardNumber() {
        return this.cardNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setItems(ArrayList<ShopItem> items) {
        this.items = items;
    }

    public void setItem(int index, ShopItem item) {
        this.items.set(index, item);
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static Shop getShop(String name) {
        for (Shop shop : CommonVariables.shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        return null;
    }
}
