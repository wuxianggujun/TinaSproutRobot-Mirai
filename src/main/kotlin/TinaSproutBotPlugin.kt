package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.PermissionService.Companion.cancel
import net.mamoe.mirai.console.permission.PermissionService.Companion.getPermittedPermissions
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.*
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData.groupWelcomeMessage
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.AdminCommand
import wxgj.tinasproutrobot.mirai.command.GroupCommand
import wxgj.tinasproutrobot.mirai.command.MasterCommand
import wxgj.tinasproutrobot.mirai.command.WelcomeCommand

object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗特")
    }) {

    private var master: Long? = null

    //命令列表用于统一管理，统一注册
    private lateinit var commands: List<Command>
    private lateinit var data: List<PluginData>

    // private lateinit var adminPermission: Permission

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")
        data = listOf(SettingsConfig, TinaSproutRobotPluginData, GroupPermissionData)
        commands = listOf(MasterCommand, AdminCommand, GroupCommand, WelcomeCommand)

        data.forEach {
            it.reload()
        }

        commands.forEach {
            it.register()
        }
//        adminPermission = PermissionService.INSTANCE.register(
//            PermissionId(name, "admin"), "Admin Permission"
//        )
        // 授予权限
        try {
            AbstractPermitteeId.AnyContact.permit(AdminCommand.permission)
        } catch (e: Exception) {
            logger.warning("无法自动授予权限，请自行使用权限管理来授予权限")
        }

        val gwp = PermissionId("group", "welcome.message")
        PermissionService.INSTANCE.register(gwp, "群欢迎你")

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
                    val hasPerm = group.permitteeId.getPermittedPermissions().any { it.id == gwp }
                    logger.info("为什么没有用")
                    if (hasPerm && message.content == "#r") {

                        group.sendMessage(QuoteReply(source) + Dice((1..6).random()))
                    }
                }

                botEvent.subscribeAlways<MemberJoinEvent> {
                    val hasPerm = group.permitteeId.getPermittedPermissions().any { it.id == gwp }
                    if (hasPerm && groupWelcomeMessage.isNotEmpty()) {
                        val builder = MessageChainBuilder()
                        builder.add(At(user))
                        builder.add("Hello Mirai :)")
                        val msg = builder.build() // builder.asMessageChain() 也可以
                        group.sendMessage(msg)

                    }
                }

            }


    }

//    fun checkPermission(sender: Member): Boolean {
//        when (SettingsConfig.permitMode) {
//            0 -> {
//                return true
//            }
//            1 -> {
//                if (SettingsConfig.master == sender.id)
//                    return true
//            }
//            2 -> {
//                if (SettingsConfig.master == sender.id)
//                    return true
//                return sender.isOperator()
//            }
//            3 -> {
//                return sender.permitteeId.hasPermission(adminPermission)
//
//            }
//            else -> {
//                logger.error("权限设置信息错误，请检查权限模式设置")
//            }
//        }
//        return false
//    }

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

