package com.xiaotao.acmerhome.network;

import android.os.Handler;
import android.os.Looper;

import com.xiaotao.acmerhome.util.AppUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
	private Socket socket = null;

	// 定义向UI线程发送消息的Handler对象
	private Handler handler = null;

	// 该线程所处理的Socket所对应的输入流
	private BufferedReader br = null;

	private ClientReceive receive = null;
	public ClientSend send = null;

	public ClientThread() {}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	public ClientThread(Handler handler)
	{
		this.handler = handler;
	}

	public void run()
	{
		try
		{
			socket = new Socket(AppUtil.net.IP, AppUtil.net.port);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// 启动一条子线程来读取服务器响应的数据
			receive = new ClientReceive(handler,br);
			new Thread(receive).start();

			//	将用户的请求提交到网络
			send = new ClientSend(socket.getOutputStream());
			// 为当前线程初始化Looper
			Looper.prepare();
			// 启动Looper
			Looper.loop();
		}
		catch (SocketTimeoutException e1)
		{
			System.out.println("网络连接超时！！");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

