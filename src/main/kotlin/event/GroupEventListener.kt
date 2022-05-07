package wxgj.tinasproutrobot.mirai.event

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.utils.HttpUtils
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext


object GroupEventListener : SimpleListenerHost() {

    private val logger = TinaSproutBotPlugin.logger

    private val needAuth = ConcurrentHashMap<Long, HashSet<Long>>()

    private val captchaCookie = ConcurrentHashMap<Long, String>()


    @EventHandler(priority = EventPriority.HIGHEST)
    suspend fun onGroupMessageEvent(event: GroupMessageEvent) {
        logger.info("事件被拦截了")
        //拦截事件
        //event.intercept()
        logger.info("事件被拦截了,我发不了信息")

    }

    @EventHandler
    suspend fun MemberJoinEvent.onJoin() {
//        delay(2000)
//        when (GroupData.switchGroup(group)) {
//            addToNeedAuth(member)
//                launch { captchaSession(member) }
//        }

    }


    @EventHandler
    suspend fun MemberJoinRequestEvent.onRequest() {


    }


    override fun handleException(context: CoroutineContext, exception: Throwable) {
        logger.error(exception)
    }


    private fun addToNeedAuth(member: NormalMember) {
        val accountSet = needAuth.getOrDefault(member.group.id, HashSet())
        accountSet.add(member.id)
        needAuth[member.group.id] = accountSet
    }

    private suspend fun launchTimeOutJob(time: Long, member: NormalMember): Job {
        val groupId = member.group.id
        return launch {
            delay(time)
            if (needAuth[groupId]?.contains(member.id) == true) {
                member.group.sendMessage(member.at() + PlainText("验证超时，请重新加群"))
                needAuth[groupId]?.remove(member.id)
                member.kick("请重试")
            }

        }
    }

    private suspend fun kickWrongAnswer(member: NormalMember) {
        member.group.sendMessage(member.at() + PlainText("您未通过验证，请重新加群"))
        needAuth[member.group.id]?.remove(member.id)
        captchaCookie.remove(member.id)
        launch {
            delay(5000)
            member.kick("请重试")
        }
    }

    private suspend fun sendCaptcha(member: NormalMember): Boolean {
        val group = member.group
        val rsp = HttpUtils.getCaptCha()
        val cookie = rsp.headers("Set-Cookie")
        if (!rsp.isSuccessful || cookie.isEmpty()) {
            group.sendMessage("网络错误 ${rsp.message}")
            return false
        }
        val img = rsp.body!!.bytes().toExternalResource()
        group.sendMessage(At(member) + img.uploadAsImage(group))
        img.close()
        rsp.close()

        captchaCookie[member.id] = cookie.joinToString(separator = ";")
        return true
    }


    private suspend fun captchaSession(member: NormalMember) {
        val group = member.group
        val groupId = group.id
        var timeoutJob: Job? = null
        var listener: Listener<GroupMessageEvent>? = null
        var tries = 0

        group.sendMessage(At(member) + PlainText("欢迎来到本群，为保障良好的聊天环境，请在180秒内输入以下验证码。验证码不区分大小写"))
        sendCaptcha(member)
        timeoutJob = launchTimeOutJob(180_000, member)
        listener = GlobalEventChannel.subscribeAlways<GroupMessageEvent> {
            val thisMember = this.sender
            if (thisMember == member) {
                val code = message.contentToString().trim()
                val result = HttpUtils.verifyCaptcha(captchaCookie.getOrDefault(member.id, ""), code)
                if (result) {
                    group.sendMessage(member.at() + PlainText("您已通过验证！"))
                    needAuth[groupId]?.remove(member.id)
                    captchaCookie.remove(member.id)
                    timeoutJob.cancel()
                    listener?.complete()
                    return@subscribeAlways
                }
                tries++
                if (tries >= 5) {
                    kickWrongAnswer(member)
                    timeoutJob.cancel()
                    listener?.complete()
                    return@subscribeAlways
                }
                group.sendMessage(member.at() + PlainText("验证码错误，您还有${5 - tries}次机会"))
                sendCaptcha(member)
            }
        }


    }


}