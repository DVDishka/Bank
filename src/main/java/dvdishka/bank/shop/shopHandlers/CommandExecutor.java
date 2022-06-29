package dvdishka.bank.shop.shopHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dvdishka.bank.blancville.Classes.Card;
import dvdishka.bank.shop.Classes.shop.PlayerCard;
import dvdishka.bank.shop.Classes.shop.Shop;
import dvdishka.bank.common.CommonVariables;
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

        if (commandName.equals("create") && args.length == 2) {
            File cardFile = new File("plugins/Bank/Cards/" + player.getName() + ".json");
            if (!cardFile.exists()) {
                sender.sendMessage(ChatColor.RED + "You have no card!");
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
                    sender.sendMessage(ChatColor.RED + "You does not have 10 l`argent!");
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
                Card adminCard = gson.fromJson(json, Card.class);
                adminCard.setMoney(adminCard.getMoney() + 10);
                FileWriter adminFileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/111111.json");
                adminFileWriter.write(gson.toJson(adminCard));
                adminFileWriter.close();
            } catch (Exception e) {
                CommonVariables.logger.warning(e.getMessage());
                sender.sendMessage(ChatColor.RED + "Something went wrong!");
                return false;
            }

            for (Shop checkShop : CommonVariables.shops) {
                if (checkShop.getName().equals(args[1])) {
                    sender.sendMessage(ChatColor.RED + "There is already a shop with the same name!");
                    return false;
                }
            }
            Shop shop = new Shop(args[1], player.getName(), number);
            CommonVariables.shops.add(shop);
            CommonVariables.shopsInventories.put(shop.getName(),
                    Bukkit.createInventory(null, 27, ChatColor.GOLD + shop.getName()));

            sender.sendMessage("Shop " + shop.getName() + " has been created!");
            return true;
        }

        if (commandName.equals("open") && args.length == 2) {

            Shop shop = Shop.getShop(args[1]);
            if (shop == null) {
                sender.sendMessage(ChatColor.RED + "There is no shop with that name!");
                return false;
            }

            Inventory shopInventory = CommonVariables.shopsInventories.get(shop.getName());
            if (!shop.getOwner().equals(player.getName())) {
                int i = 0;
                for (ItemStack itemStack : shopInventory) {
                    if (itemStack == null || itemStack.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                        ItemStack newItemStack = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1);
                        shopInventory.setItem(i, newItemStack);
                    }
                    i++;
                }
            } else {
                int i = 0;
                for (ItemStack itemStack : shopInventory) {
                    if (itemStack != null) {
                        if (itemStack.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                            shopInventory.setItem(i, null);
                        }
                    }
                    i++;
                }
            }
            player.openInventory(shopInventory);
            return true;
        }

        if (commandName.equals("edit") && args.length == 6 && args[2].equals("price") && args[3].equals("set")) {

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
                index = Integer.parseInt(args[4]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Index must be a number!");
                return false;
            }
            if (index < 1 || index > 27) {
                sender.sendMessage(ChatColor.RED + "Index must be >= 1 and <= 27");
                return false;
            }
            try {
                price = Integer.parseInt(args[5]);
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
            sender.sendMessage("Price for item has been set");
            return true;
        }

        if (commandName.equals("edit") && args.length == 5 && args[2].equals("card") && args[3].equals("set")) {

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
                cardNumber = Integer.parseInt(args[4]);
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
            sender.sendMessage("Shop card number has been set!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown command or wrong answers!");
        return false;
    }
}
