package com.xiaotao.Afamily.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.utils.AppUtil;

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
 * @date 2016-04-17  21:13
 */
public class LocalService extends Service {

    public static boolean soundFlag = true;
    private static boolean vibrateFlag = true;

    private MediaPlayer mediaPlayer = null;

    private SettingServiceReceiver receiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private void init() {
        initReceiver();
        mediaPlayer = MediaPlayer.create(this, R.raw.dingdong);
    }

    private void initReceiver(){
        //  创建ServiceReceiver
        receiver = new SettingServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.local_service);
        registerReceiver(receiver, filter);
    }

    private void playSound() {
        if (soundFlag) {
            mediaPlayer.start();
        }
    }

    private void shake() {
        if (vibrateFlag) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // 获取振动器Vibrator实例
            if (vibrator == null) {
                Vibrator localVibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator = localVibrator;
            }
            long[] pattern = {50, 800, 500, 800}; // OFF/ON/OFF/ON
            vibrator.vibrate(pattern,-1);
        }
    }

    //  本地服务广播，播放音乐、手机震动
    public class SettingServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            int type = intent.getIntExtra(AppUtil.message.type, -1);
            if (type == AppUtil.localService.all) {
                shake();
                playSound();
            } else if (type == AppUtil.localService.sound) {
                playSound();
            } else if (type == AppUtil.localService.vibrate) {
                shake();
            }
        }
    }
}
