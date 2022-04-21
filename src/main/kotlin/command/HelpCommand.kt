package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.PermitteeId
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin

object HelpCommand : SimpleCommand(TinaSproutBotPlugin, "help", description = "查看指令帮助") {

    @Override
    @Handler
    public suspend fun CommandSender.handle(override: Override) {
          TinaSproutBotPlugin.logger.info("我就是帮助")
    }
    // for https://github.com/mamoe/mirai-console/issues/416

}