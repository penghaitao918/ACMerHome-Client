package com.xiaotao.Afamily.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.test.TestActivity;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.StringUtil;

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
 * @date 2016-02-19  15:26
 */
public class LoginActivity extends BaseActivity {

    private EditText accountEdit = null;
    private EditText passwordEdit = null;

    private ProgressDialog progressDialog = null;
    private LoginReceiver loginReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.init();
    }

    private void init(){
        this.initBroadcast();
        this.accountEdit = (EditText) findViewById(R.id.login_accountEdit);
        this.passwordEdit = (EditText) findViewById(R.id.login_passwardEdit);
        this.progressDialog = new ProgressDialog(LoginActivity.this);
        this.progressDialog.setMessage("Login...");
    }

    private void initBroadcast(){
        //  TestReceiver
        loginReceiver = new LoginReceiver();
        //  创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.login);
        registerReceiver(loginReceiver, filter);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    progressDialog.onStart();
                    progressDialog.show();
                    break;
                case 1:
                    Intent loginIntent = new Intent(LoginActivity.this,TestActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginIntent);
                    finish();
                    break;
            }
        }
    };

    public void loginOnClick(View view) {
        switch (view.getId()) {
            case R.id.login_backButton:
                onBackPressed();
                break;
            case R.id.login_loginButton:
                Message m = new Message();
                m.what = 0;
                handler.sendMessage(m);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //  判断是否输入账号密码
                        if (true) {
                            User user = new User();
                            user.setStuId(accountEdit.getText().toString());
                            user.setPassword(StringUtil.MD5(passwordEdit.getText().toString()));
                            JSONUtil jsonUtil = new JSONUtil();
                            JSONObject jsonObject = jsonUtil.login(user);
                            Intent it = new Intent(AppUtil.broadcast.service_client);
                            it.putExtra(AppUtil.message.sendMessage, jsonObject.toString());
                            sendBroadcast(it);
                        }
                    }
                }).start();
                break;
            case R.id.login_registerButton:
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    public class LoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
/*            User testEntity = (User)intent.getSerializableExtra(AppUtil.message.login);
            Message msg = new Message();
            msg.what = -1;
            msg.obj = testEntity.getMsg();
            handler.sendMessage(msg);*/

        }
    }

}
