package wxgj.tinasproutrobot.mirai.event.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.Group
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.bot.data.GroupData


object AdminCommand : CompositeCommand(
    TinaSproutBotPlugin,
    "admin",
    description = "管理员指令"
) {
    @SubCommand("TOTB", "开启机器人")
    @Description("在本群开启机器人")
    suspend fun CommandSender.turnOnTheBot(groupNum: Long, switchBot: String) {
        val groupList = GroupData.groupList
        val group: Group? = this.bot!!.getGroup(groupNum)
        val turnOnTheBot = switchBot.toBoolean()
        //检测群列表有没有这个群
        if (groupList.containsKey(group!!.id)) {
            groupList[group.id] = turnOnTheBot
        } else {
            groupList[group.id] = turnOnTheBot
        }
        TinaSproutBotPlugin.logger.info("打印一下：${groupList[group.id]}")
    }

    @SubCommand("add", "添加")
    @Description("添加群欢迎消息")
    suspend fun CommandSender.addWelcomeMessage(gNum: Long, msg: String) {
        val groupWelcomeMessageList = GroupData.groupWelcomeMessage
        //查看是否包含群
        if (groupWelcomeMessageList.containsKey(gNum)) {
            //获取群欢迎消息
            val messageList = groupWelcomeMessageList.getValue(gNum)
            messageList.add(msg)
        } else {
            val messageList = mutableListOf<String>()
            messageList.add(msg)
            groupWelcomeMessageList[gNum] = messageList
        }

    }

//
//    @SubCommand("list", "查看列表")
//    suspend fun CommandSender.list() {
//        sendMessage("你在干哈")
//
//    }
//
//    @SubCommand("add", "添加")
//    suspend fun CommandSender.add(member: NormalMember) {
//        val group = GroupData.groupList
//        if (group != null) {
//            if (!group.containsKey(member.group.id)) {
//                group[member.group.id] = false
//            }
//            if (GroupData.adminPermMap.containsKey(member.group.id)) {
//                val groupAdminList = GroupData.adminPermMap.get(member.group.id)
//                if (!groupAdminList!!.contains(member.id)) {
//                    groupAdminList.add(member.id)
//                }
//            } else {
//                val adminList = mutableListOf<Long>()
//                adminList.add(member.id)
//                GroupData.adminPermMap[member.group.id] = adminList
//                TinaSproutBotPlugin.logger.info("添加到管理员列表")
//            }
//
//            PermissionService.INSTANCE.getRegisteredPermissions().forEach { P ->
//                TinaSproutBotPlugin.logger.info("注册的权限:${P.id}")
//                if (P.id == AdminCommand.permission.id) {
//                    TinaSproutBotPlugin.logger.info(":${P.id}权限等于${permission.id}")
//                    GroupData.adminPermMap.filter { (key, value) ->
//                        key == member.group.id
//                    }.map {
//                        it.value.forEach { v ->
//                            val sb = StringBuilder()
//                            sb.append("m")
//                            sb.append(it.key)
//                            sb.append(".")
//                            sb.append(v)
//                            TinaSproutBotPlugin.logger.info("SB：${sb.toString()}")
//                            TinaSproutBotPlugin.logger.info("列表里面的V：${v.toString()}")
//
//                            AbstractPermitteeId.parseFromString(sb.toString()).permit(AdminCommand.permission)
//                        }
//                    }
//                }
//            }
//
//        }
//        //AbstractPermitteeId.parseFromString("m864358403.3548346511").permit(AdminCommand.permission)
//
//    }
//
//
//
//    @SubCommand("clear")
//    suspend fun CommandSender.clear() {
//        sendMessage("你在干哈")
//        AbstractPermitteeId.parseFromString("m864358403.3548346511").cancel(AdminCommand.permission, true)
//    }
//
//    @SubCommand("禁言")
//    suspend fun MemberCommandSenderOnMessage.main(MemberTarget: Member, durationSeconds: Int) {
//        //先判断使用这条命令的人是不是管理员或者群主，是的话就把用户禁言,可以防止身为机器人管理员而滥用权限
//        if (user.permission.isOperator()) {
//            runCatching {
//                if (durationSeconds != 0) {
//                    MemberTarget.mute(durationSeconds)
//                }
//            }.onSuccess {
//                sendMessage("${At(MemberTarget)} 您的获得了${SettingsConfig.botName}的祝福。")
//            }.onFailure { sendMessage("${SettingsConfig.botName}也想跟群主一起管理") }
//        } else sendMessage("${SettingsConfig.botName}:臣妾做不到啊！")
//    }


}