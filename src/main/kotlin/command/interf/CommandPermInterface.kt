package wxgj.tinasproutrobot.mirai.command.interf

import net.mamoe.mirai.contact.Member

interface CommandPermInterface {
    //添加
   fun add(member: Member)
   //清除
   fun clear()

}