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
}
