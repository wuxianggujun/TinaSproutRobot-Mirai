package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandOwner
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.permission.PermitteeId
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin

object HelpCommand : SimpleCommand(TinaSproutBotPlugin, "help", secondaryNames = arrayOf("帮助"),description = "查看指令帮助") {

    @Handler
    suspend fun CommandSender.handle() {
          TinaSproutBotPlugin.logger.info("我就是帮助")
        CommandManager.getRegisteredCommands(TinaSproutBotPlugin).filter {
            permitteeId.hasPermission(it.permission)
        }.map {
            sendMessage(it.primaryName)
        }
    }
    // for https://github.com/mamoe/mirai-console/issues/416

}