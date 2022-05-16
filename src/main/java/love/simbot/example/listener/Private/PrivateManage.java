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

import java.io.*;
import java.util.Arrays;

@Beans
public class PrivateManage {
    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     */
    @Depend
    private MessageContentBuilderFactory builderFactory;
    private static final Logger LOG = LoggerFactory.getLogger(MyGroupListen.class);
    CatCodeUtil catCodeUtil = CatCodeUtil.getInstance();//使用猫猫码
//    MessageContentBuilder builder = builderFactory.getMessageContentBuilder();//消息构建器
    //创建文件类 GroupMessageFile，用于操作被管理的群号文件信息
    File GroupMessageFile=new File("C:\\Users\\Administrator\\Desktop\\CB\\GroupMessage.txt");
//    File GroupMessageFile=new File("C:\\Users\\崔震云\\Desktop\\CB\\GroupMessage.txt");

    //群组信息的数组
    String [] GroupMessage =new String[10];

    /**
     * 本方法用于添加一个需要被管理的群
     * @param privateMsg
     * @param sender
     */
    @OnPrivate
    @Filter(value = "/添加群组",matchType = MatchType.CONTAINS)
    public void addGroupMessage(PrivateMsg privateMsg, Sender sender){
        String[] messageSplit=privateMsg.getText().split(" ");//切割消息正文
        //拿到第二位置的字符串，也就是需要被添加的群组信息
        String groupCode;
        try{
            groupCode=messageSplit[1];
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"消息格式不对奥。");
            return;
        }
        if(!(groupCode==null&&groupCode.equals(""))){
            FileWriter fileWriter=null;
            try {
                //创建文件写入者，并且设定为追加的形式写入
                fileWriter=new FileWriter(GroupMessageFile,true);
            } catch (IOException e) {
                sender.sendPrivateMsg(privateMsg.getAccountInfo(),"打开文件失败，请检查文件路径是否正确");
                return;
            }
            //文件的IO操作
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            try {
                //写入一行数据并且换行
                bufferedWriter.write(groupCode+"\r"+"\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                sender.sendPrivateMsg(privateMsg.getAccountInfo(),"写入文件失败！");
                return;
            }finally {
                try {
                    //关闭之前开启的IO资源
                    if(bufferedWriter!=null){bufferedWriter.close();}
                    if(fileWriter!=null){fileWriter.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!groupCode.equals("")){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"群组："+groupCode+"添加成功！");
        }
    }
    @OnPrivate
    @Filter(value ="/群组列表")
    public void GroupList(PrivateMsg privateMsg,Sender sender){
        MessageContentBuilder builder = builderFactory.getMessageContentBuilder();//消息构建器
        builder.text("群组列表 \n").build();
        BufferedReader bufferedReader=null;
        FileReader fileReader=null;
        try {
            //创建文件读取者读取群组信息文件
            fileReader=new FileReader(GroupMessageFile);
            bufferedReader=new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"打开文件失败，请检查文件路径是否正确");
        }
        String rowMessage;
        try{
            //读取每一行的信息然后添加的消息构建器里面
            while((rowMessage=bufferedReader.readLine())!=null){
                builder.text(rowMessage+"\n").build();
            }
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"读取文件信息失败");
        }finally {
            //关闭IO资源
                try {
                    if(fileReader!=null){fileReader.close();}
                    if(bufferedReader!=null){bufferedReader.close();}
                }catch (IOException e) {
                    e.printStackTrace();
            }
        }
        //构建信息，然后发送
        MessageContent mc=builder.build();
        sender.sendPrivateMsg(privateMsg.getAccountInfo(),mc);
    }
    @OnPrivate
    @Filter(value = "/删除群组",trim = true, matchType = MatchType.CONTAINS)
    public void removeGroup(PrivateMsg privateMsg,Sender sender){
        String[] messageSplit=privateMsg.getText().split(" ");//切割消息正文
        //拿到第二位置的字符串，也就是需要被添加的群组信息
        int index=0;
        try{
            index= Integer.parseInt(messageSplit[1])-1;
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"消息格式不对奥。");
        }
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        //先将消息读取到数组中，然后操作数组，然后将新的数组存到文件里
        try {
            fileReader=new FileReader(GroupMessageFile);
            bufferedReader=new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"打开文件失败，请检查文件路径是否正确");
        }
        //读取每一行到数组里面
        String rowMessage;
        try {
            int i=0;
            while((rowMessage=bufferedReader.readLine())!=null){
                GroupMessage[i]=rowMessage;
                i++;
            }
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"读取文件信息失败");
        }
            //将第index位置的置为“”
            GroupMessage[index]="";
        System.out.println(Arrays.toString(GroupMessage));
        String allCode = "";
        for (int i = 0; i < GroupMessage.length; i++) {
            if(GroupMessage[i]==null){
                break;
            }
            else if(GroupMessage[i].equals("")){
                continue;
            }
            else {
                allCode=allCode+GroupMessage[i]+"\n";
            }
        }
        FileWriter fileWriter=null;
        BufferedWriter bufferedWriter=null;
        try {
            fileWriter=new FileWriter(GroupMessageFile,false);
            bufferedWriter=new BufferedWriter(fileWriter);
        } catch (IOException e) {
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"读取文件失败，请检查文件路径");
        }
        try {
            bufferedWriter.write(allCode);
            bufferedWriter.flush();
        }catch (Exception e){
            sender.sendPrivateMsg(privateMsg.getAccountInfo(),"写入文件失败");
        }finally {
            //关闭IO资源
            try {
                if(bufferedReader!=null){bufferedReader.close();}
                if(fileReader!=null){fileReader.close();}
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        sender.sendPrivateMsg(privateMsg.getAccountInfo(),"第"+index+"行删除完毕");
    }
}
