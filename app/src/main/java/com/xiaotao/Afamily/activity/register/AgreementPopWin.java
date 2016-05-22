package com.xiaotao.Afamily.activity.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.utils.HttpUtil;


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
 * @date 2016-04-10  20:17
 */
public class AgreementPopWin extends BaseActivity {

    private TextView textView = null;
    private String response = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.popwin_agreement);
        thread.start();
        init();
    }

    private void init() {
        this.textView = (TextView) findViewById(R.id.textView);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AgreementPopWin.this, "获取失败！", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    textView.setText(response);
                    break;
            }
        }
    };

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Message m = new Message();
            response = HttpUtil.get("http://192.168.56.1:8080/afamily/agreement");
            if (!response.equals(HttpUtil.ERROR)){
                System.out.println("Success");
                m.what = 2;
            }else {
                System.out.println("Error");
                m.what = 1;
            }
            handler.sendMessage(m);
        }
    });
}
