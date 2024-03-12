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

package top.cmarco.temperatureplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.cmarco.temperatureplugin.TemperaturePlugin;
import top.cmarco.temperatureplugin.manager.NamespaceManager;
import top.cmarco.temperatureplugin.utilities.ChatUtils;

public final class TemperatureCommand implements CommandExecutor {

    private final TemperaturePlugin plugin;

    public TemperatureCommand(@NotNull final TemperaturePlugin plugin) {
        this.plugin = plugin;
    }

    private static void help(@NotNull Player player) {
        player.sendMessage(ChatUtils.colorStd("&7[&cTemperature&7]&f: Help Page"));
        player.sendMessage(ChatUtils.colorStd("&7/&etemperature help"));
        player.sendMessage(ChatUtils.colorStd("&7/&etemperature toggle"));
        player.sendMessage(ChatUtils.colorStd("&7/&etemperature reload"));
    }

    private static void noPerm(@NotNull Player player, @NotNull String perm) {
        player.sendMessage(ChatUtils.colorStd("&7[&cTemperature&7]&f: Missing Permission &c" + perm));
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender,
                             @NotNull final Command command,
                             @NotNull final String label,
                             @NotNull final String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatUtils.colorStd("&cThis command cannot be used from console."));
            return true;
        }

        if (args.length == 0) {
            help(player);
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {
                help(player);
                return true;
            } else if (args[0].equalsIgnoreCase("toggle")) {

                if (!player.hasPermission("temperature.toggle")) {
                    noPerm(player, "temperature.toggle");
                    return true;
                }

                NamespaceManager namespaceManager = plugin.getNamespaceManager();
                boolean isEnabled = namespaceManager.isEnableTemperature(player);
                if (isEnabled) {
                    player.sendMessage(ChatUtils.colorStd("&7[&cTemperature&7]&f: &cDisabled Temperature"));
                    namespaceManager.disableTemperature(player);
                } else {
                    player.sendMessage(ChatUtils.colorStd("&7[&cTemperature&7]&f: &aEnabled Temperature"));
                    namespaceManager.enableTemperature(player);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {

                if (!player.hasPermission("temperature.reload")) {
                    noPerm(player, "temperature.reload");
                    return true;
                }

                final long ms = System.currentTimeMillis();
                plugin.reload();
                player.sendMessage(ChatUtils.colorStd("&7[&cTemperature&7]&f: &aReloaded in " + (System.currentTimeMillis()-ms) + "ms"));
            } else {
                player.sendMessage(ChatUtils.colorStd("&cInvalid command syntax."));
            }

        } else {
            player.sendMessage(ChatUtils.colorStd("&cInvalid command syntax."));
        }

        return true;
    }
}
