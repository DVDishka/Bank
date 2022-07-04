package ru.dvdishka.bank;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.checkerframework.checker.units.qual.A;
import ru.dvdishka.bank.shop.Classes.map.ShopItemMap;
import ru.dvdishka.bank.shop.Classes.map.ShopItemMapView;
import ru.dvdishka.bank.shop.Classes.meta.Meta;
import ru.dvdishka.bank.shop.Classes.meta.banner.ShopItemPatternColor;
import ru.dvdishka.bank.shop.Classes.meta.banner.ShopItemBanner;
import ru.dvdishka.bank.shop.Classes.meta.banner.ShopItemPattern;
import ru.dvdishka.bank.shop.Classes.meta.book.ShopItemBook;
import ru.dvdishka.bank.shop.Classes.meta.firework.ShopItemFirework;
import ru.dvdishka.bank.shop.Classes.meta.firework.ShopItemFireworkEffect;
import ru.dvdishka.bank.shop.Classes.meta.potion.MainPotionEffect;
import ru.dvdishka.bank.shop.Classes.shop.Shop;
import ru.dvdishka.bank.shop.Classes.shop.ShopItem;
import ru.dvdishka.bank.shop.Classes.meta.enchantment.ShopItemEnchantment;
import ru.dvdishka.bank.shop.Classes.meta.potion.ShopItemPotionEffect;
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
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
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
                                itemMeta.setDisplayName(item.getMeta().getName());
                                itemStack.setItemMeta(itemMeta);
                                itemStack.setLore(item.getMeta().getLore());

                                try {
                                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemMeta;
                                    for (ShopItemEnchantment enchantment : item.getMeta().getStoredEnchantments()) {
                                        if (enchantment.getEnchantment() != null) {
                                            ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment.getEnchantment(),
                                                    enchantment.getLevel(), true);
                                        }
                                    }
                                    itemStack.setItemMeta(enchantmentStorageMeta);
                                } catch (Exception ignored) {}

                                try {
                                    PotionMeta potionMeta = (PotionMeta) itemMeta;
                                    potionMeta.setBasePotionData(new PotionData(
                                            PotionType.valueOf(item.getMeta().getMainPotionEffect().getName()),
                                            item.getMeta().getMainPotionEffect().isExtended(),
                                            item.getMeta().getMainPotionEffect().isUpgraded()));
                                    for (ShopItemPotionEffect effect : item.getMeta().getPotionEffects()) {
                                        if (effect.getEffect() != null) {
                                            potionMeta.addCustomEffect(effect.getEffect(), true);
                                        }
                                    }
                                    itemStack.setItemMeta(potionMeta);
                                } catch (Exception ignored) {
                                }

                                try {
                                    BookMeta bookMeta = (BookMeta) itemMeta;
                                    ShopItemBook shopItemBook = item.getMeta().getShopItemBook();
                                    bookMeta.setAuthor(shopItemBook.getAuthor());
                                    bookMeta.setGeneration(BookMeta.Generation.valueOf(shopItemBook.getGeneration()));
                                    bookMeta.setTitle(shopItemBook.getTitle());
                                    bookMeta.setPages(shopItemBook.getPages());
                                    itemStack.setItemMeta(bookMeta);
                                } catch (Exception ignored) {}

                                try {
                                    FireworkMeta fireworkMeta = (FireworkMeta) itemMeta;
                                    ShopItemFirework shopItemFirework = item.getMeta().getShopItemFirework();
                                    ArrayList<FireworkEffect> effects = new ArrayList<>();
                                    fireworkMeta.setPower(shopItemFirework.getPower());
                                    for (ShopItemFireworkEffect effect : shopItemFirework.getEffects()) {
                                        ArrayList<Color> colors = new ArrayList<>();
                                        ArrayList<Color> fadeColors = new ArrayList<>();
                                        for (Integer color : effect.getColors()) {
                                            colors.add(Color.fromRGB(color));
                                        }
                                        for (Integer fadeColor : effect.getFadeColors()) {
                                            fadeColors.add(Color.fromRGB(fadeColor));
                                        }
                                        effects.add(FireworkEffect.builder().withColor(colors).withFade(fadeColors).
                                                flicker(effect.hasFlicker()).trail(effect.hasTrail()).with
                                                        (FireworkEffect.Type.valueOf(effect.getType())).build());
                                    }
                                    fireworkMeta.addEffects(effects);
                                    itemStack.setItemMeta(fireworkMeta);
                                } catch (Exception ignored) {}

                                try {
                                    BannerMeta bannerMeta = (BannerMeta) itemMeta;
                                    ShopItemBanner shopItemBanner = item.getMeta().getShopItemBanner();
                                    ArrayList<Pattern> patterns = new ArrayList<>();
                                    for (ShopItemPattern pattern : shopItemBanner.getPatterns()) {
                                        patterns.add(new Pattern(DyeColor.getByColor(Color.fromRGB(
                                                pattern.getPatternColor().getColor())),
                                                PatternType.getByIdentifier(pattern.getType())));
                                    }
                                    bannerMeta.setPatterns(patterns);
                                    itemStack.setItemMeta(bannerMeta);
                                } catch (Exception ignored) {}

                                try {
                                    MapMeta mapMeta = (MapMeta) itemMeta;
                                    ShopItemMap shopItemMap = item.getMeta().getShopItemMap();
                                    ShopItemMapView shopItemMapView = item.getMeta().getShopItemMap().getMapView();
                                    mapMeta.setColor(Color.fromRGB(shopItemMap.getColor()));
                                    mapMeta.setLocationName(shopItemMap.getLocationName());
                                    mapMeta.setScaling(shopItemMap.isScaling());
                                    MapView mapView = mapMeta.getMapView();
                                    mapView.setCenterX(shopItemMapView.getCenterX());
                                    mapView.setCenterZ(shopItemMapView.getCenterZ());
                                    mapView.setLocked(shopItemMapView.isLocked());
                                    mapView.setWorld(Bukkit.getWorld(shopItemMapView.getWorldName()));
                                    mapView.setTrackingPosition(shopItemMapView.isTrackingPosition());
                                    mapView.setUnlimitedTracking(shopItemMapView.isUnlimitedTracking());
                                    mapView.setScale(MapView.Scale.valueOf(shopItemMapView.getScale()));
                                    itemStack.setItemMeta(mapMeta);
                                } catch (Exception ignored) {}

                                try {
                                    for (ShopItemEnchantment shopItemEnchantment : item.getMeta().getEnchantments()) {
                                        if (shopItemEnchantment.getEnchantment() != null) {
                                            itemStack.addEnchantment(shopItemEnchantment.getEnchantment(), shopItemEnchantment.getLevel());
                                        }
                                    }
                                } catch (Exception ignored) {}

                                inventory.setItem(i, itemStack);

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


        for (Map.Entry<String, Inventory> entry : CommonVariables.shopsInventories.entrySet()) {
            int i = 0;
            ArrayList<ShopItem> items = new ArrayList<>();
            for (ItemStack itemStack : entry.getValue().getStorageContents()) {


                if (itemStack != null) {


                    ArrayList<ShopItemEnchantment> enchantments = new ArrayList<>();
                    ArrayList<ShopItemPotionEffect> potionEffects = new ArrayList<>();
                    ArrayList<ShopItemEnchantment> storedEnchantments = new ArrayList<>();
                    MainPotionEffect mainPotion = null;

                    try {
                        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                        for (Map.Entry<Enchantment, Integer> enchantmentEntry :
                                enchantmentStorageMeta.getStoredEnchants().entrySet()) {

                            storedEnchantments.add(new ShopItemEnchantment(enchantmentEntry.getKey().getName(),
                                    enchantmentEntry.getValue()));
                        }
                    } catch (Exception ignored) {}

                    try {
                        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                        mainPotion = new MainPotionEffect(potionMeta.getBasePotionData().getType().name(),
                                potionMeta.getBasePotionData().isExtended(), potionMeta.getBasePotionData().isUpgraded());
                        for (PotionEffect effect : potionMeta.getCustomEffects()) {
                            potionEffects.add(new ShopItemPotionEffect(effect.getType().getName(), effect.getDuration(),
                                    effect.getAmplifier()));
                        }
                    } catch (Exception ignored) {}

                    ShopItemBook shopItemBook = null;
                    try {
                        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
                        ArrayList<String> pages = new ArrayList<>(bookMeta.getPages());
                        shopItemBook = new ShopItemBook(bookMeta.getAuthor(), bookMeta.getPageCount(), bookMeta.getTitle(),
                                bookMeta.getGeneration().toString(), pages);
                    } catch (Exception ignored) {}

                    for (Map.Entry<Enchantment, Integer> enchantmentEntry : itemStack.getEnchantments().entrySet()) {
                        enchantments.add(new ShopItemEnchantment(enchantmentEntry.getKey().getName(), enchantmentEntry.getValue()));
                    }

                    ShopItemFirework shopItemFirework = null;
                    try {
                        FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
                        ArrayList<ShopItemFireworkEffect> effects = new ArrayList<>();
                        for (FireworkEffect effect : fireworkMeta.getEffects()) {
                            ArrayList<Integer> colors = new ArrayList<>();
                            ArrayList<Integer> fadeColors = new ArrayList<>();
                            for (Color color : effect.getColors()) {
                                colors.add(color.asRGB());
                            }
                            for (Color fadeColor : effect.getFadeColors()) {
                                fadeColors.add(fadeColor.asRGB());
                            }
                            effects.add(new ShopItemFireworkEffect(effect.getType().toString(), colors, fadeColors,
                                    effect.hasTrail(), effect.hasFlicker()));
                        }
                        shopItemFirework = new ShopItemFirework(fireworkMeta.getPower(), effects);
                    } catch (Exception ignored) {}

                    ShopItemBanner shopItemBanner = null;
                    try {
                        BannerMeta bannerMeta = (BannerMeta) itemStack.getItemMeta();
                        ArrayList<ShopItemPattern> shopItemPatterns = new ArrayList<>();
                        for (Pattern pattern : bannerMeta.getPatterns()) {
                            ShopItemPattern shopItemPattern = new ShopItemPattern(pattern.getPattern().getIdentifier(),
                                    new ShopItemPatternColor(pattern.getColor().getDyeData(), pattern.getColor().getWoolData(),
                                            pattern.getColor().getColor().asRGB(), pattern.getColor().getFireworkColor().
                                            asRGB()));
                            shopItemPatterns.add(shopItemPattern);
                        }
                        shopItemBanner = new ShopItemBanner(shopItemPatterns);
                    } catch (Exception ignored) {}

                    ShopItemMap shopItemMap = null;
                    try {
                        MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
                        MapView mapView = mapMeta.getMapView();
                        ShopItemMapView shopItemMapView = new ShopItemMapView(mapView.getCenterX(), mapView.getCenterZ(),
                                mapView.getId(), mapView.getWorld().getName(), mapView.getScale().toString(), mapView.isLocked(),
                                mapView.isTrackingPosition(), mapView.isUnlimitedTracking());
                        try {
                            shopItemMap = new ShopItemMap(shopItemMapView, mapMeta.getColor().asRGB(), mapMeta.getLocationName(),
                                    mapMeta.isScaling());
                        } catch (Exception e) {
                            shopItemMap = new ShopItemMap(shopItemMapView, 0, mapMeta.getLocationName(),
                                    mapMeta.isScaling());
                        }
                    } catch (Exception ignored) {}

                    try {
                        items.add(i, new ShopItem(itemStack.getType().name(), itemStack.getAmount(),
                                Integer.parseInt(itemStack.getLore().get(itemStack.getLore().size() - 1)),
                                new Meta(itemStack.getItemMeta().getDisplayName(), itemStack.getItemMeta().getLore(),
                                        enchantments, storedEnchantments, mainPotion, potionEffects, shopItemBook,
                                        shopItemFirework, shopItemBanner, shopItemMap)));
                    } catch (Exception e) {
                        items.add(i, new ShopItem(itemStack.getType().name(), itemStack.getAmount(), -1,
                                new Meta(itemStack.getItemMeta().getDisplayName(), itemStack.getItemMeta().getLore(),
                                        enchantments, storedEnchantments, mainPotion, potionEffects, shopItemBook,
                                        shopItemFirework, shopItemBanner, shopItemMap)));
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
