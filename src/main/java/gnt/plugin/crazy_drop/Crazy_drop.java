package gnt.plugin.crazy_drop;

import gnt.plugin.crazy_drop.commands.ReloadCommand;
import gnt.plugin.crazy_drop.config.PluginConfig;
import gnt.plugin.crazy_drop.listeners.EndermanKillListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Crazy_drop extends JavaPlugin {

    private PluginConfig configManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        configManager = new PluginConfig(getConfig());

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new EndermanKillListener(configManager), this);

        this.getCommand("crazydrop").setExecutor(new ReloadCommand(this, configManager));


        getLogger().info( "&2Crazy Drop Plugin Enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info( "&2Crazy Drop Plugin Disabled");
    }

    public void setPluginConfig(PluginConfig newConfig) {
        this.configManager = newConfig;
    }
    public PluginConfig getPluginConfig() {
        return configManager;
    }

}
