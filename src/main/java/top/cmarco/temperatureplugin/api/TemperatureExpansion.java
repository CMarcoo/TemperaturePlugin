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

package top.cmarco.temperatureplugin.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;

import java.util.Locale;

public final class TemperatureExpansion extends PlaceholderExpansion {

    private final TemperaturePlugin plugin;

    public TemperatureExpansion(@NotNull TemperaturePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "temperature";
    }

    @Override
    public @NotNull String getAuthor() {
        return "CMarco";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(final @Nullable Player player, @NotNull final String identifier) {
        if (player == null) {
            return "";
        }

        PlayerTemperatureManager temperatureManager = plugin.getTemperatureDisplay().getPlayerTemperatureManager();
        return switch (identifier.toLowerCase(Locale.ROOT)) {
            case "temperature" -> String.format("%.1f", temperatureManager.getTemperature(player));
            case "humidity" -> String.format("%.1f", plugin.getHumidityManager().getHumidity(player.getLocation().getBlock().getBiome()));
            default -> throw new IllegalStateException("Unexpected value: " + identifier.toLowerCase(Locale.ROOT));
        };
    }
}
