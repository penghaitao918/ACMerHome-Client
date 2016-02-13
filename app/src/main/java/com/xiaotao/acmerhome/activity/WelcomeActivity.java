package com.xiaotao.acmerhome.activity;

import android.app.Activity;
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
import com.xiaotao.acmerhome.util.MSGUtil;

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
	// 定义界面上的两个文本框
	EditText input;
	TextView show;
	// 定义界面上的一个按钮
	Button send;
	Handler handler;
	// 定义与服务器通信的子线程
	ClientThread clientThread;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		input = (EditText) findViewById(R.id.input);
		send = (Button) findViewById(R.id.send);
		show = (TextView) findViewById(R.id.show);


		// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
		clientThread = new ClientThread(handler);
		new Thread(clientThread).start(); // ①


		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clientThread.send.put(input.getText().toString());
				input.setText("");
			}
		});


		handler = new Handler() // ②
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 如果消息来自于子线程
				if (msg.what == MSGUtil.net.testReceive)
				{
					// 将读取的内容追加显示在文本框中
					show.append("\n" + msg.obj.toString());
				}
			}
		};
	}
}

