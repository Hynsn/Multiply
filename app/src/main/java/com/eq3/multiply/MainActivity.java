package com.eq3.multiply;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    final static String TAG = "Multiply";

    final static int MIN_SUPPORT_HEIGHT = 620;

    SQLiteHelper helper;
    SQLiteDatabase db;

    int screenWidth,screenHeight;
    int result = 0,keyNumber = 1,deepNumber = 3;
    int gameNum = 1;

    Button number1Btn,number2Btn,number3Btn,number4Btn,
            number5Btn,number6Btn,number7Btn,number8Btn,
            number9Btn,number0Btn,clearBtn,calculateBtn;
    TextView keyInputTV,firstNumTV,secondNumTV,counterTimeTV,rightCounterTV,wrongCounterTV;
    LinearLayout mainTopTitle;

    Context mContext;

    static final int TIMER_MAX = 20;
    static final int COUNTER_SPACE = 1000;

    int counterTime = TIMER_MAX;
    int rightCounter = 0,wrongCounter = 0;

    int firstRandomNum = 0,secondRandomNum = 0;
    // 结果位数
    int resultLen = 0;

    Handler handler=new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            counterTime--;

            if(counterTime==0){
                counterTime = TIMER_MAX;

                try{
                    int sum = Integer.valueOf(keyInputTV.getText().toString().trim());
                    boolean ret =(secondRandomNum*firstRandomNum)==sum;
                    blinkTime = 0;
                    if(ret){
                        handlerBlink.post(blinkRight);
                        rightCounter++;
                        rightCounterTV.setText(""+rightCounter);
                    }
                    else {
                        handlerBlink.post(blinkWrong);
                        wrongCounter++;
                        wrongCounterTV.setText(""+wrongCounter);
                    }

                    /*
                    QuesDetail info = new QuesDetail(gameNum,firstRandomNum+" × "+secondRandomNum,TIMER_MAX,ret);
                    helper.insert(db,info);

                    firstRandomNum = getRandomNum();
                    secondRandomNum = getRandomNum();

                    secondNumTV.setText(""+firstRandomNum);
                    firstNumTV.setText(""+secondRandomNum);
                    keyInputTV.setText("?");
                    */
                    counterTimeTV.setText(String.format("%1$02d",counterTime)+"s");
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext,"over "+TIMER_MAX+"s no typing ...",0).show();
                }
            }
            else {
                counterTimeTV.setText(String.format("%1$02d",counterTime)+"s");
            }

            handler.postDelayed(this, COUNTER_SPACE);
        }
    };

    int blinkTime = 0;
    Handler handlerBlink = new Handler();
    Runnable blinkRight = new Runnable() {
        @Override
        public void run() {
            switch (blinkTime){
            case 0:
                keyInputTV.setBackgroundResource(R.drawable.woman_02_right);
                break;
            case 1:
                keyInputTV.setBackgroundResource(R.drawable.woman_021);
                break;
            case 2:
                keyInputTV.setBackgroundResource(R.drawable.woman_02_right);
                break;
            case 3:
                keyInputTV.setBackgroundResource(R.drawable.woman_021);
                break;
            }
            if(blinkTime>3){
                try {
                    int sum = Integer.valueOf(keyInputTV.getText().toString().trim());
                    boolean ret =(secondRandomNum*firstRandomNum)==sum;

                    QuesDetail info = new QuesDetail(gameNum,firstRandomNum+" × "+secondRandomNum,TIMER_MAX-counterTime,ret);
                    helper.insert(db,info);

                    counterTime = TIMER_MAX;
                    firstRandomNum = getRandomNum();
                    secondRandomNum = getRandomNum();
                    resultLen = LengthNum(firstRandomNum*secondRandomNum);

                    //Log.i(TAG, "resultLen: "+LengthNum(firstRandomNum*secondRandomNum));

                    secondNumTV.setText(""+firstRandomNum);
                    firstNumTV.setText(""+secondRandomNum);
                    keyInputTV.setText("?");
                } catch (Exception e){
                    e.printStackTrace();
                }

                blinkTime = 0;
                handlerBlink.removeCallbacks(this);
            }
            else {
                handlerBlink.postDelayed(this,200);
            }
            blinkTime++;
        }
    };
    Runnable blinkWrong = new Runnable() {
        @Override
        public void run() {
            switch (blinkTime){
                case 0:
                    keyInputTV.setBackgroundResource(R.drawable.woman_024);
                    break;
                case 1:
                    keyInputTV.setBackgroundResource(R.drawable.woman_021);
                    break;
                case 2:
                    keyInputTV.setBackgroundResource(R.drawable.woman_024);
                    break;
                case 3:
                    keyInputTV.setBackgroundResource(R.drawable.woman_021);
                    break;
            }
            if(blinkTime>3){
                try {
                    int sum = Integer.valueOf(keyInputTV.getText().toString().trim());

                    boolean ret =(secondRandomNum*firstRandomNum)==sum;

                    QuesDetail info = new QuesDetail(gameNum,firstRandomNum+" × "+secondRandomNum,TIMER_MAX-counterTime,ret);
                    helper.insert(db,info);

                    counterTime = TIMER_MAX;
                    firstRandomNum = getRandomNum();
                    secondRandomNum = getRandomNum();

                    resultLen = LengthNum(firstRandomNum*secondRandomNum);

                    secondNumTV.setText(""+firstRandomNum);
                    firstNumTV.setText(""+secondRandomNum);
                    keyInputTV.setText("?");
                }catch (Exception e){
                    e.printStackTrace();
                }


                blinkTime = 0;
                handlerBlink.removeCallbacks(this);
            }
            else {
                handlerBlink.postDelayed(this,200);
            }
            blinkTime++;


        }
    };

    public static int LengthNum(int num) {
        int count = 0;
        while(num>=1) {
            num/=10;
            count++;
        }
        return count;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        gameNum = getIntent().getIntExtra("game_num",1);
        getAndroiodScreenProperty();

        number1Btn = findViewById(R.id.number1_btn);
        number2Btn = findViewById(R.id.number2_btn);
        number3Btn = findViewById(R.id.number3_btn);
        number4Btn = findViewById(R.id.number4_btn);
        number5Btn = findViewById(R.id.number5_btn);
        number6Btn = findViewById(R.id.number6_btn);
        number7Btn = findViewById(R.id.number7_btn);
        number8Btn = findViewById(R.id.number8_btn);
        number9Btn = findViewById(R.id.number9_btn);
        number0Btn = findViewById(R.id.number0_btn);
        clearBtn = findViewById(R.id.clear_btn);
        calculateBtn = findViewById(R.id.calculate_btn);
        keyInputTV = findViewById(R.id.key_input_tv);
        firstNumTV = findViewById(R.id.first_num_tv);
        secondNumTV = findViewById(R.id.second_num_tv);

        mainTopTitle = findViewById(R.id.main_top_title);

        counterTimeTV = findViewById(R.id.counter_time_tv);
        counterTimeTV.setText(counterTime+"s");
        rightCounterTV = findViewById(R.id.right_counter_tv);
        rightCounterTV.setText(rightCounter+"");
        wrongCounterTV = findViewById(R.id.wrong_counter_tv);
        wrongCounterTV.setText(wrongCounter+"");

        firstRandomNum = getRandomNum();
        secondRandomNum = getRandomNum();
        resultLen = LengthNum(firstRandomNum*secondRandomNum);
        firstNumTV.setText(""+firstRandomNum);
        secondNumTV.setText(""+secondRandomNum);

        number1Btn.setOnClickListener(this);
        number2Btn.setOnClickListener(this);
        number3Btn.setOnClickListener(this);
        number4Btn.setOnClickListener(this);
        number5Btn.setOnClickListener(this);
        number6Btn.setOnClickListener(this);
        number7Btn.setOnClickListener(this);
        number8Btn.setOnClickListener(this);
        number9Btn.setOnClickListener(this);
        number0Btn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        calculateBtn.setOnClickListener(this);

        int gridWidth = dp2px(mContext,(float) screenWidth/3);
        Log.i(TAG, "screenHeight: "+screenHeight);

        if(screenHeight<MIN_SUPPORT_HEIGHT){
            mainTopTitle.setVisibility(View.GONE);
        }

        number1Btn.setWidth(gridWidth);
        number2Btn.setWidth(gridWidth);
        number3Btn.setWidth(gridWidth);
        number4Btn.setWidth(gridWidth);
        number5Btn.setWidth(gridWidth);
        number6Btn.setWidth(gridWidth);
        number7Btn.setWidth(gridWidth);
        number8Btn.setWidth(gridWidth);
        number9Btn.setWidth(gridWidth);
        number0Btn.setWidth(gridWidth);
        clearBtn.setWidth(gridWidth);
        calculateBtn.setWidth(gridWidth);
        /*
        keyInputTV.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
        keyInputTV.getPaint().setAntiAlias(true); // 抗锯齿
        */

        helper = new SQLiteHelper(MainActivity.this,null,null,2);
        db = this.openOrCreateDatabase("question",MODE_PRIVATE,null);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    boolean checkIsLegal(String num){
        int length = keyInputTV.getText().length();
        if(length<=3){
            try{
                Log.i(TAG, "checkIsLegal: "+Integer.valueOf(result));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            String str = keyInputTV.getText().toString().trim();
            if(str.startsWith("0")||str.startsWith("?")){
                Log.i(TAG, "is start with 0");
                keyInputTV.setText("");
            }

            Log.i(TAG, "len : "+length);
            if(length==3){
                Toast.makeText(mContext,"typing number too big. ",0).show();
            }
            else {
                keyInputTV.append(num);
                if(resultLen==keyInputTV.getText().toString().length()){
                    try{
                        int sum = Integer.valueOf(keyInputTV.getText().toString().trim());
                        boolean ret =(secondRandomNum*firstRandomNum)==sum;
                        blinkTime = 0;
                        if(ret){
                            handlerBlink.post(blinkRight);
                            rightCounter++;
                            rightCounterTV.setText(""+rightCounter);
                        }
                        else {
                            handlerBlink.post(blinkWrong);
                            wrongCounter++;
                            wrongCounterTV.setText(""+wrongCounter);
                        }
                        Log.i(TAG, "gameNum: "+gameNum);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mContext,"pelase typing numberkey",0).show();
                    }
                }
                //result = keyInputTV.getText().toString().trim();
                //Log.i(TAG, "append 2: " + result.length());
            }
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number1_btn:
                checkIsLegal("1");
                break;
            case R.id.number2_btn:
                checkIsLegal("2");
                break;
            case R.id.number3_btn:
                checkIsLegal("3");
                break;
            case R.id.number4_btn:
                checkIsLegal("4");
                break;
            case R.id.number5_btn:
                checkIsLegal("5");
                break;
            case R.id.number6_btn:
                checkIsLegal("6");
                break;
            case R.id.number7_btn:
                checkIsLegal("7");
                break;
            case R.id.number8_btn:
                checkIsLegal("8");
                break;
            case R.id.number9_btn:
                checkIsLegal("9");
                break;
            case R.id.number0_btn:
                checkIsLegal("0");
                break;
            case R.id.clear_btn:
                //keyInputTV
                keyInputTV.setText("");
                break;
            case R.id.calculate_btn:// 判断是否正确
                try{
                    int sum = Integer.valueOf(keyInputTV.getText().toString().trim());
                    boolean ret =(secondRandomNum*firstRandomNum)==sum;
                    blinkTime = 0;
                    if(ret){
                        handlerBlink.post(blinkRight);
                        rightCounter++;
                        rightCounterTV.setText(""+rightCounter);
                    }
                    else {
                        handlerBlink.post(blinkWrong);
                        wrongCounter++;
                        wrongCounterTV.setText(""+wrongCounter);
                    }
                    Log.i(TAG, "gameNum: "+gameNum);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext,"pelase typing numberkey",0).show();
                }

                break;
        }

        //Log.i(TAG, "result: "+result);

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(runnable, COUNTER_SPACE);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    int getRandomNum(){
        Random random =new Random();
        int result=random.nextInt(10);
        return result+1;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}
