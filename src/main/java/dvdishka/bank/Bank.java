package dvdishka.bank;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dvdishka.bank.shop.Classes.potion.MainPotionEffect;
import dvdishka.bank.shop.Classes.shop.Shop;
import dvdishka.bank.shop.Classes.shop.ShopItem;
import dvdishka.bank.shop.Classes.enchantment.ShopItemEnchantment;
import dvdishka.bank.shop.Classes.potion.ShopItemPotionEffect;
import dvdishka.bank.common.CommonVariables;
import dvdishka.bank.blancville.Classes.JsonPrices;
import dvdishka.bank.blancville.Classes.Prices;
import dvdishka.bank.blancville.blancvilleHandlers.CommandExecutor;
import dvdishka.bank.shop.shopHandlers.EventHandler;
import dvdishka.bank.blancville.blancvilleHandlers.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public final class Bank extends JavaPlugin {

    @Override
    public void onEnable() {

        File rootDir = new File("plugins/Bank");
        File pricesFile = new File("plugins/Bank/prices.json");
        File shopsFile = new File("plugins/Bank/shops.json");
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
                    CommonVariables.logger.info("shops.json file has been created!");
                } else {
                    CommonVariables.logger.warning("shops.json file can not be created!");
                }
            } catch (Exception e) {
                CommonVariables.logger.warning("shops.json file can not be created!");
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
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileReader fileReader = new FileReader(shopsFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json = json.concat(line);
                }
                bufferedReader.close();
                fileReader.close();
                Type arrayListShop = new TypeToken<ArrayList<Shop>>(){}.getType();
                ArrayList<Shop> shops = gson.fromJson(json, arrayListShop);

                if (shops != null) {

                    for (Shop shop : shops) {

                        CommonVariables.shops.add(shop);
                        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + shop.getName());

                        int i = 0;
                        for (ShopItem item : shop.getItems()) {
                            if (item != null) {

                                ItemStack itemStack = new ItemStack(item.getMaterial(), item.getAmount());
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.setDisplayName(item.getName());
                                itemStack.setItemMeta(itemMeta);

                                if (item.getMaterial() == Material.ENCHANTED_BOOK) {

                                    itemStack.setLore(item.getLore());
                                    EnchantmentStorageMeta enchantmentStorageMeta =
                                            (EnchantmentStorageMeta) itemStack.getItemMeta();

                                    for (ShopItemEnchantment shopItemEnchantment : item.getEnchantments()) {
                                        if (shopItemEnchantment.getEnchantment() != null) {
                                            enchantmentStorageMeta.addStoredEnchant(shopItemEnchantment.getEnchantment(),
                                                    shopItemEnchantment.getLevel(), true);
                                        }
                                    }
                                    itemStack.setItemMeta(enchantmentStorageMeta);
                                    inventory.setItem(i, itemStack);


                                } else {

                                    itemStack.setLore(item.getLore());

                                    try {
                                        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                                        potionMeta.setBasePotionData(new PotionData(
                                                PotionType.valueOf(item.getMainPotionEffect().getName()),
                                                item.getMainPotionEffect().isExtended(),
                                                item.getMainPotionEffect().isUpgraded()));
                                        for (ShopItemPotionEffect effect : item.getPotionEffects()) {
                                            if (effect.getEffect() != null) {
                                                potionMeta.addCustomEffect(effect.getEffect(), true);
                                            }
                                        }
                                        itemStack.setItemMeta(potionMeta);
                                    } catch (Exception ignored) {}

                                    for (ShopItemEnchantment shopItemEnchantment : item.getEnchantments()) {
                                        if (shopItemEnchantment.getEnchantment() != null) {
                                            itemStack.addEnchantment(shopItemEnchantment.getEnchantment(), shopItemEnchantment.getLevel());
                                        }
                                    }
                                    inventory.setItem(i, itemStack);
                                }
                            } else {
                                inventory.setItem(i, null);
                            }
                            i++;
                        }
                        CommonVariables.shopsInventories.put(shop.getName(), inventory);
                    }
                }
            } catch (Exception e) {
                CommonVariables.logger.warning(e.getMessage());
                CommonVariables.logger.warning("Something went wrong while trying to read shops.json file!");
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

        dvdishka.bank.shop.shopHandlers.CommandExecutor shopCommandExecutor = new dvdishka.bank.shop.shopHandlers.CommandExecutor();
        dvdishka.bank.shop.shopHandlers.TabCompleter shopTabCompleter = new dvdishka.bank.shop.shopHandlers.TabCompleter();

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
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(file);
            JsonPrices price = new JsonPrices(Prices.getDiamondPrice(), Prices.getNetheritePrice());
            fileWriter.write(gson.toJson(price));
            fileWriter.close();
        } catch (Exception e) {
            CommonVariables.logger.warning("Can not write prices.json file");
        }


        for (Map.Entry<String, Inventory> entry : CommonVariables.shopsInventories.entrySet()) {
            int i = 0;
            ArrayList<ShopItem> items = new ArrayList<>();
            for (ItemStack itemStack : entry.getValue().getStorageContents()) {


                if (itemStack != null) {

                    if (itemStack.getType() == Material.ENCHANTED_BOOK) {

                        ArrayList<ShopItemEnchantment> enchantments = new ArrayList<>();
                        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                        for (Map.Entry<Enchantment, Integer> enchantmentEntry :
                                enchantmentStorageMeta.getStoredEnchants().entrySet()) {

                            enchantments.add(new ShopItemEnchantment(enchantmentEntry.getKey().getName(),
                                    enchantmentEntry.getValue()));
                        }

                        try {
                            items.add(i, new ShopItem(itemStack.getItemMeta().getDisplayName(),
                                    itemStack.getType().name(), itemStack.getAmount(),
                                    Integer.parseInt(itemStack.getLore().get(itemStack.getLore().size() - 1)),
                                    itemStack.getItemMeta().getLore(), enchantments, null, null));
                        } catch (Exception e) {
                            items.add(i, new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack.getType().name(),
                                    itemStack.getAmount(), -1, itemStack.getItemMeta().getLore(),
                                    enchantments, null, null));
                        }


                    } else {

                        ArrayList<ShopItemEnchantment> enchantments = new ArrayList<>();
                        ArrayList<ShopItemPotionEffect> potionEffects = new ArrayList<>();
                        MainPotionEffect mainPotion = null;
                        try {
                            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                            mainPotion = new MainPotionEffect(potionMeta.getBasePotionData().getType().name(),
                                    potionMeta.getBasePotionData().isExtended(), potionMeta.getBasePotionData().isUpgraded());
                            for (PotionEffect effect : potionMeta.getCustomEffects()) {
                                potionEffects.add(new ShopItemPotionEffect(effect.getType().getName(), effect.getDuration(),
                                        effect.getAmplifier()));
                            }
                        } catch (Exception ignored) {}

                        for (Map.Entry<Enchantment, Integer> enchantmentEntry : itemStack.getEnchantments().entrySet()) {
                            enchantments.add(new ShopItemEnchantment(enchantmentEntry.getKey().getName(), enchantmentEntry.getValue()));
                        }

                        try {
                            items.add(i, new ShopItem(itemStack.getItemMeta().getDisplayName(),
                                    itemStack.getType().name(), itemStack.getAmount(),
                                    Integer.parseInt(itemStack.getLore().get(itemStack.getLore().size() - 1)),
                                    itemStack.getItemMeta().getLore(), enchantments, mainPotion, potionEffects));
                        } catch (Exception e) {
                            items.add(i, new ShopItem(itemStack.getItemMeta().getDisplayName(),
                                    itemStack.getType().name(), itemStack.getAmount(), -1,
                                    itemStack.getItemMeta().getLore(), enchantments, mainPotion, potionEffects));
                        }
                    }
                } else {
                    items.add(null);
                }
                i++;
            }
            Shop.getShop(entry.getKey()).setItems(items);
        }
        try {
            File shopsFile = new File("plugins/Bank/shops.json");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(shopsFile);
            fileWriter.write(gson.toJson(CommonVariables.shops));
            fileWriter.close();
        } catch (Exception e) {
            CommonVariables.logger.warning("Can not write shops.json file");
        }
        CommonVariables.logger.info("Bank plugin has been disabled!");
    }
}
