package wxgj.tinasproutrobot.mirai.test;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AllMessages extends SimpleListenerHost {
    MiraiLogger logger;

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
        logger = event.getBot().getLogger();
//        RemoteFiles files = event.getGroup().getFiles();
//logger.info("RomoteFiles:"+files.toString());
//        AbsoluteFolder root = files.getRoot();
//        logger.debug("AbsoluteFolder:"+root.children());

        if (event.getMessage().contentToString().equals("游历文件")) {
            logger.info("草" + event.getMessage().contentToString());
//            Iterator<RemoteFiles> iterator = event.getGroup().getFiles();
//            List<RemoteFile> list = event.getGroup().getFilesRoot().listFilesCollection();
            RemoteFiles remoteFiles = event.getGroup().getFiles();
            logger.debug("RemoteFiles:" + remoteFiles.getRoot().getName());
            AbsoluteFolder absoluteFile = remoteFiles.getRoot();
            logger.info("AbsoluteFolder:" + absoluteFile.getName());
            Flow<AbsoluteFile> absoluteFolders = remoteFiles.getRoot().files();
            absoluteFolders.collect(new FlowCollector<AbsoluteFile>() {
                @Nullable
                @Override
                public Object emit(AbsoluteFile absoluteFile, @NotNull Continuation<? super Unit> continuation) {
                    logger.info("AbsoluteFile:" + absoluteFile.getName());
                    return null;
                }
            },null);

        }

//        FileMessage fileMessage = event.getMessage().get(FileMessage.Key);
//        if (fileMessage != null) {
//            AbsoluteFile remoteFiles = fileMessage.toAbsoluteFile(event.getGroup());
//            logger.info("文件名字：" + remoteFiles.getName());
//            logger.info("文件链接：" + remoteFiles.getUrl());
//            //return;
//        }


    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        logger.error(exception);

    }

}
