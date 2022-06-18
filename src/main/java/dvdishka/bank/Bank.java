package dvdishka.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dvdishka.bank.Shop.Items;
import dvdishka.bank.Shop.Shop;
import dvdishka.bank.Shop.Shops;
import dvdishka.bank.common.CommonVariables;
import dvdishka.bank.common.JsonPrices;
import dvdishka.bank.common.Prices;
import dvdishka.bank.handlers.CommandExecutor;
import dvdishka.bank.handlers.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.Map;

public final class Bank extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Shops.class, "Shops");
        ConfigurationSerialization.registerClass(Shop.class, "Shop");
        ConfigurationSerialization.registerClass(Items.class, "Items");
    }

    @Override
    public void onEnable() {

        File rootDir = new File("plugins/Bank");
        File pricesFile = new File("plugins/Bank/prices.json");
        File shopsFile = new File("plugins/Bank/shops.yml");
        if (!rootDir.exists()) {
            if (rootDir.mkdir()) {
                CommonVariables.logger.info("Bank directory has been created!");
            } else {
                CommonVariables.logger.warning("Bank directory can not be created!");
            }
        }
        if (!shopsFile.exists()) {
            try {
                if (shopsFile.createNewFile()) {
                    CommonVariables.logger.info("shops.yml file has been created!");
                } else {
                    CommonVariables.logger.warning("shops.yml file can not be created!");
                }
            } catch (Exception e) {
                CommonVariables.logger.warning("shops.yml file can not be created!");
            }
        }
        if (!pricesFile.exists()) {
            try {
                if (pricesFile.createNewFile()) {
                    CommonVariables.logger.info("prices.json file has been created!");
                } else {
                    CommonVariables.logger.warning("prices.json file can not be created!");
                }
            } catch (Exception e) {
                CommonVariables.logger.warning("prices.json file can not be created!");
            }
        } else {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileReader fileReader = new FileReader(pricesFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json = json.concat(line);
                }
                bufferedReader.close();
                fileReader.close();
                JsonPrices prices = gson.fromJson(json, JsonPrices.class);
                Prices.setDiamondPrice(prices.getDiamondPrice());
                Prices.setNetheritePrice(prices.getNetheritePrice());
            } catch (Exception e) {
                CommonVariables.logger.warning("prices.json file can not be read!");
            }
            File file = new File("plugins/Bank/shops.yml");
            FileConfiguration shopsConfig = YamlConfiguration.loadConfiguration(file);
            Shops shops = (Shops) shopsConfig.get("Shops");
            if (shops != null) {
                for (Shop shop : shops.getShops()) {
                    CommonVariables.shops.add(shop);
                }
            }
        }


        PluginCommand bankCommand = Bukkit.getPluginCommand("bank");
        PluginCommand shopCommand = Bukkit.getPluginCommand("shop");

        CommandExecutor commandExecutor = new CommandExecutor();
        TabCompleter tabCompleter = new TabCompleter();

        bankCommand.setExecutor(commandExecutor);
        bankCommand.setTabCompleter(tabCompleter);
        shopCommand.setExecutor(commandExecutor);

        CommonVariables.logger.info("Bank plugin has been enabled!");
    }

    @Override
    public void onDisable() {

        File file = new File("plugins/Bank/prices.json");

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(file);
            JsonPrices price = new JsonPrices(Prices.getDiamondPrice(), Prices.getNetheritePrice());
            fileWriter.write(gson.toJson(price));
            fileWriter.close();
        } catch (Exception e) {
            CommonVariables.logger.warning("Can not write prices.json file");
        }
        File shopsFile = new File("plugins/Bank/shops.yml");
        YamlConfiguration shops = new YamlConfiguration();
        shops.set("Shops", new Shops(CommonVariables.shops));
        try {
            shops.save(shopsFile);
        } catch (IOException e) {
            CommonVariables.logger.warning("Something went wrong while trying to save shops file!");
        }
        CommonVariables.logger.info("Bank plugin has been disabled!");
    }
}
