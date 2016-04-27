package com.xiaotao.Afamily.activity.core;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

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
 * @date 2016-04-11  13:11
 */
public class UserPageActivity extends BaseActivity {

    private UserPageReceiver receiver = null;

    private TextView nameText = null;
    private TextView classesText = null;
    //  该控件已被隐藏
    private TextView sexText = null;
    private String[] sexItem = new String[] { "-", "男", "女" };

    private ImageView portraitImage = null;
    private TextView accountText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_page_user);
        initBroadcast();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initBroadcast(){
        receiver = new UserPageReceiver();
        //  创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.userPage);
        registerReceiver(receiver, filter);
    }

    private void initView() {
        portraitImage = (ImageView) findViewById(R.id.portraitImage);
        accountText = (TextView) findViewById(R.id.accountText);
        classesText = (TextView) findViewById(R.id.classesText);
        nameText = (TextView) findViewById(R.id.nameText);
        sexText = (TextView) findViewById(R.id.sexText);
    }

    private void initData() {
        portraitImage.setImageBitmap(BaseApplication.getInstance().getUser().getPortrait());
        accountText.setText(BaseApplication.getInstance().getUser().getStuId());
        nameText.setText(BaseApplication.getInstance().getUser().getUserName());
        sexText.setText(BaseApplication.getInstance().getUser().getSex());
        classesText.setText(BaseApplication.getInstance().getUser().getClasses());
    }

    public void UserPageOnClick(View view) {
        switch (view.getId()){
            case R.id.myPortrait:
                break;
            case R.id.myName:
                break;
            case R.id.mySex:
                dialog();
                break;
        }
    }
    private int position = 0;
    private void dialog() {
        new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("性别")
                        // 设置图标
                .setIcon(R.drawable.sex_icon)
                        // 设置单选列表项，默认选中第二项（索引为1）
                .setSingleChoiceItems(sexItem, position, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        position = which;
                    }
                })
                        // 为AlertDialog.Builder添加“确定”按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 此处可执行登录处理
                        sendToService(JSONUtil.updateUserInfo(AppUtil.updateUserInfo.updateSex, sexItem[position]).toString());
                    }
                })
                        // 为AlertDialog.Builder添加“取消”按钮
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AppUtil.updateUserInfo.updateSex:
                    sexText.setText(sexItem[position]);
                    Toast.makeText(UserPageActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(UserPageActivity.this, "操作错误！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public class UserPageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Message();
            message.what = intent.getIntExtra(AppUtil.message.userPage, -1);
            handler.sendMessage(message);
        }
    }

}