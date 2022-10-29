package cat.kiwi.minecraft.metcdMenu.listener

import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.utils.Logger
import cat.kiwi.minecraft.metcdMenu.config.Config
import cat.kiwi.minecraft.metcdMenu.MEtcdStatus
import cat.kiwi.minecraft.metcdMenu.isMEtcdItem
import cat.kiwi.minecraft.metcdMenu.serverName
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.Listener

class ClickEvents : Listener {
    @EventHandler
    fun onClickEvent(e: InventoryClickEvent) {
        Logger.debug(e.clickedInventory)
        Logger.debug(e.currentItem)
        val item = e.currentItem ?: return

        if (item.isMEtcdItem) {
            e.isCancelled = true
            val player = e.whoClicked as Player
            when (item.MEtcdStatus) {
                GameStatus.WAITING -> {
                    val serverName = item.serverName
                    val command = Config.switchServerCommand.replace("%server%", serverName)
                    Logger.debug("${player.name} is trying to switch to $serverName")
                    player.performCommand(command)
                }

                GameStatus.LOADING -> {
                    val serverName = item.serverName
                    val command = Config.switchServerCommand.replace("%server%", serverName)
                    Logger.debug("${player.name} is trying to switch to $serverName")
                    player.performCommand(command)
                }

                else -> {

                }
            }



            return
        }
    }
}