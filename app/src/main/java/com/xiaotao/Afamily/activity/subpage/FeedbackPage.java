package com.xiaotao.Afamily.activity.subpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

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
 * @date 2016-04-22  21:01
 */
public class FeedbackPage extends BaseActivity {

    private EditText titleEdit = null;
    private EditText bodyEdit = null;

    private FeedbackReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page_feedback);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void init() {
        initBroadcast();
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        bodyEdit = (EditText) findViewById(R.id.bodyEdit);
    }

    private void initBroadcast() {
        receiver = new FeedbackReceiver();
        //  创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.feedback);
        registerReceiver(receiver, filter);
    }

    public void feedbackOnClick(View view) {
        sendToService(JSONUtil.feedback(
                titleEdit.getText().toString(), bodyEdit.getText().toString()
        ).toString());

    }

    public class FeedbackReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(AppUtil.message.type, -1) == 1) {
                Toast.makeText(FeedbackPage.this, "发送成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
