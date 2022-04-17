package wxgj.tinasproutrobot.mirai.bot.data

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object GroupPermissionData : AutoSavePluginData("GroupPermissionData") {
    @ValueDescription("组")
    val group: MutableMap<String, MutableList<String>> by value(mutableMapOf())

    @ValueDescription("组权限")
    val groupPerm: MutableMap<String, MutableList<String>> by value(mutableMapOf())

    @ValueDescription("群欢迎消息")
    val groupWelcomeMessage: MutableMap<String, String> by value(mutableMapOf())

    @ValueDescription("一个数组")
    val permission = emptyArray<String>()

}