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

package top.cmarco.temperatureplugin.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.performance.Performance;

import java.util.List;

public final class StandardConfig {

    private final TemperaturePlugin plugin;
    private FileConfiguration config = null;

    public StandardConfig(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    @NotNull
    public List<String> getWorlds() {
        return this.config.getStringList("worlds");
    }

    @NotNull
    public String getUnit() {
        return this.config.getString("temperature-unit", "CELSIUS");
    }

    @NotNull
    public Performance getPerformance() {
        return Performance.valueOf(this.config.getString("performance", "MEDIUM"));
    }

    @NotNull
    public String getActionBarFormat() {
        return this.config.getString("format.action-bar");
    }

    @NotNull
    public String getBarProgressReached() {
        return this.config.getString("format.bar-progress.reached");
    }

    @NotNull
    public String getBarProgressUnreached() {
        return this.config.getString("format.bar-progress.unreached");
    }

    public double getStandardTempsMinimum() {
        return this.config.getDouble("biomes.STANDARD_TEMPS.minimum");
    }

    public double getStandardTempsMaximum() {
        return this.config.getDouble("biomes.STANDARD_TEMPS.maximum");
    }

    public double getColdTempsMinimum() {
        return this.config.getDouble("biomes.COLD_TEMPS.minimum");
    }

    public double getColdTempsMaximum() {
        return this.config.getDouble("biomes.COLD_TEMPS.maximum");
    }

    public double getWarmTempsMinimum() {
        return this.config.getDouble("biomes.WARM_TEMPS.minimum");
    }

    public double getWarmTempsMaximum() {
        return this.config.getDouble("biomes.WARM_TEMPS.maximum");
    }

    public double getOceanTempsMinimum() {
        return this.config.getDouble("biomes.OCEAN_TEMPS.minimum");
    }

    public double getOceanTempsMaximum() {
        return this.config.getDouble("biomes.OCEAN_TEMPS.maximum");
    }

    public double getColdOceanTempsMinimum() {
        return this.config.getDouble("biomes.COLD_OCEAN_TEMPS.minimum");
    }

    public double getColdOceanTempsMaximum() {
        return this.config.getDouble("biomes.COLD_OCEAN_TEMPS.maximum");
    }

    public double getNetherTempsMinimum() {
        return this.config.getDouble("biomes.NETHER_TEMPS.minimum");
    }

    public double getNetherTempsMaximum() {
        return this.config.getDouble("biomes.NETHER_TEMPS.maximum");
    }
}
