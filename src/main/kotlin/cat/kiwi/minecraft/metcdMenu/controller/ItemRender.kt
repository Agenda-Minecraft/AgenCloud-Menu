package cat.kiwi.minecraft.metcdMenu.controller

import cat.kiwi.minecraft.metcd.model.GameStatus
import cat.kiwi.minecraft.metcd.model.ServerStatus
import cat.kiwi.minecraft.metcdMenu.config.Config
import org.bukkit.inventory.meta.ItemMeta

val ServerStatus.renderedName: String
    get() {
        var template = Config.templateMap[this.gameType]
        if (template == null) {
            template = Config.templateMap["default"]
        }
        return template!!.itemName.replace("%gameType%", this.gameType)
            .replace("%displayName%", this.displayName)
            .replace("%players%", this.players.joinToString(", "))
            .replace("%total%", this.total.toString())
            .replace("%gameStatus%", this.gameStatus.toString().gameStatusReplacement)
            .replace("%address%", this.address)
            .replace("%port%", this.port.toString())
            .replace("%version%", this.version)
            .replace("%meta%", this.meta)
    }
val ServerStatus.renderedLore: List<String>
    get() {
        var template = Config.templateMap[this.gameType]
        if (template == null) {
            template = Config.templateMap["default"]
        }
        val result = template!!.itemLore.map {
            it.replace("%gameType%", this.gameType)
                .replace("%displayName%", this.displayName)
                .replace("%players%", this.players.joinToString(", "))
                .replace("%total%", this.total.toString())
                .replace("%gameStatus%", this.gameStatus.toString().gameStatusReplacement)
                .replace("%address%", this.address)
                .replace("%port%", this.port.toString())
                .replace("%version%", this.version)
                .replace("%meta%", this.meta)
        }
        return result
    }
val String.gameStatusReplacement: String
    get() {
        var that = this
        val replacement = Config.displayReplacement.findLast { it["keys"] == "gameStatus" }
        replacement?.forEach {
            that = that.replace(it.key, it.value as String)
        }
        return that
    }
val GameStatus.gameStatusReplacement: String
    get() {
        var that = this.toString()
        val replacement = Config.displayReplacement.findLast { it["keys"] == "gameStatus" }
        replacement?.forEach {
            that = that.replace(it.key, it.value as String)
        }
        return that
    }