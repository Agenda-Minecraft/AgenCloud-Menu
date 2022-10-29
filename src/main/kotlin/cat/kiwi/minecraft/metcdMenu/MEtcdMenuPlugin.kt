package cat.kiwi.minecraft.metcdMenu

import cat.kiwi.minecraft.metcdMenu.config.Config
import cat.kiwi.minecraft.metcdMenu.listener.ClickEvents
import org.bukkit.plugin.java.JavaPlugin

class MEtcdMenuPlugin:JavaPlugin() {
    companion object {
        lateinit var instance: MEtcdMenuPlugin
    }
    override fun onEnable() {
        instance = this
        try {
            Config.readConfig()
        } catch (e: Exception) {
            e.printStackTrace()
            onDisable()
        }
        server.pluginManager.registerEvents(ClickEvents(), this)

        getCommand("metcdmenu")?.setExecutor(MEtcdMenuCommands())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}