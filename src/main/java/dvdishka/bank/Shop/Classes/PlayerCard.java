package dvdishka.bank.Shop.Classes;

import java.io.Serializable;

public class PlayerCard implements Serializable {

    private String name;
    private int number;

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
