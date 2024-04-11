package com.eq3.multiply;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordActivity extends Activity {
    static final String TAG = "Record";
    ListView recordLV;
    RecordListAdapter recordAdapter;
    ArrayList<RecordInfo> recordInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recordLV = findViewById(R.id.record_data_lv);

        recordAdapter = new RecordListAdapter(RecordActivity.this,R.layout.record_item);
        recordLV.setAdapter(recordAdapter);

        recordLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), QuesActivity.class);
                intent.putExtra("record_id",recordInfos.get(position).getRecordId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });
        recordLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"long pressed ",0).show();

                SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("question",MODE_PRIVATE,null);
                db.delete("question", "game_num=?", new String[]{String.valueOf(recordInfos.get(position).getRecordId())});
                db.close();

                recordAdapter.remove(recordInfos.get(position));
                recordInfos.remove(position);
                recordAdapter.notifyDataSetChanged();
                return true;
            }
        });

        SQLiteDatabase db = this.openOrCreateDatabase("question",MODE_PRIVATE,null);
        // change to game_num from RecordList
        //select * from person order by id desc
        // 查询最大值 gamenum最大值 select max(game_num)  from question
        // 分组查询 select *  from question  order by game_num desc
        // 条件查询 select *  from question where game_num=1
        // 查询各组中sum的数量和OK的数量 select count(*) as sum from question where game_num=1 and res=0/1
        Cursor cursor = db.rawQuery("select max(game_num) from question", null);
        if(cursor.moveToNext()){
            int maxNum = cursor.getInt(0);
            Log.i(TAG, "max: "+maxNum);
            for (int i=maxNum;i>=1;i--){
                int sum = 0,right = 0,totalTime = 0;
                cursor = db.rawQuery("select count(*) as sum from question where game_num="+i, null);
                if(cursor.moveToNext()){
                    sum = cursor.getInt(0);
                }
                cursor = db.rawQuery("select count(*) as sum from question where game_num="+i+" and res=1", null);
                if(cursor.moveToNext()){
                    right = cursor.getInt(0);
                }
                cursor = db.rawQuery("select sum(count_time) from question where game_num="+i, null);
                if(cursor.moveToNext()){
                    totalTime = cursor.getInt(0);
                }
                if((sum!=0)&&(right!=0)&&(totalTime!=0)){
                    Log.i(TAG, "sum: "+sum+", "+right);
                    int grade = (int)(((float)right/sum)*100.0f);
                    int level = (int)(((float)right/sum)*5.0f);
                    RecordInfo info = new RecordInfo(i,sum,grade,level,totalTime);
                    recordInfos.add(info);
                }
            }
        }
        else {
            // 无数据
            Toast.makeText(getApplicationContext(),"no score record",0).show();
        }
        cursor.close();
        db.close();
        recordAdapter.addAll(recordInfos);

        recordAdapter.notifyDataSetChanged();
    }
}
