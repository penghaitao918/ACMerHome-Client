package com.xiaotao.Afamily.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.xiaotao.Afamily.network.ClientReceive;
import com.xiaotao.Afamily.network.ClientSend;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
 * @author xiaoTao
 * @date 2016-02-17  23:32
 */
public class NetworkService extends Service {
    //  Socket 连接
    private boolean flag = true;
    private Socket socket = null;
    //  定义客户端发送数据的广播接收
    private ClientServiceReceiver serviceReceiver = null;
    //  将客户端数据发送到服务器
    private ClientSend send = null;
    // 该线程所处理的Socket所对应的输入流
    private BufferedReader br = null;
    private ClientReceive receive = null;

    public static boolean isStart = false;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("### service onCreate");
        this.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(serviceReceiver);
        //  告诉服务器登出
        new Thread(){
            @Override
            public void run() {
                try{
                    OutputStream outputStream = getSocket().getOutputStream();
                    outputStream.write((JSONUtil.logout() + "\r\n").getBytes("utf-8"));
                }catch (Exception e) {
                    Log.i(AppUtil.tag.error, "logout is Error");
                    e.printStackTrace();
                }
    //            NetworkService.this.close();
    //            socket = null;
            }
        }.start();
    }

    private void init(){
        //  创建ServiceReceiver
        serviceReceiver = new ClientServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.network_service);
        registerReceiver(serviceReceiver, filter);
        isStart = true;
    }


    public void close(){
        try {
            br.close();
            socket.close();
            System.out.println("Socket 已关闭");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //  获取Socket连接
    private Socket getSocket() {
        if (null == socket) {
            setSocket();
        }
        while (flag && socket == null){ }
        flag = true;
        return socket;
    }

    //  创建Socket连接
    private void setSocket(){
        new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(AppUtil.net.IP, AppUtil.net.port);
                    System.out.println("###新建socket连接");
                    receiveThread();
                } catch (SocketTimeoutException timeException) {
                    flag = false;
                    Log.i(AppUtil.tag.network, "NetworkService is Error -----> 服务器连接超时");
                    timeException.printStackTrace();
                } catch (Exception e) {
                    flag = false;
                    Log.i(AppUtil.tag.network, "NetworkService is Error -----> 服务器连接失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //  创建一个子线程来读取服务器的相应
    private void receiveThread(){
        new Thread() {
            @Override
            public void run() {
                try {
                    br = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
                    // 启动一条子线程来读取服务器响应的数据
                    receive = new ClientReceive(getBaseContext(), br);
                    new Thread(receive).start();
                    // 为当前线程初始化Looper
                    Looper.prepare();
                    // 启动Looper
                    Looper.loop();Log.i(AppUtil.tag.network, "NetworkService.receiveThread() is Connect");
                } catch (Exception e) {
                    Log.i(AppUtil.tag.error, "NetworkService.receiveThread() is Error ----->  Exception ");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //  服务器广播,将接受到的客户端请求发送到服务器
    public class ClientServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String msg = intent.getStringExtra(AppUtil.message.sendMessage);
                    try {
                        JSONObject jsonObject = new JSONObject(msg);
                        send = new ClientSend(getSocket(),jsonObject);
                        new Thread(send).start();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

}