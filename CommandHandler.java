package pl.coffee;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    private final CoffeeAntiSwearV2 plugin;

    public CommandHandler(CoffeeAntiSwearV2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("coffeeantiswear.admin")) {
            sender.sendMessage("§cNie masz uprawnień.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§7Użycie: /coffeeantiswear <reload|start|stop>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                plugin.getSwearManager().loadSets();
                plugin.getSwearManager().activateAllSets();
                sender.sendMessage("§aConfig przeładowany.");
                return true;

            case "start":
                if (args.length < 2) {
                    sender.sendMessage("§cUżycie: /coffeeantiswear start <zestaw>");
                    return true;
                }
                if (!plugin.getSwearManager().getSets().containsKey(args[1])) {
                    sender.sendMessage("§cZestaw nie istnieje.");
                    return true;
                }
                plugin.getSwearManager().activateSet(args[1]);
                sender.sendMessage("§aZestaw §e" + args[1] + " §azostał aktywowany.");
                return true;

            case "stop":
                if (args.length < 2) {
                    sender.sendMessage("§cUżycie: /coffeeantiswear stop <zestaw>");
                    return true;
                }
                plugin.getSwearManager().deactivateSet(args[1]);
                sender.sendMessage("§aZestaw §e" + args[1] + " §azostał wyłączony.");
                return true;

            default:
                sender.sendMessage("§cNieznana komenda.");
                return true;
        }
    }
}
