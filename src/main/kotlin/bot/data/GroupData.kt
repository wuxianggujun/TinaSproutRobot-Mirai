package wxgj.tinasproutrobot.mirai.bot.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value
import wxgj.tinasproutrobot.mirai.command.AdminPermissionsData.provideDelegate

/*用来存储语群有关系的数据
比如当前群的机器人的管理员列表*/

object GroupData : AutoSavePluginData("GroupData") {

    @ValueName("groupList")
    @ValueDescription("群列表,如果是TRUE也就是在本群开启机器人")
    val groupList: MutableMap<Long, Boolean> by value(mutableMapOf())


    @ValueName("botAdminMap")
    @ValueDescription("Bot管理员列表")
    val botAdminMap: MutableMap<Long, MutableList<Long>> by value(mutableMapOf())

}