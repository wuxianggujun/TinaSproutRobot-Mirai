package wxgj.tinasproutrobot.mirai.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.PermissionService.Companion.cancel
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.NormalMember
import wxgj.tinasproutrobot.mirai.TinaSproutBotPlugin
import wxgj.tinasproutrobot.mirai.bot.data.GroupPermissionData.provideDelegate
import wxgj.tinasproutrobot.mirai.bot.data.TinaSproutRobotPluginData
import wxgj.tinasproutrobot.mirai.command.AdminCommand.add
import wxgj.tinasproutrobot.mirai.command.interf.CommandPermInterface


object AdminCommand : CompositeCommand(
    TinaSproutBotPlugin,
    "admin",
    description = "管理员指令"
) {

    private val adminData = AdminPermissionsData

    @SubCommand("list", "查看列表")
    suspend fun CommandSender.list() {
        sendMessage("你在干哈")

    }

    @SubCommand("add", "添加")
    suspend fun CommandSender.add(member: NormalMember) {
        val group = adminData.grouplist
        if (group != null) {
            if (!group.containsKey(member.group.id)) {
                group[member.group.id] = false
            }
            if (adminData.adminPermMap.containsKey(member.group.id)) {
                val groupAdminList = adminData.adminPermMap.get(member.group.id)
                if (!groupAdminList!!.contains(member.id)) {
                    groupAdminList.add(member.id)
                }
            } else {
                val adminList = mutableListOf<Long>()
                adminList.add(member.id)
                adminData.adminPermMap[member.group.id] = adminList
                TinaSproutBotPlugin.logger.info("添加到管理员列表")
            }


            PermissionService.INSTANCE.getRegisteredPermissions().forEach { P ->
                TinaSproutBotPlugin.logger.info("注册的权限:${P.id}")
                if (P.id == AdminCommand.permission.id) {
                    TinaSproutBotPlugin.logger.info(":${P.id}权限等于${permission.id}")
                    adminData.adminPermMap.filter { (key, value) ->
                        key == member.group.id
                    }.map {
                        it.value.forEach { v ->
                            val sb = StringBuilder()
                            sb.append("m")
                            sb.append(it.key)
                            sb.append(".")
                            sb.append(v)
                            TinaSproutBotPlugin.logger.info("SB：${sb.toString()}")
                            TinaSproutBotPlugin.logger.info("列表里面的V：${v.toString()}")

                            AbstractPermitteeId.parseFromString(sb.toString()).permit(AdminCommand.permission)
                        }
                    }
                }
            }

        }
        //AbstractPermitteeId.parseFromString("m864358403.3548346511").permit(AdminCommand.permission)

    }


    @SubCommand("clear")
    suspend fun CommandSender.clear() {
        sendMessage("你在干哈")
        AbstractPermitteeId.parseFromString("m864358403.3548346511").cancel(AdminCommand.permission, true)
    }



}

object AdminPermissionsData : AutoSavePluginData("AdminPermissionsData") {
    //    boolean 表示是否在本群开启机器人
    @ValueDescription("群列表")
    val grouplist: MutableMap<Long, Boolean> by value(mutableMapOf())

    @ValueDescription("管理员列表")
    val adminPermMap: MutableMap<Long, MutableList<Long>> by value(mutableMapOf())

}