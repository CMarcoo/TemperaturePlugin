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

package top.cmarco.temperatureplugin.manager;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.temperature.Temperature;

public final class NamespaceManager {

    private final TemperaturePlugin plugin;
    private final NamespacedKey enabledKey;
    private final NamespacedKey unitKey;


    public NamespaceManager(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        this.enabledKey = new NamespacedKey(plugin, "temperature_enabled");
        this.unitKey = new NamespacedKey(plugin, "temperature_unit");
    }

    public void enableTemperature(@NotNull final Player player) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        pdc.set(enabledKey, PersistentDataType.BYTE, (byte) 0x00);
    }

    public void disableTemperature(@NotNull final Player player) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        pdc.set(enabledKey, PersistentDataType.BYTE, (byte) 0x01);
    }

    public void setTemperatureUnit(@NotNull final Player player, @NotNull final String unit) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        try {
            Temperature temperature = Temperature.valueOf(unit);
            pdc.set(unitKey, PersistentDataType.STRING, unit);
        } catch (IllegalArgumentException ignored) {
            plugin.getLogger().warning("WARNING! Passed illegal unit value as " + unit);
        }
    }

    @Nullable
    public Temperature getPlayerTemperature(@NotNull final Player player) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        final String string = pdc.get(unitKey, PersistentDataType.STRING);
        if (string == null) return null;
        return Temperature.valueOf(string);
    }

    public boolean isEnableTemperature(@NotNull final Player player) {
        final PersistentDataContainer pdc = player.getPersistentDataContainer();
        final Byte value = pdc.get(enabledKey, PersistentDataType.BYTE);
        return value == null || value == (byte) 0x00;
    }
}
