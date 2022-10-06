package ru.dvdishka.bank.blancville;

import java.io.Serializable;

public class ServerCardJson implements Serializable {

    private String name;
    private int number;

    public ServerCardJson(String name, int number) {

        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }
}
