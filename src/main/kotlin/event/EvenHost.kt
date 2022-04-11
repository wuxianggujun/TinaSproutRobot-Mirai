package wxgj.tinasproutrobot.mirai.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import net.mamoe.mirai.contact.file.AbsoluteFile
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.MiraiLogger

object EventHost : SimpleListenerHost() {



    // 所有 Kotlin 非 suspend 的函数都将会在 Dispatchers.IO 中调用
    @EventHandler
    suspend fun FriendMessageEvent.onFriendMessage() {
        if (message.contentToString().equals("你好")) {
            subject.sendMessage("Hello Mirai :)")
        }
    }

    @EventHandler
    suspend fun handleMessage(event: GroupMessageEvent) {
        //println(event.message);
        val miraiLogger:MiraiLogger = event.bot.logger;
        miraiLogger.info("草");
        val absoluteFile: Flow<AbsoluteFile> = event.group.files.root.files().catch {
            miraiLogger.error("Error loading reserved $it")
        }
        absoluteFile.collect{
            miraiLogger.info("我在干哈")
           miraiLogger.info(it.name);
        }
       //miraiLogger.info()

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


