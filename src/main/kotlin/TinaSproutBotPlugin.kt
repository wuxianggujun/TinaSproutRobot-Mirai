package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginDataHolder
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginConfig
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.AdminCommand
import wxgj.tinasproutrobot.mirai.command.MasterCommand

@OptIn(ConsoleExperimentalApi::class)
object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗")
    }),
    PluginDataHolder {

    private lateinit var autoMaticResponseMap: MutableMap<Long, Boolean>
    private var master: Long? = null
    private var preMessage: String = ""
    private var num: Int = 1

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")

        TinaSproutRobotPluginConfig.reload()
        //TinaSproutRobotPluginData.reload()
MasterCommand.register()
        AdminCommand.register()

        autoMaticResponseMap = TinaSproutRobotPluginData.autoMaticResponseMap
        if (master == null) {
            master = TinaSproutRobotPluginConfig.master
        }
        logger.info("主人：$master")

        this.globalEventChannel().subscribeAlways<GroupMessageEvent> {


        }

//            if (sender.id != master && message.serializeToMiraiCode() != preMessage) {
//                num++
//                if (num >= 3) {
//                    //if (group.botPermission > sender.permission) {
//                    if (autoMaticResponseMap.containsKey(sender.id)) {
//                        if (autoMaticResponseMap.getValue(sender.id)) {
//                            autoMaticResponseMap[sender.id] = false
//                        }
//                    } else {
//                        autoMaticResponseMap[sender.id]
//                        autoMaticResponseMap[sender.id] = true
//                    }
//                    //设置禁言
//                    //}
//                    num = 0
//                }
//
//            } else {
//                preMessage = message.serializeToMiraiCode()
//                num = -1
//            }
//
//        }
//        launch {
//            if (LocalDateTime.now()
//                    .format(DateTimeFormatter.ofPattern("HH:mm")) == TinaSproutRobotPluginConfig.clearTimer
//            ) {
//                autoMaticResponseMap.clear()
//            }
//
//
//        }

    }


    override fun onDisable() {
        TinaSproutRobotPluginData.autoMaticResponseMap = autoMaticResponseMap
        TinaSproutRobotPluginConfig.save()
        //TinaSproutRobotPluginData.save()
        MasterCommand.unregister()
        AdminCommand.unregister()
        super.onDisable()
    }


}