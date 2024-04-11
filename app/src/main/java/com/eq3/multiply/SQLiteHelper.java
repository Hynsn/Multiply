package com.eq3.multiply;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    static final String TAG = "SQLiteHelper";

    static final String TB_NAME = "question";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_NAME + '(' +
                QuesDetail.QUES_ID + " integer primary key autoincrement, " +
                QuesDetail.GAME_NUM + " integer, "+
                QuesDetail.TEXT + " varchar(10), " +
                QuesDetail.COUNT_TIME + " integer, "+
                QuesDetail.RESULT + " integer "+
                ')');
        Log.i(TAG, "create");
    }

    public void insert(SQLiteDatabase db,QuesDetail ques){
        ContentValues cv = new ContentValues();
        cv.put("game_num",ques.getGameNum());
        cv.put("text",ques.getText());
        cv.put("count_time",ques.getCountTime());
        cv.put("res",ques.isResult());
        db.insert(TB_NAME,null,cv);
        Log.i(TAG, "insert");
    }

    void querybyGameNum(){
        //select * from person order by id desc
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }
}
