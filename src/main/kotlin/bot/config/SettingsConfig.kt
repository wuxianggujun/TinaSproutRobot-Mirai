package wxgj.tinasproutrobot.mirai.bot.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object SettingsConfig : AutoSavePluginConfig("Settings") {

    @ValueDescription("最小时间")
    val min: Int by value(60)
    val max: Int by value(6000)
    val timer: Int by value(10)

    val clearTimer: String by value("00:00")

    @ValueDescription("机器人总开关")
    val botSwitch: Boolean by value(true)

    //机器人主人
    @ValueDescription("主人")
    val master: Long by value(3344207732)

    @ValueDescription("机器人账号")
    val roBot: Long by value(2405024938L)

    @ValueDescription("机器人名字")
    val botName:String by value("小缇娜")

}