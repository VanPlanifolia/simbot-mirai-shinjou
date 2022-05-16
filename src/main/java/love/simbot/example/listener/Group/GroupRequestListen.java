package love.simbot.example.listener.Group;

import catcode.CatCodeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ktor.http.Url;
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
import love.simbot.example.tool.HttpClient;
import love.simbot.example.tool.ImageDownloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Beans
public class GroupRequestListen {
    @Depend
    private MessageContentBuilderFactory messageContentBuilderFactory;
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
    static ExecutorService ThreadPool = Executors.newCachedThreadPool();//创建一个线程池
    int requestMusic=1;
    String NameValue="";
    String ResourcesValue="";
    String artValue="";
    @OnGroup
    @Filter(atBot = true,value = "每日壁纸",matchType = MatchType.CONTAINS)
    public void getImageBz(GroupMsg groupMsg, Sender sender){
        try {
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="https://api.sumt.cn/api/bing.php?token=&format=json";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            String result = HttpClient.doGetMessage(imgUrl);
            JSONObject object = JSONObject.parseObject(result);
            //拿到杰森文件imgurl后面的东西
            String value = object.getString("img_url");
            //下载图片
            String dwImage = ImageDownloader.download(value);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
            file.delete();
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取壁纸失败！");
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "来点黄色",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "来张色图",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "来点色图",matchType = MatchType.CONTAINS)
    public void GroupMessageForOther(GroupMsg groupMsg, Sender sender){
        String image=catCodeUtil.toCat("image",true,"file=C:\\Users\\Administrator\\Desktop\\CB\\image\\bkyss.gif");
        sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
    }
    @OnGroup
    @Filter(atBot = true,value = "setu",matchType = MatchType.CONTAINS)
    public void GroupMessageForST(GroupMsg groupMsg, Sender sender){
        //色图API
        String stUrl="https://api.nyan.xyz/httpapi/sexphoto/";
        //使用HttpClient里面的方法获取到api网站里面的内容
        String result = HttpClient.doGetMessage(stUrl);
        JSONObject object = JSONObject.parseObject(result);
        //拿到杰森文件data里面的URL
        JSONObject objectData=object.getJSONObject("data");
        String ImageUrl=objectData.getString("url");
        ImageUrl=ImageUrl.replace("[\"","");
        ImageUrl=ImageUrl.replace("\"]","");
        System.out.println(ImageUrl);
        //下载图片
        String dwImage = ImageDownloader.download(ImageUrl);
        File file=new File(dwImage);
        //猫猫码构建图片消息
        String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
        sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
        file.delete();
    }

    @OnGroup
    @Filter(atBot = true,value = "来点二次元",matchType = MatchType.CONTAINS)
    public void getImageECY(GroupMsg groupMsg, Sender sender){
        try {
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="http://api.mtyqx.cn/api/random.php?return=json";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            String result = HttpClient.doGetMessage(imgUrl);
            JSONObject object = JSONObject.parseObject(result);
            //拿到杰森文件imgurl后面的东西
            String value = object.getString("imgurl");
            //下载图片
            String dwImage = ImageDownloader.download(value);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
            file.delete();
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取壁纸失败！");
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "老婆",matchType = MatchType.CONTAINS)
    public void getImageMen(GroupMsg groupMsg, Sender sender){
        try {
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="https://api.ixiaowai.cn/mcapi/mcapi.php";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            //使用url对链接进行一次request
            URL url = new URL(imgUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.getResponseCode();
            //请求后再次获取到跳转到的真实url
            String realUrl=conn.getURL().toString();
            conn.disconnect();
            //下载图片
            String dwImage = ImageDownloader.download(realUrl);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
            file.delete();
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取壁纸失败！");
        }
    }

    @OnGroup
    @Filter(atBot = true,value = "历史上的今天",matchType = MatchType.CONTAINS)
    public void GroupMessageForHis(GroupMsg groupMsg,Sender sender) {
        MessageContentBuilder MsgBuilder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
        String result = null;
        String msgContext = null;
        //获取当天的日期
        Date date = new Date();
        //日期标准化1得到格式 月日
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MMdd");
        //日期标准化2得到格式 月-日
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd");
        String nowDataNub = simpleDateFormat1.format(date);
        String nowData=simpleDateFormat2.format(date);
        MsgBuilder.text(nowData+"\n").build();
        //对API地址进行拼接
        String ApiUrl = "https://zhufred.gitee.io/zreader/ht/event/";//api网址
        ApiUrl = ApiUrl + nowDataNub + ".json";
        try {
            //使用工具类获取历史上的今天json值
            result = HttpClient.doGetMessage(ApiUrl);
        } catch (Exception e) {
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "获取失败，可能上游API错误！");
        }
        //或缺杰森数组
        JSONArray jsonArray = JSONArray.parseArray(result);
        //便利杰森数组取到所有的 title 值，并且拼接消息正文
        for (int i = 0; i < jsonArray.size(); i++) {
            MsgBuilder.text(jsonArray.getJSONObject(i).getString("title") + "\n").build();
        }
        MessageContent messageContent=MsgBuilder.build();
        sender.sendGroupMsg(groupMsg.getGroupInfo(), messageContent);



    }
    @OnGroup
    @Filter(atBot = true)
    public void GroupMessageForAt(GroupMsg groupMsg, Sender sender) {
        if (groupMsg.getText().equals("")||groupMsg.getText().equals(" ")) {
            try {
                MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
                String imgUrl = "https://v1.hitokoto.cn/";//api网址
                //使用HttpClient里面的方法获取到api网站里面的内容
                String result = HttpClient.doGetMessage(imgUrl);
                JSONObject object = JSONObject.parseObject(result);
                //拿到杰森文件imgurl后面的东西
                String hitokoto = object.getString("hitokoto");
                String from = object.getString("from");
                sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "\u3000"+hitokoto +"\n"+ "FROM:" + from);

            } catch (Exception e) {
                sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), "网络波动，获取words失败！");
            }
        }
    }
    @OnGroup
    @Filter(value = "/atAll", trim = true, matchType = MatchType.STARTS_WITH)
    @Filter(value = "/atall", trim = true, matchType = MatchType.STARTS_WITH)
    @Filter(value = "/at全体", trim = true, matchType = MatchType.STARTS_WITH)
    @Filter(value = "/艾特全体", trim = true, matchType = MatchType.STARTS_WITH)
    public void onGroupMsgForAtAll(GroupMsg groupMsg, Sender sender) {
        String messageContext="";
        MessageContentBuilder builder1 = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器调用at()
        String messageSplit[] = groupMsg.getText().split(" ");//拿到消息text并且split切片
        try {
             messageContext=messageSplit[1];
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"指令格式不对哦\uD83D\uDE44，使用方法 指令+空格+需要@全体的消息正文");
            return;
        }
        try {
            MessageContent msg = builder1.atAll().text(" "+messageContext+"。队长：").at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), msg);
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"很抱歉今天的@全体次数用完了");
            return;
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "来张头像",matchType = MatchType.CONTAINS)
    public void getImageTx(GroupMsg groupMsg, Sender sender){
        try {
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="http://api.btstu.cn/sjtx/api.php?lx=c1&format=json";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            String result = HttpClient.doGetMessage(imgUrl);
            JSONObject object = JSONObject.parseObject(result);
            //拿到杰森文件imgurl后面的东西
            String value = object.getString("imgurl");
            //下载图片
            String dwImage = ImageDownloader.download(value);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image);
            file.delete();
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取头像失败！");
        }
    }

    @OnGroup
    @Filter(value = "/玩家查询",matchType = MatchType.CONTAINS)
    public void getPlayerMessage(GroupMsg groupMsg, Sender sender){
        MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
        String[] split=groupMsg.getText().split(" ");
        String Url="https://socialclub.rockstargames.com/member/";
        try{
            String NewUrl=Url+split[1];
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
//            URL url=new URL(NewUrl);
//            URLConnection con=url.openConnection();
//            //设置请求超时 5sg3
//            con.setConnectTimeout(5*1000);
//            //缓冲流
//            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
//            System.out.println(in);
            sender.sendGroupMsg(groupMsg.getGroupInfo(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo(),NewUrl);
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"指令格式不对奥，使用方式/玩家查询+空格+玩家id");
        }
    }
    @OnGroup
    @Filter(value = "早间新闻",atBot = true,matchType = MatchType.CONTAINS)
    @Filter(value = "每日新闻",atBot = true,matchType = MatchType.CONTAINS)
    @Filter(value = "新闻",atBot = true,matchType = MatchType.CONTAINS)
    public void getDailyNews(GroupMsg groupMsg,Sender sender){
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
        try {
            String todayStr = simpleDateFormat.format(date);
            String dwImage = ImageDownloader.download(newsUrl, todayStr);
            File file = new File(dwImage);
            String image = catCodeUtil.toCat("image", true, "file=" + file.getAbsolutePath());
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(), image);
            file.delete();
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"上游API错误！");
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "来首ACG",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "来首二次元",matchType = MatchType.CONTAINS)
    public void getMusicAcg(GroupMsg groupMsg, Sender sender){
        try {
            //判断是否在15s内没被申请过
            if(requestMusic==0){
                sender.sendGroupMsg(groupMsg.getGroupInfo(),"太快了！请在15s后在进行请求！\uD83D\uDE44");
                return;
            }
            //申请一次后上锁
            requestMusic=0;
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="https://api.paugram.com/acgm/";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            String result = HttpClient.doGetMessage(imgUrl);
            //拿到杰森对象
            JSONObject object = JSONObject.parseObject(result);
            //拿到里面的一系列参数
            NameValue = object.getString("title");
            ResourcesValue=object.getString("link");
            String PicValue=object.getString("cover");
            artValue=object.getString("artist");
            //下载图片

            String dwImage = ImageDownloader.download(PicValue);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image+" "+NameValue+"--"+artValue+"URL:"+ResourcesValue);
            file.delete();
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(15*1000);
                        //15s后解开锁,并且清空歌曲信息
                        requestMusic=1;
                        NameValue="";
                        ResourcesValue="";
                        artValue="";
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取歌曲失败！");
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "今日运势",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "运势",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "占卜",matchType = MatchType.CONTAINS)
    public void getDailyFortune(GroupMsg groupMsg,Sender sender){
        MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
        String ApiUrl="https://api.fanlisky.cn/api/qr-fortune/get/";//api网址
        String requestCode=groupMsg.getAccountInfo().getAccountCode();//获取申请者的qq号
        ApiUrl=ApiUrl+requestCode;//拼装成正确的Url
        //拿到网页里面的信息
        String result=HttpClient.doGetMessage(ApiUrl);
        //获取其中的Json对象
        JSONObject object=JSONObject.parseObject(result);
        //获取杰森里面的data对象
        JSONObject data=object.getJSONObject("data");
        String luckyStar="";
        String signText="";
        String unSignText="";
        try{
            //开始获取data里面的内容
            luckyStar=data.getString("luckyStar");
            signText=data.getString("signText");
            unSignText=data.getString("unSignText");
        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo(),"上游API错误,获取信息失败！");
        }

        //@请求者
        MessageContent mc1=builder.at(requestCode).build();
        builder.clear();
        //将获得到的消息封装成MessageContent
        if(!("".equals(luckyStar)||"".equals(signText)||"".equals(unSignText))){
            MessageContent mc2=builder.text("今日运势："+luckyStar+"\n"+"\n").text("占卜结果："+signText+"\n"+"\n").text("人话："+unSignText).build();
            //送信器发送
            sender.sendGroupMsg(groupMsg.getGroupInfo(),mc1);
            sender.sendGroupMsg(groupMsg.getGroupInfo(),mc2);
        }
    }
    @OnGroup
    @Filter(atBot = true,value = "来首音乐",matchType = MatchType.CONTAINS)
    @Filter(atBot = true,value = "唱支歌",matchType = MatchType.CONTAINS)
    public void getMusic(GroupMsg groupMsg, Sender sender){
        try {
            //判断是否在15s内没被申请过
            if(requestMusic==0){
                sender.sendGroupMsg(groupMsg.getGroupInfo(),"太快了！请在15s后在进行请求！\uD83D\uDE44");
                return;
            }
            //申请一次后上锁
            requestMusic=0;
            MessageContentBuilder builder = messageContentBuilderFactory.getMessageContentBuilder();//消息构建器
            String imgUrl="https://api.uomg.com/api/rand.music&format=json";//api网址
            //使用HttpClient里面的方法获取到api网站里面的内容
            String result = HttpClient.doGetMessage(imgUrl);
            //拿到杰森对象
            JSONObject object = JSONObject.parseObject(result);
            //拿到杰森对象中的data对象
            JSONObject object1=object.getJSONObject("data");
            //拿到杰森data对象里面的一系列参数
            NameValue = object1.getString("name");
            ResourcesValue=object1.getString("url");
            String PicValue=object1.getString("picurl");
            artValue=object1.getString("artistsname");
            //下载图片
            String dwImage = ImageDownloader.download(PicValue);
            File file=new File(dwImage);
            //猫猫码构建图片消息
            String image=catCodeUtil.toCat("image",true,"file="+file.getAbsolutePath());
            MessageContent mct1=builder.at(groupMsg.getAccountInfo().getAccountCode()).build();
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),mct1);
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),image+" "+NameValue+"--"+artValue+"URL:"+ResourcesValue);
            file.delete();
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(15*1000);
                        //15s后解开锁,并且清空歌曲信息
                        requestMusic=1;
                        NameValue="";
                        ResourcesValue="";
                        artValue="";
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            sender.sendGroupMsg(groupMsg.getGroupInfo().getGroupCode(),"网络波动，获取歌曲失败！");
        }
    }

