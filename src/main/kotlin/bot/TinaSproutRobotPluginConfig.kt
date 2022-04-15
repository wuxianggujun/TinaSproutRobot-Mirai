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

    /**
     * 机器人管理员，只开放某些权限
     * Admin map
     * Long:机器人QQ号
     * Long:管理员QQ号
     */
    val adminMap: MutableMap<Long, Long> by value()

    /**
     * Auto matic response map
     * 是否在某个群开启自动回复
     * Long:群号
     * Boolean:是，否
     */
    var autoMaticResponseMap: MutableMap<Long, Boolean> by value()
}