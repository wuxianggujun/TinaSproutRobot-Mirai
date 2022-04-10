package wxgj.tinasproutrobot.mirai.test;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.contact.User;
import wxgj.tinasproutrobot.mirai.JavaPluginMain;

//这样在聊天环境（安装chat-command并分配权限后）发送/echo @<bot> <message>，bot就会复读这个message
public class Echo extends JSimpleCommand {

    public static final Echo INSTANCE = new Echo();

    public Echo() {
        super(JavaPluginMain.INSTANCE, "echo", "复读");
        this.setDescription("复读消息");
    }

    @Handler // 标记这是指令处理器  // 函数名随意
    public void handle(CommandSender sender, User target, String msg) {
        Bot bot = sender.getBot();
        if (bot != null && target.getId() == bot.getId()) { // 判断@对象是否是bot
            sender.sendMessage(msg); // 复读
        }
    }
}
