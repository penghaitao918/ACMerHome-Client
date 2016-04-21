package com.xiaotao.Afamily.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import com.xiaotao.Afamily.model.entity.Chat;
import com.xiaotao.Afamily.model.entity.Notify;
import com.xiaotao.Afamily.utils.AppUtil;

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
 * @date 2016-04-21  13:40
 */
public class NotifyTab {

    private SQLiteDatabase db = null;

    public NotifyTab(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(String title, String message){
        String sql = "INSERT INTO notify VALUES(?, ?, datetime())";
        Object args[] = new Object[]{ title, message };
        this.db.execSQL(sql, args);
        this.db.close();
    }

    public ArrayList<Notify> selectAll() {
        ArrayList<Notify> arrayList = new ArrayList<>();
        arrayList.add(0,new Notify());
        String sql = "select * from notify";
        Cursor result = this.db.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Notify item = new Notify();
            item.setTitle(result.getString(0));
            item.setMessage(result.getString(1));
            item.setTime(result.getString(2));
            arrayList.add(item);
        }
        this.db.close();
        return arrayList;
    }

    public void delete() {
        String sql = "delete from notify";
        this.db.execSQL(sql);
        this.db.close();
    }

}
