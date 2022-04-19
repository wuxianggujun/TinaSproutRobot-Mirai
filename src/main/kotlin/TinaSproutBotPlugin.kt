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
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.*

object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗特")
    }) {

    private var master: Long? = null

    //命令列表用于统一管理，统一注册
    private lateinit var commands: List<Command>
    private lateinit var data: List<PluginData>

     private lateinit var adminPermission: Permission

    override fun onEnable() {
        logger.info("TinaSproutBotPlugin Loaded")
        data = listOf(SettingsConfig, TinaSproutRobotPluginData, GroupPermissionData,AdminPermissionsData)
        commands = listOf(MasterCommand, AdminCommand, GroupCommand, WelcomeCommand)

        data.forEach {
            it.reload()
        }

        commands.forEach {
            it.register()
        }
        adminPermission = PermissionService.INSTANCE.register(
            PermissionId(name, "admin"), "缇娜——管理员权限"
        )
//        // 授予权限
//        try {
//            AbstractPermitteeId.AnyContact.permit(AdminCommand.permission)
//        } catch (e: Exception) {
//            logger.warning("无法自动授予权限，请自行使用权限管理来授予权限")
//        }

        val eventChannel = this.globalEventChannel().parentScope(this)

        if (master == null) {
            master = SettingsConfig.master
        }
        logger.info("主人：$master")
        logger.info("名字${this.name}")

        eventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { event: BotOnlineEvent -> event.bot.id == SettingsConfig.roBot }
            .subscribeAlways<BotOnlineEvent> {
                val bot: Bot = this.bot
                val botEvent: EventChannel<BotEvent> = bot.eventChannel
                //用户权限
                botEvent.subscribeAlways<GroupMessageEvent> {

                }


            }


    }

    override fun onDisable() {
//        // 撤销权限 如果权限里面有*那么撤销不了
//        try {
//            AbstractPermitteeId.AnyContact.cancel(AdminCommand.permission, true)
//        } catch (e: Exception) {
//            logger.warning("无法自动撤销权限，请自行使用权限管理来撤销权限")
//        }

        data.forEach {
            it.save()
        }
        commands.forEach {
            it.unregister()
        }
        super.onDisable()
    }


}

