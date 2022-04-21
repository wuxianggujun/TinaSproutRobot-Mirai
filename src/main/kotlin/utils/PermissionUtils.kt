package wxgj.tinasproutrobot.mirai.utils

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.permission.AbstractPermitteeId.*
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.plugin.jvm.AbstractJvmPlugin
import net.mamoe.mirai.contact.Group


object PermissionUtils {

//
//    fun checkCosPermission(bot: Bot, group: Group): Boolean {
//        val targetPermission: Permission = characterCosPermission
//        val exactGroup = ExactMember(group.id, bot.id)
//        return PermissionService.INSTANCE.testPermission(targetPermission,exactGroup)
//    }

    //封装注册权限
    private fun AbstractJvmPlugin.registerPermission(name: String, description: String): Permission {
        return PermissionService.INSTANCE.register(permissionId(name), description, parentPermission)
    }

}