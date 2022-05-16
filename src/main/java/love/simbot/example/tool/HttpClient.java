package love.simbot.example.tool;

import com.sun.jndi.toolkit.url.Uri;
import love.forte.simbot.api.sender.Sender;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;

public class HttpClient {
    public static String doGetMessage(String ApiUrl){
        CloseableHttpResponse response = null;
        String returnMessage="";//返回的网页内容
        //生成httpClient相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //使用uri进行httpGet
            HttpGet httpGet=new HttpGet(String.valueOf(ApiUrl));
            //相当于回车操作
            response=httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                //如果成功获取将浏览器里面的内容拿到放到returnMessage里面
                returnMessage= EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {//关闭所有链接
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回一个消息
        return returnMessage;

    }
}
