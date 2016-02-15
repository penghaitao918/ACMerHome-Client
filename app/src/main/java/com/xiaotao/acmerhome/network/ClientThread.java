package com.xiaotao.acmerhome.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xiaotao.acmerhome.util.AppUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
 * @date 2016-02-13  0:13
 */
public class ClientThread implements Runnable {

	private NetConnect connect = NetConnect.getInstance();

	// 该线程所处理的Socket所对应的输入流
	private BufferedReader br = null;
	// 该线程所处理的Socket所对应的输出流
	private OutputStream os = null;

	private ClientReceive receive = null;
	private ClientSend send = null;

	public ClientThread() {}

	public void run()
	{
		try
		{
			br = new BufferedReader(new InputStreamReader(connect.getSocket().getInputStream()));
			// 启动一条子线程来读取服务器响应的数据
			receive = new ClientReceive(br);
			new Thread(receive).start();
			// 为当前线程初始化Looper
			Looper.prepare();
			// 启动Looper
			Looper.loop();
		}
		catch (Exception e)
		{
			Log.i(AppUtil.tag.error, "ClientThread.run() is Error ----->  Exception ");
			e.printStackTrace();
		}
	}

	//	将用户的请求提交到网络
	public void getSend(String msg) {
		send = new ClientSend(msg);
		new Thread(send).start();
	}
}

