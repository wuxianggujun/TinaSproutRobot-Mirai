package wxgj.tinasproutrobot.mirai.event.command

import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.MemberCommandSenderOnMessage
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.bot.config.SettingsConfig

object UserCommand : CompositeCommand(TinaSproutBotPlugin, "user", description = "普通用户权限") {
    @SubCommand("sleep", "睡眠套餐")
    @Description("我想自闭")
    suspend fun MemberCommandSenderOnMessage.sleep(durationSeconds: Int) {
        runCatching {
            if (durationSeconds != 0) {
                user.mute(durationSeconds)
            }
        }.onSuccess {
            sendMessage("${SettingsConfig.botName}祝你睡个好觉。")
        }.onFailure {
            sendMessage("${SettingsConfig.botName},也无能为力哦!")
        }

    }
}