package ru.dvdishka.bank.blancville;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.dvdishka.bank.shops.common.ConfigVariables;
import ru.dvdishka.bank.shops.common.CommonVariables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Card {

    private double money;
    private int number;
    private String code;
    private double MNC;
    private double CLK;
    private double RTS;
    private int security;
    private double deposit;
    private String email;

    public Card(double money, int number, String code, double MNC, double CLK, double RTS, int security, double deposit, String email) {
        this.money = money;
        this.number = number;
        this.code = code;
        this.MNC = MNC;
        this.CLK = CLK;
        this.RTS = RTS;
        this.security = security;
        this.deposit = deposit;
        this.email = email;
    }

    public static Card getCard(String playerName) {

        File file = new File("plugins/Bank/Cards/" + playerName + ".json");

        if (!file.exists()) {

            return null;
        }

        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

            String cardNumber = "";
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                cardNumber = cardNumber.concat(line);
            }

            bufferedReader.close();
            fileReader.close();

            ServerCardJson cardJson = gson.fromJson(cardNumber, ServerCardJson.class);

            file = new File(ConfigVariables.blancvillePath + "users/" + cardJson.getNumber() + ".json");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String json = "";
            line = null;

            while ((line = bufferedReader.readLine()) != null) {

                json = json.concat(line);
            }

            return gson.fromJson(json, Card.class);

        } catch (Exception e) {

            return null;
        }
    }

    public double getMoney() {
        return money;
    }

    public int getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public double getMNC() {
        return MNC;
    }

    public double getCLK() {
        return CLK;
    }

    public double getRTS() {
        return RTS;
    }

    public int getSecurity() {
        return security;
    }

    public double getDeposit() {
        return deposit;
    }

    public String getEmail() {
        return email;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMNC(double MNC) {
        this.MNC = MNC;
    }

    public void setCLK(double CLK) {
        this.CLK = CLK;
    }

    public void setRTS(double RTS) {
        this.RTS = RTS;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasMoney(int amount) {
        return this.money >= amount;
    }

    public boolean addMoney(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.money += amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeMoney(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.money -= amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean addMNC(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.MNC += amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeMNC(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.MNC -= amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean addCLK(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.CLK += amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeCLK(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.CLK -= amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean addRTS(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.RTS += amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeRTS(int amount) {

        try {

            File cardFile = new File(ConfigVariables.blancvillePath + "/users/" + number + ".json");
            FileWriter fileWriter = new FileWriter(cardFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            this.RTS -= amount;

            fileWriter.write(gson.toJson(this, this.getClass()));

            fileWriter.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}