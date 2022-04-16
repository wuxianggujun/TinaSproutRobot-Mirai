package wxgj.tinasproutrobot.mirai.bot

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object TinaSproutRobotPluginData : AutoSavePluginData("data") {

    /**
     * 机器人管理员，只开放某些权限
     * Admin map
     * Long:机器人QQ号
     * Long:管理员QQ号
     */
    @ValueDescription("管理员列表")
    val adminMap: MutableMap<Long, MutableList<Long>> by value()

    /**
     * Auto matic response map
     * 是否在某个群开启自动回复
     * Long:群号
     * Boolean:是，否
     */
    var autoMaticResponseMap: MutableMap<Long, Boolean> by value(mutableMapOf())
}