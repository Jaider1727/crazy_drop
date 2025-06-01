package gnt.plugin.crazy_drop.utils;


import org.bukkit.enchantments.Enchantment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class EnchantmentUtils {

    // Mapa de encantamientos con su peso
    private static final Map<Enchantment, Integer> ENCANTAMIENTOS_CON_PESO = Map.ofEntries(
            Map.entry(Enchantment.UNBREAKING, 30),
            Map.entry(Enchantment.EFFICIENCY, 25),
            Map.entry(Enchantment.MENDING, 20),
            Map.entry(Enchantment.SHARPNESS, 15),
            Map.entry(Enchantment.FIRE_ASPECT, 10),
            Map.entry(Enchantment.FORTUNE, 10),
            Map.entry(Enchantment.POWER, 5),
            Map.entry(Enchantment.PUNCH, 5),
            Map.entry(Enchantment.PROTECTION, 5),
            Map.entry(Enchantment.SILK_TOUCH, 5),
            Map.entry(Enchantment.THORNS, 5),
            Map.entry(Enchantment.LOOTING, 5)
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
