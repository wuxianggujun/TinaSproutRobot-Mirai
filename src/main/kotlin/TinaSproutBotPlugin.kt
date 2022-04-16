package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.globalEventChannel
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.AdminCommand
import wxgj.tinasproutrobot.mirai.command.MasterCommand

object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗特")
    }) {

    //private lateinit var autoMaticResponseMap: MutableMap<Long, Boolean>
    private var master: Long? = null
    private var preMessage: String = ""
    private var num: Int = 1

    //命令列表用于统一管理，统一注册
    private lateinit var commands: List<Command>
    private lateinit var data: List<PluginData>

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")

        data = listOf(SettingsConfig, TinaSproutRobotPluginData)
        commands = listOf(MasterCommand, AdminCommand)

        data.forEach {
            it.reload()
        }

        val eventChannel = this.globalEventChannel().parentScope(this)

        if (master == null) {
            master = SettingsConfig.master
        }
        logger.info("主人：$master")

        eventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { (bot): BotOnlineEvent -> bot.id == SettingsConfig.roBot }
            .subscribeAlways<BotOnlineEvent> {
                val bot: Bot = this.bot
                val eventChannel: EventChannel<BotEvent> = bot.eventChannel
                commands.forEach {
                    it.register()
                }
            }

    }


    override fun onDisable() {
        data.forEach {
            it.save()
        }
        commands.forEach {
            it.unregister()
        }
        super.onDisable()
    }


}