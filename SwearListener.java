package pl.coffee;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SwearListener implements Listener {

    private final CoffeeAntiSwearV2 plugin;

    public SwearListener(CoffeeAntiSwearV2 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        plugin.getSwearManager().checkMessage(event.getPlayer(), event.getMessage());
    }
}
