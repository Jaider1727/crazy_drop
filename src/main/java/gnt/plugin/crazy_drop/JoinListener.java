package gnt.plugin.crazy_drop;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        var player = event.getPlayer();

        player.sendMessage(ChatColor.GOLD + "Joined " + ChatColor.GREEN + player.getName());
    }
}
