package ru.dvdishka.bank.blancville.blancvilleHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;
import ru.dvdishka.bank.blancville.Classes.Card;
import ru.dvdishka.bank.shop.Classes.PlayerCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Commands {

    public static boolean doHaveMoney(String player, int money) {

        try {
            File cardFile = new File("plugins/Bank/Cards/" + player + ".json");
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
            int number = playerCard.getNumber();

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
            if (card.getMoney() >= money) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean takeMoney(String player, int money) {

        try {
            File cardFile = new File("plugins/Bank/Cards/" + player + ".json");
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
            int number = playerCard.getNumber();

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
            if (card.getMoney() < money) {
                return false;
            }
            card.setMoney(card.getMoney() - money);
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
            adminCard.setMoney(adminCard.getMoney() + money);
            FileWriter adminFileWriter = new FileWriter("/home/server/minecraft/Blancville_bank/users/111111.json");
            adminFileWriter.write(gson.toJson(adminCard));
            adminFileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
