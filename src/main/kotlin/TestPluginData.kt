package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object TestPluginData {
    object TestPlugin : KotlinPlugin(JvmPluginDescription("wxgj.tinasproutrobot.mirai", "1.0.0") {
        name("TinaSproutRobot")
    })

    object TestPluginData : AutoSavePluginData("test.yaml") {
        val snmp: MutableMap<String, String> by value()
    }

}

fun main(args: Array<String>){
}