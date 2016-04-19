package com.xiaotao.Afamily.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PengHaitao on 2015/2/24.
 */
public class DATABASE extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "acm_family.db";
    private static final int DATABASE_VERSION = 1;

    public DATABASE(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.CreateConversationTab(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.DropConversationTab(db);
        DATABASE.this.onCreate(db);
    }

    private void CreateConversationTab(SQLiteDatabase database) {
        String sql = "CREATE TABLE `conversation` (" +
                "  `task_id` int(2) DEFAULT NULL," +
                "  `user_name` varchar(50) DEFAULT NULL," +
                "  `portrait` mediumblob," +
                "  `message` mediumblob," +
                "  `time` datetime DEFAULT NULL" +
                ")";

        database.execSQL(sql);
    }

    private void DropConversationTab(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS conversation";
        db.execSQL(sql);
    }
}