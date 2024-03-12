package top.cmarco.temperatureplugin.manager.data;

import org.bukkit.block.Biome;
import top.cmarco.temperatureplugin.data.Tuple;
import top.cmarco.temperatureplugin.data.impl.DoublesTuple;

import java.util.EnumMap;

public class BiomeTemperatures {

    public static final EnumMap<Biome, Tuple<Double, Double>> BIOME_TEMPS = new EnumMap<>(Biome.class);

    public static final Tuple<Double, Double> STANDARD_TEMPS = DoublesTuple.getBuilder()
            .setFirst(10.0d)
            .setSecond(35.0d)
            .build();

    public static final Tuple<Double, Double> COLD_TEMPS = DoublesTuple.getBuilder()
            .setFirst(-20.0)
            .setSecond(10.00d)
            .build();

    public static final Tuple<Double, Double> WARM_TEMPS = DoublesTuple.getBuilder()
            .setFirst(25.0d)
            .setSecond(46.5d)
            .build();

    public static final Tuple<Double, Double> OCEAN_TEMPS = DoublesTuple.getBuilder()
            .setFirst(0.5d)
            .setSecond(17.5d)
            .build();

    public static final Tuple<Double, Double> COLD_OCEAN_TEMPS = DoublesTuple.getBuilder()
            .setFirst(-2.00)
            .setSecond(8.00d)
            .build();

    public static final Tuple<Double, Double> NETHER_TEMPS = DoublesTuple.getBuilder()
            .setFirst(30.0d)
            .setSecond(50.00d)
            .build();

    static {
        BIOME_TEMPS.put(Biome.OCEAN, OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.PLAINS, STANDARD_TEMPS);
        BIOME_TEMPS.put(Biome.DESERT, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.WINDSWEPT_HILLS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.FOREST, STANDARD_TEMPS);
        BIOME_TEMPS.put(Biome.TAIGA, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.SWAMP, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.MANGROVE_SWAMP, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.RIVER, STANDARD_TEMPS);
        BIOME_TEMPS.put(Biome.NETHER_WASTES, NETHER_TEMPS);
        BIOME_TEMPS.put(Biome.THE_END, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.FROZEN_OCEAN, COLD_OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.FROZEN_RIVER, COLD_OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.SNOWY_PLAINS, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.MUSHROOM_FIELDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.BEACH, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.JUNGLE, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.SPARSE_JUNGLE, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.DEEP_OCEAN, OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.STONY_SHORE, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.SNOWY_BEACH, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.BIRCH_FOREST, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.DARK_FOREST, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.SNOWY_TAIGA, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.OLD_GROWTH_PINE_TAIGA, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.WINDSWEPT_FOREST, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.SAVANNA, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.SAVANNA_PLATEAU, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.BADLANDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.WOODED_BADLANDS, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.SMALL_END_ISLANDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.END_MIDLANDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.END_HIGHLANDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.END_BARRENS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.WARM_OCEAN, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.LUKEWARM_OCEAN, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.COLD_OCEAN, COLD_OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.DEEP_LUKEWARM_OCEAN, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.DEEP_COLD_OCEAN, COLD_OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.DEEP_FROZEN_OCEAN, COLD_OCEAN_TEMPS);
        BIOME_TEMPS.put(Biome.THE_VOID, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.SUNFLOWER_PLAINS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.WINDSWEPT_GRAVELLY_HILLS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.FLOWER_FOREST, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.ICE_SPIKES, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.OLD_GROWTH_BIRCH_FOREST, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.OLD_GROWTH_SPRUCE_TAIGA, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.WINDSWEPT_SAVANNA, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.ERODED_BADLANDS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.BAMBOO_JUNGLE, WARM_TEMPS);
        BIOME_TEMPS.put(Biome.SOUL_SAND_VALLEY, STANDARD_TEMPS);
        BIOME_TEMPS.put(Biome.CRIMSON_FOREST, NETHER_TEMPS);
        BIOME_TEMPS.put(Biome.WARPED_FOREST, NETHER_TEMPS);
        BIOME_TEMPS.put(Biome.BASALT_DELTAS, NETHER_TEMPS);
        BIOME_TEMPS.put(Biome.DRIPSTONE_CAVES, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.LUSH_CAVES, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.DEEP_DARK, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.MEADOW, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.GROVE, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.SNOWY_SLOPES, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.FROZEN_PEAKS, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.JAGGED_PEAKS, COLD_TEMPS);
        BIOME_TEMPS.put(Biome.STONY_PEAKS, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.CHERRY_GROVE, STANDARD_TEMPS);  
        BIOME_TEMPS.put(Biome.CUSTOM, STANDARD_TEMPS);  
    }
    
}
