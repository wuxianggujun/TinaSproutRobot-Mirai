package wxgj.tinasproutrobot.mirai.bot.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.Group
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData.provideDelegate
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData.provideDelegate
import wxgj.tinasproutrobot.mirai.event.command.AdminPermissionsData.provideDelegate

/*用来存储语群有关系的数据
比如当前群的机器人的管理员列表*/

object GroupData : AutoSavePluginData("GroupData") {

    @ValueName("groupList")
    @ValueDescription("群列表,如果是TRUE也就是在本群开启机器人")
    val groupList: MutableMap<Long, Boolean> by value(mutableMapOf())

    /**
     * 机器人管理员，只开放某些权限
     * Admin map
     * Long:机器人QQ号
     * Long:管理员QQ号
     */
    @ValueName("botAdminMap")
    @ValueDescription("Bot管理员列表")
    val botAdminMap: MutableMap<Long, MutableList<Long>> by value(mutableMapOf())

    /**
     * Auto matic response map
     * 是否在某个群开启自动回复
     * Long:群号
     * Boolean:是，否
     */
    @ValueDescription("开启自动回复")
    var autoMaticResponseMap: MutableMap<Long, Boolean> by value(mutableMapOf())

    @ValueDescription("群欢迎消息")
    val groupWelcomeMessage: MutableMap<Long, String> by value(mutableMapOf())


    //用来检测群列表有没有开启机器人
    fun switchGroup(group: Group?): Boolean {
        return if (groupList.containsKey(group!!.id)) groupList[group.id] == true else false
    }

}