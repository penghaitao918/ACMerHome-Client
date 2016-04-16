package com.xiaotao.Afamily.activity.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.LoginActivity;
import com.xiaotao.Afamily.activity.core.BasePageActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.ChangeUtil;
import com.xiaotao.Afamily.utils.HttpUtil;
import com.xiaotao.Afamily.utils.SPUtils;
import com.xiaotao.Afamily.utils.StringUtil;

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
 * @date 2016-02-22  11:54
 */
public class RegisterActivity extends BaseActivity {

    private String response = null;
    private ProgressDialog progressDialog = null;

    private EditText accountEdit = null;
    private EditText pswEdit = null;
    private EditText nameEdit = null;
    private EditText classesEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void init(){
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("注册中...");
        this.progressDialog.onStart();

        this.accountEdit = (EditText) super.findViewById(R.id.register_accountEdit);
        this.pswEdit = (EditText) super.findViewById(R.id.register_passwordEdit);
        this.nameEdit = (EditText) super.findViewById(R.id.register_nameEdit);
        this.classesEdit = (EditText) super.findViewById(R.id.register_classesEdit);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0: //  开始注册
                    progressDialog.show();
                    break;
                case 1: //  注册错误
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                    break;
                case 2: //  注册成功
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this,"  注册成功\n欢迎你的加入",Toast.LENGTH_LONG).show();
                    break;
                case 3: //  注册超时
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(RegisterActivity.this,"连接超时，请检查是否已连接互联网",Toast.LENGTH_LONG).show();
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

    public void registerOnClick(View view){
        Message message = new Message();
        switch (view.getId()){
            case R.id.register_backButton:
                onBackPressed();
                break;
            case R.id.register_registerBtn:
                message.what = 0;
                handler.sendMessage(message);
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        setProgressDialogTime(1000 * 15);
                        String account = accountEdit.getText().toString();
                        String password = StringUtil.MD5(pswEdit.getText().toString());
                        String classes = classesEdit.getText().toString();
                        String name = nameEdit.getText().toString();
                        Message m = new Message();
                        response = HttpUtil.post("http://192.168.56.1:8080/afamily/register",
                                "account="+account+"&password="+password+"&classes="+classes+"&name="+name);
                        if (!response.equals(HttpUtil.ERROR)){
                            System.out.println("Success");
                            m.what = 2;
                        }else {
                            System.out.println("Error");
                            m.what = 1;
                        }
                        handler.sendMessage(m);
                    }
                }.start();
                break;
            case R.id.register_tipBtn:
                Intent intent = new Intent(this,AgreementPopWin.class);
                startActivity(intent);
                break;
        }
    }
}