//    @OnGroup
//    @Filter(value = "/获取音乐文件")
//    public void getMusicSources(GroupMsg groupMsg, Sender sender){
//        if(!NameValue.equals("")){
//            //下载音乐，
//            String dwMusic=this.dowLoadMusic(ResourcesValue,NameValue,artValue);
//            //关联文件
//            File file=new File(dwMusic);
//            //猫猫码构建音乐消息
//            String music=catCodeUtil.toCat("voice",true,"file="+file.getAbsolutePath());
//            sender.sendGroupMsg(groupMsg.getGroupInfo(),music);
//            file.delete();
//        }
//    }
    private String dowLoadMusic(String musUrl,String musName,String musArt ){
        String filePath="";
        try {
            //获取url
            URL url=new URL(musUrl);
            //建立链接
            URLConnection con=url.openConnection();
            //设置请求超时 5sg3
            con.setConnectTimeout(5*1000);
            //缓冲流
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            //文件名字
            filePath = "C:\\Users\\Administrator\\Desktop\\CB\\image\\" + musName+"-"+musArt + ".mp3";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] buffer = new byte[1024*8];
            int count;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            //关闭流
            out.close();
            in.close();
            return filePath;
        }catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }

//    public  String downloadImg(String imgurl){
//        String filePath="";
//        try{
//            URL url = new URL(imgurl);
//            //打开链接
//            URLConnection con = url.openConnection();
//            //设置请求超时 5sg3
//            con.setConnectTimeout(5*1000);
////            InputStream inputStream = con.getInputStream();
//            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
//            //文件名字
//            Random R=new Random();
//            int random=R.nextInt(1000);
////             filePath = "C:\\Users\\Administrator\\Desktop\\CB\\image\\" + random + ".png";
//             filePath = "C:\\Users\\崔震云\\Desktop\\CB\\image\\" + random + ".png";
//            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
//            byte[] buffer = new byte[1024*8];
//            int count;
//            while ((count = in.read(buffer)) > 0) {
//                out.write(buffer, 0, count);
//            }
//            //关闭流
//            out.close();
//            in.close();
//            return filePath;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return filePath;
//    }
}
