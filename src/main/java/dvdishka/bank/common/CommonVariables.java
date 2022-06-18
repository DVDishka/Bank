package dvdishka.bank.common;

import dvdishka.bank.Shop.Shop;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.logging.Logger;

public class CommonVariables {

    public static Logger logger = Bukkit.getLogger();
    public static HashSet<Shop> shops = new HashSet<>();
}
