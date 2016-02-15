package com.xiaotao.acmerhome.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaotao.acmerhome.R;
import com.xiaotao.acmerhome.base.BaseActivity;
import com.xiaotao.acmerhome.network.ClientThread;
import com.xiaotao.acmerhome.network.NetConnect;
import com.xiaotao.acmerhome.test.TestActivity;
import com.xiaotao.acmerhome.util.AppUtil;
import com.xiaotao.acmerhome.util.MSGUtil;

import java.net.Socket;

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

		send = (Button) findViewById(R.id.send);


		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomeActivity.this, TestActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});

	}

}

