package com.xiaotao.acmfamily.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaotao.acmfamily.test.TestEntity;
import com.xiaotao.acmfamily.util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    private Context context = null;
    private BufferedReader bufferedReader = null;

    public ClientReceive(Context context, BufferedReader bufferedReader) {
        this.context = context;
        this.bufferedReader = bufferedReader;
    }

    @Override
    //  对数据包进行解读
    public void run()
    {
        String content = null;
        // 不断读取Socket输入流中的内容
        try
        {
            while ((content = bufferedReader.readLine()) != null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int type = jsonObject.getInt(AppUtil.connectType.type);
                    get(type,jsonObject);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //  设置同步方法，任意时刻有且只能有一个接收程序
    public synchronized void get(int type, JSONObject jsonObject) {
        System.out.println("### " + type + " # ");
        // 每当读到来自服务器的数据之后，发送消息通知程序
        try {
            switch (type) {
                case AppUtil.connectType.test:
                    TestEntity testEntity = new TestEntity(jsonObject);
                    System.out.println(testEntity.getMsg());

                    Intent intent = new Intent(AppUtil.broadcast.test);
                    intent.putExtra(AppUtil.message.test, testEntity);
                    context.sendBroadcast(intent);
                    break;
                default:
                    Log.i(AppUtil.tag.network,AppUtil.net.tip);
                    System.out.println(AppUtil.connectType.checkMSG);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
