package wxgj.tinasproutrobot.mirai.logger

import net.mamoe.mirai.utils.MiraiLogger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import kotlin.math.E

object TinaSproutLogger : MiraiLogger {

    private val log: Logger = LogManager.getLogger("TinaSproutBot")

    override val identity: String?
        get() = "TinaSproutBot"
    override val isEnabled: Boolean
        get() = true

    override fun debug(message: String?) {
        log.debug(message)
    }

    override fun debug(message: String?, e: Throwable?) {
        log.debug(message, e)
    }

    override fun error(message: String?) {
        log.error(message)
    }

    override fun error(message: String?, e: Throwable?) {
        log.error(message, e)
    }

    override fun info(message: String?) {
        log.info(message)
    }

    override fun info(message: String?, e: Throwable?) {
        log.info(message, e)
    }

    override fun verbose(message: String?) {
        log.info(message)
    }

    override fun verbose(message: String?, e: Throwable?) {
        log.info(message, e)
    }

    override fun warning(message: String?) {
        log.warn(message)
    }

    override fun warning(message: String?, e: Throwable?) {
        log.warn(message, e)
    }
}