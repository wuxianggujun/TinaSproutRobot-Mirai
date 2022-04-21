package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.SimpleCommand
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin

object HelpCommand : SimpleCommand(TinaSproutBotPlugin, "help", description = "查看指令帮助") {

    @Handler
    public suspend fun CommandSender.handle() {

    }


}