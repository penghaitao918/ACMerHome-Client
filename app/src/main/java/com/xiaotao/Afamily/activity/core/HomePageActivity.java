package com.xiaotao.Afamily.activity.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.activity.popwin.ConfirmSignIn;
import com.xiaotao.Afamily.activity.popwin.ConfirmTask;
import com.xiaotao.Afamily.activity.subpage.ChatActivity;
import com.xiaotao.Afamily.activity.subpage.FeedbackPage;
import com.xiaotao.Afamily.activity.subpage.NotifyPage;
import com.xiaotao.Afamily.activity.subpage.SettingsPage;
import com.xiaotao.Afamily.activity.subpage.TaskListPage;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.service.LocalService;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

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

    private HomePageReceiver receiver = null;

    private final String IMAGE = "image";
    private final String TEXT = "text";
    private final String[] from = {IMAGE, TEXT};
    private final int[] to = {R.id.labIcon, R.id.labName};
    private final String[] iconName = {
            "签到", "任务", "通知", "家人", "天气",  "日程规划", "游戏", "设置", "意见反馈", "更新"
    };
    private final int[] icon = {
            R.drawable.sign_btn, R.drawable.task_info_btn, R.drawable.notify_btn, R.drawable.family_btn,
            R.drawable.weather_btn, R.drawable.calendar_btn, R.drawable.game_btn, R.drawable.setting_btn,
            R.drawable.feedback_btn, R.drawable.update_btn
    };

    private GridView gridView = null;
    private SimpleAdapter simpleAdapter = null;
    private ArrayList<Map<String, Object>> dataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_page_home);
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        initBroadcast();
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

        simpleAdapter = new SimpleAdapter(this,
                dataList,
                R.layout.gridview_item,
                from,
                to
        );
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
    private void initBroadcast() {
        receiver = new HomePageReceiver();
        //  创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppUtil.broadcast.check);
        registerReceiver(receiver, filter);
    }


    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(position);
            switch (position) {
                case 0:
                    Intent intent0 = new Intent(HomePageActivity.this, ConfirmSignIn.class);
                    startActivity(intent0);
                    break;
                case 1:
                    Intent intent1 = new Intent(HomePageActivity.this, TaskListPage.class);
                    startActivity(intent1);
                    break;
                case 2:
                    Intent intent2 = new Intent(HomePageActivity.this, NotifyPage.class);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(HomePageActivity.this, ChatActivity.class);
                    intent3.putExtra(AppUtil.conversation.taskId, 0);
                    intent3.putExtra(AppUtil.conversation.taskName, "家人");
                    startActivity(intent3);
                    break;
                case 4:
                    Toast.makeText(HomePageActivity.this, "该功能尚未开放，敬请期待。", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(HomePageActivity.this, "该功能尚未开放，敬请期待。", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(HomePageActivity.this, "该功能尚未开放，敬请期待。", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Intent intent7 = new Intent(HomePageActivity.this, SettingsPage.class);
                    startActivity(intent7);
                    break;
                case 8:
                    Intent intent8 = new Intent(HomePageActivity.this, FeedbackPage.class);
                    startActivity(intent8);
                    break;
                case 9:
                    Toast.makeText(HomePageActivity.this, "Version 1.1 \n 当前已是最高版本！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class HomePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(AppUtil.message.type, -1) == 1) {
                Toast.makeText(HomePageActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
