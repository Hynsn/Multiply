package com.eq3.multiply;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import static com.eq3.multiply.SQLiteHelper.TB_NAME;

public class QuesActivity extends Activity {

    ListView quesLV;
    QuesListAdapter quesAdapter;
    ArrayList<QuesInfo> quesInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);

        quesLV = findViewById(R.id.ques_data_lv);
        quesAdapter = new QuesListAdapter(QuesActivity.this,R.layout.question_item);

        quesLV.setAdapter(quesAdapter);

        SQLiteDatabase db = this.openOrCreateDatabase("question",MODE_PRIVATE,null);
        // change to game_num from RecordList
        int i = getIntent().getIntExtra("record_id",1);
        Log.i("ques", "game_num: "+i);

        Cursor cursor = db.query(TB_NAME, new String[]{QuesDetail.QUES_ID,QuesDetail.TEXT,QuesDetail.COUNT_TIME,QuesDetail.RESULT}, "game_num=?", new String[]{String.valueOf(i)}, null, null, null);
        int id = 1;
        while(cursor.moveToNext()){
            //int id = cursor.getInt(cursor.getColumnIndex(QuesDetail.QUES_ID));
            //int gameNum = cursor.getInt(cursor.getColumnIndex(QuesDetail.GAME_NUM));
            String text = cursor.getString(cursor.getColumnIndex(QuesDetail.TEXT));
            int counteTime = cursor.getInt(cursor.getColumnIndex(QuesDetail.COUNT_TIME));
            int res = cursor.getInt(cursor.getColumnIndex(QuesDetail.RESULT));
            QuesInfo quesInfo = new QuesInfo(id,text,counteTime,(res!=0)?true:false);
            quesInfos.add(quesInfo);
            id++;
            //Log.i("data", "query: "+text+","+id);
        }
        //select * from person order by id desc
        // 查询最大值 gamenum最大值 select max(game_num)  from question
        // 分组查询 select *  from question  order by game_num desc
        // 条件查询 select *  from question where game_num=1
        // 查询各组中sum的数量和OK的数量 select count(*) as sum from question where game_num=1 and res=0/1
        Cursor cursor1 = db.rawQuery("select count(*) as sum from question where game_num=1 ", null);
        while (cursor1.moveToNext()) {
            int ques_id = cursor1.getInt(0); //获取第一列的值,第一列的索引从0开始
            /*
            int game_num = cursor1.getInt(1);//获取第二列的值
            String text = cursor1.getString(2);//获取第三列的值
            int counter = cursor1.getInt(3);
            int res = cursor1.getInt(4);

            Log.i("data", "raw Query: "+ques_id+", "+game_num+", "+text+", "+counter+", "+res);
            */
            Log.i("data", "ques_id: "+ques_id);
        }

        cursor.close();
        db.close();

        quesAdapter.addAll(quesInfos);

    }
}
