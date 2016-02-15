package com.xiaotao.acmerhome.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.xiaotao.acmerhome.activity.WelcomeActivity;
import com.xiaotao.acmerhome.test.TestActivity;
import com.xiaotao.acmerhome.util.AppUtil;
import com.xiaotao.acmerhome.util.MSGUtil;

import java.io.BufferedReader;
import java.io.IOException;

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
 * @date 2016-02-13  22:57
 */
public class ClientReceive implements Runnable {

    // 定义向UI线程发送消息的Handler对象
    private Handler handler = null;
    private BufferedReader bufferedReader = null;

    public ClientReceive(Handler handler, BufferedReader bufferedReader) {
        this.handler = handler;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run()
    {
        String content = null;
        // 不断读取Socket输入流中的内容
        try
        {
            while ((content = bufferedReader.readLine()) != null)
            {
                get(MSGUtil.net.testReceive, content);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //  设置同步方法，任意时刻有且只能有一个接收程序
    public synchronized void get(int type, String content) {
        // 每当读到来自服务器的数据之后，发送消息通知程序
        try {
            switch (type) {
                case MSGUtil.net.testReceive:
                    Message msg = new Message();
                    msg.what = MSGUtil.net.testReceive;
                    msg.obj = content;
                    TestActivity.handler.sendMessage(msg);
                    break;
                default:
                    Log.i(AppUtil.tag.network,AppUtil.net.tip);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
