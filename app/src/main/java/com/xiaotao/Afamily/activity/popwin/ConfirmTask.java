package com.xiaotao.Afamily.activity.popwin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
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
 * @date 2016-04-17  18:31
 */
public class ConfirmTask extends BaseActivity {

    private int taskId = -1;
    private TextView taskName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.popwin_confirm_task);
        this.init();
    }

    private void init() {
        this.taskName = (TextView) super.findViewById(R.id.taskName);
        this.taskId =  getIntent().getIntExtra(AppUtil.studentTask.taskId, -1);
        String title = getIntent().getStringExtra(AppUtil.studentTask.name) + " " + this.taskName.getText();
        this.taskName.setText(title);
    }

    public void confirmTaskOnClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (taskId != -1) {
                    sendToService(JSONUtil.submitTask(taskId).toString());
                    //  回执
                    Intent it = new Intent(AppUtil.broadcast.studentTaskList);
                    it.putExtra(AppUtil.message.type, 2);
                    it.putExtra(AppUtil.message.studentTask, taskId);
                    sendBroadcast(it);
                } else {
                    Toast.makeText(this,"该任务已经完成！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                break;
        }
        onBackPressed();
    }

}
