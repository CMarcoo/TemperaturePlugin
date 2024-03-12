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

package top.cmarco.temperatureplugin.data;

import org.jetbrains.annotations.NotNull;

public class Tuple<K, T> {

    public static final class Builder<K, T> {
        private K k = null;
        private T t = null;

        public Builder<K, T> setFirst(@NotNull final K k) {
            this.k = k;
            return this;
        }

        public Builder<K, T> setSecond(@NotNull final T t) {
            this.t = t;
            return this;
        }

        public Tuple<K, T> build() {
            if (k == null || t == null) {
                return null;
            }

            return new Tuple<>(k, t);
        }
    }

    private final K a;
    private final T b;

    public Tuple(@NotNull final K a, @NotNull final T b) {
        this.a = a;
        this.b = b;
    }

    @NotNull
    public K getA() {
        return a;
    }

    @NotNull
    public T getB() {
        return b;
    }
}
