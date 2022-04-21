package wxgj.tinasproutrobot.mirai

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.data.PluginData
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.permission.PermissionService.Companion.testPermission
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotJoinGroupEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.events.MessagePreSendEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.event.subscribeAlways
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig
import wxgj.tinasproutrobot.mirai.bot.data.GroupData
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData
import wxgj.tinasproutrobot.mirai.command.*
import wxgj.tinasproutrobot.mirai.event.GroupEventListener
import java.time.LocalDateTime
import java.util.*

object TinaSproutBotPlugin : KotlinPlugin(
    JvmPluginDescription(id = "wxgj.tinasproutrobot.mirai", version = "1.0.0") {
        author("WuXiangGuJun")
        info("提娜——斯普朗特")
    }) {

    private var master: Long? = null

    //命令列表用于统一管理，统一注册
    private lateinit var commands: List<Command>
    private lateinit var data: List<PluginData>

    //群欢迎权限
    lateinit var welcomeJoinGroupPermission: Permission

    override fun onEnable() {
        data = listOf(SettingsConfig, GroupPermissionData, AdminPermissionsData, GroupData)
        commands = listOf(MasterCommand, AdminCommand, GroupCommand, WelcomeCommand)
        data.forEach {
            it.reload()
        }
        commands.forEach {
            it.register()
        }
        welcomeJoinGroupPermission = PermissionService.INSTANCE.register(
            PermissionId(name, "WelcomeJoinGroup"), "缇娜——欢迎进群权限"
        )

        val eventChannel = this.globalEventChannel().parentScope(this)
        master = SettingsConfig.master

        //多个机器人账号登录时，只有配置文件上写的QQ才会响应
        eventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { e -> e.bot.id == SettingsConfig.roBot }
            .subscribeAlways(BotOnlineEvent::class.java) {
                val currentBot: Bot = it.bot
                val botEventChannel: EventChannel<BotEvent> = currentBot.eventChannel
                //设置群事件监听器
                botEventChannel.registerListenerHost(GroupEventListener)


                this@TinaSproutBotPlugin.launch {


                }


                this.launch {
                    //检查群有没有欢迎新用户权限，有的话就欢迎新用户
                    currentBot.groups.filter { currentGroup ->
                        welcomeJoinGroupPermission.testPermission(currentGroup.permitteeId)
                    }.forEach {
                    }

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

