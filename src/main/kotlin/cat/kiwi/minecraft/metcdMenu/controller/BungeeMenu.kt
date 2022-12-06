package cat.kiwi.minecraft.metcdMenu.controller

import cat.kiwi.minecraft.acloud.AgendaCloud
import cat.kiwi.minecraft.acloud.model.GameStatus
import cat.kiwi.minecraft.metcdMenu.MEtcdMenuPlugin
import cat.kiwi.minecraft.metcdMenu.config.Config
import cat.kiwi.minecraft.metcdMenu.serverName
import cat.kiwi.minecraft.metcdMenu.setMEtcdCondition
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class BungeeMenu {
    fun initMenu(player: Player, serverType: String) {
        val inventory = Bukkit.createInventory(null, 54, Config.title)
        player.openInventory(inventory)
        Bukkit.getScheduler().runTaskAsynchronously(MEtcdMenuPlugin.instance, Runnable {
            getItemStacks(serverType).forEachIndexed { index, itemStack ->
                inventory.setItem(index, itemStack)
            }
        })
    }

    private fun getItemStacks(serverType: String): List<ItemStack> {
        val servers = AgendaCloud.getServerStatusList(serverType)
        val result = arrayListOf<ItemStack>()
        servers.forEach {
            var itemStack: ItemStack
            when (it.gameStatus) {
                GameStatus.WAITING -> {
                    itemStack = ItemStack(Config.waitingMaterial)
                }

                GameStatus.LOADING -> {
                    itemStack = ItemStack(Config.loadingMaterial)
                }

                GameStatus.STARTING -> {
                    itemStack = ItemStack(Config.startingMaterial)
                }

                GameStatus.RUNNING -> {
                    itemStack = ItemStack(Config.runningMaterial)
                }

                GameStatus.ENDING -> {
                    itemStack = ItemStack(Config.endingMaterial)
                }

                GameStatus.EXITED -> {
                    itemStack = ItemStack(Config.exitedMaterial)
                }
            }
            val itemMeta: ItemMeta = itemStack.itemMeta!!
            itemMeta.lore = it.renderedLore
            itemMeta.setDisplayName(it.renderedName)
            val serverConnectionName = it.address.replace(".", "-") + "-" + it.port
            itemStack.itemMeta = itemMeta
            itemStack = itemStack.setMEtcdCondition(it.gameStatus, serverConnectionName)
            result.add(itemStack)
        }
        // padding empty
        for (i in 1..Config.paddingTo - result.size) {
            var itemStack = ItemStack(Config.exitedMaterial)
            val itemMeta = itemStack.itemMeta
            itemMeta!!.setDisplayName(Config.emptyItemName)
            itemMeta!!.lore = Config.emptyItemLore
            itemStack.itemMeta = itemMeta

            itemStack = itemStack.setMEtcdCondition(GameStatus.EXITED)
            result.add(itemStack)
        }

        return result
    }
}