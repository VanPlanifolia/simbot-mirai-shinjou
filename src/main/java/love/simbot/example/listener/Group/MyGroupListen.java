package love.simbot.example.listener.Group;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 群消息监听的示例类。
 * 所有需要被管理的类都需要标注 {@link Beans} 注解。
 * @author ForteScarlet
 */
@Beans
public class MyGroupListen {
    @Depend
    private MessageContentBuilderFactory builderFactory;
    private static final Logger LOG = LoggerFactory.getLogger(MyGroupListen.class);
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
    private MessageContentBuilder builder;

//    @OnGroup
//    @Filter(atBot = true)
//    public void test1(GroupMsg groupMsg,Sender sender){
//        sender.sendGroupMsg(groupMsg.getGroupInfo(),groupMsg.getMsgContent());
//    }
//    @OnGroup
//    @Filter(value = ".出货", trim = true, matchType = MatchType.STARTS_WITH)
//    @Filter(value = ".出车", trim = true, matchType = MatchType.STARTS_WITH)
//    public void onGroupMsgForGoods1(GroupMsg groupMsg, Sender sender) {
//        String peopleNub = groupMsg.getAccountInfo().getAccountCode();//获得申请计时器人的qq信息
//        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();//消息构建器调用atAll()方法
//        if (peopleNub.equals("2975046967")) {
//            try {
//            String messageStr = groupMsg.getText();//拿到消息正文的字符串
//            String messageSplit[] = messageStr.split(" ");//空格作为隔断符号分割字符串
//            int Guid = Integer.parseInt(messageSplit[1]);
//            MessageContent msg = builder.atAll().text("出货啦，货主").at(Guid).build();
//            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), msg);
//
//                } catch (Exception e) {
//                String cat3 = catCodeUtil.getStringTemplate().face(9);
//                sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "很抱歉！" + cat3 + "今天群里的@全体次数用完了");
//                }
//        }
//        else{
//                String cat4 = catCodeUtil.getStringTemplate().face(21);
//                sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "请先使用Takarada Rikka!" + cat4);
//            }
//
//    }


}










