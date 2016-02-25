package com.xiaotao.Afamily.model.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.test.TestActivity;

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
 * @date 2016-02-25  20:53
 */
public class MyNotification {

    private Context mContext = null;
    private Notification notification = null;
    private NotificationManager notificationManager = null;
    private String mText = "已有新的版本，请及时更新。";

    public MyNotification(Context context, String text){
        this.mContext = context;
        this.mText = text;
        this.build();
    }

    private void build(){
        // 获取系统的NotificationManager服务
        this.notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(mContext, TestActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, 0);
        this.notification = new Notification.Builder(mContext)
                // 设置打开该通知，该通知自动消失
                .setAutoCancel(true)
                        // 设置显示在状态栏的通知提示信息
                .setTicker("有新的消息")
                        // 设置通知的图标
                .setSmallIcon(R.drawable.ic_launcher)
                        // 设置通知内容的标题
                .setContentTitle("一家人")
                        // 设置通知内容
                .setContentText(mText)
                        // 设置使用系统默认的声音、默认LED灯、震动
                .setDefaults(Notification.DEFAULT_ALL)
/*                        // 设置通知的自定义声音
                .setSound(Uri.parse("android.resource://org.crazyit.ui/"
                        + R.raw.msg))*/
                        //  获取当前时间
                .setWhen(System.currentTimeMillis())
                        // 设改通知将要启动程序的Intent
                .setContentIntent(pi)
                .build();
    }

    //  通知
    public void start()
    {
        // 发送通知
        notificationManager.notify(0, notification);
    }
}
