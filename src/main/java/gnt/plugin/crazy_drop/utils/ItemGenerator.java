package gnt.plugin.crazy_drop.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class ItemGenerator {

    // Filtrar automáticamente los materiales que nos interesan
    private static final Set<Material> POSIBLES_ITEMS = Arrays.stream(Material.values())
            .filter(mat -> {
                String name = mat.name();
                return name.endsWith("_SWORD")
                        || name.endsWith("_AXE")
                        || name.endsWith("_PICKAXE")
                        || name.endsWith("_HOE")
                        || name.endsWith("_SHOVEL")
                        || name.endsWith("_HELMET")
                        || name.endsWith("_CHESTPLATE")
                        || name.endsWith("_LEGGINGS")
                        || name.endsWith("_BOOTS")
                        || name.endsWith("BOW");
            })
            .collect(Collectors.toSet());

    // Calcular puntuación aleatoria
    public static double calcularPuntuacionAleatoria() {
        double velocidad = Math.random() * 10;
        double resistencia = Math.random() * 10;
        double daño = Math.random() * 10;

        double velocidadN = velocidad / 10.0;
        double resistenciaN = resistencia / 10.0;
        double dañoN = daño / 10.0;

        return 0.6 * velocidadN + 0.2 * resistenciaN + 0.4 * dañoN;
    }

    // Generar ItemStack aleatorio con 1–8 encantamientos
    public static ItemStack generarItemAleatorioConEncantamientos() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Elegir material aleatorio
        Material tipo = getRandomFromSet(POSIBLES_ITEMS, random);
        ItemStack item = new ItemStack(tipo);

        // Elegir cuántos encantamientos (1, 2 o 8)
        int cantidadEncantamientos = 1 + random.nextInt(8);
        Set<Enchantment> yaUsados = new HashSet<>();

        // Seleccionar encantamientos sin repetir, según peso
        for (int i = 0; i < cantidadEncantamientos; i++) {
            Enchantment encant = EnchantmentUtils.seleccionarEncantamientoConProbabilidad(yaUsados);
            if (encant == null) break;

            int nivelAleatorio = 1 + random.nextInt(10); // entre 1 y 10
            item.addUnsafeEnchantment(encant, nivelAleatorio);
            yaUsados.add(encant);
        }
        return item;
    }

    // Metodo auxiliar para sacar un elemento aleatorio de un Set
    private static Material getRandomFromSet(Set<Material> set, ThreadLocalRandom random) {
        int index = random.nextInt(set.size());
        Iterator<Material> iter = set.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter.next();
    }
}
