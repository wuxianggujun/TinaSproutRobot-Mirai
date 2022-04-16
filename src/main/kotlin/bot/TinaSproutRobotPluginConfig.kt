package wxgj.tinasproutrobot.mirai.bot

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object TinaSproutRobotPluginConfig : ReadOnlyPluginConfig("Settings") {
    val min: Int by value(60)
    val max: Int by value(6000)
    val timer: Int by value(10)
    @ValueDescription(
        """
        插件权限控制设置
        0 为所有人都可以控制
        1 为只有插件主人可以进行配置
        2 为群管理员也可以配置
        3 为拥有权限（mirai-setu:admin）者可以配置
        """
    )
    val permitMode by value(1)

    val clearTimer: String by value("00:00")

    //机器人主人
    @ValueDescription("主人")
    val master: Long by value(3344207732)

    @ValueDescription("机器人账号")
    val roBot:Long by value(2405024938L)

}