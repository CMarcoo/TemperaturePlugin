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

package top.cmarco.temperatureplugin.task;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.config.StandardConfig;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;
import top.cmarco.temperatureplugin.manager.WorldManager;
import top.cmarco.temperatureplugin.season.Season;
import top.cmarco.temperatureplugin.temperature.Temperature;
import top.cmarco.temperatureplugin.utilities.ChatUtils;

public final class TemperatureDisplayRunnable implements Runnable {

    private final WorldManager worldManager;
    private final NamespaceManager namespaceManager;
    private final PlayerTemperatureManager temperatureManager;

    public TemperatureDisplayRunnable(@NotNull final WorldManager worldManager,
                                      @NotNull final NamespaceManager namespaceManager,
                                      @NotNull final PlayerTemperatureManager playerTemperatureManager) {

        this.worldManager = worldManager;
        this.namespaceManager = namespaceManager;
        this.temperatureManager = playerTemperatureManager;
    }

    @NotNull
    private String getFormatTemperature(@NotNull final Player player, final double temp) {
        final StandardConfig config = this.worldManager.getPlugin().getStandardConfig();
        final Season currentSeason = worldManager.getWorldSeason(player.getWorld());
        final Temperature playerTemp = this.temperatureManager.getPlayerUnitManager().getPlayerTemperature(player);

        final double minConverted = playerTemp.convertToUnit(-40.0d);
        final double maxConverted = playerTemp.convertToUnit(+40.0d);
        final double step = (maxConverted - minConverted) / 10d;

        final StringBuilder bar = new StringBuilder(); // ▓ ░

        final double celsiusTemp = playerTemp.convertUnitToCelsius(temp);

        final char relativeTempColour = (char) (celsiusTemp < 5.0 ? 0x62 : celsiusTemp < 29.5 ? 0x65 : 0x63);

        for (int i = 1; i <= 10; i++) {
            if (i * step + minConverted >= temp && i != 1) {
                bar.append("&7").append((String) config.getBarProgressUnreached());
            } else {
                bar.append("&").append(relativeTempColour).append(config.getBarProgressReached());
            }
        }

        return ChatUtils.colorStd(config.getActionBarFormat()
                .replace("{PROGRESS}", bar.toString())
                .replace("{TEMP}", String.format("&%c%.1f%s", relativeTempColour, temp, playerTemp.getName()))
                .replace("{SEASON}", currentSeason.getName()));
    }


    private void display(@NotNull final Player player) {
        final double temp = this.temperatureManager.getTemperature(player);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getFormatTemperature(player, temp)));
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
