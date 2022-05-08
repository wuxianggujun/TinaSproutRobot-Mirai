package wxgj.tinasproutrobot.mirai.bot.data

import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData.provideDelegate

object AdminAutoConfig : ReadOnlyPluginConfig("AdminAutoConfig") {
    @ValueName("auto_friend_request")
    @ValueDescription("自动同意好友请求")
    val autoFriendAccept: Boolean by value(false)

    @ValueName("auto_group_request")
    @ValueDescription("自动同意加群请求")
    val autoGroupAccept: Boolean by value(false)

    @ValueName("auto_member_accept")
    @ValueDescription("自动同意新成员请求")
    val autoMemberAccept: Boolean by value(false)

    @ValueName("reply_accept")
    @ValueDescription("回复触发同意请求")
    val replyAccept: String by value("同意|OK|没问题")

    @ValueName("reply_black")
    @ValueDescription("回复触发拉黑请求")
    val replyBlack: String by value("拉黑|黑名单")

}