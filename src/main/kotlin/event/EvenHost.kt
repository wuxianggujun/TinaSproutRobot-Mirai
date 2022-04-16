package wxgj.tinasproutrobot.mirai.event

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.MiraiLogger
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.event.file.BotGroupFile
import kotlin.coroutines.CoroutineContext

object EventHost : SimpleListenerHost() {


    // 所有 Kotlin 非 suspend 的函数都将会在 Dispatchers.IO 中调用
    @EventHandler
    suspend fun FriendMessageEvent.onFriendMessage() {
        if (message.contentToString() == "你好") {
            subject.sendMessage("Hello Mirai :)")
        }
    }

    @EventHandler
    suspend fun handleMessage(event: GroupMessageEvent) {
        //println(event.message);
        val miraiLogger: MiraiLogger = event.bot.logger
        miraiLogger.info("QQ号:${event.bot}")
        //val groupId = TinaSproutRobotPluginData.adminMap[event.bot.id]
        //miraiLogger.info("机器人$groupId");

        //TinaSproutRobotPluginData.adminMap[event.bot.id] = event.group.id
        if (event.message.contentToString() == "群文件") {
            miraiLogger.info("执行代码·")
            val mList: MutableList<String>? = BotGroupFile.getGroupAllFiles(event.bot, event.group.id)
            mList!!.forEach { i ->
                miraiLogger.info("文件:$i")
                // BotGroupFile.deleteFile(event.bot,event.group.id,i);
            }
        }

    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println(exception.cause)
    }


//        val text = toText(event.message).trim();
//        handMessage(text, event)


}


//    @EventHandler
//    suspend fun handleMessage(event: FriendMessageEvent) {
//        val text = toText(event.message).trim();
//        if (conf.prK)
//            handMessage(text, event)
//    }


