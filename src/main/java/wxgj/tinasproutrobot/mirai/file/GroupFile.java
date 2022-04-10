package wxgj.tinasproutrobot.mirai.file;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import wxgj.tinasproutrobot.mirai.JavaPluginMain;

import java.io.File;

public class GroupFile extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // super.handleException(context, exception);
        System.out.println("出错了");
        exception.printStackTrace();
    }

    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event) {
        MiraiLogger logger = event.getBot().getLogger();
        if (event.getGroup().getId() != 537756994) {
            logger.debug("当前群聊不是指定的群");
            return;
        }
        FileMessage fileMessage = null;
        for (SingleMessage SingleMessage : event.getMessage()) {
            if (SingleMessage instanceof FileMessage) {
                logger.debug(String.format("是文件消息 %s", SingleMessage));
                fileMessage = (FileMessage) SingleMessage;
            }

        }
        // 为空代表此条信息无文件，直接结束
        if (fileMessage == null) {
            logger.debug("本次收到的消息中没有匹配到文件消息");
            return;
        }
        File path = JavaPluginMain.INSTANCE.resolveDataFile("/");
        File dataFile = new File(path+"/"+fileMessage.getName());
        logger.info(String.format("此匹配到文件将被下载至目录：%s", dataFile));

        //下载文件
        AbsoluteFile absoluteFile = fileMessage.toAbsoluteFile(event.getGroup());
        String url = absoluteFile.getUrl();
        logger.info("文件路径："+url);

    }


}
