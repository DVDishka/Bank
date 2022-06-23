package dvdishka.bank.Blancville.Classes;

public class JsonPrices {

    private double diamondPrice = 0.25;

    private double netheritePrice = 4;

    public JsonPrices(double diamondPrice, double netheritePrice) {
        this.diamondPrice = diamondPrice;
        this.netheritePrice = netheritePrice;
    }

    public double getDiamondPrice() {
        return diamondPrice;
    }

    public double getNetheritePrice() {
        return netheritePrice;
    }

    public void setDiamondPrice(double diamondPrice) {
        this.diamondPrice = diamondPrice;
    }

    public void setNetheritePrice(double netheritePrice) {
        this.netheritePrice = netheritePrice;
    }
}
