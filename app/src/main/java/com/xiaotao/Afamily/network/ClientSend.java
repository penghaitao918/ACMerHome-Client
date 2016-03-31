package com.xiaotao.Afamily.network;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.xiaotao.Afamily.service.ClientService;
import com.xiaotao.Afamily.utils.AppUtil;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.Socket;

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
 * @date 2016-02-13  22:10
 */
public class ClientSend implements Runnable {

    private Socket mSocket = null;
    private JSONObject mJsonObject = null;

    public ClientSend(Socket socket, JSONObject jsonObject) {
        this.mSocket = socket;
        this.mJsonObject = jsonObject;
    }

    @Override
    public void run()
    {
        try{
            OutputStream outputStream = mSocket.getOutputStream();
            outputStream.write((mJsonObject + "\r\n").getBytes("utf-8"));
        }catch (Exception e) {
            Log.i(AppUtil.tag.error, "ClientSend.run() is Error");
            e.printStackTrace();
        }
    }
}
