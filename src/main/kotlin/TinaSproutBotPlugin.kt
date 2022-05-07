package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.event.globalEventChannel
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig
import wxgj.tinasproutrobot.mirai.bot.data.GroupData
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData
import wxgj.tinasproutrobot.mirai.event.GroupEventListener
import wxgj.tinasproutrobot.mirai.event.command.*
import wxgj.tinasproutrobot.mirai.logger.TinaSproutLogger

object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗特")
    }) {

    //命令列表用于统一管理，统一注册
    private lateinit var commands: List<Command>
    private lateinit var data: List<PluginData>

    //群欢迎权限
    lateinit var welcomeJoinGroupPermission: Permission

    override fun onEnable() {
        data = listOf(SettingsConfig, GroupPermissionData, AdminPermissionsData, GroupData)
        commands = listOf(MasterCommand, AdminCommand, GroupCommand, WelcomeCommand, HelpCommand, UserCommand)
        data.forEach {
            it.reload()
        }
        commands.forEach {
            //@param override 是否覆盖重名指令.
            it.register(true)
        }
        try {
            //赋予任何用户都有使用help权限
            AbstractPermitteeId.AnyUser.permit(HelpCommand.permission)
            AbstractPermitteeId.AnyMemberFromAnyGroup.permit(UserCommand.permission)
        } catch (e: Exception) {
            TinaSproutLogger.error("权限授予出错:$e")
        }

        welcomeJoinGroupPermission = PermissionService.INSTANCE.register(
            PermissionId(name, "WelcomeJoinGroup"), "缇娜——欢迎进群权限"
        )

        val eventChannel = this.globalEventChannel().parentScope(this)

        //多个机器人账号登录时，只有配置文件上写的QQ才会响应
        eventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { e -> e.bot.id == SettingsConfig.roBot }
            .subscribeAlways(BotOnlineEvent::class.java) {
                val currentBot: Bot = it.bot
                val botEventChannel: EventChannel<BotEvent> = currentBot.eventChannel
                //设置群事件监听器
                botEventChannel.registerListenerHost(GroupEventListener)

//                botEventChannel.subscribeAlways<GroupMessageEvent>(priority = EventPriority.HIGHEST) {
//                    //如果发送者是机器人主人则拦截信息
//                    if (sender.id == SettingsConfig.master) {
//                        intercept()
//                    } else {
//                        botEventChannel.registerListenerHost(GroupEventListener)
//                    }
//                }

//                //居然还可以这样做
//                with(botEventChannel) {
//
//                    registerListenerHost(GroupEventListener)
//                }

//                this@TinaSproutBotPlugin.launch {
//                }
//                this.launch {
//                    //检查群有没有欢迎新用户权限，有的话就欢迎新用户
//                    currentBot.groups.filter { currentGroup ->
//                        welcomeJoinGroupPermission.testPermission(currentGroup.permitteeId)
//                    }.forEach {
//                    }
//
//                }

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
//        // 授予权限
//        try {
//            AbstractPermitteeId.AnyContact.permit(AdminCommand.permission)
//        } catch (e: Exception) {
//            logger.warning("无法自动授予权限，请自行使用权限管理来授予权限")
//        }
//        // 撤销权限 如果权限里面有*那么撤销不了
//        try {
//            AbstractPermitteeId.AnyContact.cancel(AdminCommand.permission, true)
//        } catch (e: Exception) {
//            logger.warning("无法自动撤销权限，请自行使用权限管理来撤销权限")
//        }

