package com.xiaotao.Afamily.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.xiaotao.Afamily.base.BaseApplication;
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
public class ClientService extends Service {
    //  Socket 连接
    private static Socket socket = null;
    //  定义客户端发送数据的广播接收
    private ServiceReceiver serviceReceiver = null;
    //  将客户端数据发送到服务器
    private ClientSend send = null;


    // 该线程所处理的Socket所对应的输入流
    private BufferedReader br = null;
    private ClientReceive receive = null;

    private boolean FLAG = true;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.init();
        //  创建网络连接&心跳检测
        check.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(serviceReceiver);
        FLAG = false;
        check.interrupt();

        socket = null;
        System.out.println("Socket 连接中断");
    }
    private void init(){
        System.out.println("### service onCreate");
        //  创建ServiceReceiver
        serviceReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.service_client);
        registerReceiver(serviceReceiver, filter);
    }

    public static Socket getSocket() {
        return socket;
    }

    //  创建Socket连接
    private void setSocket() {
        try{
            Thread.sleep(5 * 1000);
            if (!FLAG) {
                return;
            }
            socket = null;
            System.out.println("###新建socket连接");
            socket = new Socket(AppUtil.net.IP, AppUtil.net.port);
            receiveThread();
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

    //  创建一个子线程来读取服务器的相应
    private void receiveThread(){
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.i(AppUtil.tag.error, "ClientService.receiveThread() is Connect");
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // 启动一条子线程来读取服务器响应的数据
                    receive = new ClientReceive(ClientService.this, br);
                    new Thread(receive).start();
                    // 为当前线程初始化Looper
                    Looper.prepare();
                    // 启动Looper
                    Looper.loop();
                } catch (Exception e) {
                    Log.i(AppUtil.tag.error, "ClientService.receiveThread() is Error ----->  Exception ");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //  心跳检测
    private Thread check = new Thread(new Runnable() {
        @Override
        public void run() {
            while (FLAG){
                try {
                    socket.sendUrgentData(0xFF);
                    System.out.println("目前是处于连接状态！");
                    Thread.sleep(30 * 1000);
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("目前是处于断开状态！");
                    try {
                        socket.close();
                    }catch (Exception e1){
                        e.printStackTrace();
                    }
                    if (FLAG) {
                        setSocket();
                    }
                }
                if (FLAG && !BaseApplication.getInstance().isRunning()){
                    System.out.println("### check is close");
                    ClientSend send = new ClientSend(JSONUtil.logout());
                    new Thread(send).start();
                    FLAG = false;
                    return;
                }
            }
            stopSelf();
        }
    });




    //  服务器广播,将接受到的客户端请求发送到服务器
    public class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String msg = intent.getStringExtra(AppUtil.message.sendMessage);
            try {
                JSONObject jsonObject = new JSONObject(msg);
                send = new ClientSend(jsonObject);
                new Thread(send).start();
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}