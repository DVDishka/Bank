package ru.dvdishka.bank;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import ru.dvdishka.bank.shop.Classes.shop.Shop;
import ru.dvdishka.bank.shop.Classes.shop.ShopItem;
import ru.dvdishka.bank.common.CommonVariables;
import ru.dvdishka.bank.blancville.Classes.JsonPrices;
import ru.dvdishka.bank.blancville.Classes.Prices;
import ru.dvdishka.bank.blancville.blancvilleHandlers.CommandExecutor;
import ru.dvdishka.bank.shop.shopHandlers.EventHandler;
import ru.dvdishka.bank.blancville.blancvilleHandlers.TabCompleter;
import org.bukkit.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public final class Bank extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Shop.class, "Shop");
        ConfigurationSerialization.registerClass(ShopItem.class, "ShopItem");
    }

    @Override
    public void onEnable() {

        File rootDir = new File("plugins/Bank");
        File pricesFile = new File("plugins/Bank/prices.json");
        File shopsFile = new File("plugins/Bank/shops.yml");
        File playerCardsDir = new File("plugins/Bank/Cards");

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
        } else {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/Bank/shops.yml"));
                CommonVariables.shops = (HashSet<Shop>) config.get("Shops");
                if (CommonVariables.shops != null) {
                    for (Shop shop : CommonVariables.shops) {
                        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + shop.getName());
                        int i = 0;
                        for (ShopItem shopItem : shop.getItems()) {
                            inventory.setItem(i, shopItem.getItem());
                            i++;
                        }
                        CommonVariables.shopsInventories.put(shop.getName(), inventory);
                    }
                }
            } catch (Exception e) {
                CommonVariables.logger.warning(e.getMessage());
                CommonVariables.logger.warning("Something went wrong while trying to read shops.yml file!");
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
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
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
        if (!playerCardsDir.exists()) {
            try {
                if (playerCardsDir.mkdir()) {
                    CommonVariables.logger.info("cards.json file has been created!");
                } else {
                    CommonVariables.logger.warning("Something went wrong while trying to create cards.json file!");
                }
            } catch (Exception e) {
                CommonVariables.logger.warning("Something went wrong while trying to create cards.json file!");
            }
        }

        PluginCommand bankCommand = Bukkit.getPluginCommand("bank");
        PluginCommand shopCommand = Bukkit.getPluginCommand("shop");

        CommandExecutor bankCommandExecutor = new CommandExecutor();
        TabCompleter bankTabCompleter = new TabCompleter();

        ru.dvdishka.bank.shop.shopHandlers.CommandExecutor shopCommandExecutor = new ru.dvdishka.bank.shop.shopHandlers.CommandExecutor();
        ru.dvdishka.bank.shop.shopHandlers.TabCompleter shopTabCompleter = new ru.dvdishka.bank.shop.shopHandlers.TabCompleter();

        Bukkit.getPluginManager().registerEvents(new EventHandler(), this);

        assert bankCommand != null;
        bankCommand.setExecutor(bankCommandExecutor);
        bankCommand.setTabCompleter(bankTabCompleter);

        assert shopCommand != null;
        shopCommand.setExecutor(shopCommandExecutor);
        shopCommand.setTabCompleter(shopTabCompleter);

        CommonVariables.logger.info("Bank plugin has been enabled!");
    }

    @Override
    public void onDisable() {

        File file = new File("plugins/Bank/prices.json");

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
            FileWriter fileWriter = new FileWriter(file);
            JsonPrices price = new JsonPrices(Prices.getDiamondPrice(), Prices.getNetheritePrice());
            fileWriter.write(gson.toJson(price));
            fileWriter.close();
        } catch (Exception e) {
            CommonVariables.logger.warning("Can not write prices.json file");
        }

        FileConfiguration config = new YamlConfiguration();
        config.set("Shops", CommonVariables.shops);
        try {
            config.save(new File("plugins/Bank/shops.yml"));
        } catch (Exception e) {
            CommonVariables.logger.warning("Something went wrong while trying to write shops.yml file");
        }

        try {
            File shopsFile = new File("plugins/Bank/shops.json");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
            FileWriter fileWriter = new FileWriter(shopsFile);
            fileWriter.write(gson.toJson(CommonVariables.shops));
            fileWriter.close();
        } catch (Exception e) {
            CommonVariables.logger.warning("Can not write shops.json file");
        }
        CommonVariables.logger.info("Bank plugin has been disabled!");
    }
}
