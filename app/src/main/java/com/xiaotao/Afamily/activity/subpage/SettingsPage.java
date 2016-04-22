package com.xiaotao.Afamily.activity.subpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.popwin.ConfirmConversation;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.service.LocalService;
import com.xiaotao.Afamily.service.NetworkService;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.SPUtils;

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
 * @date 2016-04-17  22:13
 */
public class SettingsPage extends BaseActivity {

    private SPUtils sharedPreferencesUtils = null;

    private Switch soundSwitch = null;
    private Switch vibrateSwitch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.subpage_settings);
        init();
        initSwitch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        soundSwitch = (Switch) findViewById(R.id.switch1);
        vibrateSwitch = (Switch) findViewById(R.id.switch2);

        soundSwitch.setOnCheckedChangeListener(new SoundSwitchOnCheckedChangeListenerImpl());
        vibrateSwitch.setOnCheckedChangeListener(new VibrateSwitchOnCheckedChangeListenerImpl());

        sharedPreferencesUtils = new SPUtils(this);
    }

    private void initSwitch() {
        if (LocalService.soundFlag) {
            soundSwitch.setChecked(true);
        }
        if (LocalService.vibrateFlag) {
            vibrateSwitch.setChecked(true);
        }
    }

    public void SettingsOnClick(View view) {
        switch (view.getId()) {
            case R.id.deleteConversation:
                Intent intent = new Intent(this, ConfirmConversation.class);
                startActivity(intent);
                break;
            case R.id.about:
                break;
            case R.id.exitSystem:
                SPUtils spUtils = new SPUtils(getBaseContext());
                spUtils.set(AppUtil.sp.loginFlag, false);
                spUtils.recycle();
                stopService(new Intent(this, NetworkService.class));
                BaseApplication.getInstance().exit();
                break;
        }
    }

    private class SoundSwitchOnCheckedChangeListenerImpl implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                LocalService.soundFlag = true;
            } else {
                LocalService.soundFlag = false;
            }
            sharedPreferencesUtils.set(AppUtil.sp.localSound, LocalService.soundFlag);
        }
    }

    private class VibrateSwitchOnCheckedChangeListenerImpl implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                LocalService.vibrateFlag = true;
            } else {
                LocalService.vibrateFlag = false;
            }
            sharedPreferencesUtils.set(AppUtil.sp.localVibrate, LocalService.vibrateFlag);
        }
    }

}
