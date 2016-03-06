package com.xiaotao.Afamily.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.network.ClientSend;
import com.xiaotao.Afamily.service.ClientService;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

import org.json.JSONObject;

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
 * @date 2016-02-15  20:53
 */
public class TestActivity extends BaseActivity {
    // 定义界面上的两个文本框
    EditText input;
    TextView show;
    // 定义界面上的一个按钮
    Button send;
    private static Handler handler;

    private TestReceiver testReceiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //  TestReceiver
        testReceiver = new TestReceiver();
        //  创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.test);
        registerReceiver(testReceiver, filter);

        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        show = (TextView) findViewById(R.id.show);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  发送数据到服务器

                TestEntity testEntity = new TestEntity();
                testEntity.setMsg(input.getText().toString());

                JSONUtil jsonUtil = new JSONUtil();
                JSONObject jsonObject = jsonUtil.test(testEntity);

				Intent it = new Intent(AppUtil.broadcast.service_client);
				it.putExtra(AppUtil.message.sendMessage, jsonObject.toString());
                sendBroadcast(it);

                input.setText("");
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 如果消息来自于子线程
                if (msg.what == -1) {
                    // 将读取的内容追加显示在文本框中
                    show.append("\n" + msg.obj.toString());
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(testReceiver);
        //  告诉服务器登出
        ClientSend send = new ClientSend(JSONUtil.logout());
        new Thread(send).start();
        try {
            Thread.sleep(3 * 1000);
            //      System.out.println("Socket xiu mian 中断");
            ClientService.getSocket().close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this, ClientService.class);
        stopService(intent);
        BaseApplication.getInstance().exit(this);
    }

    public class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TestEntity testEntity = (TestEntity)intent.getSerializableExtra(AppUtil.message.test);

                Message msg = new Message();
                msg.what = -1;
                msg.obj = testEntity.getMsg();
                handler.sendMessage(msg);

        }
    }
}
