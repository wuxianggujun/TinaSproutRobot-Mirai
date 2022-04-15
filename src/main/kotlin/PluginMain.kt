package wxgj.tinasproutrobot.mirai

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.utils.info
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.Echo
import wxgj.tinasproutrobot.mirai.event.EventHost


class PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "wxgj.tinasproutrobot.mirai",
        name = "TinaSproutRobot-Mirai",
        version = "0.1.0"
    )
)
{

    override fun onEnable() {
        TinaSproutRobotPluginData.reload()
        logger.info { "机器人已经加载" }
        //配置文件目录 "${dataFolder.absolutePath}/"
        Echo.register()//注册命令
        GlobalEventChannel.filterIsInstance(BotOnlineEvent::class.java)
            .filter { (bot): BotOnlineEvent -> bot.id == 2405024938L }
            .subscribeAlways<BotOnlineEvent> {
                val bot: Bot = this.bot
                val eventChannel: EventChannel<BotEvent> = bot.eventChannel
                eventChannel.registerListenerHost(EventHost)
            }

    }

    override fun onDisable() {
        //保存
        TinaSproutRobotPluginData.save()
    }

    companion object {
        @JvmField
        var INSTANCE = PluginMain()
    }

}


/*  globalEventChannel().registerListenerHost(EventHost)

        globalEventChannel().subscribeAlways<GroupMessageEvent>{
            //群消息
            //复读示例
            if (message.contentToString().startsWith("复读")) {
                group.sendMessage(message.contentToString().replace("复读", ""))
            }
            if (message.contentToString() == "hi") {
                //群内发送
                group.sendMessage("hi")
                //向发送者私聊发送消息
                sender.sendMessage("hi")
                //不继续处理
                return@subscribeAlways
            }
            //分类示例
            message.forEach {
                //循环每个元素在消息里
                if (it is Image) {
                    //如果消息这一部分是图片
                    val url = it.queryUrl()
                    group.sendMessage("图片，下载地址$url")
                }
                if (it is PlainText) {
                    //如果消息这一部分是纯文本
                    group.sendMessage("纯文本，内容:${it.content}")
                }
            }
        }
        globalEventChannel().subscribeAlways<FriendMessageEvent>{
            //好友信息
            sender.sendMessage("hi")
        }
        globalEventChannel().subscribeAlways<NewFriendRequestEvent>{
            //自动同意好友申请
            accept()
        }
        globalEventChannel().subscribeAlways<BotInvitedJoinGroupRequestEvent>{
            //自动同意加群申请
            accept()
        }*/

