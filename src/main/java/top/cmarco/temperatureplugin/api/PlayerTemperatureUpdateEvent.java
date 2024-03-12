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

package top.cmarco.temperatureplugin.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.temperature.Temperature;

public class PlayerTemperatureUpdateEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Temperature playerTemperatureUnit;
    private double temperatureAsUnit;
    private boolean cancelled = false;

    public PlayerTemperatureUpdateEvent(@NotNull final Player who,
                                        @NotNull final Temperature playerTemperatureUnit,
                                        final double temperatureAsUnit) {
        super(who);
        this.playerTemperatureUnit = playerTemperatureUnit;
        this.temperatureAsUnit = temperatureAsUnit;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public double getTemperatureAsUnit() {
        return temperatureAsUnit;
    }

    @NotNull
    public Temperature getPlayerTemperatureUnit() {
        return playerTemperatureUnit;
    }

    public void setTemperatureAsUnit(double temperatureAsUnit) {
        this.temperatureAsUnit = temperatureAsUnit;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
