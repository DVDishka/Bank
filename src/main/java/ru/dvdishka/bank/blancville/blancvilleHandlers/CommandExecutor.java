package ru.dvdishka.bank.blancville.blancvilleHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.dvdishka.bank.blancville.Classes.Card;
import ru.dvdishka.bank.common.CommonVariables;
import ru.dvdishka.bank.blancville.Classes.Prices;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Unknown command or wrong answers!");
            return false;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        String commandName = args[0];

        if (commandName.equals("sell") && args.length == 4 && args[1].equals("diamond")) {
            int amountSell = 0;
            int cardNumber = 0;
            try {
                amountSell = Integer.parseInt(args[2]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Amount must be a number!");
                return false;
            }
            try {
                cardNumber = Integer.parseInt(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Card number must be a number!");
                return false;
            }
            File file = new File("/home/server/minecraft/Blancville_bank/users/" + cardNumber + ".json");
            if (!file.exists()) {
                sender.sendMessage(ChatColor.RED + "There is no card with this number!");
                return false;
            }
            Card bankCard;
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json = json.concat(line);
                }
                bufferedReader.close();
                fileReader.close();
                bankCard = gson.fromJson(json, Card.class);
            } catch (Exception e) {
                CommonVariables.logger.warning("Permission Denided!");
                return false;
            }
            if (amountSell <= 0) {
                sender.sendMessage(ChatColor.RED + "You can not sell " + amountSell + " diamonds!");
                return false;
            }
            int amountInventory = 0;
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack != null && itemStack.getType() == Material.DIAMOND) {
                    amountInventory += itemStack.getAmount();
                }
            }
            if (amountInventory < amountSell) {
                sender.sendMessage(ChatColor.RED + "You have not " + amountSell + " diamonds!");
                return false;
            }
            int amountDelete = amountSell;
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack == null) {
                    continue;
                }
                if (itemStack.getType() == Material.DIAMOND) {
                    if (amountDelete >= itemStack.getAmount()) {
                        amountDelete -= itemStack.getAmount();
                        itemStack.setAmount(0);
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - amountDelete);
                        amountDelete = 0;
                    }
                }
                if (amountDelete == 0) {
                    break;
                }
            }
            try {
                bankCard.setMoney(bankCard.getMoney() + Prices.getDiamondPrice() * amountSell);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(gson.toJson(bankCard));
                fileWriter.close();
            } catch (Exception e) {
                CommonVariables.logger.warning("Permission Denided!");
                return false;
            }
            sender.sendMessage("You sold " + amountSell + " diamonds!");
            return true;
        }

        if (commandName.equals("sell") && args.length == 4 && args[1].equals("netherite")) {
            int amountSell = 0;
            int cardNumber = 0;
            try {
                amountSell = Integer.parseInt(args[2]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Amount must be a number!");
                return false;
            }
            try {
                cardNumber = Integer.parseInt(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Card number must be a number!");
                return false;
            }
            File file = new File("/home/server/minecraft/Blancville_bank/users/" + cardNumber + ".json");
            if (!file.exists()) {
                sender.sendMessage(ChatColor.RED + "There is no card with this number!");
                return false;
            }
            Card bankCard;
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json = json.concat(line);
                }
                bufferedReader.close();
                fileReader.close();
                bankCard = gson.fromJson(json, Card.class);
            } catch (Exception e) {
                CommonVariables.logger.warning("Permission Denided!");
                return false;
            }
            if (amountSell <= 0) {
                sender.sendMessage(ChatColor.RED + "You can not sell " + amountSell + " netherite ingots!");
                return false;
            }
            int amountInventory = 0;
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack != null && itemStack.getType() == Material.NETHERITE_INGOT) {
                    amountInventory += itemStack.getAmount();
                }
            }
            if (amountInventory < amountSell) {
                sender.sendMessage(ChatColor.RED + "You have not " + amountSell + " netherite ingots!");
                return false;
            }
            int amountDelete = amountSell;
            for (ItemStack itemStack : player.getInventory()) {
                if (itemStack == null) {
                    continue;
                }
                if (itemStack.getType() == Material.NETHERITE_INGOT) {
                    if (amountDelete >= itemStack.getAmount()) {
                        amountDelete -= itemStack.getAmount();
                        itemStack.setAmount(0);
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - amountDelete);
                        amountDelete = 0;
                    }
                }
                if (amountDelete == 0) {
                    break;
                }
            }
            try {
                bankCard.setMoney(bankCard.getMoney() + Prices.getNetheritePrice() * amountSell);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(gson.toJson(bankCard));
                fileWriter.close();
            } catch (Exception e) {
                CommonVariables.logger.warning("Permission Denided!");
                return false;
            }
            sender.sendMessage("You sold " + amountSell + " netherite ingots!");
            return true;
        }

        if (commandName.equals("get") && args.length == 3 && args[1].equals("price") && args[2].equals("diamond")) {
            sender.sendMessage("Diamond price: " + Prices.getDiamondPrice());
            return true;
        }

        if (commandName.equals("get") && args.length == 3 && args[1].equals("price") && args[2].equals("netherite")) {
            sender.sendMessage("Netherite ingot price: " + Prices.getNetheritePrice());
            return true;
        }

        if (commandName.equals("set") && args.length == 4 && args[1].equals("price") && args[2].equals("diamond")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You are not an operator!");
                return false;
            }
            double newPrice;
            try {
                newPrice = Double.parseDouble(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "New price must be a number!");
                return false;
            }
            Prices.setDiamondPrice(newPrice);
            sender.sendMessage("Diamond price has been set!");
            return true;
        }

        if (commandName.equals("set") && args.length == 4 && args[1].equals("price") && args[2].equals("netherite")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You are not an operator!");
                return false;
            }
            double newPrice;
            try {
                newPrice = Double.parseDouble(args[3]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "New price must be a number!");
                return false;
            }
            Prices.setNetheritePrice(newPrice);
            sender.sendMessage("Netherite ingot price has been set!");
            return true;
        }

    sender.sendMessage(ChatColor.RED + "Unknown command or wrong answers!");
    return false;
    }
}
