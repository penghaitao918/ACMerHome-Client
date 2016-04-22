package com.xiaotao.Afamily.activity.popwin;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.sqlite.ConversationTab;
import com.xiaotao.Afamily.sqlite.DATABASE;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.JSONUtil;

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
 * @date 2016-04-22  13:52
 */
public class ConfirmConversation extends BaseActivity {

    private SQLiteOpenHelper helper = null;
    private ConversationTab conversationTab = null;

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
        conversationTab = new ConversationTab(helper.getWritableDatabase());
    }

    public void confirmConversationOnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                conversationTab.deleteAllConversation();
                Toast.makeText(this, "聊天记录清空完成！", Toast.LENGTH_SHORT).show();
                break;
        }
        onBackPressed();
    }

}
