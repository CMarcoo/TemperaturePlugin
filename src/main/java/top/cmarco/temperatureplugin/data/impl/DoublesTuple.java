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

package top.cmarco.temperatureplugin.data.impl;

import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.data.Tuple;

public final class DoublesTuple extends Tuple<Double, Double> {
    public DoublesTuple(@NotNull final Double a, @NotNull final Double b) {
        super(a, b);
    }

    public static Builder<Double, Double> getBuilder() {
        return new Builder<>();
    }
}
