package com.eq3.multiply;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {
    static final String TAG = "SQLiteHelper";

    SQLiteHelper helper;
    SQLiteDatabase db;

    Button gameStartBtn,gameRecordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gameStartBtn = findViewById(R.id.game_start_btn);
        gameStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int gameNum = 1;
                Cursor cursor = db.rawQuery("select max(game_num) from question", null);
                if(cursor.moveToNext()){
                    int max = cursor.getInt(0);
                    if(max>0){
                        gameNum = max+1;
                    }
                }
                else {
                    gameNum = 1;
                }
                intent.putExtra("game_num",gameNum);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });
        gameRecordBtn = findViewById(R.id.game_record_btn);
        gameRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        helper = new SQLiteHelper(StartActivity.this,null,null,2);
        db = this.openOrCreateDatabase("question",MODE_PRIVATE,null);
        helper.onCreate(db);

    }
}
