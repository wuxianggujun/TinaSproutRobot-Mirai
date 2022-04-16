package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
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
    }) {

    //private lateinit var autoMaticResponseMap: MutableMap<Long, Boolean>
    private var master: Long? = null
    private var preMessage: String = ""
    private var num: Int = 1

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")

        TinaSproutRobotPluginConfig.reload()
        TinaSproutRobotPluginData.reload()

        if (master == null) {
            master = TinaSproutRobotPluginConfig.master
        }
        logger.info("主人：$master")

        this.globalEventChannel().filterIsInstance(BotOnlineEvent::class.java)
            .filter { (bot): BotOnlineEvent -> bot.id == 2405024938L }
            .subscribeAlways<BotOnlineEvent> {
                val bot: Bot = this.bot
                val eventChannel: EventChannel<BotEvent> = bot.eventChannel
                MasterCommand.register()
                AdminCommand.register()
            }

    }


    override fun onDisable() {

        TinaSproutRobotPluginConfig.save()
        TinaSproutRobotPluginData.save()
        MasterCommand.unregister()
        AdminCommand.unregister()
        super.onDisable()
    }


}