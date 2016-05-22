package com.xiaotao.Afamily.activity.popwin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @date 2016-05-22  11:25
 */
public class ConfirmSignIn extends BaseActivity {

    private SPUtils spUtils = null;
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.popwin_confirm_check_in);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.spUtils.recycle();
    }

    private void init() {
        this.textView = (TextView) findViewById(R.id.oldSignTime);
        this.spUtils = new SPUtils(getBaseContext());
        String oldTime = (String)spUtils.get(AppUtil.sp.signTime, "");
        if (oldTime == null || oldTime.equals("")) {
            textView.setText("当前为首次签到");
        }else {
            textView.setText(textView.getText().toString() + "\n" + oldTime);
        }
    }

    public void confirmSignOnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                sendToService(JSONUtil.check().toString());
                //  写入当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowTime = simpleDateFormat.format(new Date());
                System.out.println("### " + nowTime);
                spUtils.set(AppUtil.sp.signTime, nowTime);
                break;
            case R.id.cancel:
                break;
        }
        onBackPressed();
    }
}
