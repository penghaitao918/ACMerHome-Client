package com.xiaotao.Afamily.activity.subpage;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.adapter.NotifyAdapter;
import com.xiaotao.Afamily.model.entity.Notify;
import com.xiaotao.Afamily.sqlite.DATABASE;
import com.xiaotao.Afamily.sqlite.NotifyTab;
import com.xiaotao.Afamily.utils.ChangeUtil;

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
 * @date 2016-04-21  13:29
 */
public class NotifyPage extends BaseActivity {

    private SQLiteOpenHelper helper = null;
    private NotifyTab notifyTab = null;

    private ListView listView = null;
    private ArrayList<Notify> dataList = null;
    private NotifyAdapter simpleAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page_notify);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void init() {
        initData();
        this.listView = (ListView) findViewById(R.id.listView);
        listView.setDivider(null);
        simpleAdapter = new NotifyAdapter(this, dataList);
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }

    private void initData() {
        this.helper = new DATABASE(this);
        this.notifyTab = new NotifyTab(helper.getReadableDatabase());
        dataList = notifyTab.selectAll();
    }
}
