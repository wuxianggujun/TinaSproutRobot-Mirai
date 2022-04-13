package wxgj.tinasproutrobot.mirai.file

import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group

object BotGroupFile {
    //object 对象声明 对象声明将类的声明与该类的单一实例声明结合到一起，
    // 也就是说，你可以像声明一个类一样声明一个对象，这个对象在定义的时候就创造了
    // ，不需要在任何地方调用构造方法初始化。因此，对象声明没有构造方法，并且是唯一实例
    //listOf()用于创建没有元素的空List
    //val mList:List<String> = listOf();
    //可变集合
    val mList: List<String> = mutableListOf();

    fun getGroupFile(bots: Bot, gId: Long) {
        val bot: Bot = bots.bot
        val group: Group = bot.getGroup(gId) ?: return throw GroupObjectNull("机器人不在此群聊!")

    }

    class GroupObjectNull : Exception {
        //无参数的构造方法
        constructor()

        //带一个字符串参数的构造器
        constructor(msg: String) : super(msg) {}
    }

}