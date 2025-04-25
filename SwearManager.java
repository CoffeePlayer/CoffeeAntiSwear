package pl.coffee;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.util.*;

public class SwearManager {

    public static class SetData {
        public List<String> words;
        public int muteTime;
        public String command;
        public String message;

        public SetData(List<String> words, int muteTime, String command, String message) {
            this.words = words;
            this.muteTime = muteTime;
            this.command = command;
            this.message = message;
        }
    }

    private final CoffeeAntiSwearV2 plugin;
    private final Map<String, SetData> sets = new HashMap<>();
    private final Set<String> activeSets = new HashSet<>();

    public SwearManager(CoffeeAntiSwearV2 plugin) {
        this.plugin = plugin;
        loadSets();
    }

    public void loadSets() {
        sets.clear();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("sets");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                List<String> words = section.getStringList(key + ".words");
                int muteTime = section.getInt(key + ".mute_time");
                String command = section.getString(key + ".command");
                String message = section.getString(key + ".message");
                sets.put(key, new SetData(words, muteTime, command, message));
            }
        }
    }

    public void checkMessage(Player player, String message) {
        for (String setName : activeSets) {
            SetData data = sets.get(setName);
            for (String swear : data.words) {
                if (message.toLowerCase().contains(swear.toLowerCase())) {
                    punish(player, data, swear);
                    return;
                }
            }
        }
    }

    private void punish(Player player, SetData data, String word) {
        String cmd = data.command.replace("{player}", player.getName()).replace("{time}", String.valueOf(data.muteTime));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        player.sendMessage(data.message.replace("&", "§"));
    }

    public Map<String, SetData> getSets() {
        return sets;
    }

    public void activateSet(String name) {
        if (sets.containsKey(name)) {
            activeSets.add(name);
        }
    }

    public void deactivateSet(String name) {
        activeSets.remove(name);
    }

    public void activateAllSets() {
        activeSets.clear();
        activeSets.addAll(sets.keySet());
    }

    public boolean isSetActive(String name) {
        return activeSets.contains(name);
    }
}
