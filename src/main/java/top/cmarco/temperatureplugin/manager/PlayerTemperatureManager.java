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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.api.PlayerTemperatureUpdateEvent;
import top.cmarco.temperatureplugin.data.Tuple;
import top.cmarco.temperatureplugin.humidity.HumidityManager;
import top.cmarco.temperatureplugin.math.MathUtils;
import top.cmarco.temperatureplugin.season.Season;
import top.cmarco.temperatureplugin.temperature.BiomeTemperatures;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class PlayerTemperatureManager {

    private final HashMap<UUID, Double> temperatureMap = new HashMap<>();
    private final HumidityManager humidityManager;
    private final PlayerUnitManager playerUnitManager;
    private final TemperaturePlugin plugin;

    public static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    public PlayerTemperatureManager(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
        this.humidityManager = plugin.getHumidityManager();
        this.playerUnitManager = plugin.getPlayerUnitManager();
    }

    public static double mapAltitudeToTemperature(double altitude, final double minHeight,
                                                  final double maxHeight, final double minTemperature,
                                                  final double maxTemperature) {
        altitude = Math.max(minHeight, Math.min(altitude, maxHeight));
        // linear interpolation;
        final double increment = (maxHeight - minHeight) / (MathUtils.CACHED_HEIGHT_MAP_SCALE.length -1);
        final double factor = MathUtils.CACHED_HEIGHT_MAP_SCALE[(int) Math.round( (altitude-minHeight) /  increment)];
        return minTemperature + factor * (maxTemperature-minTemperature);
    }

    private static final double[] COEFFICIENTS = {
            -8.78469475556d, 1.611394110d, 2.33854883889d,
            -0.14611605d, -0.012308094d, -0.0164248277778d,
            2.211732e-3d, 7.2546e-4d, -3.582e-6d };

    private static final Material[] WARM_BLOCKS = {};

    public double computePerceivedTemp(@NotNull final Player player, @NotNull final Block block) {
        double temperature;
        double humidity;
        final String bukkitVersion = Bukkit.getBukkitVersion();
        final boolean versionAbove1_18 = bukkitVersion.contains(".18") || bukkitVersion.contains(".19")
                || bukkitVersion.contains(".20") || bukkitVersion.contains(".21");
        final double maxHeight = block.getWorld().getMaxHeight();
        final double blockY = block.getY();
        final double minHeight = versionAbove1_18 ? -64.0d : 0d;
        final Biome blockBiome = block.getBiome();
        final Tuple<Double, Double> biomeTempTuple = BiomeTemperatures.BIOME_TEMPS.getOrDefault(blockBiome, BiomeTemperatures.STANDARD_TEMPS);
        final Block highestBlock = block.getWorld().getHighestBlockAt(block.getX(), block.getZ());

        final boolean hitBySun = highestBlock.getX() <= block.getX() && highestBlock.getZ() <= block.getZ();
        double maxTemp = biomeTempTuple.getB();

        if (hitBySun) {
            maxTemp += 8.0d;
        }

        temperature = mapAltitudeToTemperature(blockY, minHeight, maxHeight, biomeTempTuple.getA(), maxTemp);
        humidity = this.humidityManager.getHumidity(blockBiome) / 10d;
        final double scale = MathUtils.CACHED_HUMIDITY_SCALE[(int) block.getWorld().getTime()];
        double delta = biomeTempTuple.getB() - biomeTempTuple.getA();

        Season currentWorldSeason = plugin.getWorldManager().getWorldSeason(block.getWorld());
        switch ( currentWorldSeason ) {
            case AUTUMN -> temperature-=5.5d;
            case WINTER -> temperature-=12.5d;
            case SPRING -> temperature+=1.07d;
            case SUMMER -> temperature+=5.05d;
        }

        final Location playerLoc = player.getLocation();
        final World world = playerLoc.getWorld();

        // -ln(x+0.001)+3.2189

        if (world!=null) {
            byte r = 0;
            for (int x = -3; x < +3; x++) {
                for (int z = -3; z < +3; z++) {
                    for (int y = -1; y < +1; y++) {
                        final Location tempLoc = playerLoc.clone().add(x, y, z);
                        final Block tempBlock = world.getBlockAt(tempLoc);
                        final Material type = tempBlock.getType();
                        final boolean isFurnace = type == Material.FURNACE || type == Material.BLAST_FURNACE;
                        final boolean isLava = type == Material.LAVA || type == Material.MAGMA_BLOCK;
                        final boolean isFireplace = type == Material.CAMPFIRE || type == Material.SOUL_CAMPFIRE;
                        final boolean isIce = type == Material.ICE || type ==Material.PACKED_ICE || type == Material.BLUE_ICE || type == Material.FROSTED_ICE;
                        final boolean isSnow = type == Material.SNOW_BLOCK || type == Material.POWDER_SNOW || type == Material.SNOW;

                        if (!isFurnace && !isLava && !isFireplace && !isIce && !isSnow) {
                            continue;
                        }

                        if (++r > 10) {
                            break;
                        }

                        final double sqdDist = tempLoc.distanceSquared(playerLoc);

                        if (isFurnace) {
                            Furnace furnace = (Furnace) tempBlock.getState();
                            if (furnace.getBurnTime() > (short) 0x00) {
                                temperature += -Math.log(sqdDist+1E-3d)+3.218905d;
                            }
                        } else if (isLava) {
                            temperature += -Math.log(sqdDist+1E-5d)+3.218905d;
                        } else if (isFireplace) {
                            temperature += -Math.log(sqdDist+1.7e-1d)+3.21893d;
                        } else if (isIce) {
                            temperature -= -Math.log(sqdDist+1.5E-4d)+3.21891d;
                        } else if (isSnow) {
                            temperature -= -Math.log(sqdDist+1.5e-1d)+3.21895d;
                        }

                    }
                }
            }
        }

        final double f1 = COEFFICIENTS[0];
        final double f2 = COEFFICIENTS[1] * temperature;
        final double f3 = COEFFICIENTS[2] * humidity;
        final double f4 = COEFFICIENTS[3] * temperature * humidity;
        final double f5 = COEFFICIENTS[4] * temperature * temperature;
        final double f6 = COEFFICIENTS[5] * humidity * humidity;
        final double f7 = COEFFICIENTS[6] * temperature * temperature * humidity;
        final double f8 = COEFFICIENTS[7] * humidity * humidity * temperature;
        final double f9 = COEFFICIENTS[8] * temperature * temperature * humidity * humidity;


        return this.playerUnitManager.getPlayerTemperature(player).convertToUnit(f1 + f2 + f3 + f4 + f5 + f6 + f7 + f8 + f9) + (delta*scale/6.282d);
    }

    public double getTemperature(@NotNull final UUID uuid) {
        return this.temperatureMap.get(uuid);
    }

    public double getTemperature(@NotNull final Player player) {
        return this.temperatureMap.getOrDefault(player.getUniqueId(), Double.MIN_VALUE);
    }

    public void setTemperature(@NotNull final Player player) {
        if (!player.isOnline()) {
            return;
        }

        final Block eyeBlock = player.getEyeLocation().getBlock();
        final double result = computePerceivedTemp(player, eyeBlock);
        PlayerTemperatureUpdateEvent event = new PlayerTemperatureUpdateEvent(player, playerUnitManager.getPlayerTemperature(player), result);
        this.plugin.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.temperatureMap.put(player.getUniqueId(), event.getTemperatureAsUnit());
    }

    @NotNull
    public PlayerUnitManager getPlayerUnitManager() {
        return playerUnitManager;
    }

    public void clearAll() {
        this.temperatureMap.clear();
    }
}
