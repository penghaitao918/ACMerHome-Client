package com.xiaotao.Afamily.activity.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.subpage.ChatActivity;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.adapter.ConversationListAdapter;
import com.xiaotao.Afamily.model.entity.Conversation;
import com.xiaotao.Afamily.sqlite.ConversationTab;
import com.xiaotao.Afamily.sqlite.DATABASE;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.SearchViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.xiaotao.Afamily.R.drawable.btn_search_bg;
import static com.xiaotao.Afamily.R.drawable.shape_search_cursor_res;

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
 * @date 2016-04-12  20:26
 */
public class ConversationListPageActivity extends BaseActivity {

    private boolean isLoad = false;

    private ListView listView = null;
    private SearchView searchView = null;
    private ArrayList<Conversation> dataList = null;
    private ConversationListAdapter simpleAdapter = null;

    private SQLiteOpenHelper helper = null;
    private ConversationTab conversationTab = null;

    private ConversationListBroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_page_conversation);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataList != null) {
            dataList.clear();
        }
        super.unregisterReceiver(receiver);
        helper.close();
    }

    private void init() {
        initRegisterBroadcas();
        initList();
        initSearchViewRes();
        initSQLite();

        listView = (ListView) super.findViewById(R.id.listView);
        listView.setDivider(null);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());

        simpleAdapter = new ConversationListAdapter(this, dataList);
        listView.setAdapter(simpleAdapter);
        /*动态刷新ListView*/
        simpleAdapter.notifyDataSetChanged();
    }

    private void initSearchViewRes(){
        this.searchView = (SearchView) super.findViewById(R.id.search_view);
        SearchViewUtil searchViewUtil = new SearchViewUtil(this,searchView);
        searchViewUtil.setBackground(btn_search_bg);
        searchViewUtil.setCursorDrawableRes(shape_search_cursor_res);
        searchViewUtil.setCloseButton();
        searchViewUtil.setTextColor(Color.BLACK);
    }

    //  从服务器获取task列表
    private void initList(){
        dataList = new ArrayList<>();
        Conversation conversation = new Conversation("家人");
        dataList.add(0, conversation);
        sendToService(JSONUtil.getAllTaskInfoList().toString());
    }

    private void initRegisterBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new ConversationListBroadcastReceiver();
        filter.addAction(AppUtil.broadcast.conversationList);
        this.registerReceiver(receiver, filter);
    }

    private void initSQLite() {
        this.helper = new DATABASE(this);
    }

    private void setListData(JSONArray id, JSONArray name) {
        try {
            for (int i = 0; i < id.length(); ++ i)
                dataList.add((int)id.get(i), new Conversation((String) name.get(i)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        simpleAdapter.notifyDataSetChanged();
    }

    private void refresh(String jsonMsg) {
        try {
            JSONObject jsonObject = new JSONObject(jsonMsg);
            int position = jsonObject.getInt(AppUtil.conversation.taskId);
            dataList.get(position).setAccountName(jsonObject.getString(AppUtil.conversation.who) + "：");
            dataList.get(position).setConversationMessage(jsonObject.getString(AppUtil.conversation.mesaage));
            Date date=new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dataList.get(position).setConversationTime(format.format(date));
            dataList.get(position).addCount();
        }catch (JSONException e){
            e.printStackTrace();
        }

        Intent localService = new Intent(AppUtil.broadcast.local_service);
        localService.putExtra(AppUtil.message.type, AppUtil.localService.all);
        sendBroadcast(localService);

        simpleAdapter.notifyDataSetChanged();
    }

    private void cleanCount(int position) {
        dataList.get(position).setMessageCount(0);
        simpleAdapter.notifyDataSetChanged();
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ConversationListPageActivity.this, ChatActivity.class);
            intent.putExtra(AppUtil.conversation.taskId, position);
            intent.putExtra(AppUtil.conversation.taskName, dataList.get(position).getTaskName());
            startActivity(intent);
        }
    }

    public class ConversationListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type =  intent.getIntExtra(AppUtil.message.type, -1);
            //  初始化列表
            if (type == 0 && !isLoad){
                try {
                    String taskListMsg = intent.getStringExtra(AppUtil.message.taskList);
                    JSONObject jsonObject = new JSONObject(taskListMsg);
                    JSONArray idList = jsonObject.getJSONArray(AppUtil.conversation.taskId);
                    JSONArray nameList = jsonObject.getJSONArray(AppUtil.conversation.taskName);
                    setListData(idList, nameList);
                    isLoad = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(type == 1) {
                final String recentConversationtMsg = intent.getStringExtra(AppUtil.message.chatMessage);
                refresh(recentConversationtMsg);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conversationTab = new ConversationTab(helper.getWritableDatabase());
                        conversationTab.create(recentConversationtMsg);
                    }
                }).start();
            } else if(type == 2) {
                int id = intent.getIntExtra(AppUtil.conversation.taskId, -1);
                if (id >= 0) {
                    cleanCount(id);
                }
            }
        }
    }

}
