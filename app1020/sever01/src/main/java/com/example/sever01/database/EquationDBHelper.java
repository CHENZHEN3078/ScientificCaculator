package com.example.sever01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EquationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "equation.db";
    public static final String TABLE_NAME = "equation_info";
    private static final int DB_VERSION = 1;
    private static EquationDBHelper mHelper = null;

    private EquationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static EquationDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new EquationDBHelper(context);
        }
        return mHelper;
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " str VARCHAR NOT NULL," +
                " ans DOUBLE NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}