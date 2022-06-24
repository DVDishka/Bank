package dvdishka.bank.Shop.shopHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dvdishka.bank.Blancville.Classes.Card;
import dvdishka.bank.Shop.Classes.PlayerCard;
import dvdishka.bank.Shop.Classes.Shop;
import dvdishka.bank.common.CommonVariables;
import net.kyori.adventure.sound.Sound;
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
    public void inventoryClickEventHandler(InventoryClickEvent event) {

        for (Shop shop : CommonVariables.shops) {
            if (!shop.getOwner().equals(event.getWhoClicked().getName()) &&
                    event.getView().getTopInventory().equals(CommonVariables.shopsInventories.get(shop.getName()))) {
                int i = 0;
                for (ItemStack item : CommonVariables.shopsInventories.get(shop.getName())) {
                    if (item == null) {
                        CommonVariables.shopsInventories.get(shop.getName()).setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1));
                    }
                    i++;
                }
            }
        }

        boolean firstFlag = false;
        boolean secondFlag = false;

        for (Shop shop : CommonVariables.shops) {
            if (!event.getWhoClicked().getName().equals(shop.getOwner())) {
                if (CommonVariables.shopsInventories.get(shop.getName()) == event.getClickedInventory()) {
                    firstFlag = true;
                }
                if (event.getView().getTopInventory().equals(CommonVariables.shopsInventories.get(shop.getName()))) {
                    secondFlag = true;
                }
            }
        }
        if (!firstFlag && secondFlag) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                        (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                Sound.Source.BLOCK, 50, 1));
                event.setCancelled(true);
                return;
            }
        }

        for (Shop shop : CommonVariables.shops) {
            if (CommonVariables.shopsInventories.get(shop.getName()) == event.getClickedInventory()) {

                if (event.getWhoClicked().getName().equals(shop.getOwner())) {
                    if (event.getCurrentItem() != null) {
                        List<String> list = event.getCurrentItem().getLore();
                        if (list != null) {
                            try {
                                Integer.parseInt(list.get(list.size() - 1));
                                list.remove(list.size() - 1);
                                event.getCurrentItem().setLore(list);
                                return;
                            } catch (Exception ignored) {}
                        }
                    }
                }

                if (!event.getWhoClicked().getName().equals(shop.getOwner())) {

                    if (event.getCursor() != null && !event.getCursor().getType().equals(Material.AIR)) {
                        event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                        Sound.Source.BLOCK, 50, 1));
                        event.setCancelled(true);
                        return;
                    }

                    if (event.getCurrentItem() != null) {
                        if (event.getCurrentItem().getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                            if (event.getCurrentItem().getLore() != null && event.getCurrentItem().getLore().size() > 0) {

                                int price = 0;
                                try {


                                    price = Integer.parseInt(event.getCurrentItem().getLore().get(event.getCurrentItem().getLore().size() - 1));
                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();


                                    File file = new File("plugins/Bank/Cards/" + event.getWhoClicked().getName() + ".json");
                                    if (!file.exists()) {
                                        event.getWhoClicked().sendMessage(ChatColor.RED + "You have no bank card!");
                                        event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                                (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                        Sound.Source.BLOCK, 50, 1));
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
                                        event.getWhoClicked().sendMessage(ChatColor.RED + "You have not " + price + " l`argent");
                                        event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                                (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                        Sound.Source.BLOCK, 50, 1));
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


                                    List<String> list = event.getCurrentItem().getLore();
                                    list.remove(list.size() - 1);
                                    event.getCurrentItem().setLore(list);


                                    event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                            (org.bukkit.Sound.ENTITY_PLAYER_LEVELUP,
                                                    Sound.Source.NEUTRAL, 50, 1));

                                } catch (Exception e) {
                                    event.getWhoClicked().sendMessage(ChatColor.RED + "Something went wrong!");
                                    event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                            (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                            Sound.Source.BLOCK, 50, 1));
                                    event.setCancelled(true);
                                    return;
                                }
                            } else {
                                event.getWhoClicked().sendMessage(ChatColor.RED + "This item has no price!");
                                event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                        (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                                Sound.Source.BLOCK, 50, 1));
                                event.setCancelled(true);
                                return;
                            }
                        } else {
                            event.getWhoClicked().playSound(net.kyori.adventure.sound.Sound.sound
                                    (org.bukkit.Sound.BLOCK_ANVIL_PLACE,
                                            Sound.Source.BLOCK, 50, 1));
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
