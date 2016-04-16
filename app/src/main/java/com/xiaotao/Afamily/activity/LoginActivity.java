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
import com.xiaotao.Afamily.activity.core.BasePageActivity;
import com.xiaotao.Afamily.activity.register.RegisterActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.SPUtils;
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
    private SPUtils spUtils = null;

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
        this.spUtils.recycle();
    }
/**
  * @author xiaoTao
  * @date 2016/3/31  14:37
  *
  * @description 初始化编辑框控件和进度条
  */
    private void init(){
        this.initBroadcast();
        this.spUtils = new SPUtils(getBaseContext());
        this.accountEdit = (EditText) findViewById(R.id.login_accountEdit);
        this.passwordEdit = (EditText) findViewById(R.id.login_passwardEdit);
        this.progressDialog = new ProgressDialog(this);
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
                    if (getCurrentFocus() != null) {
                        im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    progressDialog.show();
                    break;
                //  登录成功进入MainPage
                case 1:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    System.out.println("111111");
                    Intent loginIntent = new Intent(LoginActivity.this,BasePageActivity.class);
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

    private void setProgressDialogTime(final int time){
        new Thread(){
            @Override
            public void run() {
                Message m = new Message();
                m.what = 0;
                handler.sendMessage(m);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (progressDialog.isShowing()){
                    Message m = new Message();
                    m.what = 3;
                    handler.sendMessage(m);
                }
            }
        }.start();
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
                        if (account.equals("1234") && password.equals("5678")){
                            Message m = new Message();
                            m.what = 1;
                            handler.sendMessage(m);
                        }else {
                            User user = new User();
                            user.setStuId(account);
                            user.setPassword(StringUtil.MD5(password));
                            sendToService(JSONUtil.login(user).toString());
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

    private void saveInfoToSP(final JSONObject jsonObject) {
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    spUtils.set(AppUtil.sp.loginFlag, true);
                    spUtils.set(AppUtil.sp.account, accountEdit.getText().toString());
                    spUtils.set(AppUtil.sp.password, StringUtil.MD5(passwordEdit.getText().toString()));
                    spUtils.set(AppUtil.sp.classes, jsonObject.getString(AppUtil.user.classes));
                    spUtils.set(AppUtil.sp.userName, jsonObject.getString(AppUtil.user.userName));
                    spUtils.set(AppUtil.sp.portrait, jsonObject.getString(AppUtil.user.portrait));
                    spUtils.set(AppUtil.sp.sex, jsonObject.getString(AppUtil.user.sex));
                    System.out.println("写入");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class LoginReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(AppUtil.message.login);
            System.out.println(msg);
            try {
                Message m = new Message();
                JSONObject jsonObject = new JSONObject(msg);
                Boolean flag = jsonObject.getBoolean(AppUtil.user.loginFlag);
                if (flag){
                    //  获取账号信息并写入缓存
                    saveInfoToSP(jsonObject);
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
