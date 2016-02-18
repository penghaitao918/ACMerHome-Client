package com.xiaotao.acmfamily.network;

import android.util.Log;

import com.xiaotao.acmfamily.service.ClientService;
import com.xiaotao.acmfamily.util.AppUtil;

import org.json.JSONObject;

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

    private JSONObject jsonObject = null;

    public ClientSend(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public void run()
    {
        try{
            OutputStream outputStream = ClientService.getSocket().getOutputStream();
            outputStream.write((jsonObject + "\r\n").getBytes("utf-8"));
        }catch (Exception e) {
            Log.i(AppUtil.tag.error, "ClientSend.run() is Error");
            e.printStackTrace();
        }
    }
}
