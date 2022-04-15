package wxgj.tinasproutrobot.mirai.bot

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object TinaSproutRobotPluginData : AutoSavePluginData("data") {

    /**
     * Admin map
     * 管理员和群
     */
    val adminMap: MutableMap<Long, Long> by value()
}