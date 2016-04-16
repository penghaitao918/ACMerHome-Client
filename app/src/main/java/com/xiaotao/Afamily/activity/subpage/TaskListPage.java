package com.xiaotao.Afamily.activity.subpage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;

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
    // TODO: 2016/4/16 任务名字 是否完成 完成率 从服务器获取

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page_sub_task_info);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        this.listView = (ListView) super.findViewById(R.id.listView);
        listView.setDivider(null);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //    System.out.println(position + " # " + dataList.get(position).getTaskName());
        }
    }
}
