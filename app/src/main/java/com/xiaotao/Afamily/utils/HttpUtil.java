package com.xiaotao.Afamily.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 *
 * @author littleTao
 * @date 2016-02-03  21:13
 */
public class HttpUtil {

    private static int connectTimeout = 5 * 1000;
    private static int readTimeout = 5 * 1000;

    public static final String ERROR = "error";

    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @return URL所代表远程资源的响应
     */
    public static String get(String url){
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("GET");// 提交模式
            conn.setConnectTimeout(connectTimeout);//连接超时 单位毫秒
            conn.setReadTimeout(readTimeout);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            // 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
            if(conn.getResponseCode() == 200){
                // 定义BufferedReader输入流来读取URL的响应
                return readInputStream(conn.getInputStream());
            }
            return ERROR + conn.getResponseCode();
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String post(String url, String params){
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            conn.setConnectTimeout(connectTimeout);//连接超时 单位毫秒
            conn.setReadTimeout(readTimeout);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            // 表单参数与get形式一样
            byte[] bypes = params.toString().getBytes();
            conn.getOutputStream().write(bypes);// 输入参数
/*            System.out.println("#getResponseMessage#" + conn.getResponseMessage());
            System.out.println("#getResponseCode#" + conn.getResponseCode());*/
            return readInputStream(conn.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 将InputStreamReader转化成String
     * @param inputStream URL响应结果
     * @return String 转化结果
     */
    private static String readInputStream(InputStream inputStream) throws Exception{
        // 定义BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null, result = "";
        while ((line = in.readLine()) != null)
        {
            result += line + "\n";
        }
        return result;
    }
}
