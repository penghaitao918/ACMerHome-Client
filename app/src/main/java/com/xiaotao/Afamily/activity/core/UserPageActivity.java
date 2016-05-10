package com.xiaotao.Afamily.activity.core;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.xiaotao.Afamily.utils.ChangeUtil;
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//相片类型
                //  ViewPager中的子Activity的startActivityForResult调用方法
                getParent().startActivityForResult(intent, AppUtil.updateUserInfo.IMAGE_REQUEST_CODE);
                break;
            case R.id.myName:
                break;
            case R.id.mySex:
                dialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppUtil.updateUserInfo.IMAGE_REQUEST_CODE:
                try {
                    try {
                        startPhotoZoom(data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case AppUtil.updateUserInfo.RESULT_REQUEST_CODE:
                if (data != null) {
                    setImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        getParent().startActivityForResult(intent, AppUtil.updateUserInfo.RESULT_REQUEST_CODE);
    }

    private Bitmap bitmap = null;
    private void setImageToView(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            bitmap = bundle.getParcelable("data");
            sendToService(JSONUtil.updateUserInfo(AppUtil.updateUserInfo.updatePortrait, ChangeUtil.toBinary(bitmap)).toString());
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
                .setSingleChoiceItems(sexItem, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = which;
                    }
                })
                        // 为AlertDialog.Builder添加“确定”按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 此处可执行更新处理
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
                case AppUtil.updateUserInfo.updatePortrait:
                    portraitImage.setImageBitmap(bitmap);
                    Toast.makeText(UserPageActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
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