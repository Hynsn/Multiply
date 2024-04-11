package com.eq3.multiply;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordListAdapter extends ArrayAdapter<RecordInfo> {
    private Context mContext;
    private final LayoutInflater mInflater;
    private int mResource;

    public RecordListAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null == mContext){
            mContext = parent.getContext();
        }
        //if (view == null) {
            view = mInflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder();
            if(position%2==1){
                view.setBackgroundColor(Color.parseColor("#AFEEEE"));
            }
            /*
            viewHolder.recordIdTV = view.findViewById(R.id.record_id_tv);
            viewHolder.sumTV = view.findViewById(R.id.record_sum_tv);
            viewHolder.rightSumTV = view.findViewById(R.id.record_rightsum_tv);
            viewHolder.gradeTV = view.findViewById(R.id.record_grade_tv);
            viewHolder.appleNum0IV = view.findViewById(R.id.apple_num0_iv);
            viewHolder.appleNum1IV = view.findViewById(R.id.apple_num1_iv);
            viewHolder.appleNum2IV = view.findViewById(R.id.apple_num2_iv);
            viewHolder.appleNum3IV = view.findViewById(R.id.apple_num3_iv);
            viewHolder.appleNum4IV = view.findViewById(R.id.apple_num4_iv);
            viewHolder.appleNum5IV = view.findViewById(R.id.apple_num5_iv);
            viewHolder.appleNum6IV = view.findViewById(R.id.apple_num6_iv);
            */
            viewHolder.recordIdTV = view.findViewById(R.id.record_id_tv);
            viewHolder.totalTimeTV = view.findViewById(R.id.total_time_tv);
            viewHolder.quesQtyTV = view.findViewById(R.id.ques_qty_tv);
            viewHolder.scoreTV = view.findViewById(R.id.score_tv);
            viewHolder.appleNum0IV = view.findViewById(R.id.apple_num0_iv);
            viewHolder.appleNum1IV = view.findViewById(R.id.apple_num1_iv);
            viewHolder.appleNum2IV = view.findViewById(R.id.apple_num2_iv);
            viewHolder.appleNum3IV = view.findViewById(R.id.apple_num3_iv);
            viewHolder.appleNum4IV = view.findViewById(R.id.apple_num4_iv);

        //}

            RecordInfo recordInfo = getItem(position);
            viewHolder.recordIdTV.setText(recordInfo.getRecordId() + "");
            viewHolder.totalTimeTV.setText(recordInfo.getTotalTime()+"s");
            viewHolder.quesQtyTV.setText(recordInfo.getSum() + "");
            viewHolder.scoreTV.setText(recordInfo.getGrade() + "");
            switch (recordInfo.getLevel()){
            case 0:
                viewHolder.appleNum0IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.bad_apple_48px);
                break;
            case 1:
                viewHolder.appleNum0IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.bad_apple_48px);
                break;
            case 2:
                viewHolder.appleNum0IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.bad_apple_48px);
                break;
            case 3:
                viewHolder.appleNum0IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.bad_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.bad_apple_48px);
                break;
            case 4:
                viewHolder.appleNum0IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.bad_apple_48px);
                break;
            case 5:
                viewHolder.appleNum0IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum1IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum2IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum3IV.setImageResource(R.drawable.good_apple_48px);
                viewHolder.appleNum4IV.setImageResource(R.drawable.good_apple_48px);
                break;
            }

        return view;
    }

    static class ViewHolder{
        TextView recordIdTV;
        TextView totalTimeTV;
        TextView quesQtyTV;
        TextView scoreTV;
        ImageView appleNum0IV;
        ImageView appleNum1IV;
        ImageView appleNum2IV;
        ImageView appleNum3IV;
        ImageView appleNum4IV;
        /*
        ImageView appleNum5IV;
        ImageView appleNum6IV;

        TextView rightSumTV;

        */
    }
}
