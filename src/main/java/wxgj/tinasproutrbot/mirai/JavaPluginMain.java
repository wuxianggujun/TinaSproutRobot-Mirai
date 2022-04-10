package wxgj.tinasproutrbot.mirai;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import wxgj.tinasproutrbot.mirai.test.AllMessages;
import wxgj.tinasproutrbot.mirai.test.Echo;


/*
使用java请把
src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin
文件内容改成"wxgj.tinasproutrbot.mirai.JavaPluginMain"也就是当前主类
使用java可以把kotlin文件夹删除不会对项目有影响

在settings.gradle.kts里改生成的插件.jar名称
build.gradle.kts里改依赖库和插件版本
在主类下的JvmPluginDescription改插件名称，id和版本
用runmiraikt这个配置可以在ide里运行，不用复制到mcl或其他启动器
 */
    public  class JavaPluginMain extends JavaPlugin {
        public static final JavaPluginMain INSTANCE = new JavaPluginMain();

        private JavaPluginMain() {
            super(new JvmPluginDescriptionBuilder("wxgj.tinasporutrobot.mirai.plugin", "0.1.0")
                    .info("EG")
                    .build());
        }

        @Override
        public void onEnable() {
            long qqBotNo = 2405024938L;

            getLogger().info("日志"+qqBotNo);
            //注册命令
            CommandManager.INSTANCE.registerCommand(Echo.INSTANCE, false);
    //        //GlobalEventChannel.INSTANCE.registerListenerHost(new AllMessages());
    //        //只在消息有 At 的时候才触发事件
    //        EventChannel channel = GlobalEventChannel.INSTANCE.filter(e -> {
    //            if (e instanceof MessageEvent) {
    //                return ((MessageEvent) e).getMessage().contains(At.Key);
    //            }
    //            return false;
    //        });
    //        channel.registerListenerHost(new AllMessages());

            GlobalEventChannel.INSTANCE.filterIsInstance(BotOnlineEvent.class).filter(event -> event.getBot().getId() == qqBotNo).subscribeAlways(BotOnlineEvent.class, event -> {
                Bot bot = event.getBot();
                EventChannel<BotEvent> eventChannel = bot.getEventChannel();
                eventChannel.registerListenerHost(new AllMessages());

            });

    //        // java:
    //// 这样写只是方便理解，实际上可以缩写成下面这句
    //// GlobalEventChannel.INSTANCE.filter(e -> (e instanceof MessageEvent) && ((MessageEvent)e).getMessage().contains(At.Key));
    //        EventChannel<Event> channel = GlobalEventChannel.INSTANCE.filter(e -> {
    //            if (e instanceof MessageEvent) {
    //                return ((MessageEvent) e).getMessage().contains(At.Key);
    //            }
    //            return false;
    //        });
    //// java:
    //       // GlobalEventChannel.INSTANCE.registerListenerHost();

        }

        @Override
        public void onDisable() {
            super.onDisable();
        }
    }