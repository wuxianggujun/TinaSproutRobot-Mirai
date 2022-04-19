package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.cancel
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData.provideDelegate
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData


object AdminCommand : CompositeCommand(
    TinaSproutBotPlugin,
    "admin",
    description = "管理员指令"
) {

    private val adminData = TinaSproutRobotPluginData


    @SubCommand("list", "查看列表")
    suspend fun CommandSender.list() {
        sendMessage("你在干哈")

    }

    @SubCommand("add", "添加")
    suspend fun CommandSender.add(member: NormalMember) {
        AbstractPermitteeId.parseFromString("m864358403.3548346511").permit(AdminCommand.permission)
        //TinaSproutBotPlugin.logger.info("解析的权限：${PermissionId.parseFromString(" aide ")}")

    }
    @SubCommand("add")
    suspend fun CommandSender.add() {
        AbstractPermitteeId.parseFromString("m864358403.3548346511").permit(AdminCommand.permission)
        //TinaSproutBotPlugin.logger.info("解析的权限：${PermissionId.parseFromString(" aide ")}")

    }


    @SubCommand("clear")
    suspend fun CommandSender.clear() {
        sendMessage("你在干哈")
        AbstractPermitteeId.parseFromString("m864358403.3548346511").cancel(AdminCommand.permission, true)
    }



}

object AdminPermissionsData : AutoSavePluginData("AdminPermissionsData"){

    @ValueDescription("管理员列表")
    val adminPermMap:MutableMap<Long,MutableList<Long>> by value(mutableMapOf())

}