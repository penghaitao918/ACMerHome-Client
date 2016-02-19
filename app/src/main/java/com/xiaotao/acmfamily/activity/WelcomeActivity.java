package com.xiaotao.acmfamily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

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
	private LinearLayout layout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		this.layout = (LinearLayout) findViewById(R.id.flagLayout);
		//	启动后台Service
		Intent intent = new Intent(this,ClientService.class);
		startService(intent);
		this.init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void init(){
		SPUtils spUtils = new SPUtils(this);
		Boolean isLogin = (Boolean) spUtils.get(AppUtil.sp.flagLogin,false);
		if (!isLogin) {
			layout.setVisibility(View.VISIBLE);
		}else {
			//	自主登录
		}
	}

	public void welcomeOnClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton:
				Intent loginIntent = new Intent(WelcomeActivity.this, TestActivity.class);
				startActivity(loginIntent);
				break;
			case R.id.registerButton:
				Intent registerIntent = new Intent(WelcomeActivity.this, TestActivity.class);
				startActivity(registerIntent);
				break;
		}
	}
}

