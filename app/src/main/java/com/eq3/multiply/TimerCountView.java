package com.eq3.multiply;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class TimerCountView extends View {
    private int mWidth,mHeight; // 长宽
    private int strokenWidth = 10; // 弧度
    private boolean isWrapperLine;

    private float lastX,curX;
    //
    private int mCurrValue=100,mLastValue=0;

    private int mBgColor;
    private Paint paint;
    private Paint humiValPaint;
    private ValueChangeCallback mCallback;

    public TimerCountView(Context context) {
        this(context,null);
    }

    public TimerCountView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimerCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        //初始化弧度大小
        strokenWidth = strokenWidth!=-1?strokenWidth:mWidth/5;
    }

    private void init(Context context,AttributeSet attrs) {
        mBgColor = Color.green(0);
        strokenWidth = 10;
        isWrapperLine = true;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        // 百分比
        humiValPaint = new Paint();
        humiValPaint.setAntiAlias(true);
        humiValPaint.setTextSize(sp2px(16));
        humiValPaint.setStrokeWidth((float) 2.0);
        humiValPaint.setColor(Color.parseColor("#E27A3F"));//E27A3F
        humiValPaint.setStyle(Paint.Style.FILL);
    }

    public void setTitleSize(float size){
        humiValPaint.setTextSize(sp2px(size));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制底部的矩形
        RectF rectF = new RectF(0,0,mCurrValue,mHeight);
        int layerId = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        mBgColor = Color.parseColor("#9933FA");
        paint.setColor(mBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF,paint);
        // 开始绘制圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokenWidth);
        rectF = new RectF(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rectF,strokenWidth,strokenWidth,paint);
        canvas.drawText(mCurrValue+"", (mWidth-humiValPaint.measureText(mCurrValue+""))/2, mHeight/2, humiValPaint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
        // 判断是否需要外边框
        if(isWrapperLine){
            paint.setStrokeWidth((float)3.0);
            paint.setColor(Color.GRAY);
            int t = 2;
            rectF = new RectF(t,t,mWidth-t,mHeight-t);
            canvas.drawRoundRect(rectF,strokenWidth,strokenWidth,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX =  event.getX();
                return true;//切记要进行事件拦截消费操作
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                resetValuesX(lastX-curX);
                lastX =  event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void resetValuesX(float v) {
        float cacheValue = mCurrValue+v;
        Log.i("chenxiaoping","v := "+v+"  "+cacheValue+"  mWidth "+mWidth);
        if(cacheValue<=mWidth&&cacheValue>=0){
            mCurrValue = (int) cacheValue;
            if(mCallback!=null){
                mCallback.onValueChanged(cacheValue/mWidth);
            }
        }
        postInvalidate();
    }

    public void setValues(float v){
        setInitialValue(v);
    }

    public TimerCountView setColor(int color){
        mBgColor = color;
        postInvalidate();
        return this;
    }

    /**
     * persent 0-1
     * @param value
     * @return
     */
    public TimerCountView setInitialValue(double value){
        if(value>1||value<0){
            mCurrValue = 0;
        }else{
            mCurrValue = (int) (mHeight*value);
        }

        if(mCallback!=null){
            mCallback.onValueChanged(mCurrValue/mHeight);
        }

        mLastValue = mCurrValue;
        postInvalidate();
        return this;
    }

    public void registerCallback(ValueChangeCallback callback){
        mCallback = callback;
    }

    interface ValueChangeCallback{
        void onValueChanged(double value);
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
