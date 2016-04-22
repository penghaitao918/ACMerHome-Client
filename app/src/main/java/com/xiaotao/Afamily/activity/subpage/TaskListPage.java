package com.xiaotao.Afamily.activity.subpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.popwin.ConfirmTask;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.adapter.StudentTaskAdapter;
import com.xiaotao.Afamily.model.entity.StudentTask;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

import org.json.JSONArray;
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
 * @date 2016-04-16  21:08
 */
public class TaskListPage extends BaseActivity {

    private ListView listView = null;
    private ArrayList<StudentTask> dataList = null;
    private StudentTaskAdapter simpleAdapter = null;

    private TaskListBroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page_sub_task_info);
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
        this.initRegisterBroadcas();
        this.initListVIew();
        this.initListData();
    }

    private void initListVIew() {
        this.listView = (ListView) super.findViewById(R.id.listView);
        listView.setDivider(null);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    private void initListData() {
        dataList = new ArrayList<>();
        simpleAdapter = new StudentTaskAdapter(this, dataList);
        listView.setAdapter(simpleAdapter);
        /*动态刷新ListView*/
        simpleAdapter.notifyDataSetChanged();
        sendToService(JSONUtil.getAllStudentTaskList().toString());
    }

    private void initRegisterBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new TaskListBroadcastReceiver();
        filter.addAction(AppUtil.broadcast.studentTaskList);
        this.registerReceiver(receiver, filter);
    }

    private void setListData(JSONArray id, JSONArray name) {
        try {
            for (int i = 0; i < id.length(); ++ i)
                dataList.add((int)id.get(i) - 1, new StudentTask((String) name.get(i)));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(TaskListPage.this, ConfirmTask.class);
            if (dataList.get(position).isFinish()) {
                intent.putExtra(AppUtil.studentTask.taskId, -1);
                intent.putExtra(AppUtil.studentTask.name, dataList.get(position).getTaskName());
            } else {
                intent.putExtra(AppUtil.studentTask.taskId, position);
                intent.putExtra(AppUtil.studentTask.name, dataList.get(position).getTaskName());
            }
            startActivity(intent);
        }
    }


    public class TaskListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg;
            JSONObject jsonObject;
            int type =  intent.getIntExtra(AppUtil.message.type, -1);
            try {
                if (type == 0) {
                    //  初始化列表
                    msg = intent.getStringExtra(AppUtil.message.studentTask);
                    jsonObject = new JSONObject(msg);
                    JSONArray idList = jsonObject.getJSONArray(AppUtil.conversation.taskId);
                    JSONArray nameList = jsonObject.getJSONArray(AppUtil.conversation.taskName);
                    setListData(idList, nameList);
                } else if (type == 1) {
                    msg = intent.getStringExtra(AppUtil.message.studentTask);
                    jsonObject = new JSONObject(msg);
                    for (int i = 0; i < dataList.size(); ++ i) {
                        dataList.get(i).setFinish(jsonObject.getBoolean(AppUtil.studentTask.task[i]));
                    }
                } else if(type == 2) {
                    int Id = intent.getIntExtra(AppUtil.message.studentTask, -1);
                    dataList.get(Id).setFinish(true);
                }
                simpleAdapter.notifyDataSetChanged();
            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
