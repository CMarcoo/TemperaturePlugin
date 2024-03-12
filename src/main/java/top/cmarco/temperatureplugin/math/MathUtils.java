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

package top.cmarco.temperatureplugin.math;

public class MathUtils {
    public static final double G_FACTOR_1 = 116e3 / ((47e2) * Math.sqrt(2*Math.PI));
    public static final double G_FACTOR_2 = 2*47e2*47e2;

    //public static final double ATAN_HEIGHT = 1.865E+1d;
    //public static final double ATAN_SPEED = 5.605E-2d;
    //public static final double ATAN_HOR_MOVE = 5.750E-1d;
    //public static final double HALF_PI = Math.PI / 2.0d;
    //public static final double ATAN_VERT_MOVE = 19.80d;
    public static final double NOON_TICKS = 65E+2d;

    public static final double[] CACHED_HUMIDITY_SCALE = new double[24_001];
    public static final double[] CACHED_HEIGHT_MAP_SCALE = new double[79_864];

    public static final double[] CACHED_HEIGHT_POLYNOMIAL_COEFFS = {
            +3.981796231733E-6d,
            -0.00322799d,
            0.695913d
    };

    public static double getScaleAt(final long ticks) {
        return (MathUtils.G_FACTOR_1 * Math.exp(-((ticks-NOON_TICKS)*(ticks-NOON_TICKS) / (MathUtils.G_FACTOR_2))) + 1.5E-1d)/ 10.0d;
    }

    static {
        for (int i = 0; i <= 24000; i++) {
            final double v = getScaleAt(i);
            CACHED_HUMIDITY_SCALE[i] = v;
        }

        int j = 0;
        for (double i = -64.0d; i < +330.0d; i+=5E-3d, j+=1) {
            final double ax2 = CACHED_HEIGHT_POLYNOMIAL_COEFFS[0] * i * i;
            final double bx = CACHED_HEIGHT_POLYNOMIAL_COEFFS[1] * i;
            final double c = CACHED_HEIGHT_POLYNOMIAL_COEFFS[2];
            final double f = ax2 + bx + c;
            CACHED_HEIGHT_MAP_SCALE[j] = f;
        }
    }


}
