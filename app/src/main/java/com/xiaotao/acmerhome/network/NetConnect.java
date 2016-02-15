package com.xiaotao.acmerhome.network;

import android.util.Log;

import com.xiaotao.acmerhome.util.AppUtil;

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
 * @date 2016-02-13  21:48
 */
public class NetConnect {

    private Socket socket = null;
    private static NetConnect instance = null;

    // 单例模式中获取唯一的NetConnect实例
    public static NetConnect getInstance() {
        if (null == instance) {
            instance = new NetConnect();
        }
        return instance;
    }

    private void setSocket() {
        try {
            socket = new Socket(AppUtil.net.IP, AppUtil.net.port);
            Log.i(AppUtil.tag.network, "NetConnect : Socket is build.");
        }
        catch (SocketTimeoutException e1)
        {
            System.out.println("网络连接超时！！");
        }
        catch (Exception e){
            Log.i(AppUtil.tag.network, "NetConnect.setSocket() is Error ----->  Exception 2");
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        if (socket == null){
            setSocket();
        }
        return socket;
    }
}
