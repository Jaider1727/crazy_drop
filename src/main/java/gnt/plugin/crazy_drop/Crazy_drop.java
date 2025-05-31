package gnt.plugin.crazy_drop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Crazy_drop extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new EndermanKillListener(), this);


        getLogger().info(ChatColor.GREEN + "Crazy Drop Plugin Enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info(ChatColor.RED + "Crazy Drop Plugin Disabled");
    }
}
