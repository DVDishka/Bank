package ru.dvdishka.bank.shop.shopHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import ru.dvdishka.bank.blancville.Classes.Card;
import ru.dvdishka.bank.blancville.blancvilleHandlers.Commands;
import ru.dvdishka.bank.shop.Classes.PlayerCard;
import ru.dvdishka.bank.shop.Classes.Shop;
import ru.dvdishka.bank.common.CommonVariables;
import net.kyori.adventure.audience.Audience;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onShopInventoryClick(InventoryClickEvent event) {

        for (Shop shop : CommonVariables.shops) {
            int page = 0;
            for (Inventory inventory : CommonVariables.shopsInventories.get(shop.getName()))
            {
                if (!shop.getOwner().equals(event.getWhoClicked().getName()) &&
                        event.getView().getTopInventory().equals(inventory)) {
                    int i = 0;
                    for (ItemStack item : inventory) {
                        if (item == null) {
                            CommonVariables.shopsInventories.get(shop.getName()).get(page)
                                    .setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                        }
                        i++;
                    }
                }
                page++;
            }
        }

        for (Shop shop : CommonVariables.shops) {
            int i = 0;
            for (Inventory inventory : CommonVariables.shopsInventories.get(shop.getName())) {
                if (inventory.equals(event.getClickedInventory())) {
                    if (event.getCurrentItem() != null) {

                        Player player = (Player) event.getWhoClicked();
                        ItemStack prevPage = new ItemStack(Material.ARROW);
                        ItemStack nextPage = new ItemStack(Material.ARROW);
                        ItemMeta prevPageMeta = prevPage.getItemMeta();
                        prevPageMeta.setDisplayName("<--");
                        prevPage.setItemMeta(prevPageMeta);
                        ItemMeta nextPageMeta = nextPage.getItemMeta();
                        nextPageMeta.setDisplayName("-->");
                        nextPage.setItemMeta(nextPageMeta);

                        if (event.getCurrentItem().equals(prevPage)) {

                            if (i > 0) {
                                player.playSound(
                                        player.getLocation(),
                                        org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                        50,
                                        1);
                                event.getWhoClicked().openInventory(CommonVariables.shopsInventories
                                        .get(shop.getName()).get(i - 1));
                                event.setCancelled(true);
                                return;
                            } else {
                                player.playSound(
                                        player.getLocation(),
                                        org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                        50,
                                        1);
                            }
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem().equals(nextPage)) {

                            if (i < CommonVariables.shopsInventories.get(shop.getName()).size() - 1) {
                                player.playSound(
                                        player.getLocation(),
                                        org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                        50, 1);
                                event.getWhoClicked().openInventory(CommonVariables.shopsInventories
                                        .get(shop.getName()).get(i + 1));
                                event.setCancelled(true);
                                return;
                            } else {
                                player.playSound(
                                        player.getLocation(),
                                        org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                        50,
                                        1);
                            }
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
                i++;
            }
        }

        boolean firstFlag = false;
        boolean secondFlag = false;

        for (Shop shop : CommonVariables.shops) {
            if (!event.getWhoClicked().getName().equals(shop.getOwner())) {
                for (Inventory inventory : CommonVariables.shopsInventories.get(shop.getName())) {
                    if (inventory == event.getClickedInventory()) {
                        firstFlag = true;
                    }
                    if (event.getView().getTopInventory().equals(inventory)) {
                        secondFlag = true;
                    }
                }
            }
        }
        if (!firstFlag && secondFlag) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                Player player = (Player) event.getWhoClicked();
                player.playSound(
                        player.getLocation(),
                        org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                        50,
                        1);
                event.setCancelled(true);
                return;
            }
        }

        for (Shop shop : CommonVariables.shops) {

            for (Inventory inventory : CommonVariables.shopsInventories.get(shop.getName())) {

                if (inventory == event.getClickedInventory()) {

                    if (event.getWhoClicked().getName().equals(shop.getOwner())) {
                        if (event.getCurrentItem() != null) {
                            List<String> list = event.getCurrentItem().getItemMeta().getLore();
                            if (list != null) {
                                try {
                                    Integer.parseInt(list.get(list.size() - 1));
                                    list.remove(list.size() - 1);
                                    ItemMeta itemMeta  = event.getCurrentItem().getItemMeta();
                                    itemMeta.setLore(list);
                                    event.getCurrentItem().setItemMeta(itemMeta);
                                    return;
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    }

                    if (!event.getWhoClicked().getName().equals(shop.getOwner())) {

                        Player player = (Player) event.getWhoClicked();

                        if (event.getCursor() != null && !event.getCursor().getType().equals(Material.AIR)) {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50,
                                    1);
                            event.setCancelled(true);
                            return;
                        }

                        if (event.getCurrentItem() != null) {
                            if (event.getCurrentItem().getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                                ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                                if (itemMeta.getLore() != null && itemMeta.getLore().size() > 0) {

                                    int price = 0;
                                    try {


                                        price = Integer.parseInt(itemMeta.getLore().get(itemMeta.getLore().size() - 1));
                                        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();


                                        File file = new File("plugins/Bank/Cards/" + event.getWhoClicked().getName() + ".json");
                                        if (!file.exists()) {
                                            event.getWhoClicked().sendMessage(ChatColor.RED + "You have no bank card!");
                                            player.playSound(
                                                    player.getLocation(),
                                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                    50, 1);
                                            event.setCancelled(true);
                                            return;
                                        }
                                        FileReader fileReader = new FileReader(file);
                                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                                        String json = "";
                                        String line;
                                        while ((line = bufferedReader.readLine()) != null) {
                                            json = json.concat(line);
                                        }
                                        bufferedReader.close();
                                        fileReader.close();
                                        PlayerCard playerCard = gson.fromJson(json, PlayerCard.class);


                                        FileReader cardFileReader = new FileReader("/home/server/minecraft/Blancville_bank/users/" + playerCard.getNumber() + ".json");
                                        BufferedReader cardBufferedReader = new BufferedReader(cardFileReader);
                                        String cardJson = "";
                                        String cardLine;
                                        while ((cardLine = cardBufferedReader.readLine()) != null) {
                                            cardJson = cardJson.concat(cardLine);
                                        }
                                        cardBufferedReader.close();
                                        cardFileReader.close();
                                        Card card = gson.fromJson(cardJson, Card.class);
                                        double money = card.getMoney();
                                        if (money >= price) {
                                            money -= price;
                                            card.setMoney(money);
                                            FileWriter fileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/" + playerCard.getNumber() + ".json");
                                            fileWriter.write(gson.toJson(card));
                                            fileWriter.close();
                                        } else {
                                            event.getWhoClicked().sendMessage(ChatColor.RED + "You dont have " + price + " l`argent");
                                            player.playSound(
                                                    player.getLocation(),
                                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                    50,
                                                    1);
                                            event.setCancelled(true);
                                            return;
                                        }


                                        FileReader ownerCardFileReader = new FileReader("/home/server/minecraft/Blancville_bank/users/" + shop.getCardNumber() + ".json");
                                        BufferedReader ownerCardBufferedReader = new BufferedReader(ownerCardFileReader);
                                        String ownerCardJson = "";
                                        String ownerCardLine;
                                        while ((ownerCardLine = ownerCardBufferedReader.readLine()) != null) {
                                            ownerCardJson = ownerCardJson.concat(ownerCardLine);
                                        }
                                        ownerCardBufferedReader.close();
                                        ownerCardFileReader.close();
                                        Card ownerCard = gson.fromJson(ownerCardJson, Card.class);
                                        double ownerMoney = ownerCard.getMoney();
                                        ownerCard.setMoney(ownerMoney + price);
                                        FileWriter ownerCardFileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/" + shop.getCardNumber() + ".json");
                                        ownerCardFileWriter.write(gson.toJson(ownerCard));
                                        ownerCardFileWriter.close();


                                        List<String> list = itemMeta.getLore();
                                        list.remove(list.size() - 1);
                                        itemMeta.setLore(list);
                                        event.getCurrentItem().setItemMeta(itemMeta);


                                        player.playSound(
                                                player.getLocation(),
                                                org.bukkit.Sound.ENTITY_PLAYER_LEVELUP,
                                                50,
                                                1);

                                    } catch (Exception e) {
                                        event.getWhoClicked().sendMessage(ChatColor.RED + "Something went wrong!");
                                        player.playSound(
                                                player.getLocation(),
                                                org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                50,
                                                1);
                                        event.setCancelled(true);
                                        return;
                                    }
                                } else {
                                    event.getWhoClicked().sendMessage(ChatColor.RED + "This item has no price!");
                                    player.playSound(
                                            player.getLocation(),
                                            org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                            50,
                                            1);
                                    event.setCancelled(true);
                                    return;
                                }
                            } else {
                                player.playSound(
                                        player.getLocation(),
                                        org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                        50,
                                        1);
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onShopMenuInventoryClick(InventoryClickEvent event) {

        int i = 0;

        for (Inventory shopMenuPage : CommonVariables.shopMenu) {

            if (shopMenuPage.equals(event.getClickedInventory())) {

                ItemStack prevPage = new ItemStack(Material.ARROW);
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta prevPageMeta = prevPage.getItemMeta();
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                prevPageMeta.setDisplayName("<--");
                nextPageMeta.setDisplayName("-->");
                prevPage.setItemMeta(prevPageMeta);
                nextPage.setItemMeta(nextPageMeta);

                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem() != null) {

                    if (event.getCurrentItem().equals(prevPage)) {
                        if (i > 0) {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50,
                                    1);
                            event.getWhoClicked().openInventory(CommonVariables.shopMenu.get(i - 1));
                        } else {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50,
                                    1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem().equals(nextPage)) {
                        if (i < CommonVariables.shopMenu.size() - 1) {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50,
                                    1);
                            event.getWhoClicked().openInventory(CommonVariables.shopMenu.get(i + 1));
                        } else {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50,
                                    1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem().equals(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE))) {
                        player.playSound(
                                player.getLocation(),
                                org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                50,
                                1);
                        event.setCancelled(true);
                        return;
                    }

                    ItemMeta currentItemMeta = event.getCurrentItem().getItemMeta();
                    player.playSound(
                            player.getLocation(),
                            org.bukkit.Sound.UI_BUTTON_CLICK,
                            50,
                            1);
                    event.getWhoClicked().openInventory(CommonVariables.shopsInventories
                            .get(currentItemMeta.getDisplayName()).get(0));
                    event.setCancelled(true);
                    return;
                }
                return;
            }
            i++;
        }
    }

    @org.bukkit.event.EventHandler
    public void onIconMenuInventoryClick(InventoryClickEvent event) {

        int i = 0;

        for (Inventory iconMenuPage : CommonVariables.iconMenu) {

            if (iconMenuPage.equals(event.getClickedInventory())) {

                ItemStack prevPage = new ItemStack(Material.ARROW);
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta prevPageMeta = prevPage.getItemMeta();
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                prevPageMeta.setDisplayName("<--");
                nextPageMeta.setDisplayName("-->");
                prevPage.setItemMeta(prevPageMeta);
                nextPage.setItemMeta(nextPageMeta);

                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem() != null) {

                    if (event.getCurrentItem().equals(prevPage)) {
                        if (i > 0) {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50,
                                    1);
                            event.getWhoClicked().openInventory(CommonVariables.iconMenu.get(i - 1));
                        } else {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50,
                                    1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem().equals(nextPage)) {
                        if (i < CommonVariables.iconMenu.size() - 1) {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.ITEM_BOOK_PAGE_TURN,
                                    50,
                                    1);
                            event.getWhoClicked().openInventory(CommonVariables.iconMenu.get(i + 1));
                        } else {
                            player.playSound(
                                    player.getLocation(),
                                    org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                    50,
                                    1);
                        }
                        event.setCancelled(true);
                        return;
                    }

                    ItemStack icon = new ItemStack(event.getCurrentItem().getType());
                    if (!icon.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                        player.playSound(
                                player.getLocation(),
                                org.bukkit.Sound.ENTITY_PLAYER_LEVELUP,
                                50,
                                1);
                        ItemMeta iconMeta = icon.getItemMeta();
                        iconMeta.setDisplayName(CommonVariables.playerShopIconChoose.get(event.getWhoClicked().getName()));
                        icon.setItemMeta(iconMeta);
                        Shop shop = Shop.getShop(CommonVariables.playerShopIconChoose.get(event.getWhoClicked().getName()));
                        shop.setIcon(icon);
                        player.sendTitle(ChatColor.DARK_GREEN + shop.getName(),
                                ChatColor.GOLD + "New icon has been set");
                        player.closeInventory();
                    } else {
                        player.playSound(
                                player.getLocation(),
                                org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                50,
                                1);
                    }
                    event.setCancelled(true);
                    return;
                }
            }
            i++;
        }
    }

    @org.bukkit.event.EventHandler
    public void onUpgradeMenuClick(InventoryClickEvent event) {

        if (event.getClickedInventory().equals(CommonVariables.upgradeMenu)) {
            if (event.getCurrentItem() != null) {
                Shop shop = Shop.getShop(CommonVariables.playerShopUpgrade.get(event.getWhoClicked().getName()));
                ItemStack newPage = new ItemStack(Material.PAPER);
                ItemMeta newPageMeta = newPage.getItemMeta();
                newPageMeta.setDisplayName("New page");
                newPage.setItemMeta(newPageMeta);
                if (event.getCurrentItem().equals(newPage)) {
                    if (Commands.doHaveMoney(event.getWhoClicked().getName(), 10)) {
                        if (Commands.takeMoney(event.getWhoClicked().getName(), 10)) {
                            ItemStack prevPage = new ItemStack(Material.ARROW);
                            ItemStack nextPage = new ItemStack(Material.ARROW);
                            ItemMeta prevPageMeta = prevPage.getItemMeta();
                            prevPageMeta.setDisplayName("<--");
                            prevPage.setItemMeta(prevPageMeta);
                            ItemMeta nextPageMeta = nextPage.getItemMeta();
                            nextPageMeta.setDisplayName("-->");
                            nextPage.setItemMeta(nextPageMeta);

                            Player player = (Player) event.getWhoClicked();

                            Inventory inventory = Bukkit.createInventory(null, 27,
                                    ChatColor.GOLD + shop.getName() + " " +
                                            (CommonVariables.shopsInventories.get(shop.getName()).size() + 1));
                            inventory.setItem(18, prevPage);
                            inventory.setItem(26, nextPage);
                            CommonVariables.shopsInventories.get(shop.getName()).add(inventory);
                            player.playSound(
                                    player.getLocation(),
                                    Sound.ENTITY_PLAYER_LEVELUP,
                                    50,
                                    1);
                            event.setCancelled(true);
                        } else {
                            event.getWhoClicked().closeInventory();
                            Player player = (Player) event.getWhoClicked();
                            player.sendTitle(ChatColor.DARK_GREEN + shop.getName(),
                                    ChatColor.RED + "Something went wrong");
                            event.getWhoClicked().closeInventory();
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        event.getWhoClicked().closeInventory();
                        Player player = (Player) event.getWhoClicked();
                        player.sendTitle(ChatColor.DARK_GREEN + shop.getName(),
                                ChatColor.RED + "You do not have money for upgrade");
                        event.getWhoClicked().closeInventory();
                        event.setCancelled(true);
                        return;
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
