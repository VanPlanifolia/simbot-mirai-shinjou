package love.simbot.example.listener.Group;
import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.listener.Group.MyGroupListen;
import love.simbot.example.tool.TimerPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 此类仅用于处理计时请求
 */
@Beans
public class MyTimerListen {
    int timeLength;
    TimerPlus t[]=new TimerPlus[5];
    int VacancyNumber=5;
    String TimerRemark=null;
    @Depend
    private MessageContentBuilderFactory builderFactory;
    private static final Logger LOG = LoggerFactory.getLogger(MyGroupListen.class);
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
    @OnGroup
    @Filter(value = "/Timer" ,matchType = MatchType.CONTAINS)
    @Filter(value = "/timer" ,matchType = MatchType.CONTAINS)
    public void requestForTimer(GroupMsg groupMsg, Sender sender){
        if(VacancyNumber<=0){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"当前计时器队列已满，请稍后尝试！🥺");
            return;
        }
        //拿到消息字符串
        String MsgText=groupMsg.getText();
        //切割字符串
        String [] splits=MsgText.split(" ");
        try {
            //判断一下指令的第二个参数是否能够转化成int类型的
            timeLength = Integer.parseInt(splits[1]);
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"指令格式不对哦！使用格式/Timer 参数1 参数2，参数1：计时时长，参数2：任务名字");
        }
        try{
            //保存计时任务的名字，没有的话就抛一下异常
            TimerRemark=splits[2];
        }catch (Exception e){}
        //便利所有对象找到非空的，创建一个计时器
        for (int i = 0; i < 5; i++) {
            if(t[i]==null){
                if(TimerRemark!=null){
                    //获取日期
                    Date dd=new Date();
                    SimpleDateFormat sim=new SimpleDateFormat("HH:mm:ss");
                    String time=sim.format(dd);
                    MessageContentBuilder builder1 = builderFactory.getMessageContentBuilder();//消息构建器
                    //创建对象并且为其设一个标记值
                    t[i]=new TimerPlus();
                    t[i].setName(TimerRemark+i);
                    MessageContent msg1 = builder1.at(groupMsg.getAccountInfo().getAccountCode()).text("当前时间["+time+"]任务:"+TimerRemark+" 开始计时，" + timeLength + "分钟！").build();
                    sender.sendGroupMsg(groupMsg.getGroupInfo(),msg1);
                    //创建成功后计时器数量减一
                    VacancyNumber--;
                    //匿名内部类创建一个TimerTask
                    int finalI1 = i;
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            MessageContentBuilder builder2 = builderFactory.getMessageContentBuilder();//消息构建器
                            MessageContent msg1 = builder2.at(groupMsg.getAccountInfo().getAccountCode()).text("时间到了！").build();
                            sender.sendGroupMsg(groupMsg.getGroupInfo(),msg1);
                            //在任务结束后将对象置空
                            t[finalI1]=null;
                            //空位加一
                            VacancyNumber++;
                            }
                    };
                    //执行schedule后置空TimerRemark
                    t[i].schedule(task,timeLength*60000);
                    TimerRemark=null;
                    break;
                }

                else{
                    MessageContentBuilder builder1 = builderFactory.getMessageContentBuilder();//消息构建器
                    Date dd=new Date();
                    //格式化
                    SimpleDateFormat sim=new SimpleDateFormat("HH:mm:ss");
                    String time=sim.format(dd);
                    t[i]= (TimerPlus) new Timer(String.valueOf(i));
                    t[i].setName(String.valueOf(i));
                    MessageContent msg1 = builder1.at(groupMsg.getAccountInfo().getAccountCode()).text("当前时间["+time+"] 任务:"+i+" 开始计时，" + timeLength + "分钟！").build();
                    sender.sendGroupMsg(groupMsg.getGroupInfo(),msg1);
                    VacancyNumber--;
                    int finalI = i;
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            MessageContentBuilder builder2 = builderFactory.getMessageContentBuilder();//消息构建器
                            MessageContent msg1 = builder2.at(groupMsg.getAccountInfo().getAccountCode()).text("时间到了！").build();
                            sender.sendGroupMsg(groupMsg.getGroupInfo(),msg1);
                            t[finalI]=null;
                            VacancyNumber++;
                        }
                    };
                    t[i].schedule(task,timeLength*60000);
                    break;
                }
            }
        }
    }
    @OnGroup
    @Filter(value = "/number" ,matchType = MatchType.CONTAINS)
    @Filter(value = "/number" ,matchType = MatchType.CONTAINS)
    public void TimerNumber(GroupMsg groupMsg, Sender sender){
        sender.sendGroupMsg(groupMsg.getGroupInfo(), String.valueOf(VacancyNumber));
    }

    @OnGroup
    @Filter(value = "/stop" ,matchType = MatchType.CONTAINS)
    @Filter(value = "/Stop" ,matchType = MatchType.CONTAINS)
    public void requestForStop(GroupMsg groupMsg, Sender sender){
        String name = null;
        if(VacancyNumber>=5){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"当前没有人计时！😋");
            return;
        }
        //拿到消息字符串
        String MsgText=groupMsg.getText();
        //切割字符串
        String [] splits=MsgText.split(" ");
        try {
            //判断一下指令的第二个参数是否能够转化成int类型的
            name = splits[1];
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"指令格式不对哦！使用格式/stop 参数1，参数1：任务名称");
        }
        //便利所有对象数组，找到非空的然后在判断找到name值相同的
        for (int i = 0; i < 5; i++) {
            if(t[i]!=null){
                if(t[i].getName().equals(name)){
                    //找到后停止任务
                    t[i].cancel();
                    MessageContentBuilder builder3 = builderFactory.getMessageContentBuilder();//消息构建器
                    MessageContent msg1 = builder3.at(groupMsg.getAccountInfo().getAccountCode()).text("任务:"+t[i].getName()+"已终止！").build();
                    sender.sendGroupMsg(groupMsg.getGroupInfo(),msg1);
                    VacancyNumber++;
                    t[i]=null;
                    break;
                }
            }
        }
    }
}

