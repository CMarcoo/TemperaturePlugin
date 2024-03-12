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

package top.cmarco.temperatureplugin.humidity;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.data.Tuple;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;
import top.cmarco.temperatureplugin.manager.WorldManager;
import top.cmarco.temperatureplugin.math.MathUtils;

import java.util.*;

public final class HumidityManager {

    public static final long DAY = 1000L, NIGHT = 13000L, NOON = 6000L, MIDNIGHT = 18000L, SUNRISE = 23000L, SUNSET = 12000L;

    private final HashMap<Biome, Double> biomesHumidity = new HashMap<>();
    private final TemperaturePlugin plugin;
    private final WorldManager worldManager;

    private final ArrayList<BukkitTask> bukkitTasks = new ArrayList<>();

    public HumidityManager(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        this.worldManager = plugin.getWorldManager();
    }


    public void startTask() {
        if (!this.bukkitTasks.isEmpty()) {
            stopTask();
        }

        final BukkitScheduler scheduler = plugin.getServer().getScheduler();

        for (final World world : this.worldManager.getWorlds()) {
            final long timeTicks = world.getTime();
            final double scale = MathUtils.CACHED_HUMIDITY_SCALE[(int) timeTicks];

            this.bukkitTasks.add(scheduler.runTaskTimer(plugin, () -> {

                        for (final Map.Entry<Biome, Tuple<Double, Double>> entry : BiomeHumidity.BIOME_HUMIDITY.entrySet()) {
                            final Tuple<Double, Double> value = entry.getValue();
                            final double humDeltaBiome = value.getB() - value.getA();
                            final double hum = (humDeltaBiome * scale) + value.getA() + Math.sin(PlayerTemperatureManager.RAND.nextDouble(0, Math.PI));
                            this.biomesHumidity.put(entry.getKey(), hum);
                        }

                    }, 1L, 20L * 60L)
            );
        }
    }

    public void stopTask() {
        this.bukkitTasks.stream().filter(Objects::nonNull).forEach(BukkitTask::cancel);
        this.bukkitTasks.clear();
    }

    public double getHumidity(@NotNull final Biome biome) {
        return this.biomesHumidity.get(biome);
    }
}
