package dvdishka.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dvdishka.bank.common.CommonVariables;
import dvdishka.bank.common.JsonPrices;
import dvdishka.bank.common.Prices;
import dvdishka.bank.handlers.CommandExecutor;
import dvdishka.bank.handlers.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class Bank extends JavaPlugin {

    @Override
    public void onEnable() {

        File rootDir = new File("plugins/Bank");
        File pricesFile = new File("plugins/Bank/prices.json");
        if (!rootDir.exists()) {
            if (rootDir.mkdir()) {
                CommonVariables.logger.info("Bank directory has been created!");
            } else {
                CommonVariables.logger.warning("Bank directory can not be created!");
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
        }


        PluginCommand bankCommand = Bukkit.getPluginCommand("bank");

        bankCommand.setExecutor(new CommandExecutor());
        bankCommand.setTabCompleter(new TabCompleter());

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

        CommonVariables.logger.info("Bank plugin has been disabled!");
    }
}
