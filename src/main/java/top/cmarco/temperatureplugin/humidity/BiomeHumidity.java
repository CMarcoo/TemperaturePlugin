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

import org.bukkit.block.Biome;
import top.cmarco.temperatureplugin.data.Tuple;
import top.cmarco.temperatureplugin.data.impl.DoublesTuple;

import java.util.EnumMap;

public final class BiomeHumidity {

    public static final EnumMap<Biome, Tuple<Double, Double>> BIOME_HUMIDITY = new EnumMap<>(Biome.class);

    public static final Tuple<Double, Double> STANDARD = DoublesTuple.getBuilder()
            .setFirst(40.0d)
            .setSecond(50.0d)
            .build();

    public static final Tuple<Double, Double> HUMID = DoublesTuple.getBuilder()
            .setFirst(70d)
            .setSecond(90d)
            .build();

    public static final Tuple<Double, Double> VERY_HUMID = DoublesTuple.getBuilder()
            .setFirst(80d)
            .setSecond(99.999d)
            .build();

    public static final Tuple<Double, Double> DRY = DoublesTuple.getBuilder()
            .setFirst(20d)
            .setSecond(42.50d)
            .build();

    public static final Tuple<Double, Double> VERY_DRY = DoublesTuple.getBuilder()
            .setFirst(5.0d)
            .setSecond(20.0d)
            .build();

    public static final Tuple<Double, Double> SANITIZED = DoublesTuple.getBuilder()
            .setFirst(0.00d)
            .setSecond(25e-3)
            .build();
    
    static {
        BIOME_HUMIDITY.put(Biome.OCEAN, HUMID);
        BIOME_HUMIDITY.put(Biome.PLAINS, HUMID);
        BIOME_HUMIDITY.put(Biome.DESERT, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.WINDSWEPT_HILLS, DRY);
        BIOME_HUMIDITY.put(Biome.FOREST, HUMID);
        BIOME_HUMIDITY.put(Biome.TAIGA, HUMID);
        BIOME_HUMIDITY.put(Biome.SWAMP, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.MANGROVE_SWAMP, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.RIVER, STANDARD);
        BIOME_HUMIDITY.put(Biome.NETHER_WASTES, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.THE_END, STANDARD);
        BIOME_HUMIDITY.put(Biome.FROZEN_OCEAN, HUMID);
        BIOME_HUMIDITY.put(Biome.FROZEN_RIVER, DRY);
        BIOME_HUMIDITY.put(Biome.SNOWY_PLAINS, DRY);
        BIOME_HUMIDITY.put(Biome.MUSHROOM_FIELDS, STANDARD);
        BIOME_HUMIDITY.put(Biome.BEACH, HUMID);
        BIOME_HUMIDITY.put(Biome.JUNGLE, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.SPARSE_JUNGLE, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.DEEP_OCEAN, HUMID);
        BIOME_HUMIDITY.put(Biome.STONY_SHORE, STANDARD);
        BIOME_HUMIDITY.put(Biome.SNOWY_BEACH, DRY);
        BIOME_HUMIDITY.put(Biome.BIRCH_FOREST, HUMID);
        BIOME_HUMIDITY.put(Biome.DARK_FOREST, HUMID);
        BIOME_HUMIDITY.put(Biome.SNOWY_TAIGA, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.OLD_GROWTH_PINE_TAIGA, HUMID);
        BIOME_HUMIDITY.put(Biome.WINDSWEPT_FOREST, STANDARD);
        BIOME_HUMIDITY.put(Biome.SAVANNA, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.SAVANNA_PLATEAU, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.BADLANDS, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.WOODED_BADLANDS, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.SMALL_END_ISLANDS, STANDARD);
        BIOME_HUMIDITY.put(Biome.END_MIDLANDS, STANDARD);
        BIOME_HUMIDITY.put(Biome.END_HIGHLANDS, STANDARD);
        BIOME_HUMIDITY.put(Biome.END_BARRENS, STANDARD);
        BIOME_HUMIDITY.put(Biome.WARM_OCEAN, HUMID);
        BIOME_HUMIDITY.put(Biome.LUKEWARM_OCEAN, HUMID);
        BIOME_HUMIDITY.put(Biome.COLD_OCEAN, DRY);
        BIOME_HUMIDITY.put(Biome.DEEP_LUKEWARM_OCEAN, STANDARD);
        BIOME_HUMIDITY.put(Biome.DEEP_COLD_OCEAN, DRY);
        BIOME_HUMIDITY.put(Biome.DEEP_FROZEN_OCEAN, DRY);
        BIOME_HUMIDITY.put(Biome.THE_VOID, STANDARD);
        BIOME_HUMIDITY.put(Biome.SUNFLOWER_PLAINS, STANDARD);
        BIOME_HUMIDITY.put(Biome.WINDSWEPT_GRAVELLY_HILLS, DRY);
        BIOME_HUMIDITY.put(Biome.FLOWER_FOREST, STANDARD);
        BIOME_HUMIDITY.put(Biome.ICE_SPIKES, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.OLD_GROWTH_BIRCH_FOREST, HUMID);
        BIOME_HUMIDITY.put(Biome.OLD_GROWTH_SPRUCE_TAIGA, HUMID);
        BIOME_HUMIDITY.put(Biome.WINDSWEPT_SAVANNA, HUMID);
        BIOME_HUMIDITY.put(Biome.ERODED_BADLANDS, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.BAMBOO_JUNGLE, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.SOUL_SAND_VALLEY, HUMID);
        BIOME_HUMIDITY.put(Biome.CRIMSON_FOREST, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.WARPED_FOREST, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.BASALT_DELTAS, VERY_HUMID);
        BIOME_HUMIDITY.put(Biome.DRIPSTONE_CAVES, HUMID);
        BIOME_HUMIDITY.put(Biome.LUSH_CAVES, HUMID);
        BIOME_HUMIDITY.put(Biome.DEEP_DARK, STANDARD);
        BIOME_HUMIDITY.put(Biome.MEADOW, STANDARD);
        BIOME_HUMIDITY.put(Biome.GROVE, DRY);
        BIOME_HUMIDITY.put(Biome.SNOWY_SLOPES, DRY);
        BIOME_HUMIDITY.put(Biome.FROZEN_PEAKS, VERY_DRY);
        BIOME_HUMIDITY.put(Biome.JAGGED_PEAKS, STANDARD);
        BIOME_HUMIDITY.put(Biome.STONY_PEAKS, DRY);
        BIOME_HUMIDITY.put(Biome.CHERRY_GROVE, DRY);
        BIOME_HUMIDITY.put(Biome.CUSTOM, STANDARD);
    }
}
