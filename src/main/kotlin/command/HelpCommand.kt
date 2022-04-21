package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin

object HelpCommand :
    SimpleCommand(TinaSproutBotPlugin, "help", secondaryNames = arrayOf("帮助"), description = "查看指令帮助") {

    @Handler
    suspend fun CommandSender.handle() {
        TinaSproutBotPlugin.logger.info("我就是帮助")
        CommandManager.getRegisteredCommands(TinaSproutBotPlugin).asSequence().filter {
            permitteeId.hasPermission(it.permission)
        }.joinToString("\n\n") { commands ->
            val lines = commands.usage.lines()
            if (lines.isEmpty()) "/${commands.primaryName} ${commands.description}"
            else
                "◆ " + lines.first() + "\n" + lines.drop(1).joinToString("\n") { "  $it" }
        }.lines().filterNot(String::isBlank).joinToString("\n")
    }


}