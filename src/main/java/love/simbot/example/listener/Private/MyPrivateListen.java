package love.simbot.example.listener.Private;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.listener.Group.MyGroupListen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 私聊消息监听的示例类。
 * 所有需要被管理的类都需要标注 {@link Beans} 注解。
 * @author ForteScarlet
 */
@Beans
public class MyPrivateListen {

    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     */
    @Depend
    private MessageContentBuilderFactory builderFactory;
    private static final Logger LOG = LoggerFactory.getLogger(MyGroupListen.class);
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
    private MessageContentBuilder builder;
    String GBGroupCode="";//存放目标群code
    String GBAtPeopleCode="";//存放@的消息
    int GroupMessageLock=0;
    static ExecutorService ThreadPool = Executors.newCachedThreadPool();//创建一个线程池

    /**
     * 此监听函数监听一个私聊消息，并会复读这个消息，然后再发送一个表情。
     * 此方法上使用的是一个模板注解{@link OnPrivate}，
     * 其代表监听私聊。
     * 由于你监听的是私聊消息，因此参数中要有个 {@link PrivateMsg} 来接收这个消息实体。
     *
     * 其次，由于你要“复读”这句话，因此你需要发送消息，
     * 因此参数中你需要一个 "消息发送器" {@link Sender}。
     *
     * 当然，你也可以使用 {@link love.forte.simbot.api.sender.MsgSender}，
     * 然后 {@code msgSender.SENDER}.
     */
    @OnPrivate
    @Filter(value = "/test", trim = true, matchType = MatchType.STARTS_WITH)
    public void replyPrivateMsg1(PrivateMsg privateMsg, Sender sender){
        // 获取消息正文。
        MessageContent msgContent = privateMsg.getMsgContent();
        sender.sendPrivateMsg(privateMsg, msgContent);
        CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();
        String cat3 = catCodeUtil.getStringTemplate().face(9);
        sender.sendPrivateMsg(privateMsg, "表情：" + cat3);
    }
    @OnPrivate
    @Filter(value = "/SGM", trim = true, matchType = MatchType.STARTS_WITH)
    public void senderGroupNotice(PrivateMsg privateMsg, Sender sender){
        int GroupCode = 0;
        String messageConte="";
        MessageContent msgContent ;
        // 获取消息正文。
        String[] messageSplit =privateMsg.getText().split(" ");
        try {
            GroupCode= Integer.parseInt(messageSplit[1]);
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"缺少目标信息");
            return;
        }
        try{
            messageConte=messageSplit[2];
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"缺少正文信息");
            return;
        }
        sender.sendGroupMsg(GroupCode,messageConte);
    }
    @OnPrivate
    @Filter(value = "/目标", trim = true, matchType = MatchType.STARTS_WITH)
    @Filter(value = "/Local", trim = true, matchType = MatchType.STARTS_WITH)
    public void setLocalMessage(PrivateMsg privateMsg, Sender sender){
        String[] messageSplit=privateMsg.getText().split(" ");//切割消息正文
        try{
            GBGroupCode=messageSplit[1];
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"不能没有目标群信息！");
            return;
        }
        try{
            GBAtPeopleCode=messageSplit[2];
        }catch (Exception e){
            GBAtPeopleCode="NULL";
        }
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"会话开始");
                    Thread.sleep(1000);
                    GroupMessageLock=1;
                    Thread.sleep(60*1000);
                    if(GroupMessageLock==1){
                    sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"会话结束");
                    GroupMessageLock=0;
                    GBAtPeopleCode="";
                    GBGroupCode="";
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @OnPrivate
    public void sendGroupMsg(PrivateMsg privateMsg, Sender sender){
        if(GroupMessageLock==1){
            if(GBAtPeopleCode.equals("NULL")){
                sender.sendGroupMsg(GBGroupCode,privateMsg.getMsgContent());
            }
            else{
                MessageContentBuilder builder = builderFactory.getMessageContentBuilder();//消息构建器
                MessageContent mct1=builder.at(GBAtPeopleCode).build();
                sender.sendGroupMsg(GBGroupCode,mct1);
                sender.sendGroupMsg(GBGroupCode,privateMsg.getMsgContent());
            }
        }

    }


    @OnPrivate
    @Filter(value = ".出货", trim = true, matchType = MatchType.STARTS_WITH)
    public void senderGroupAtAll(PrivateMsg privateMsg, Sender sender) {
        builder=builderFactory.getMessageContentBuilder();
        if (privateMsg.getAccountInfo().getAccountCode().equals("2975046967")||privateMsg.getAccountInfo().getAccountCode().equals("1443176972")) {
            String GroupCode = "";
            String PrivateCode = "";
            String[] messageSplit = privateMsg.getText().split(" ");
            try {
                GroupCode = messageSplit[1];
            } catch (Exception e) {
                sender.sendPrivateMsg("1443176972", e.getMessage());
            }
            try {
                PrivateCode = messageSplit[2];
            } catch (Exception e) {
                sender.sendPrivateMsg("1443176972", e.getMessage());
            }
            try {
                System.out.println(" "+GroupCode+" "+PrivateCode);
                MessageContent msg = builder.atAll().text("出货啦，货主").at(PrivateCode).build();
                sender.sendGroupMsg(GroupCode, msg);
            }catch (Exception e){
                String cat3 = catCodeUtil.getStringTemplate().face(9);
                sender.sendGroupMsg(GroupCode,"很抱歉！" + cat3 + "今天群里的@全体次数用完了！");
            }
        }
    }

}
