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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.temperature.Temperature;

public class PdcUpdaterListener implements Listener {

    private final TemperaturePlugin plugin;

    public PdcUpdaterListener(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(@NotNull final PlayerJoinEvent event) {
        final NamespaceManager namespaceManager = plugin.getNamespaceManager();
        final Player player = event.getPlayer();

        //final Temperature temperature = namespaceManager.getPlayerTemperature(player);
        //if (temperature == null) {
        //    namespaceManager.setTemperatureUnit(player, plugin.getStandardConfig().getUnit());
        //}
    }
}
