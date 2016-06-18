package com.xiaotao.Afamily.activity.popwin;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.sqlite.ConversationTab;
import com.xiaotao.Afamily.sqlite.DATABASE;
import com.xiaotao.Afamily.sqlite.NotifyTab;

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
 * @date 2016-05-29  20:42
 */
public class ConfirmNotifition extends BaseActivity {

    private SQLiteOpenHelper helper = null;
    private NotifyTab notifyTab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.popwin_confirm_selete_conversation);
        this.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        helper = new DATABASE(this);
        notifyTab = new NotifyTab(helper.getWritableDatabase());
    }

    public void confirmConversationOnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                notifyTab.delete();
                Toast.makeText(this, "通知记录清空完成！", Toast.LENGTH_SHORT).show();
                break;
        }
        onBackPressed();
    }

}
