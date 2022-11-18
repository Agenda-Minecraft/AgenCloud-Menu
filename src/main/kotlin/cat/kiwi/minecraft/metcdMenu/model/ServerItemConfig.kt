package cat.kiwi.minecraft.metcdMenu.model

data class ServerItemConfig(
    val serverType: String,
    val itemName: String,
    val itemLore: List<String>
)