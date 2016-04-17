package com.xiaotao.Afamily.activity.core;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.subpage.TaskListPage;
import com.xiaotao.Afamily.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 * @date 2016-04-11  13:04
 */
public class HomePageActivity extends BaseActivity {

    private final String IMAGE = "image";
    private final String TEXT = "text";
    private final String[] from = {IMAGE, TEXT};
    private final int[] to = {R.id.labIcon, R.id.labName};
    private final String[] iconName = {
            "签到", "任务b", "通知", "家人", "天气",  "日程规划", "游戏", "设置", "意见反馈", "更新"
    };
    private final int[] icon = {
            R.drawable.ic_launcher, R.drawable.task_info_btn, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.weather_btn, R.drawable.calendar_btn, R.drawable.ic_launcher, R.drawable.setting_btn,
            R.drawable.feedback_btn, R.drawable.update_btn
    };

    private GridView gridView = null;
    private SimpleAdapter simpleAdapter = null;
    private ArrayList<Map<String, Object>> dataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_page_home);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataList.clear();
    }

    private void init(){
        initData();
        gridView = (GridView) super.findViewById(R.id.gridView);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new OnItemClickListenerImpl());

        simpleAdapter = new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(simpleAdapter);
    }

    public void initData(){
        dataList = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < iconName.length; ++ i){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(IMAGE, icon[i]);
            map.put(TEXT, iconName[i]);
            dataList.add(map);
        }
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(position);
            switch (position) {
                case 0:
                    break;
                case 1:
                    Intent intent1 = new Intent(HomePageActivity.this, TaskListPage.class);
                    startActivity(intent1);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }

}
