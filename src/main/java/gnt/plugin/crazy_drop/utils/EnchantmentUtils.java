package gnt.plugin.crazy_drop.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class EnchantmentUtils {

    // Mapa de encantamientos con su peso
    public static final Map<Enchantment, Integer> ENCANTAMIENTOS_CON_PESO = Map.ofEntries(
            Map.entry(Enchantment.UNBREAKING,    1),
            Map.entry(Enchantment.EFFICIENCY,    1),
            Map.entry(Enchantment.PROTECTION,    1),
            Map.entry(Enchantment.SHARPNESS,     1),
            Map.entry(Enchantment.FORTUNE,       1),
            Map.entry(Enchantment.FIRE_ASPECT,   1),
            Map.entry(Enchantment.POWER,         1),
            Map.entry(Enchantment.LOOTING,       1),
            Map.entry(Enchantment.SILK_TOUCH,    1),
            Map.entry(Enchantment.PUNCH,         1),
            Map.entry(Enchantment.THORNS,        1),
            Map.entry(Enchantment.KNOCKBACK,        1)
    );


    // Elegir un encantamiento aleatorio con probabilidad según peso
    public static Enchantment seleccionarEncantamientoConProbabilidad(Set<Enchantment> excluidos) {
        List<Enchantment> candidatos = new ArrayList<>();
        List<Integer> pesos = new ArrayList<>();
        int totalPesos = 0;

        for (Map.Entry<Enchantment, Integer> entry : ENCANTAMIENTOS_CON_PESO.entrySet()) {
            if (excluidos.contains(entry.getKey())) continue;
            candidatos.add(entry.getKey());
            pesos.add(entry.getValue());
            totalPesos += entry.getValue();
        }

        if (candidatos.isEmpty()) return null;

        int rand = ThreadLocalRandom.current().nextInt(totalPesos);
        int acumulado = 0;
        for (int i = 0; i < candidatos.size(); i++) {
            acumulado += pesos.get(i);
            if (rand < acumulado) {
                return candidatos.get(i);
            }
        }
        return candidatos.get(0);
    }
}
