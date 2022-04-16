package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.PermissionService.Companion.cancel
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
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

    private lateinit var adminPermission: Permission

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")

        data = listOf(SettingsConfig, TinaSproutRobotPluginData)
        commands = listOf(MasterCommand, AdminCommand)

        data.forEach {
            it.reload()
        }

        commands.forEach {
            it.register()
        }
        adminPermission = PermissionService.INSTANCE.register(
            PermissionId(name, "admin"), "Admin Permission"
        )
        //AbstractPermitteeId.AnyContact.permit(MasterCommand.permission)
// 授予权限
        try {
            AbstractPermitteeId.AnyContact.permit(AdminCommand.permission)
        } catch (e: Exception) {
            logger.warning("无法自动授予权限，请自行使用权限管理来授予权限")
        }

        val eventChannel = this.globalEventChannel().parentScope(this)

        if (master == null) {
            master = SettingsConfig.master
        }
        logger.info("主人：$master")

        eventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { event: BotOnlineEvent -> event.bot.id == SettingsConfig.roBot }
            .subscribeAlways<BotOnlineEvent> {
                val bot: Bot = this.bot
                val botEvent: EventChannel<BotEvent> = bot.eventChannel
                botEvent.subscribeAlways<GroupMessageEvent> {
//                    val nextMsg = nextMessage()
//                    //判断发送时间
//                    if (nextMsg.time - time < 60) {
//                        group.sendMessage(message.quote() + "你好快啊")
//                    }
//                    if (checkPermission(sender)) {
//                        logger.info("我权限比较大哈")
//                    }
                    logger.info("草：$message")
                }
            }

    }

    fun checkPermission(sender: Member): Boolean {
        when (SettingsConfig.permitMode) {
            0 -> {
                return true
            }
            1 -> {
                if (SettingsConfig.master == sender.id)
                    return true
            }
            2 -> {
                if (SettingsConfig.master == sender.id)
                    return true
                return sender.isOperator()
            }
            3 -> {
                return sender.permitteeId.hasPermission(adminPermission)

            }
            else -> {
                logger.error("权限设置信息错误，请检查权限模式设置")
            }
        }
        return false
    }

    override fun onDisable() {

        // 撤销权限
        try {
            AbstractPermitteeId.AnyContact.cancel(AdminCommand.permission, true)
        } catch (e: Exception) {
            logger.warning("无法自动撤销权限，请自行使用权限管理来撤销权限")
        }

        data.forEach {
            it.save()
        }
        commands.forEach {
            it.unregister()
        }
        super.onDisable()
    }


}