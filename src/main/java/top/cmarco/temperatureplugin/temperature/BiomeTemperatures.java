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

package top.cmarco.temperatureplugin.temperature;

import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cmarco.temperatureplugin.config.StandardConfig;
import top.cmarco.temperatureplugin.data.Tuple;
import top.cmarco.temperatureplugin.data.impl.DoublesTuple;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class BiomeTemperatures {

    public BiomeTemperatures(@NotNull final StandardConfig standardConfig) {
        this.config = standardConfig;
    }

    private final StandardConfig config;

    private final Map<TemperatureType, Tuple<Double, Double>> loadedTemperatureTypes = new EnumMap<>(TemperatureType.class);
    private final Map<Biome, Tuple<Double, Double>> biomeTemps = new EnumMap<>(Biome.class);

    public void initializeTemps() {
        final Tuple<Double, Double> STANDARD_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getStandardTempsMinimum())
                .setSecond(config.getStandardTempsMaximum())
                .build();

        final Tuple<Double, Double> COLD_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getColdTempsMinimum())
                .setSecond(config.getColdTempsMaximum())
                .build();

        final Tuple<Double, Double> WARM_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getWarmTempsMinimum())
                .setSecond(config.getWarmTempsMaximum())
                .build();

        final Tuple<Double, Double> OCEAN_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getOceanTempsMinimum())
                .setSecond(config.getOceanTempsMaximum())
                .build();

        final Tuple<Double, Double> COLD_OCEAN_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getColdOceanTempsMinimum())
                .setSecond(config.getColdOceanTempsMaximum())
                .build();

        final Tuple<Double, Double> NETHER_TEMPS = DoublesTuple.getBuilder()
                .setFirst(config.getNetherTempsMinimum())
                .setSecond(config.getNetherTempsMaximum())
                .build();

        loadedTemperatureTypes.put(TemperatureType.STANDARD_TEMPS, STANDARD_TEMPS);
        loadedTemperatureTypes.put(TemperatureType.COLD_TEMPS, COLD_TEMPS);
        loadedTemperatureTypes.put(TemperatureType.WARM_TEMPS, WARM_TEMPS);
        loadedTemperatureTypes.put(TemperatureType.OCEAN_TEMPS, OCEAN_TEMPS);
        loadedTemperatureTypes.put(TemperatureType.COLD_OCEAN_TEMPS, COLD_OCEAN_TEMPS);
        loadedTemperatureTypes.put(TemperatureType.NETHER_TEMPS, NETHER_TEMPS);

        this.assignTemperatures();
    }

    private void assignTemperatures() {
        for (final Biome biome : Biome.values()) {
            biomeTemps.put(biome, Objects.requireNonNull(loadedTemperatureTypes.get(determineTemperature(biome)), "Unknown TemperatureType"));
        }
    }

    @NotNull
    private TemperatureType determineTemperature(@NotNull final Biome biome) {
        final String biomeName = biome.name().toLowerCase();

        if (biomeName.contains("ocean")) {
            return TemperatureType.OCEAN_TEMPS;
        } else if (biomeName.contains("plains") || biomeName.contains("river") || biomeName.contains("shore") || biomeName.contains("beach") || biomeName.contains("plateau") || biomeName.contains("islands") || biomeName.contains("forest") || biomeName.contains("caves") || biomeName.contains("meadow") || biomeName.contains("grove") || biomeName.contains("peaks")) {
            return TemperatureType.STANDARD_TEMPS;
        } else if (biomeName.contains("desert") || biomeName.contains("jungle") || biomeName.contains("savanna") || biomeName.contains("badlands") || biomeName.contains("sand") || biomeName.contains("barrens") || biomeName.contains("wasteland") || biomeName.contains("valley") || biomeName.contains("dark") || biomeName.contains("stony")) {
            return TemperatureType.WARM_TEMPS;
        } else if (biomeName.contains("taiga") || biomeName.contains("snow") || biomeName.contains("frozen") || biomeName.contains("ice") || biomeName.contains("peak")) {
            return TemperatureType.COLD_TEMPS;
        } else if (biomeName.contains("nether")) {
            return TemperatureType.NETHER_TEMPS;
        } else {
            return TemperatureType.STANDARD_TEMPS; // Default to standard temperatures
        }
    }

    @Nullable
    public Tuple<Double, Double> getBiomeLoadedTemp(@NotNull final Biome biome) {
        return this.biomeTemps.get(biome);
    }

}
