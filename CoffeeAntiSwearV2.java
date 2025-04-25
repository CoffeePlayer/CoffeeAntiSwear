package pl.coffee;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoffeeAntiSwearV2 extends JavaPlugin {

    private SwearManager swearManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.swearManager = new SwearManager(this);
        getCommand("coffeeantiswear").setExecutor(new CommandHandler(this));
        Bukkit.getPluginManager().registerEvents(new SwearListener(this), this);
        swearManager.activateAllSets(); // Aktywuj wszystkie zestawy przy starcie
        getLogger().info("CoffeeAntiSwear uruchomiony.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CoffeeAntiSwear wyłączony.");
    }

    public SwearManager getSwearManager() {
        return swearManager;
    }
}
