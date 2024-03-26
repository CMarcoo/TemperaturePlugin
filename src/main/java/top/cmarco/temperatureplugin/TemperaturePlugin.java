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

package top.cmarco.temperatureplugin;

import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.api.TemperatureExpansion;
import top.cmarco.temperatureplugin.commands.TemperatureCommand;
import top.cmarco.temperatureplugin.config.StandardConfig;
import top.cmarco.temperatureplugin.humidity.HumidityManager;
import top.cmarco.temperatureplugin.listener.TemperatureChangeListener;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;
import top.cmarco.temperatureplugin.manager.PlayerUnitManager;
import top.cmarco.temperatureplugin.manager.WorldManager;
import top.cmarco.temperatureplugin.task.WorldSeasonUpdateRunnable;
import top.cmarco.temperatureplugin.temperature.Temperature;
import top.cmarco.temperatureplugin.temperature.TemperatureConverter;
import top.cmarco.temperatureplugin.ui.TemperatureDisplay;

public final class TemperaturePlugin extends JavaPlugin {

    private StandardConfig standardConfig = null;
    private WorldManager worldManager = null;
    private NamespaceManager namespaceManager = null;
    private HumidityManager humidityManager = null;
    private TemperatureDisplay temperatureDisplay = null;
    private PlayerUnitManager playerUnitManager;
    private TemperatureChangeListener temperatureChangeListener = null;

    @Override
    public void onEnable() {
        this.loadConfig();
        this.worldManager = new WorldManager(this);
        this.namespaceManager = new NamespaceManager(this);
        this.playerUnitManager = new PlayerUnitManager(this);
        this.loadHumidityManager();
        startSeasonUpdater();
        this.loadTempDisplay();
        this.registerListeners();
        this.assignCommand();
        this.registerPapi();
    }

    public void reload() {
        playerUnitManager.clearAll();
        temperatureDisplay.getPlayerTemperatureManager().clearAll();
    }

    @Override
    public void onDisable() {
        this.temperatureDisplay.stopTask();
        this.humidityManager.stopTask();
        reload();
    }

    private void loadConfig() {
        this.standardConfig = new StandardConfig(this);
    }

    private void assignCommand() {
        getCommand("temperature").setExecutor(new TemperatureCommand(this));
    }

    private void startSeasonUpdater() {
        this.getServer().getScheduler().runTaskTimer(this, new WorldSeasonUpdateRunnable(worldManager), 0L, 20*60L);
    }

    private void loadTempDisplay() {
        this.temperatureDisplay = new TemperatureDisplay(this);
        this.temperatureDisplay.startTask();
    }

    private void loadHumidityManager() {
        this.humidityManager = new HumidityManager(this);
        this.humidityManager.startTask();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        if (temperatureChangeListener != null) {
            temperatureChangeListener = null;
        }

        temperatureChangeListener = new TemperatureChangeListener(this);
        pluginManager.registerEvents(temperatureChangeListener, this);
    }

    private void registerPapi() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            final TemperatureExpansion temperatureExpansion = new TemperatureExpansion(this);
            if (temperatureExpansion.canRegister()) {
                temperatureExpansion.register();
                getLogger().info("Successfully registered Temperature PlaceholderAPI expansion!");
            }
        }
    }

    /* Getters */

    @NotNull
    public StandardConfig getStandardConfig() {
        return standardConfig;
    }

    @NotNull
    public WorldManager getWorldManager() {
        return worldManager;
    }

    @NotNull
    public NamespaceManager getNamespaceManager() {
        return namespaceManager;
    }

    @NotNull
    public TemperatureDisplay getTemperatureDisplay() {
        return temperatureDisplay;
    }

    @NotNull
    public HumidityManager getHumidityManager() {
        return humidityManager;
    }

    public PlayerUnitManager getPlayerUnitManager() {
        return playerUnitManager;
    }
}
