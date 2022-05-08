package wxgj.tinasproutrobot.mirai.event.command

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.util.requestInput
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.message.code.MiraiCode
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.EmptyMessageChain
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.nextMessage
import net.mamoe.mirai.utils.warning
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin.save
import wxgj.tinasproutrobot.mirai.bot.data.GroupData
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData.group
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData

object MasterCommand : CompositeCommand(TinaSproutBotPlugin, "master", description = "主人命令") {

    //QQ群机器人配置
    private val groupData = GroupData

    //日志
    private val logger = TinaSproutBotPlugin.logger

    private suspend fun CommandSender.read(contact: Contact?): Message {
        return when (this) {
            is ConsoleCommandSender -> {
                val code = MiraiConsole.requestInput("请输入要发送的消息")
                MiraiCode.deserializeMiraiCode(code, contact)
            }
            is CommandSenderOnMessage<*> -> {
                sendMessage("请输入要发送的消息")
                fromEvent.nextMessage()
            }
            else -> throw IllegalStateException("未知环境$this")
        }
    }

    @SubCommand
    @Description("群发，发送给所有群")
    public suspend fun CommandSender.groups(bot: Bot? = this.bot, at: Boolean = false) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        try {
            val message = read(contact = null)
            for (group in bot.groups) {
                group.sendMessage(message = message + if (at) AtAll else EmptyMessageChain)
            }
        } catch (cause: Throwable) {
            logger.warning({ "发送给所有群处理失败" }, cause)
        }

    }

    @SubCommand
    @Description("发送给所有好友")
    public suspend fun CommandSender.friends(bot: Bot? = this.bot) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        try {
            val message = read(contact = null)
            for (friend in bot.friends) {
                friend.sendMessage(message = message)
            }
        } catch (cause: Throwable) {
            logger.warning({ "发送给所有好友 处理失败" }, cause)
        }
    }

    @SubCommand
    @Description("发送给指定联系人")
    public suspend fun CommandSender.to(contact: Contact, at: Boolean = false) {
        contact.sendMessage(
            message = read(contact = contact) + when {
                !at -> EmptyMessageChain
                contact is User -> At(contact)
                contact is Group -> AtAll
                else -> EmptyMessageChain
            }
        )
    }


    @SubCommand
    @Description("戳一戳指定联系人")
    public suspend fun CommandSender.nudge(user: User) {
        val message = try {
            when (user) {
                is NormalMember -> user.nudge().sendTo(user.group)
                is Friend -> user.nudge().sendTo(user)
                is Stranger -> user.nudge().sendTo(user)
                else -> throw UnsupportedOperationException("nudge $user")
            }
            "发送成功"
        } catch (cause: Throwable) {
            logger.warning({ "发送失败" }, cause)
            "发送失败"
        }

        sendMessage(message)
    }

    @SubCommand
    @Description("添加管理员")
    suspend fun CommandSender.add(member: NormalMember) {
//        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
//        if (config.adminMap.containsKey(bot!!.id)) {
//            //判断管理员列表是否有指定元素，有就返回true也就是已经添加了
//            if (list.contains(member.id)) {
//                sendMessage("${member.id}已存在")
//            } else {
//                list.add(member.id)
//                config.adminMap[bot!!.id] = list
//                sendMessage("${member.id}添加成功")
//            }
//        } else {
//            list.add(member.id)
//            config.adminMap[bot!!.id] = list
//            logger.info("添加成功")
//            sendMessage("${member.id}添加成功")
//        }
//        config.save()
    }

    @SubCommand
    @Description("清除全部管理员")
    suspend fun CommandSender.clear() {
//        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
//        if (config.adminMap.containsKey(bot!!.id) && !list.isNullOrEmpty()) {
//            sendMessage("${list.size}位,清除成功！")
//            list.clear()
//            config.adminMap[bot!!.id] = list
//        }
//        config.save()
    }

    @SubCommand
    @Description("清除管理员")
    suspend fun CommandSender.clear(member: NormalMember) {
//        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
//        if (config.adminMap.containsKey(bot!!.id) && !list.isNullOrEmpty()) {
//            list.remove(member.id)
//            config.adminMap[bot!!.id] = list
//            sendMessage("已经删除${member.id}管理员")
//        }
//        config.save()
    }

    @SubCommand
    @Description("查看管理员列表")
    suspend fun CommandSender.list() {
//        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
//        if (config.adminMap.containsKey(bot!!.id) && !list.isNullOrEmpty()) {
//            val msg = buildString {
//                append("管理员列表\n")
//                for ((index, value) in list.withIndex()) {
//                    append("管理员:($value)\n")
//                }
//                append("\n共${list.size}位,管理员")
//            }
//            sendMessage(msg)
//        }
    }

}