package com.xiaotao.acmerhome.network;

import android.util.Log;

import com.xiaotao.acmerhome.util.AppUtil;
import com.xiaotao.acmerhome.util.MSGUtil;

import java.io.IOException;
import java.io.OutputStream;

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

    private OutputStream outputStream = null;
    private String msg = null;

    public ClientSend( String msg) {
        this.msg = msg;
    }

    @Override
    public void run()
    {
        try{
            outputStream = NetConnect.getInstance().getSocket().getOutputStream();
            outputStream.write((msg + "\r\n").getBytes("utf-8"));
        }catch (Exception e) {
            Log.i(AppUtil.tag.error, "ClientSend.run() is Error");
            e.printStackTrace();
        }
    }
}
