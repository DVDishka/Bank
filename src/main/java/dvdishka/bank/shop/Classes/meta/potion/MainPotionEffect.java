package dvdishka.bank.shop.Classes.meta.potion;

import java.io.Serializable;

public class MainPotionEffect implements Serializable {

    String name;
    boolean extended;
    boolean upgraded;

    public MainPotionEffect(String name, boolean extended, boolean upgraded) {
        this.name = name;
        this.extended = extended;
        this.upgraded = upgraded;
    }

    public String getName() {
        return this.name;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public boolean isUpgraded() {
        return this.upgraded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
