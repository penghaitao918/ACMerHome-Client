package com.xiaotao.acmerhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xiaotao.acmerhome.R;
import com.xiaotao.acmerhome.base.BaseActivity;
import com.xiaotao.acmerhome.service.ClientService;
import com.xiaotao.acmerhome.test.TestActivity;

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
	// 定义界面上的一个按钮
	private Button send;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		//	启动后台Service
		Intent intent = new Intent(this,ClientService.class);
		startService(intent);

		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
/*				Intent it = new Intent(AppUtil.broadcast.service_client);
				it.putExtra(AppUtil.message.service,"Hello World");
				sendBroadcast(it);*/
				Intent intent = new Intent(WelcomeActivity.this, TestActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});



	}

}

