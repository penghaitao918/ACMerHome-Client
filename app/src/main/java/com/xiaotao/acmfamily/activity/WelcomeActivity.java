package com.xiaotao.acmfamily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.xiaotao.acmfamily.R;
import com.xiaotao.acmfamily.base.BaseActivity;
import com.xiaotao.acmfamily.service.ClientService;
import com.xiaotao.acmfamily.test.TestActivity;
import com.xiaotao.acmfamily.util.AppUtil;
import com.xiaotao.acmfamily.util.SPUtils;

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
	private AlphaAnimation animation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.flagLayout);
		//	启动后台Service
		Intent intent = new Intent(this,ClientService.class);
		startService(intent);
		/** 设置透明度渐变动画 */
		animation = new AlphaAnimation(0, 1);
		animation.setDuration(4000);//设置动画持续时间
		layout.setAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();
		new Thread(){
			@Override
			public void run() {
				SPUtils spUtils = new SPUtils(WelcomeActivity.this);
				Boolean isLogin = (Boolean) spUtils.get(AppUtil.sp.flagLogin,false);
				if (!isLogin) {
					Message msg = new Message();
					handler.sendMessage(msg);
				}else {
					//	自主登录
				}
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//	结束动画
		animation.cancel();
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			//按钮淡入淡出特效
			animation.startNow();
		}
	};

	public void welcomeOnClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton:
				Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(loginIntent);
				break;
			case R.id.registerButton:
				Intent registerIntent = new Intent(WelcomeActivity.this, TestActivity.class);
				startActivity(registerIntent);
				break;
		}
	}
}