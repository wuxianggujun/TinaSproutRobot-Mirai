package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin.save
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginConfig
import wxgj.tinasproutrobot.mirai.bot.TinaSproutRobotPluginData

object MasterCommand : CompositeCommand(TinaSproutBotPlugin, "master", description = "主人命令") {

    private val config = TinaSproutRobotPluginData
    private val logger = TinaSproutBotPlugin.logger

    @SubCommand
    @Description("添加管理员")
    suspend fun CommandSender.addAdmin(member: NormalMember) {
        if (TinaSproutRobotPluginConfig.master != user!!.id) {
            sendMessage("你不是我的主人！")
            return
        }
        //写添加管理员到列表
        if (TinaSproutRobotPluginConfig.master == member.id) {
            sendMessage("主人不能添加为管理员!")
            return
        }

        if (bot!!.id !in config.adminMap.keys) {
            val adminList = mutableListOf<Long>()
            adminList.add(member.id)
            config.adminMap[bot!!.id] = adminList
            sendMessage("创建并且添加管理员成功,主人$user")
        } else {
            config.adminMap.getValue(bot!!.id).add(member.id)
            sendMessage("添加管理员成功,主人$user")
        }
        config.save()
        return
    }


    @SubCommand
    suspend fun CommandSender.add(member: NormalMember) {
        val list:MutableList<Long> = config.adminMap.getValue(bot!!.id)
        if (config.adminMap.containsKey(bot!!.id)) {
            list.add(member.id)
            config.adminMap[bot!!.id] = list
            logger.info("已经存在,但还是添加成功")
        }else{
            list.add(member.id)
            config.adminMap[bot!!.id] = list
            logger.info("添加成功")
        }
        config.save()
    }

    @SubCommand
    suspend fun CommandSender.clear() {
        val list:MutableList<Long> = config.adminMap.getValue(bot!!.id)
        if (config.adminMap.containsKey(bot!!.id)&&!list.isNullOrEmpty()){
            list.clear()
            config.adminMap[bot!!.id] = list
            logger.info("清除成功")
        }
        config.save()
    }


}