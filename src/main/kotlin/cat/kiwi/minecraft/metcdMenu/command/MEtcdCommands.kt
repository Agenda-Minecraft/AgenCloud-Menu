package cat.kiwi.minecraft.metcdMenu

import cat.kiwi.minecraft.metcdMenu.controller.BungeeMenu
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MEtcdMenuCommands: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return true
        }
        when (args[0].lowercase()) {
            "opengui" -> {
                if (!sender.hasPermission("metcd.command.opengui")) {
                    sender.sendMessage("§cYou don't have permission to use this command.")
                    return false
                }
                if (args.size == 2) {
                    BungeeMenu().initMenu(sender as Player, args[1])
                }
                return true
            }

            else -> {
            }
        }
        return true
    }
}