package top.cmarco.temperatureplugin.ui;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.manager.WorldManager;

public final class TemperatureUpdaterRunnable implements Runnable {

    private final WorldManager worldManager;
    private final NamespaceManager namespaceManager;
    private final TemperatureDisplay temperatureDisplay;

    public TemperatureUpdaterRunnable(@NotNull final WorldManager worldManager,
                                      @NotNull final NamespaceManager namespaceManager,
                                      @NotNull final TemperatureDisplay temperatureDisplay) {
        this.worldManager = worldManager;
        this.namespaceManager = namespaceManager;
        this.temperatureDisplay = temperatureDisplay;
    }

    private void display(@NotNull final Player player) {

    }

    @Override
    public void run() {
        for (final World world : this.worldManager.getWorlds()) {
            for (final Player player : world.getPlayers()) {
                if (!this.namespaceManager.isEnableTemperature(player)) {
                    continue;
                }

                this.display(player);
            }
        }
    }
}
