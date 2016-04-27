package com.xiaotao.Afamily.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.core.BasePageActivity;
import com.xiaotao.Afamily.activity.register.RegisterActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.service.LocalService;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.ChangeUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.SPUtils;

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
 * @date 2016-02-13  0:12
 */
public class WelcomeActivity extends BaseActivity
{
	private SPUtils spUtils = null;
	private AlphaAnimation animation = null;
	private RelativeLayout layout = null;
	private ReLoginReceiver reLoginReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//	启动后台Service
		startClientService();
		this.initBroadcast();
		this.layout = (RelativeLayout) findViewById(R.id.flagLayout);
		this.spUtils = new SPUtils(getBaseContext());
		//	启动本地服务
		Intent intent = new Intent(this, LocalService.class);
		startService(intent);
		/** 设置透明度渐变动画 */
		animation = new AlphaAnimation(0, 1);
		animation.setDuration(3500);//设置动画持续时间
	}

	@Override
	protected void onStart() {
		super.onStart();
		Boolean isLogin = (Boolean) spUtils.get(AppUtil.sp.loginFlag,false);
		if (!isLogin) {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}else {
			layout.setVisibility(View.GONE);
			initLogin();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//	结束动画
		animation.cancel();
		this.spUtils.recycle();
		super.unregisterReceiver(reLoginReceiver);
	}

	private void initBroadcast(){
		reLoginReceiver = new ReLoginReceiver();
		//  创建IntentFilter
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppUtil.broadcast.reLogin);
		registerReceiver(reLoginReceiver, filter);
	}

	private void initLogin(){
		//	TODO 从缓存获取数据
		User user = new User();
		user.setStuId((String) spUtils.get(AppUtil.sp.account, ""));
		user.setPassword((String) spUtils.get(AppUtil.sp.password, ""));
		sendToService(JSONUtil.reLogin(user).toString());

		user.setSex((String) spUtils.get(AppUtil.sp.sex, ""));
		user.setClasses((String) spUtils.get(AppUtil.sp.classes, ""));
		user.setUserName((String) spUtils.get(AppUtil.sp.userName, ""));
		user.setPortrait(
				ChangeUtil.toBitmap((String) spUtils.get(AppUtil.sp.portrait, ""))
		);
		BaseApplication.getInstance().setUser(user);


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
					Message m = new Message();
					m.what = 1;
					handler.sendMessage(m);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what){
				case 0:
					System.out.println("A");
					Intent intent = new Intent(WelcomeActivity.this,BasePageActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
					break;
				case 1:
					//按钮淡入淡出特效
					System.out.println("B");
					layout.setVisibility(View.VISIBLE);
					layout.setAnimation(animation);
					animation.startNow();
					break;
			}
		}
	};

	public void welcomeOnClick(View view) {
		switch (view.getId()) {
			case R.id.welcome_loginButton:
				Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(loginIntent);
				break;
			case R.id.welcome_registerButton:
				Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
				startActivity(registerIntent);
				break;
		}
	}

	public class ReLoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra(AppUtil.message.reLogin);
			try {
				Message m = new Message();
				JSONObject jsonObject = new JSONObject(msg);
				boolean flag = jsonObject.getBoolean(AppUtil.user.loginFlag);
				if (flag){
					setUserInfo(jsonObject);
					m.what = 0;
				}else {
					m.what = 1;
				}
				handler.sendMessage(m);
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
	}
}