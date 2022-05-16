package love.simbot.example.listener.Group;

import catcode.CatCodeUtil;
import com.alibaba.fastjson.JSONObject;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.Sender;
import love.simbot.example.tool.HttpClient;
import love.simbot.example.tool.ImageDownloader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Beans
public class GroupDailyTask {
    @Depend
    private MessageContentBuilderFactory messageContentBuilderFactory;
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
    int startlock=0;//启动锁
    long daySpan = 24 * 60 * 60 * 1000;//常量一天的毫秒数
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '8:00:00'");//对象 定义一天的运行时间点
    static Date startTime;//获取首次运行时间
    {
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @OnGroup
    @Filter(value = "/start",trim = true)
    public void DailyNews(GroupMsg groupMsg, Sender sender){
        if(startlock==0){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"二号机初始化完毕！");
            //当startlock为0的时候我们准许它执行一次然后立即把他关闭
            startlock=1;
            //设定定时任务
            if (System.currentTimeMillis() > startTime.getTime())//如果时间已经过去了则推迟一天
                startTime = new Date(startTime.getTime() + daySpan);
            Timer t = new Timer();
            //使用Timer来创建一个每日定时任务
            TimerTask task =new TimerTask() {
                @Override
                public void run() {
                    //创建消息构建器，来发送图片消息
                    MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();
                    //对api执行request
                    String newsUrl="https://api.vvhan.com/api/60s";//api网址
                    //使用工具类执行request操作

                    //下载图片
                    //获取当天的日期
                    Date date = new Date();
                    //日期标准化1得到格式 月日
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String todayStr = simpleDateFormat.format(date);
                    try {
                        String dwImage = ImageDownloader.download(newsUrl, todayStr);
                        File file = new File(dwImage);
                        String image = catCodeUtil.toCat("image", true, "file=" + file.getAbsolutePath());
                        sender.sendGroupMsg(groupMsg.getGroupInfo(), "早间新闻");
                        sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), image);
                        file.delete();
                    }catch (Exception e){
                        sender.sendGroupMsg(groupMsg.getGroupInfo(),"上游API错误!");
                    }
                }
            };
            t.scheduleAtFixedRate(task,startTime,daySpan);
        }
        else{
            String cat3 = catCodeUtil.getStringTemplate().face(21);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "本指令不能够乱用奥！"+cat3);
        }
    }
}
