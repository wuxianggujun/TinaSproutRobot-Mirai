package wxgj.tinasproutrobot.mirai.file;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

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
        if (event.getGroup().getId() != 537756994){
            logger.debug("当前群聊不是指定的群");
            return;
        }

    }
}
