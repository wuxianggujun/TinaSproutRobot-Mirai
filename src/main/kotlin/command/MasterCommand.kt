package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin.save
import wxgj.tinasproutrobot.mirai.bot.data.GroupData
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData

object MasterCommand : CompositeCommand(TinaSproutBotPlugin, "master", description = "主人命令") {

    //QQ群机器人配置
    private val groupData = GroupData

    //日志
    private val logger = TinaSproutBotPlugin.logger

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