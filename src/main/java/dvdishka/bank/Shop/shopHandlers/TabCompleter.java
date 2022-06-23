package dvdishka.bank.Shop.shopHandlers;

import dvdishka.bank.Shop.Classes.Shop;
import dvdishka.bank.common.CommonVariables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {

            boolean flag = false;
            for (Shop shop : CommonVariables.shops) {
                if (shop.getOwner().equals(sender.getName())) {
                    flag = true;
                }
            }
            if (flag) {
                return List.of("create", "edit", "open");
            } else {
                return List.of("create", "open");
            }
        }

        if (args.length == 2) {

            if (args[0].equals("create")) {
                return List.of("name");
            }

            else if (args[0].equals("edit")) {
                ArrayList<String> list = new ArrayList<>();
                for (Shop shop : CommonVariables.shops) {
                    if (shop.getOwner().equals(sender.getName())) {
                        list.add(shop.getName());
                    }
                }
                return list;
            }

            else if (args[0].equals("open")) {
                ArrayList<String> list = new ArrayList<>();
                for (Shop shop : CommonVariables.shops) {
                    list.add(shop.getName());
                }
                return list;
            }
        }

        if (args.length == 3) {

            if (args[0].equals("create")) {
                return List.of("cardNumber");
            }

            else if (args[0].equals("edit")) {
                return List.of("price", "card");
            }
        }

        if (args.length == 4) {

            if (args[0].equals("edit") && args[2].equals("price") || args[0].equals("edit") && args[2].equals("card")) {
                return List.of("set");
            }
        }

        if (args.length == 5) {

            if (args[0].equals("edit") && args[2].equals("price") && args[3].equals("set")) {
                return List.of("index");
            }

            if (args[0].equals("edit") && args[2].equals("card") && args[3].equals("set")) {
                return List.of("cardNumber");
            }
        }

        if (args.length == 6) {

            if (args[0].equals("edit") && args[2].equals("price") && args[3].equals("set")) {
                return List.of("price");
            }
        }

        return List.of();
    }
}
