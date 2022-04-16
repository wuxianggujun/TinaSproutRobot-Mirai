package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin


object AdminCommand : CompositeCommand(
    TinaSproutBotPlugin,
    "admin",
    description = "管理员指令"
) {


    @SubCommand("list", "查看列表")
    suspend fun CommandSender.list() {
        sendMessage("你在干哈")

    }

}