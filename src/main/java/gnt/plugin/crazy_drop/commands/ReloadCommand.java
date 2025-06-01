package gnt.plugin.crazy_drop.commands;

import gnt.plugin.crazy_drop.Crazy_drop;
import gnt.plugin.crazy_drop.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final Crazy_drop plugin;
    private PluginConfig configManager;

    public ReloadCommand(Crazy_drop plugin, PluginConfig configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("crazydrop.reload")) {
            sender.sendMessage(ChatColor.RED + "No tienes permiso para usar /crazydrop reload.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            configManager = new PluginConfig(plugin.getConfig());
            plugin.setPluginConfig(configManager);

            sender.sendMessage(ChatColor.GREEN + "CrazyDrop: configuración recargada correctamente.");
            plugin.getLogger().info("Configuración recargada vía comando por " + sender.getName());
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "/crazydrop reload");
        return true;
    }
}
