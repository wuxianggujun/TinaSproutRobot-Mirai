package wxgj.tinasproutrobot.mirai.event

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.isOperator
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin.save
import wxgj.tinasproutrobot.mirai.bot.data.AdminAutoConfig
import wxgj.tinasproutrobot.mirai.bot.data.GroupData
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.utils.HttpUtils
import wxgj.tinasproutrobot.mirai.utils.ScriptChallenge
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom
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
        if (event.sender.isOperator()) {
            if (GroupData.adminPermMap.containsKey(event.sender.group.id)) {
                val list = GroupData.adminPermMap.getValue(event.sender.group.id)
                if (!list.contains(event.sender.id)) {
                    list.add(event.sender.id)
                }
            } else {
                val list = mutableListOf<Long>()
                list.add(event.sender.id)
                GroupData.adminPermMap[event.sender.group.id] = list
            }
        }
        GroupData.save()
    }

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {
        if (this.message.contentToString() == "test") {
            val member = sender as NormalMember
            launch {
                ScriptChallenge.auth(member.group.id, member.id, message.contentToString())
            }
        }

    }

    /*成员已经加入群: MemberJoinEvent

    成员被邀请加入群: Invite
    成员主动加入群: Active*/
    @EventHandler
    suspend fun MemberJoinEvent.onJoin() {
//        delay(2000)
//        when (GroupData.switchGroup(group)) {
//            addToNeedAuth(member)
//                launch { captchaSession(member) }
//        }
        val groupMessage = GroupData.groupWelcomeMessage
        //判断群有没有开启机器人
        if (GroupData.switchGroup(group)) {
            //判断有没有添加到列表，添加到列表即为开启
            if (groupMessage.containsKey(group.id)) {
                //游历群的欢迎新用户消息
                groupMessage.forEach { (key, value) ->
                    if (key == group.id) {
                        //使用随机数，生成0~list.size中的任何一个数字
                        val random = ThreadLocalRandom.current().nextInt(0, groupMessage.getValue(group.id).size)
                        val msg: Message = At(member.id) + PlainText("\n") + PlainText(value[random])
                        member.group.sendMessage(msg)
                    }
                }
            }

        }


    }


    @EventHandler
    suspend fun MemberJoinRequestEvent.onRequest() {

        //如果群开启机器人，则自动同意加群申请
        if (GroupData.switchGroup(group)&&AdminAutoConfig.autoGroupAccept) {
            accept()
        }

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