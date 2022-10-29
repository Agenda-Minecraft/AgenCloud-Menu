package cat.kiwi.minecraft.metcdMenu.controller

import cat.kiwi.minecraft.metcd.MEtcd
import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcdMenu.MEtcdMenuPlugin
import cat.kiwi.minecraft.metcdMenu.config.Config
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
        val servers = MEtcd.getServerStatusList(serverType)
        val result = arrayListOf<ItemStack>()
        servers.forEach {
            var itemStack: ItemStack
            when (it.gameStatus) {
                GameStatus.WAITING -> {

                    itemStack = ItemStack(Material.GREEN_WOOL)

                    // set name

                }

                GameStatus.LOADING -> {
                    itemStack = ItemStack(Material.YELLOW_WOOL)
                }

                GameStatus.STARTING -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }

                GameStatus.RUNNING -> {
                    itemStack = ItemStack(Material.RED_WOOL)
                }

                GameStatus.ENDING -> {
                    itemStack = ItemStack(Material.ORANGE_WOOL)
                }

                GameStatus.EXITED -> {
                    itemStack = ItemStack(Material.GRAY_WOOL)
                }
            }
            val itemMeta: ItemMeta = itemStack.itemMeta!!
            itemMeta.lore = arrayListOf(
                "§aServer: ${it.uuid}",
                "§aGameType: ${it.gameType}",
                "§aCurrentOnline: ${it.currentOnline}",
                "§aPlayers: ${it.players}",
                "§aTotal: ${it.total}",
                "§aGameStatus: ${it.gameStatus}",
                "§aAddress: ${it.address}",
                "§aPort: ${it.port}",
                "§aVersion: ${it.version}",
                "§aMeta: ${it.meta}"
            )
            itemMeta.setDisplayName("${it.gameStatus}")
            val serverConnectionName = it.address.replace(".", "-") + "-" + it.port
            itemStack.itemMeta = itemMeta
            itemStack = itemStack.setMEtcdCondition(it.gameStatus, serverConnectionName)
            result.add(itemStack)
        }
        // padding to 10
        for (i in 1..Config.paddingTo - result.size) {
            var itemStack = ItemStack(Material.GRAY_WOOL)
            itemStack = itemStack.setMEtcdCondition(GameStatus.EXITED)

            result.add(itemStack)
        }

        return result
    }
}