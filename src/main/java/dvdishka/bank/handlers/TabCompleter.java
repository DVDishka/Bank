package dvdishka.bank.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            if (!sender.isOp()) {
                return Arrays.asList("sell", "get");
            } else {
                return Arrays.asList("sell", "get", "set");
            }
        }

        if (args.length == 2) {
            if (args[0].equals("sell")) {
                return Arrays.asList("diamond", "netherite");
            } else if (args[0].equals("get") || args[0].equals("set") && sender.isOp()) {
                return Arrays.asList("price");
            } else {
                return Arrays.asList();
            }
        }

        if (args.length == 3) {
            if (args[0].equals("get") && args[1].equals("price") || args[0].equals("set") && args[1].equals("price") &&
            sender.isOp()) {
                return Arrays.asList("diamond", "netherite");
            } else if (args[0].equals("sell") && args[1].equals("diamond") ||
                    args[0].equals("sell") && args[1].equals("netherite")) {
                return Arrays.asList("amount");
            } else {
                return Arrays.asList();
            }
        }

        if (args.length == 4) {
            if (args[0].equals("sell") && args[1].equals("diamond") ||
                    args[0].equals("sell") && args[1].equals("netherite")) {
                return Arrays.asList("cardNumber");
            } else if (args[0].equals("set") && args[1].equals("price") && args[2].equals("diamond") && sender.isOp() ||
                    args[0].equals("set") && args[1].equals("price") && args[2].equals("netherite") && sender.isOp()) {
                return Arrays.asList("newPrice");
            } else {
                return Arrays.asList();
            }
        }

        if (args.length > 4) {

        }

        return Arrays.asList();
    }
}
