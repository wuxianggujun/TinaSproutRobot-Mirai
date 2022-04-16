package wxgj.tinasproutrobot.mirai.bot

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object TinaSproutRobotPluginConfig : AutoSavePluginConfig("TinaSproutRobotPluginConfig") {
    val min: Int by value(60)
    val max: Int by value(6000)
    val timer: Int by value(10)

    val clearTimer: String by value("00:00")

    //机器人主人
    val master: Long by value(3344207732)


}