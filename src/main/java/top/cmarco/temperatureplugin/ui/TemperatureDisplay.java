/*
 *     TemperaturePlugin - The Most Realistic Temperature Plugin Ever Created!
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.temperatureplugin.ui;

import org.bukkit.Server;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;
import top.cmarco.temperatureplugin.performance.Performance;
import top.cmarco.temperatureplugin.task.TemperatureDisplayRunnable;
import top.cmarco.temperatureplugin.task.TemperatureUpdateRunnable;
import top.cmarco.temperatureplugin.temperature.Temperature;

public final class TemperatureDisplay {

    private final TemperaturePlugin plugin;
    private BukkitTask updaterTask = null;
    private TemperatureDisplayRunnable temperatureDisplayRunnable = null;
    private TemperatureUpdateRunnable temperatureUpdateRunnable = null;
    private final PlayerTemperatureManager playerTemperatureManager;

    public TemperatureDisplay(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        this.playerTemperatureManager = new PlayerTemperatureManager(plugin);
    }

    public void startTask() {
        if (this.updaterTask != null) {
            stopTask();
        }

        Server server = this.plugin.getServer();
        BukkitScheduler scheduler = server.getScheduler();
        this.temperatureDisplayRunnable = new TemperatureDisplayRunnable(plugin.getWorldManager(), plugin.getNamespaceManager(), this.playerTemperatureManager);
        this.temperatureUpdateRunnable = new TemperatureUpdateRunnable(this.playerTemperatureManager, plugin.getWorldManager());
        final Performance performanceOption = plugin.getStandardConfig().getPerformance();
        scheduler.runTaskTimer(this.plugin, temperatureUpdateRunnable, 1L, performanceOption.getUpdateValue());
        scheduler.runTaskTimer(this.plugin, temperatureDisplayRunnable, 2L, 1L);
    }

    public void stopTask() {
        if (this.updaterTask == null) {
            return;
        }

        this.updaterTask.cancel();
        this.updaterTask = null;
        this.temperatureDisplayRunnable = null;
    }

    /* GETTERS */

    @NotNull
    public PlayerTemperatureManager getPlayerTemperatureManager() {
        return playerTemperatureManager;
    }
}
