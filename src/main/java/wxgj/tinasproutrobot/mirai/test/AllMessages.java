package wxgj.tinasproutrobot.mirai.test;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;

public class AllMessages extends SimpleListenerHost {
    // 所有方法类型
    // T 表示任何 Event 类型.
    // void onEvent(T)
    // Void onEvent(T)
    // ListeningStatus onEvent(T) // 禁止返回 null
    @EventHandler
    private void onFriendMessage(FriendMessageEvent event) {
        if (event.getMessage().contentToString().equals("你好")) {
            event.getSubject().sendMessage("Hello Mirai :)");
        }
    }
}
