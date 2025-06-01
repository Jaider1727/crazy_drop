package gnt.plugin.crazy_drop.config;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {

    private final double puntuacionMinimaDrop;

    public PluginConfig(FileConfiguration cfg) {
        this.puntuacionMinimaDrop = cfg.getDouble("puntuacion-minima-drop", 0.8);
    }

    public double getPuntuacionMinimaDrop() {
        return puntuacionMinimaDrop;
    }
}
