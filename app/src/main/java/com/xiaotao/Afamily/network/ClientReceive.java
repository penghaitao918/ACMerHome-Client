package com.xiaotao.Afamily.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaotao.Afamily.model.entity.Notify;
import com.xiaotao.Afamily.model.view.NotificationView;
import com.xiaotao.Afamily.utils.AppUtil;

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
                    int type = jsonObject.getInt(AppUtil.socket.type);
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
    public void get(int type, JSONObject jsonObject) {
        System.out.println("### " + type + " # ");
        // 每当读到来自服务器的数据之后，发送消息通知程序
        try {
            switch (type) {
                case AppUtil.socket.notify:
                    NotificationView notification = new NotificationView(context, new Notify(jsonObject));
                    notification.start();
                    break;
                case AppUtil.socket.login:
                    Intent intentLogin = new Intent(AppUtil.broadcast.login);
                    intentLogin.putExtra(AppUtil.message.login, jsonObject.toString());
                    context.sendBroadcast(intentLogin);
                    break;
                case AppUtil.socket.reLogin:
                    Intent intentReLogin = new Intent(AppUtil.broadcast.reLogin);
                    intentReLogin.putExtra(AppUtil.message.reLogin, jsonObject.toString());
                    context.sendBroadcast(intentReLogin);
                    break;
                case AppUtil.socket.logout:
                //    NetworkService.getSocket().close();
                    break;
                case AppUtil.socket.taskList:
                    Intent intentTaskListToConversation = new Intent(AppUtil.broadcast.conversationList);
                    intentTaskListToConversation.putExtra(AppUtil.message.type, 0);
                    intentTaskListToConversation.putExtra(AppUtil.message.taskList, jsonObject.toString());
                    context.sendBroadcast(intentTaskListToConversation);

                    Intent intentTaskListToStudentTask = new Intent(AppUtil.broadcast.studentTaskList);
                    intentTaskListToStudentTask.putExtra(AppUtil.message.type, 0);
                    intentTaskListToStudentTask.putExtra(AppUtil.message.studentTask, jsonObject.toString());
                    context.sendBroadcast(intentTaskListToStudentTask);
                    break;
                case AppUtil.socket.studentTaskList:
                    Intent intentStudentTask = new Intent(AppUtil.broadcast.studentTaskList);
                    intentStudentTask.putExtra(AppUtil.message.type, 1);
                    intentStudentTask.putExtra(AppUtil.message.studentTask, jsonObject.toString());
                    context.sendBroadcast(intentStudentTask);
                    break;
                case AppUtil.socket.sendConversationMessage:
                    Intent intentChat = new Intent(AppUtil.broadcast.chat);
                    intentChat.putExtra(AppUtil.message.chatMessage, jsonObject.toString());
                    context.sendBroadcast(intentChat);

                    Intent intentRecentConversation = new Intent(AppUtil.broadcast.conversationList);
                    intentRecentConversation.putExtra(AppUtil.message.type, 1);
                    intentRecentConversation.putExtra(AppUtil.message.chatMessage, jsonObject.toString());
                    context.sendBroadcast(intentRecentConversation);
                    break;
                case AppUtil.socket.feedback:
                    Intent intentFeedback = new Intent(AppUtil.broadcast.feedback);
                    intentFeedback.putExtra(AppUtil.message.type, 1);
                    context.sendBroadcast(intentFeedback);
                    break;
                case AppUtil.socket.updateUserInfo:
                    Intent intentUpdateInfo = new Intent(AppUtil.broadcast.userPage);
                    intentUpdateInfo.putExtra(AppUtil.message.userPage, jsonObject.getInt(AppUtil.updateUserInfo.updateType));
                    context.sendBroadcast(intentUpdateInfo);
                    break;
                default:
                    Log.i(AppUtil.tag.network,AppUtil.net.tip);
                    System.out.println(AppUtil.socket.checkMSG);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
