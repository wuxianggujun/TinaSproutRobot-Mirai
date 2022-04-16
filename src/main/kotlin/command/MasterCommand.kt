package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginConfig
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData

object MasterCommand : CompositeCommand(TinaSproutBotPlugin, "master", description = "主人命令") {

    private val config = TinaSproutRobotPluginData
    private val logger = TinaSproutBotPlugin.logger

    @SubCommand
    suspend fun CommandSender.addAdmin(member: NormalMember) {
        if (TinaSproutRobotPluginConfig.master != user!!.id) {
            sendMessage("你不是我的主人！")
            return
        }
        //写添加管理员到列表

        if (bot!!.id !in config.adminMap) {
            val adminList: MutableList<Long> = mutableListOf<Long>()
            adminList.add(member.id)
            config.adminMap[bot!!.id] = adminList
            logger.info("列表创建成功")
            sendMessage("创建并且添加管理员成功,主人$user")
        } else {
//            val adminList:MutableList<Long> = config.adminMap.getValue(bot!!.id)
//            adminList.add(member.id)
//            config.adminMap[bot!!.id] = adminList
            config.adminMap.getValue(bot!!.id).add(member.id)
            logger.info("添加成功")
            sendMessage("添加管理员成功,主人$user")

        }
        for (config in config.adminMap) {
            sendMessage("cao:${config}")
        }

    }

}