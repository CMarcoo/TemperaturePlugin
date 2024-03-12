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

package top.cmarco.temperatureplugin.task;

import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.manager.PlayerTemperatureManager;
import top.cmarco.temperatureplugin.manager.WorldManager;

public class TemperatureUpdateRunnable implements Runnable {

    private final PlayerTemperatureManager playerTemperatureManager;
    private final WorldManager worldManager;

    public TemperatureUpdateRunnable(@NotNull final PlayerTemperatureManager playerTemperatureManager,
                                     @NotNull final WorldManager worldManager) {
        this.playerTemperatureManager = playerTemperatureManager;
        this.worldManager = worldManager;
    }

    @Override
    public void run() {
        worldManager.getWorlds().stream()
                .flatMap(w -> w.getPlayers().stream())
                .forEach(this.playerTemperatureManager::setTemperature);
    }
}
