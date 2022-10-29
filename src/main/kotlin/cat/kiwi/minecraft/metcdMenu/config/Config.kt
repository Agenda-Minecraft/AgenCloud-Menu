package cat.kiwi.minecraft.metcdMenu.config

import cat.kiwi.minecraft.metcdMenu.MEtcdMenuPlugin

object Config {
    var paddingTo = 10
    lateinit var title: String
    lateinit var switchServerCommand: String
    fun readConfig() {
        val instance = MEtcdMenuPlugin.instance
        paddingTo = instance.config.getInt("menu.paddingTo")
        title = instance.config.getString("menu.title") ?: "MEtcd"
        switchServerCommand = instance.config.getString("menu.switchServerCommand") ?: "atp %server%"
    }
}