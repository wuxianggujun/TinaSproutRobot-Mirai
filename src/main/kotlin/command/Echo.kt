package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.User

object Echo : SimpleCommand(PluginMain.INSTANCE, primaryName = "echo", secondaryNames = arrayOf("复读"), description = "复读消息") {
    @Handler//标记这是指令处理器 //函数名随意
    suspend fun CommandSender.handle(target: User, message: String) {//这两个参数会作为指令参数要求
        if (target.id == bot?.id) {//判断对象是不是@机器人
            sendMessage(message);//复读
        }
    }
}