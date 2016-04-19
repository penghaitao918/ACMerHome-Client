package com.xiaotao.Afamily.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.xiaotao.Afamily.service.NetworkService;
import com.xiaotao.Afamily.utils.AppUtil;

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
 * @author littleTao
 * @date 2016-02-03 20:01
 */
public class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onCreate");
        //设置所以的活动都是无标题的
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //将活动添加到全局变量中 MyApplication
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onResume");
    }

    protected void startClientService(){
        //	启动后台Service
        System.out.println("### 开启service");
        Intent intent = new Intent(BaseActivity.this, NetworkService.class);
        startService(intent);
    }

    protected void sendToService(final String jsonMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!NetworkService.isStart) {  }
                Intent it = new Intent(AppUtil.broadcast.network_service);
                it.putExtra(AppUtil.message.sendMessage, jsonMessage);
                sendBroadcast(it);
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().finish(this);
        Log.i(AppUtil.tag.activity, this.getClass().getSimpleName() + "--->onDestroy");
    }

}
