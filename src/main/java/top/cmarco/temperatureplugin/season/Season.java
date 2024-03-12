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

package top.cmarco.temperatureplugin.season;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public enum Season {

    WINTER("&bWinter"),
    SPRING("&6Spring"),
    SUMMER("&eSummer"),
    AUTUMN("&3Autumn");

    private final String name;


    Season(final @NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public static Season getCurrentSeason(long totalTicks) {
        // Calculate the number of months elapsed
        int monthsElapsed = (int) (totalTicks / (24000L*31L));

        // Determine the current season based on the remainder
        // This should not happen, but handle it just in case
        return switch (monthsElapsed & 0x03) {
            case 0 -> Season.SUMMER;
            case 1 -> Season.AUTUMN;
            case 2 -> Season.WINTER;
            case 3 -> Season.SPRING;
            default -> throw new IllegalStateException("Invalid season calculation");
        };
    }

    @NotNull
    public static Season getCurrentSeason(@NotNull final World world) {
        return getCurrentSeason(world.getGameTime());
    }

}
