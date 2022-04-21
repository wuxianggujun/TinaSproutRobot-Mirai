package wxgj.tinasproutrobot.mirai.event

import net.mamoe.mirai.event.SimpleListenerHost
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import kotlin.coroutines.CoroutineContext

object GroupEventListener : SimpleListenerHost() {

    private val logger = TinaSproutBotPlugin.logger



    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logger.error(exception)
    }
}