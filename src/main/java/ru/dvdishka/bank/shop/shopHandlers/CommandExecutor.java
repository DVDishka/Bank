package ru.dvdishka.bank.shop.shopHandlers;

import com.destroystokyo.paper.Title;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import ru.dvdishka.bank.blancville.Classes.Card;
import ru.dvdishka.bank.shop.Classes.PlayerCard;
import ru.dvdishka.bank.shop.Classes.Shop;
import ru.dvdishka.bank.common.CommonVariables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Unknown command or wrong answers!");
            return false;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        String commandName = args[0];
        assert player != null;

        if (commandName.equals("create") && args.length == 2) {

            String shopName = args[1];

            for (Shop shop : CommonVariables.shops) {
                if (shop.getName().equals(shopName)) {
                    sender.sendMessage(ChatColor.RED + "Shop with that name already exists!");
                    return false;
                }
            }

            CommonVariables.playerShopCreating.put(player.getName(), shopName);

            TextComponent text = Component
                    .text("Creating a shop costs 10 l`argent\n")
                    .append(Component.text(ChatColor.GREEN + "[CREATE]")
                    .clickEvent(ClickEvent.runCommand("/shop creating " + shopName + " " + player.getName())))
                    .append(Component.text("   "))
                    .append(Component.text(ChatColor.RED + "[CANCEL]")
                    .clickEvent(ClickEvent.runCommand("/shop creating cancel " + player.getName())));
            sender.sendMessage(text);
            return true;
        }

        if (commandName.equals("creating") && args.length == 3 && !args[1].equals("cancel")) {

            if (CommonVariables.playerShopCreating.get(args[2]) == null ||
                    !CommonVariables.playerShopCreating.get(args[2]).equals(args[1])) {
                return false;
            }

            player = Bukkit.getPlayer(args[2]);
            File cardFile = new File("plugins/Bank/Cards/" + player.getName() + ".json");
            if (!cardFile.exists()) {
                player.sendMessage(ChatColor.RED + "You have no card!");
                return false;
            }
            int number = 0;
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                FileReader fileReader = new FileReader(cardFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json = json.concat(line);
                }
                bufferedReader.close();
                fileReader.close();
                PlayerCard playerCard = gson.fromJson(json, PlayerCard.class);
                number = playerCard.getNumber();

                FileReader playerFileReader = new FileReader("/home/server/minecraft/Blancville_bank/users/" +
                        number + ".json");
                BufferedReader playerBufferedReader = new BufferedReader(playerFileReader);
                String playerJson = "";
                String playerLine;
                while ((playerLine = playerBufferedReader.readLine()) != null) {
                    playerJson = playerJson.concat(playerLine);
                }
                playerBufferedReader.close();
                playerFileReader.close();
                Card card = gson.fromJson(playerJson, Card.class);
                if (card.getMoney() < 10) {
                    player.sendMessage(ChatColor.RED + "You does not have 10 l`argent!");
                    return false;
                }
                card.setMoney(card.getMoney() - 10);
                FileWriter playerFileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/" +
                        number + ".json");
                playerFileWriter.write(gson.toJson(card));
                playerFileWriter.close();

                FileReader adminFileReader = new FileReader("/home/server/minecraft/Blancville_bank/users/111111.json");
                BufferedReader adminBufferedReader = new BufferedReader(adminFileReader);
                String adminJson = "";
                String adminLine;
                while ((adminLine = adminBufferedReader.readLine()) != null) {
                    adminJson = adminJson.concat(adminLine);
                }
                adminBufferedReader.close();
                adminFileReader.close();
                Card adminCard = gson.fromJson(adminJson, Card.class);
                adminCard.setMoney(adminCard.getMoney() + 10);
                FileWriter adminFileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/111111.json");
                adminFileWriter.write(gson.toJson(adminCard));
                adminFileWriter.close();
            } catch (Exception e) {
                CommonVariables.logger.warning(e.getMessage());
                player.sendMessage(ChatColor.RED + "Something went wrong!");
                return false;
            }

            for (Shop checkShop : CommonVariables.shops) {
                if (checkShop.getName().equals(args[1])) {
                    player.sendMessage(ChatColor.RED + "There is already a shop with the same name!");
                    return false;
                }
            }
            ItemStack icon = new ItemStack(Material.BARRIER);
            ItemMeta iconMeta = icon.getItemMeta();
            iconMeta.setDisplayName(args[1]);
            icon.setItemMeta(iconMeta);
            Shop shop = new Shop(args[1], player.getName(), number, icon);
            CommonVariables.shops.add(shop);
            CommonVariables.shopsInventories.put(shop.getName(),
                    Bukkit.createInventory(null, 27, ChatColor.GOLD + shop.getName()));
            CommonVariables.playerShopCreating.remove(player.getName());

            player.sendTitle(Title
                    .builder()
                    .title(ChatColor.DARK_GREEN + shop.getName())
                    .subtitle(ChatColor.GOLD + "has been created")
                    .build());
            return true;
        }

        if (commandName.equals("creating") && args.length == 3 && args[1].equals("cancel")) {

            player = Bukkit.getPlayer(args[2]);
            if (!CommonVariables.playerShopCreating.containsKey(player.getName())) {
                return false;
            }
            CommonVariables.playerShopCreating.remove(player.getName());
            player.sendMessage(ChatColor.RED + "You cancelled the creation of the shop!");
            return true;
        }

        if (commandName.equals("open") && args.length == 1) {

            ArrayList<Inventory> shopsIcons = new ArrayList<>();
            int shopsAmount = CommonVariables.shops.size();
            if (shopsAmount == 0) {
                sender.sendMessage(ChatColor.RED + "There are no shops yet");
                return false;
            }
            int i = 1;
            while (shopsAmount > 0) {
                Inventory page = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Shops " + i);
                ItemStack prevPage = new ItemStack(Material.ARROW);
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta prevPageMeta = prevPage.getItemMeta();
                prevPageMeta.setDisplayName("<--");
                prevPage.setItemMeta(prevPageMeta);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                nextPageMeta.setDisplayName("-->");
                nextPage.setItemMeta(nextPageMeta);
                page.setItem(18, prevPage);
                page.setItem(26, nextPage);
                shopsIcons.add(page);
                shopsAmount -= 25;
                i++;
            }
            int stackIndex = 0;
            int inventoryIndex = 0;
            for (Shop shop : CommonVariables.shops) {
                if (stackIndex == 18) {
                    stackIndex++;
                }
                if (stackIndex == 26) {
                    stackIndex = 0;
                    inventoryIndex++;
                }
                shopsIcons.get(inventoryIndex).setItem(stackIndex, shop.getIcon());
                stackIndex++;
            }
            stackIndex = 0;
            Inventory lastPage = shopsIcons.get(inventoryIndex);
            for (ItemStack itemStack : lastPage) {
                if (itemStack == null) {
                    lastPage.setItem(stackIndex, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                }
                stackIndex++;
            }
            shopsIcons.set(inventoryIndex, lastPage);
            CommonVariables.shopMenu = shopsIcons;
            player.openInventory(shopsIcons.get(0));
            return true;
        }

        if (commandName.equals("edit") && args.length == 5 && args[2].equals("price")) {

            int index = 0;
            int price = 0;
            Shop shop = Shop.getShop(args[1]);
            if (shop == null) {
                sender.sendMessage(ChatColor.RED + "There is no shop with that name!");
                return false;
            }
            if (!shop.getOwner().equals(player.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not the owner of this shop!");
                return false;
            }
            try {
                index = Integer.parseInt(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Index must be a number!");
                return false;
            }
            if (index < 1 || index > 27) {
                sender.sendMessage(ChatColor.RED + "Index must be >= 1 and <= 27");
                return false;
            }
            try {
                price = Integer.parseInt(args[4]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Price must be a number!");
                return false;
            }
            if (price <= 0) {
                sender.sendMessage(ChatColor.RED + "Price must be > 0!");
                return false;
            }
            if (CommonVariables.shopsInventories.get(shop.getName()).getItem(index - 1) == null ||
                    CommonVariables.shopsInventories.get(shop.getName()).getItem(index - 1).getType() ==
                            Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                sender.sendMessage(ChatColor.RED + "There is no item in this slot!");
                return false;
            }
            ItemMeta meta = CommonVariables.shopsInventories.get(shop.getName()).getItem(index - 1).getItemMeta();
            try {
                Integer.parseInt(meta.getLore().get(meta.getLore().size() - 1));
                List<String> list = meta.getLore();
                list.set(meta.getLore().size() - 1, String.valueOf(price));
                meta.setLore(list);
            } catch (Exception e) {
                if (meta.getLore() != null) {
                    List<String> list = meta.getLore();
                    list.add(String.valueOf(price));
                    meta.setLore(list);
                } else {
                    meta.setLore(List.of(String.valueOf(price)));
                }
            }
            CommonVariables.shopsInventories.get(shop.getName()).getItem(index - 1).setItemMeta(meta);
            player.sendTitle(Title.builder().title(ChatColor.DARK_GREEN + shop.getName()).subtitle(ChatColor.GOLD +
                    "price has been set").build());
            return true;
        }

        if (commandName.equals("edit") && args.length == 4 && args[2].equals("card")) {

            int cardNumber = 0;
            Shop shop = Shop.getShop(args[1]);
            if (shop == null) {
                sender.sendMessage(ChatColor.RED + "There is no shop with that name!");
                return false;
            }
            if (!shop.getOwner().equals(player.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not the owner of this shop!");
                return false;
            }
            try {
                cardNumber = Integer.parseInt(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Card must be a number!");
                return false;
            }
            File file = new File("/home/server/minecraft/Blancville_bank/users/" + cardNumber + ".json");
            if (!file.exists()) {
                sender.sendMessage(ChatColor.RED + "Card with that number does not exist!");
                return false;
            }
            shop.setCardNumber(cardNumber);
            player.sendTitle(Title.builder()
                    .title(ChatColor.DARK_GREEN + shop.getName())
                            .subtitle(ChatColor.GOLD + "card number has been set")
                    .build());
            return true;
        }

        if (commandName.equals("edit") && args.length == 4 && args[2].equals("name")) {

            String name = args[3];
            Shop shop = Shop.getShop(args[1]);
            if (shop == null) {
                sender.sendMessage(ChatColor.RED + "There is no shop with that name!");
                return false;
            }
            if (!shop.getOwner().equals(player.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not the owner of this shop!");
                return false;
            }
            for (Shop checkShop : CommonVariables.shops) {
                if (checkShop.getName().equals(name)) {
                    sender.sendMessage(ChatColor.RED + "A shop with the same name already exists!");
                    return false;
                }
            }
            Inventory inventory = CommonVariables.shopsInventories.get(shop.getName());
            Inventory newInventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + name);
            newInventory.setContents(inventory.getContents());
            CommonVariables.shopsInventories.remove(shop.getName());
            CommonVariables.shopsInventories.put(name, newInventory);
            for (Shop checkShop : CommonVariables.shops) {
                if (checkShop.getName().equals(shop.getName())) {
                    checkShop.setName(name);
                    ItemStack icon = checkShop.getIcon();
                    ItemMeta iconMeta = icon.getItemMeta();
                    iconMeta.setDisplayName(checkShop.getName());
                    icon.setItemMeta(iconMeta);
                    checkShop.setIcon(icon);
                }
            }
            player.sendTitle(Title.builder()
                    .title(ChatColor.DARK_GREEN + name)
                    .subtitle(ChatColor.GOLD + "Shop name has been set")
                    .build());
            return true;
        }

        if (commandName.equals("edit") && args.length == 3 && args[2].equals("icon")) {

            Shop shop = Shop.getShop(args[1]);
            if (shop == null) {
                sender.sendMessage(ChatColor.RED + "There is no shop with that name!");
                return false;
            }
            if (!shop.getOwner().equals(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not owner of this shop!");
                return false;
            }

            int iconsAmount = Material.values().length - 2;
            if (iconsAmount == 0) {
                sender.sendMessage(ChatColor.RED + "There are no icons yet");
                return false;
            }

            ArrayList<Inventory> icons = new ArrayList<>();

            int i = 1;
            while (iconsAmount > 0) {
                Inventory page = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Icons " + i);
                ItemStack prevPage = new ItemStack(Material.ARROW);
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta prevPageMeta = prevPage.getItemMeta();
                prevPageMeta.setDisplayName("<--");
                prevPage.setItemMeta(prevPageMeta);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                nextPageMeta.setDisplayName("-->");
                nextPage.setItemMeta(nextPageMeta);
                page.setItem(45, prevPage);
                page.setItem(53, nextPage);
                icons.add(page);
                iconsAmount -= 52;
                i++;
            }

            int stackIndex = 0;
            int inventoryIndex = 0;
            for (Material icon : Material.values()) {
                if (!icon.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE) && !icon.equals(Material.AIR)) {
                    if (stackIndex == 45) {
                        stackIndex++;
                    }
                    if (stackIndex == 53) {
                        stackIndex = 0;
                        inventoryIndex++;
                    }
                    icons.get(inventoryIndex).setItem(stackIndex, new ItemStack(icon));
                    stackIndex++;
                }
            }

            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta prevPageMeta = prevPage.getItemMeta();
            prevPageMeta.setDisplayName("<--");
            prevPage.setItemMeta(prevPageMeta);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName("-->");
            nextPage.setItemMeta(nextPageMeta);

            int pagesAmount = icons.size();
            for (int page = 0; page < pagesAmount; page++) {
                boolean isClear = true;
                for (ItemStack item : icons.get(page)) {
                    if (item != null && !item.equals(nextPage) && !item.equals(prevPage)) {
                        isClear = false;
                        break;
                    }
                }
                if (isClear) {
                    icons.remove(page);
                    pagesAmount--;
                    page--;
                }
            }
            stackIndex = 0;
            Inventory lastPage = icons.get(icons.size() - 1);
            for (ItemStack itemStack : lastPage) {
                if (itemStack == null) {
                    lastPage.setItem(stackIndex, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                }
                stackIndex++;
            }
            icons.set(icons.size() - 1, lastPage);
            player.openInventory(icons.get(0));
            CommonVariables.playerShopIconChoose.put(player.getName(), shop.getName());
            CommonVariables.iconMenu = icons;
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown command or wrong arguments!");
        return false;
    }
}
