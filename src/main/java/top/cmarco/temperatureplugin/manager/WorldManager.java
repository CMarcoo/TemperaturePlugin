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

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.api.WorldSeasonChangeEvent;
import top.cmarco.temperatureplugin.config.StandardConfig;
import top.cmarco.temperatureplugin.season.Season;

import java.util.*;

public final class WorldManager {

    private final Plugin plugin;
    private final HashSet<World> worlds = new HashSet<>(0x03);
    private final HashMap<UUID, Season> worldsSeasons = new HashMap<>(0x03);
    private long lastChecked = 0L;

    public WorldManager(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        StandardConfig stdConf = plugin.getStandardConfig();
        Server server = plugin.getServer();
        for (final String worldName : stdConf.getWorlds()) {
            worlds.add(Objects.requireNonNull(server.getWorld(worldName), "Illegal world name!"));
        }
    }

    public Set<World> getWorlds() {
        return worlds;
    }

    @NotNull
    public Season getWorldSeason(@NotNull final World world) {
        return this.worldsSeasons.get(world.getUID());
    }

    public void setWorldSeason(@NotNull final World world) {
        Season previous = getWorldSeason(world);
        Season $new = Season.getCurrentSeason(world);
        if (previous != null && previous != $new) {
            final WorldSeasonChangeEvent event = new WorldSeasonChangeEvent(world, previous, $new);
            plugin.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            $new = event.getUpcomingSeason();
        }

        this.worldsSeasons.put(world.getUID(), $new);
        this.lastChecked = System.currentTimeMillis();
    }
}
