package cat.kiwi.minecraft.metcdMenu.config

import cat.kiwi.minecraft.metcdMenu.MEtcdMenuPlugin
import cat.kiwi.minecraft.metcdMenu.model.ServerItemConfig
import org.bukkit.Material

object Config {
    var paddingTo = 10
    lateinit var title: String
    lateinit var switchServerCommand: String
    lateinit var templateList: List<Map<String, Any>>
    lateinit var displayReplacement: List<Map<String, Any>>
    lateinit var waitingMaterial: Material
    lateinit var loadingMaterial: Material
    lateinit var startingMaterial: Material
    lateinit var runningMaterial: Material
    lateinit var endingMaterial: Material
    lateinit var exitedMaterial: Material
    lateinit var emptyItemName: String
    lateinit var emptyItemLore: List<String>
    val templateMap: MutableMap<String, ServerItemConfig> = HashMap()
    fun readConfig() {
        val instance = MEtcdMenuPlugin.instance
        paddingTo = instance.config.getInt("menu.paddingTo")
        title = instance.config.getString("menu.title") ?: "MEtcd"
        switchServerCommand = instance.config.getString("menu.switchServerCommand") ?: "atp %server%"
        templateList = instance.config.getMapList("template") as List<Map<String, Any>>
        displayReplacement = instance.config.getMapList("displayReplacement") as List<Map<String, Any>>
        waitingMaterial = (instance.config.getString("material.waiting") ?: "GREEN_WOOL").material
        loadingMaterial = (instance.config.getString("material.loading") ?: "YELLOW_WOOL").material
        startingMaterial = (instance.config.getString("material.starting") ?: "RED_WOOL").material
        runningMaterial = (instance.config.getString("material.running") ?: "RED_WOOL").material
        endingMaterial = (instance.config.getString("material.ending") ?: "ORANGE_WOOL").material
        exitedMaterial = (instance.config.getString("material.exited") ?: "GRAY_WOOL").material
        emptyItemName = instance.config.getString("emptyTemplate.itemName") ?: "Server not online"
        emptyItemLore = instance.config.getStringList("emptyTemplate.itemLore")
        templateList.forEach {
            templateMap[it["serverType"] as String] =
                ServerItemConfig(it["serverType"] as String, it["itemName"] as String, it["itemLore"] as List<String>)
        }
    }
}

val String.material: Material get() = Material.getMaterial(this)!!
