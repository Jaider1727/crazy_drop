package gnt.plugin.crazy_drop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EndermanKillListener implements Listener {

    Material[] espadas = {
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD
    };

    Enchantment[] encantamientosDisponibles = {
            Enchantment.UNBREAKING,     // Unbreaking
            Enchantment.EFFICIENCY,     // Efficiency
            Enchantment.MENDING         // Mending
    };


    @EventHandler
    public void onEndermanKill(EntityDeathEvent event) {


        if(event.getEntityType()==EntityType.ENDERMAN) {
            Player killer = event.getEntity().getKiller();
            if (killer != null) {
                event.getDrops().clear();

                /*event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                        new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));*/

                var velocidad = Math.random()*10f;
                var resistencia = Math.random()*10f;
                var daño = Math.random()*10f;

                var velocidadN = velocidad / 10f;
                var resistenciaN = resistencia / 10f;
                var dañoN = daño / 10f;

                var puntuacion = 0.6f*velocidadN + 0.2f*resistenciaN + 0.4*dañoN;
                System.out.println(ChatColor.GREEN + "Puntuación: " + puntuacion);

                if (puntuacion > 0.6f) {
                    // Elegir espada aleatoria
                    Material tipoEspada = espadas[new Random().nextInt(espadas.length)];
                    ItemStack espada = new ItemStack(tipoEspada);

                    // Obtener cuántos encantamientos tendrá (de 1 a 3)
                    int cantidadEncantamientos = 1 + new Random().nextInt(3);

                    List<Enchantment> yaUsados = new ArrayList<>();

                    for (int i = 0; i < cantidadEncantamientos; i++) {
                        Enchantment encantamiento;
                        // Asegurar que no se repitan
                        do {
                            encantamiento = encantamientosDisponibles[new Random().nextInt(encantamientosDisponibles.length)];
                        } while (yaUsados.contains(encantamiento));
                        yaUsados.add(encantamiento);

                        int nivel = 1 + new Random().nextInt(10); // nivel entre 1 y 10
                        espada.addUnsafeEnchantment(encantamiento, nivel);
                    }

                    // Estética de la espada
                    ItemMeta meta = espada.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(ChatColor.AQUA + "Espada Misteriosa");

                        List<String> lore = new ArrayList<>();
                        meta.setLore(lore);
                        espada.setItemMeta(meta);
                    }

                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), espada);
                }
                    else {
                    event.getEntity().getWorld().dropItemNaturally(
                            event.getEntity().getLocation(),
                            new ItemStack(Material.ENCHANTED_GOLDEN_APPLE)
                    );
                }
            }
        }
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        ItemStack arma = player.getInventory().getItemInMainHand();

        if (arma == null || arma.getType() != Material.DIAMOND_SWORD) return;

        ItemMeta meta = arma.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String nombre = ChatColor.stripColor(meta.getDisplayName());
        if (nombre.equals("Espada de los Dioses") &&
                arma.containsEnchantment(Enchantment.SHARPNESS) &&
                arma.getEnchantmentLevel(Enchantment.SHARPNESS) == 10) {

            event.setDamage(event.getDamage() + 10.0);
            player.sendMessage(ChatColor.RED + "⚔ Daño extra aplicado con la Espada de los Dioses!");
        }
    }
}
