package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin

object HelpCommand : SimpleCommand(TinaSproutBotPlugin, "help", secondaryNames = arrayOf("帮助"), description = "查看指令帮助") {
    //permitteeId.hasPermission(it.permission)
    @Handler
    suspend fun CommandSender.handle() {
        val member = user
        if (member != null) {
            //按道理我给了用户管理员权限，应该打印出管理员权限但是并没有，只是打印了help
            sendMessage(CommandManager.getRegisteredCommands(TinaSproutBotPlugin).asSequence().filter {
                member.permitteeId.hasPermission(it.permission)
            }.joinToString("\n\n") { commands ->
                val lines = commands.usage.lines()
                if (lines.isEmpty()) "/${commands.primaryName} ${commands.description}"
                else
                    "◆ " + lines.first() + "\n" + lines.drop(1).joinToString("\n") { "  $it" }
            }.lines().filterNot(String::isBlank).joinToString("\n"))
        }
    }

}