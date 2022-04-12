package wxgj.tinasproutrobot.mirai.event

import kotlinx.coroutines.flow.Flow
import net.mamoe.mirai.contact.file.AbsoluteFile
import net.mamoe.mirai.contact.file.AbsoluteFileFolder
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.MiraiLogger
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
        if (event.message.contentToString() == "群文件") {
            //获取该目录下所有文件和子目录列表.
            val absoluteFiles: Flow<AbsoluteFileFolder> = event.group.files.root.children()
            absoluteFiles.collect {
                if (it.isFile) {
                    println("文件名:${it.name}")
                }

                if (it.isFolder) {
                    event.group.files.root.folders().collect {
                        it.files().collect { asf: AbsoluteFile ->
                            println("子文件夹:${asf.name}")
                        }
                    }
//                    val remoteFiles: Flow<AbsoluteFile>? = it.parent?.files();
//                    remoteFiles?.collect {
//                        println(it.name)
//                    }
                }


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


