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


public class EndermanKillListener implements Listener {

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
                    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
                    espada.addUnsafeEnchantment(Enchantment.SHARPNESS, 10);

                    ItemMeta meta = espada.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(ChatColor.GOLD + "Espada de los Dioses");
                        double baseDamage = 7.0; 
                        int sharpnessLevel = espada.getEnchantmentLevel(Enchantment.SHARPNESS);
                        double extraDamage = 1 + (sharpnessLevel * 0.5);
                        double totalDamage = baseDamage + extraDamage;

                        List<String> lore = new ArrayList<>();
                        lore.add(ChatColor.DARK_PURPLE + "Una espada bendecida con poder divino.");
                        lore.add(ChatColor.RED + "Daño total: " + totalDamage + " puntos");
                        meta.setLore(lore);

                        espada.setItemMeta(meta);
                    }

                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), espada);
                } else {
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
