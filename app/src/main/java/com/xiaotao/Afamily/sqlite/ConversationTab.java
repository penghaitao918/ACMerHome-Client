package com.xiaotao.Afamily.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xiaotao.Afamily.model.entity.Chat;
import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.ChangeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
 * @date 2016-04-19  16:26
 */
public class ConversationTab {

    private SQLiteDatabase db = null;

    public ConversationTab(SQLiteDatabase db) {
        this.db = db;
    }

    //  更新数据    有新消息是调用
    public void create(String jsonMsg) {
        System.out.println("MSG " + jsonMsg);
        try {
            JSONObject jsonObject = new JSONObject(jsonMsg);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ChangeUtil.toBitmap(
                    jsonObject.getString(AppUtil.conversation.potrait)
            ).compress(
                    Bitmap.CompressFormat.PNG, 100, os
            );
            String sql = "INSERT INTO conversation VALUES(?, ?, ?, ?, ?, datetime())";
            Object args[] = new Object[]{
                    jsonObject.getInt(AppUtil.conversation.taskId),
                    jsonObject.getString(AppUtil.conversation.account),
                    jsonObject.getString(AppUtil.conversation.who),
                    os.toByteArray(),
                    jsonObject.getString(AppUtil.conversation.mesaage)
            };
            this.db.execSQL(sql, args);
        }catch (JSONException e){
            e.printStackTrace();
        }
        this.db.close();
    }

    public ArrayList<Chat> getConversationMsgByTaskId(int taskId) {
        ArrayList<Chat> chatList = new ArrayList<>();
        chatList.add(0, new Chat());
        String sql = "SELECT * FROM conversation WHERE task_id = ? ORDER BY TIME ASC";
        String args[] = new String[]{Integer.toString(taskId)};
        Cursor result = this.db.rawQuery(sql, args);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Chat item = new Chat();
            item.setType(result.getInt(0));
            item.setAccount(result.getString(1));
            item.setName(result.getString(2));
            byte[] in = result.getBlob(3);
            item.setPortrait(BitmapFactory.decodeByteArray(in, 0, in.length));
            item.setMessage(result.getString(4));
            chatList.add(item);
        }
        this.db.close();
        return chatList;
    }

    //  删除指定task的讨论数据
    public void deleteByTaskId(int id) {
        String delete = "delete from conversation where task_id = ?";
        String args[] = new String[]{Integer.toString(id)};
        this.db.execSQL(delete, args);
        this.db.close();
    }

    //  删除所有对话
    public void deleteAllConversation() {
        String delete = "delete from conversation";
        this.db.execSQL(delete);
        this.db.close();
    }
}
