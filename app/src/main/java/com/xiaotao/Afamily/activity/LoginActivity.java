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
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.core.MainPageActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.network.ClientSend;
import com.xiaotao.Afamily.service.ClientService;
import com.xiaotao.Afamily.test.TestActivity;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.StringUtil;

import org.json.JSONException;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(this.loginReceiver);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
/**
  * @author xiaoTao
  * @date 2016/3/31  14:37
  *
  * @description 初始化编辑框控件和进度条
  */
    private void init(){
        this.initBroadcast();
        this.accountEdit = (EditText) findViewById(R.id.login_accountEdit);
        this.passwordEdit = (EditText) findViewById(R.id.login_passwardEdit);
        this.progressDialog = new ProgressDialog(LoginActivity.this);
        this.progressDialog.setMessage("登录中...");
        this.progressDialog.onStart();
    }
/**
  * @author xiaoTao
  * @date 2016/3/31  14:38
  *
  * @description 初始化广播
  */
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
                //  未输入账号密码
                case -1:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"请输入账号和密码",Toast.LENGTH_LONG).show();
                    break;
                //  等待登录验证结果
                case 0:
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    progressDialog.show();
                    break;
                //  登录成功进入MainPage
                case 1:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Intent loginIntent = new Intent(LoginActivity.this,MainPageActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginIntent);
                    finish();
                    break;
                //  登录失败
                case 2:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this,"账号或密码错误\r\n请查证后登录",Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"登录超时！",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    private void setProgressDialogTime(int time){
        Message m = new Message();
        m.what = 0;
        handler.sendMessage(m);
        handler.postDelayed(new Runnable() {
            public void run() {
                Message m = new Message();
                m.what = 3;
                handler.sendMessage(m);
            }
        }, time);
    }

    public void loginOnClick(View view) {
        switch (view.getId()) {
            case R.id.login_backButton:
                onBackPressed();
                break;
            case R.id.login_loginButton:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setProgressDialogTime(1000 * 15);
                        //  判断是否输入账号密码
                        String account = accountEdit.getText().toString();
                        String password = passwordEdit.getText().toString();
                        if (account.equals("") || password.equals("")){
                            Message m = new Message();
                            m.what = -1;
                            handler.sendMessage(m);
                            return;
                        }
                        //  无网络功能测试
                        //  TODO 此处在最终版本时注释
                        if (account.equals("adminTest") && password.equals("adminTest")){
                            Message m = new Message();
                            m.what = 1;
                            handler.sendMessage(m);
                        }else {
                            User user = new User();
                            user.setStuId(account);
                            user.setPassword(StringUtil.MD5(password));
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
            String msg = intent.getStringExtra(AppUtil.message.login);
            System.out.println(msg);
            try {
                Message m = new Message();
                JSONObject jsonObject = new JSONObject(msg);
                boolean flag = jsonObject.getBoolean(AppUtil.login.loginFlag);
                if (flag){
                    //  TODO 获取账号信息并写入缓存
                    System.out.println(jsonObject.getString(AppUtil.login.account));
                    System.out.println(jsonObject.getString(AppUtil.login.classes));
                    System.out.println(jsonObject.getString(AppUtil.login.userName));
                    System.out.println(jsonObject.getString(AppUtil.login.portrait));
                    System.out.println(jsonObject.getString(AppUtil.login.sex));
                    m.what = 1;
                }else {
                    m.what = 2;
                }
                handler.sendMessage(m);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
