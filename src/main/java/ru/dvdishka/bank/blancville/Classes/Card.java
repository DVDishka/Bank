package ru.dvdishka.bank.blancville.Classes;

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
}
