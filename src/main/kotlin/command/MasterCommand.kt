package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin.save
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData

object MasterCommand : CompositeCommand(TinaSproutBotPlugin, "master", description = "主人命令") {

    private val config = TinaSproutRobotPluginData
    private val logger = TinaSproutBotPlugin.logger

    @SubCommand
    @Description("添加管理员")
    suspend fun CommandSender.add(member: NormalMember) {
        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
        if (config.adminMap.containsKey(bot!!.id)) {
            list.add(member.id)
            config.adminMap[bot!!.id] = list
            sendMessage("${member.id}添加成功")
        } else {
            list.add(member.id)
            config.adminMap[bot!!.id] = list
            logger.info("添加成功")
            sendMessage("${member.id}添加成功")
        }
        config.save()
    }

    @SubCommand
    @Description("清除全部管理员")
    suspend fun CommandSender.clear() {
        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
        if (config.adminMap.containsKey(bot!!.id) && !list.isNullOrEmpty()) {
            list.clear()
            config.adminMap[bot!!.id] = list
            sendMessage("${list.size}位,清除成功！")
        }
        config.save()
    }

    @SubCommand
    @Description("清除管理员")
    suspend fun CommandSender.clear(member: NormalMember) {
        val list: MutableList<Long> = config.adminMap.getValue(bot!!.id)
        if (config.adminMap.containsKey(bot!!.id) && !list.isNullOrEmpty()) {
            list.remove(member.id)
            config.adminMap[bot!!.id] = list
            sendMessage("已经删除${member.id}管理员")
        }
        config.save()
    }


}