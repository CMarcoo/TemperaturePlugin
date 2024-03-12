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

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.temperature.Temperature;

import java.util.HashMap;
import java.util.UUID;

public final class PlayerUnitManager {

    public PlayerUnitManager(TemperaturePlugin temperaturePlugin) {
        this.temperaturePlugin = temperaturePlugin;
    }

    private final TemperaturePlugin temperaturePlugin;
    private final HashMap<UUID, Temperature> playerUnit = new HashMap<>();
    private final HashMap<UUID, Long> unitUpdate = new HashMap<>();

    @NotNull
    public Temperature getPlayerTemperature(@NotNull final Player player) {
        final UUID uuid = player.getUniqueId();

        if (this.playerUnit.containsKey(uuid) && System.currentTimeMillis() - this.unitUpdate.get(uuid) < 10E3) {
            return this.playerUnit.get(uuid);
        }

        final NamespaceManager namespaceManager = temperaturePlugin.getNamespaceManager();
        Temperature temperature = namespaceManager.getPlayerTemperature(player);
        if (temperature == null) {
            namespaceManager.setTemperatureUnit(player, temperaturePlugin.getStandardConfig().getUnit());
            temperature = namespaceManager.getPlayerTemperature(player);
        }

        this.playerUnit.put(uuid, temperature);
        this.unitUpdate.put(uuid, System.currentTimeMillis());

        return temperature;
    }

    public void setTemperature(@NotNull final Player player, @NotNull final Temperature temperature) {
        final NamespaceManager namespaceManager = temperaturePlugin.getNamespaceManager();
        final UUID uuid = player.getUniqueId();
        this.playerUnit.put(uuid, temperature);
        this.unitUpdate.put(uuid, System.currentTimeMillis());
        namespaceManager.setTemperatureUnit(player, temperature.getName());
    }

    public void clearAll() {
        unitUpdate.clear();
        playerUnit.clear();
    }
}
