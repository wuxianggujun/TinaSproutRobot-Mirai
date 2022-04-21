package wxgj.tinasproutrobot.mirai.event

import net.mamoe.mirai.console.permission.PermissionService.Companion.getPermittedPermissions
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.EventPriority
import net.mamoe.mirai.event.ListeningStatus
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import kotlin.coroutines.CoroutineContext

object GroupEventListener : SimpleListenerHost() {

    private val logger = TinaSproutBotPlugin.logger


    @EventHandler(priority = EventPriority.HIGHEST)
    suspend fun onGroupMessageEvent(event: GroupMessageEvent){
        logger.info("事件被拦截了")
        //拦截事件
        //event.intercept()
        logger.info("事件被拦截了,我发不了信息")
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logger.error(exception)
    }
}