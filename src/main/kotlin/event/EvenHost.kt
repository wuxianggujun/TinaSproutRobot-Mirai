package wxgj.tinasproutrobot.mirai.event

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.FriendEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import kotlin.coroutines.CoroutineContext

object EventHost : SimpleListenerHost() {

    // 所有 Kotlin 非 suspend 的函数都将会在 Dispatchers.IO 中调用
    @EventHandler
    suspend fun FriendMessageEvent.onFriendMessage() {
        if (message.contentToString().equals("你好")) {
            subject.sendMessage("Hello Mirai :)")
        }
    }

    

}