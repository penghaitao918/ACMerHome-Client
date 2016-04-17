package com.xiaotao.Afamily.activity.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.adapter.ConversationAdapter;
import com.xiaotao.Afamily.model.entity.Conversation;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;
import com.xiaotao.Afamily.utils.SearchViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private ConversationAdapter simpleAdapter = null;

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
    }

    private void init() {
        initRegisterBroadcas();
        initList();
        initSearchViewRes();
        listView = (ListView) super.findViewById(R.id.listView);
        listView.setDivider(null);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());

        simpleAdapter = new ConversationAdapter(this, dataList);
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
    }

    //  从服务器获取task列表
    private void initList(){
        dataList = new ArrayList<>();
        Conversation conversation = new Conversation("家人");
        dataList.add(0,conversation);
        sendToService(JSONUtil.getAllTaskInfoList().toString());
    }

    private void initRegisterBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new ConversationListBroadcastReceiver();
        filter.addAction(AppUtil.broadcast.conversationList);
        this.registerReceiver(receiver, filter);
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

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(position + " # " + dataList.get(position).getTaskName());
        }
    }

    public class ConversationListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type =  intent.getIntExtra(AppUtil.message.type, -1);
            //  初始化列表
            if (type == 0 && !isLoad){
                String msg = intent.getStringExtra(AppUtil.message.taskList);
                try {
                    JSONObject jsonObject = new JSONObject(msg);
                    JSONArray idList = jsonObject.getJSONArray(AppUtil.conversation.taskId);
                    JSONArray nameList = jsonObject.getJSONArray(AppUtil.conversation.name);
                    setListData(idList, nameList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isLoad = true;
            } else if(type == 1) {
                //  TODO 接收最新消息
            }

        }
    }

}
