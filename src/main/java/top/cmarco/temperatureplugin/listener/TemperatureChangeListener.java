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

package top.cmarco.temperatureplugin.listener;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.api.PlayerTemperatureUpdateEvent;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.temperature.Temperature;

import java.util.HashMap;
import java.util.UUID;

public class TemperatureChangeListener implements Listener {

    private final TemperaturePlugin plugin;
    private final HashMap<UUID, Long> lastFrozen = new HashMap<>();
    private final HashMap<UUID, Long> lastBurn = new HashMap<>();
    private static final double HOT_THRESHOLD = 40.5d;
    private static final double COLD_THRESHOLD = -0.5d;

    public TemperatureChangeListener(@NotNull TemperaturePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEvent(@NotNull PlayerTemperatureUpdateEvent event) {
        Player player = event.getPlayer();
        NamespaceManager namespaceManager = plugin.getNamespaceManager();

        if (!namespaceManager.isEnableTemperature(player)) {
            return;
        }

        UUID uuid = player.getUniqueId();
        Temperature playerTemperature = event.getPlayerTemperatureUnit();
        double playerTemperatureValue = event.getTemperatureAsUnit();
        double celsius = playerTemperature.convertUnitToCelsius(playerTemperatureValue);
        long now = System.currentTimeMillis();

        if (celsius >= HOT_THRESHOLD) {

            byte fireProtCount = 0x00;

            for (ItemStack armorContent : player.getInventory().getArmorContents()) {
                if (armorContent == null) {
                    continue;
                }

                final ItemMeta itemMeta = armorContent.getItemMeta();

                if (itemMeta == null) {
                    continue;
                }

                if (itemMeta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    fireProtCount++;
                }
            }

            boolean fireProt = player.getActivePotionEffects().stream().anyMatch(p -> p.getType() == PotionEffectType.FIRE_RESISTANCE);
            if (fireProtCount >= 3 || fireProt) {
                return;
            }

            long lastBurnValue = lastBurn.getOrDefault(uuid, now);
            if (now - lastBurnValue >= 1000 || lastBurnValue == now) {
                player.setFireTicks(20);
                lastBurn.put(uuid, now);
            }

        } else if (celsius <= COLD_THRESHOLD) {

            byte leather = 0x00;
            for (ItemStack armorContent : player.getInventory().getArmorContents()) {
                if (armorContent == null) {
                    continue;
                }
                ItemMeta itemMeta = armorContent.getItemMeta();
                if (itemMeta instanceof LeatherArmorMeta) {
                    leather++;
                }
            }

            if (leather >= 3) {
                return;
            }

            long lastFrozenValue = lastFrozen.getOrDefault(uuid, now);
            if (now - lastFrozenValue >= 1000 || lastFrozenValue == now) {
                player.setFreezeTicks(200);
                lastFrozen.put(uuid, now);
            }


        } else {
            // Reset timestamps if the player is within normal temperature range
            lastBurn.remove(uuid);
            lastFrozen.remove(uuid);
        }
    }
}