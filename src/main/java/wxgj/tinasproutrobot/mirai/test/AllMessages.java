package wxgj.tinasproutrobot.mirai.test;

import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.utils.MiraiLogger;

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

    @EventHandler
    private void onGroupMessage(GroupMessageEvent event) {
        MiraiLogger logger = event.getBot().getLogger();
//        RemoteFiles files = event.getGroup().getFiles();
//logger.info("RomoteFiles:"+files.toString());
//        AbsoluteFolder root = files.getRoot();
//        logger.debug("AbsoluteFolder:"+root.children());
        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
        if (fileMessage != null) {
            AbsoluteFile remoteFiles = fileMessage.toAbsoluteFile(event.getGroup());
            logger.info("文件名字：" + remoteFiles.getName());
            logger.info("文件链接：" + remoteFiles.getUrl());
            return;
        }

    }


}
