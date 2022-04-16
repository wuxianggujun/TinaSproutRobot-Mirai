package wxgj.tinasproutrobot.mirai.bot.data

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
    var adminMap:MutableMap<Long, MutableList<Long>> by value(mutableMapOf<Long,MutableList<Long>>())

    /**
     * Auto matic response map
     * 是否在某个群开启自动回复
     * Long:群号
     * Boolean:是，否
     */
    @ValueDescription("开启自动回复")
    var autoMaticResponseMap: MutableMap<Long, Boolean> by value(mutableMapOf())

//    var world: List<Map<String,String>> by value(mutableListOf<Map<String,String>>(
//        mapOf("卧底词条" to "玩家词条")
//    ))

}