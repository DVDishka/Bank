package dvdishka.bank.Blancville.Classes;

public class Prices {

    private static double diamondPrice = 0.25;

    private static double netheritePrice = 4;

    public static double getDiamondPrice() {
        return diamondPrice;
    }

    public static double getNetheritePrice() {
        return netheritePrice;
    }

    public static void setDiamondPrice(double diamondPrice) {
        Prices.diamondPrice = diamondPrice;
    }

    public static void setNetheritePrice(double netheritePrice) {
        Prices.netheritePrice = netheritePrice;
    }
}
