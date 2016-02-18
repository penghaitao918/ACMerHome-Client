package com.xiaotao.acmfamily.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.xiaotao.acmfamily.network.ClientReceive;
import com.xiaotao.acmfamily.network.ClientSend;
import com.xiaotao.acmfamily.util.AppUtil;
import com.xiaotao.acmfamily.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
public class ClientService extends Service
{

    //  Socket 连接
    private static Socket socket = null;
    // 定义与服务器通信的子线程
    private ClientThread clientThread = null;
    //  定义客户端发送数据的广播接收
    private ServiceReceiver serviceReceiver = null;
    //  将客户端数据发送到服务器
    private ClientSend send = null;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  创建ServiceReceiver
        serviceReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.service_client);
        registerReceiver(serviceReceiver, filter);
        //  创建网络连接
        clientThread = new ClientThread();
        new Thread(clientThread).start();
    }

    public class ClientThread implements Runnable {
        // 该线程所处理的Socket所对应的输入流
        private BufferedReader br = null;
        private ClientReceive receive = null;
        public ClientThread() {}
        public void run()
        {
            try
            {
                socket = new Socket(AppUtil.net.IP, AppUtil.net.port);
                socketCheck();
                //  创建一个子线程来读取服务器的相应
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            // 启动一条子线程来读取服务器响应的数据
                            receive = new ClientReceive(ClientService.this,br);
                            new Thread(receive).start();
                            // 为当前线程初始化Looper
                            Looper.prepare();
                            // 启动Looper
                            Looper.loop();
                        }
                        catch (Exception e)
                        {
                            Log.i(AppUtil.tag.error, "ClientService.onHandleIntent() is Error ----->  Exception ");
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            catch (SocketTimeoutException timeException){
                Log.i(AppUtil.tag.network, "ClientService is Error -----> 服务器连接超时");
                timeException.printStackTrace();
            }
            catch (Exception e){
                Log.i(AppUtil.tag.network, "ClientService is Error -----> 服务器连接失败");
                e.printStackTrace();
            }
        }
    }

    //	将用户的请求提交到网络
    private void getSend(JSONObject jsonObject) {
        send = new ClientSend(jsonObject);
        new Thread(send).start();
    }

    public static Socket getSocket() {
        return socket;
    }

    //  心跳检测
    private void socketCheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag) {
                    try {
                    // 发送心跳包
                        JSONUtil jsonUtil = new JSONUtil();
                        JSONObject jsonObject = jsonUtil.connectCheck();
                        getSend(jsonObject);
                        System.out.println("目前是处于链接状态！");
                        Thread.sleep(60 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("目前是处于断开状态！");
                        flag = false;
                    }
                }
            };
        }).start();
    }

    public class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String msg = intent.getStringExtra(AppUtil.message.service);
            try {
                JSONObject jsonObject = new JSONObject(msg);
                getSend(jsonObject);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}

