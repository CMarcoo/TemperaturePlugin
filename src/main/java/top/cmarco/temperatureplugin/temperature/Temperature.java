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

import org.jetbrains.annotations.NotNull;

public enum Temperature {

    CELSIUS(v -> v, v->v, "°C"),
    FAHRENHEIT(v -> (v * 1.8d) + 32d, v -> (v-32d)/1.8d, "°F"),
    KELVIN(v -> v + 273.15d, v->v-273.15d, "°K"),
    RANKINE(v -> (v + 273.15d) * 1.8d, v->(v-491.67)/1.8d, "°R"),
    REAUMUR(v -> v * 0.8d, v->v/.8d, "°Re"),
    NEWTON(v -> v * 0.33d, v->(v-32)*5d/9d, "°N"),
    DELISLE(v -> (100 - v) * 1.5d, v->(100-v)/1.5d , "°De"),
    ROMER(v -> 7.5d + (v * 0.525d), v->(v-7.5d)/0.525d, "°Ro");

    private final TemperatureConverter celsiusToUnit;
    private final TemperatureConverter unitToCelsius;
    private final String name;

    Temperature(@NotNull final TemperatureConverter celsiusToUnit, TemperatureConverter unitToCelsius, final String name) {
        this.celsiusToUnit = celsiusToUnit;
        this.unitToCelsius = unitToCelsius;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double convertToUnit(double value) {
        return this.celsiusToUnit.convert(value);
    }

    public double convertUnitToCelsius(double value) {
        return this.unitToCelsius.convert(value);
    }
}
