package love.simbot.example.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class ImageDownloader {
    public static String download(String imgurl) {
        String filePath = "";
        try {
            URL url = new URL(imgurl);
            //打开链接
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/4.76");
            url.openConnection().setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //设置请求超时 5sg3
            con.setConnectTimeout(5 * 1000);
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            //文件名字
            Random R = new Random();
            int random = R.nextInt(1000);
            filePath = "C:\\Users\\Administrator\\Desktop\\CB\\image\\" + random + ".png";
//             filePath = "C:\\Users\\崔震云\\Desktop\\CB\\image\\" + random + ".png";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] buffer = new byte[1024 * 8];
            int count;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            //关闭流
            out.close();
            in.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
    public static String download(String imgurl,String filename) {
        String filePath = "";
        try {
            URL url = new URL(imgurl);
            //打开链接
            URLConnection con = url.openConnection();
            //设置请求超时 5sg3
            con.setConnectTimeout(5 * 1000);
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            //文件名字
            Random R = new Random();
            int random = R.nextInt(1000);
            filePath = "C:\\Users\\Administrator\\Desktop\\CB\\image\\" + filename + ".png";
//             filePath = "C:\\Users\\崔震云\\Desktop\\CB\\image\\" + filename + ".png";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] buffer = new byte[1024 * 8];
            int count;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            //关闭流
            out.close();
            in.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
