package wxgj.tinasproutrobot.mirai.event.file

//import mu.KotlinLogging
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.file.AbsoluteFile
import net.mamoe.mirai.contact.file.AbsoluteFolder
import net.mamoe.mirai.contact.file.RemoteFiles

//private val logger = KotlinLogging.logger {}

object BotGroupFile {
    //object 对象声明 对象声明将类的声明与该类的单一实例声明结合到一起，
    // 也就是说，你可以像声明一个类一样声明一个对象，这个对象在定义的时候就创造了
    // ，不需要在任何地方调用构造方法初始化。因此，对象声明没有构造方法，并且是唯一实例
    //listOf()用于创建没有元素的空List
    //val mList:List<String> = listOf();
    //可变集合
    //val mList: List<String> = mutableListOf();
    private val mList: MutableList<String> = mutableListOf()

    suspend fun getGroupAllFiles(uBot: Bot, gId: Long): MutableList<String>? {
        if (mList.isNotEmpty()) mList.clear()
        try {
            val bot: Bot = uBot
            val group: Group = bot.getGroup(gId) ?: return throw GroupObjectNull("机器人不在此群聊!")
            // 该函数会遍历上级目录的所有文件并匹配当前文件, 因此可能会非常慢, 请不要频繁使用
            group.files.root.refreshed()
            val remoteFiles: RemoteFiles = group.files
            remoteFiles.root.files().collect {
                mList.add(it.name)
            }
            remoteFiles.root.folders().collect {
                it.files().collect { asf: AbsoluteFile ->
                    mList.add(asf.name)
                }
            }
            return mList
        } catch (e: Exception) {
            println(e.cause)
            //logger.error { e.cause }
        }
        return null
    }

    class GroupObjectNull : Exception {
        //无参数的构造方法
        constructor()

        //带一个字符串参数的构造器
        constructor(msg: String) : super(msg) {}
    }


}