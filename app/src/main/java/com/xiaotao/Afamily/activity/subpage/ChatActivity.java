package com.xiaotao.Afamily.activity.subpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.core.BasePageActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.model.adapter.ChatAdapter;
import com.xiaotao.Afamily.model.entity.Chat;
import com.xiaotao.Afamily.model.entity.Conversation;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
 * @date 2016-04-18  10:15
 */
public class ChatActivity extends BaseActivity {

    private int conversationId = -1;
    private TextView conversationName = null;

    private EditText editText = null;

    private ListView listView = null;
    private ArrayList<Chat> dataList = null;
    private ChatAdapter simpleAdapter = null;

    private ChatReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_chat);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataList != null) {
            dataList.clear();
        }
    }

    private void init() {
        initRegisterBroadcas();
        editText = (EditText) super.findViewById(R.id.editText);
        this.conversationName = (TextView) super.findViewById(R.id.textTab);
        this.conversationId =  getIntent().getIntExtra(AppUtil.conversation.taskId, -1);
        String title = getIntent().getStringExtra(AppUtil.conversation.taskName);
        this.conversationName.setText(title);
        initListView();
        initData();
    }

    private void initListView() {
        this.listView = (ListView) super.findViewById(R.id.listView);
        listView.setDivider(null);
    }

    private void initRegisterBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new ChatReceiver();
        filter.addAction(AppUtil.broadcast.chat);
        this.registerReceiver(receiver, filter);
    }

    private void initData() {
        dataList = new ArrayList<Chat>();
        simpleAdapter = new ChatAdapter(this,dataList);
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    public void SendOnClick(View view) {
        if (editText.getText().toString() != null && !editText.getText().toString().equals("")) {
            Chat chat = new Chat();
            chat.setType(conversationId);
            chat.setName(BaseApplication.getInstance().getName());
            chat.setAccount(BaseApplication.getInstance().getAccount());
            chat.setPortrait(BaseApplication.getInstance().getPortrait());
            chat.setMessage(editText.getText().toString());
            sendToService(JSONUtil.sendConversationMessage(chat).toString());
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            simpleAdapter.notifyDataSetChanged();
        }
    };

    public class ChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Chat chat;
                    JSONObject jsonObject;
                    String msg = intent.getStringExtra(AppUtil.message.chatMessage);
                    System.out.println("");
                    try {
                        jsonObject = new JSONObject(msg);
                        chat = new Chat(jsonObject);
                        dataList.add(chat);
                        System.out.println("## " + chat.getMessage());
                        handler.sendMessage(new Message());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
