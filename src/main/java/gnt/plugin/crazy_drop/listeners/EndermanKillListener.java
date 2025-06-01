package gnt.plugin.crazy_drop.listeners;

import gnt.plugin.crazy_drop.config.PluginConfig;
import gnt.plugin.crazy_drop.utils.ItemGenerator;
import org.bukkit.Material;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EndermanKillListener implements Listener {

    private final PluginConfig config;

    public EndermanKillListener(PluginConfig config) {
        this.config = config;
    }

    // Cuando muere un Enderman, dropea ítem o perla según puntuación
    @EventHandler
    public void onEndermanKill(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDERMAN) return;

        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        double puntuacion = ItemGenerator.calcularPuntuacionAleatoria();

        if (puntuacion > config.getPuntuacionMinimaDrop()) {
            ItemStack item = ItemGenerator.generarItemAleatorioConEncantamientos();
            event.getEntity().getWorld()
                    .dropItemNaturally(event.getEntity().getLocation(), item);
        } else {
            event.getEntity().getWorld()
                    .dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.ENDER_PEARL));
        }
    }
}