package com.eq3.multiply;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuesListAdapter extends ArrayAdapter<QuesInfo> {
    private Context mContext;
    private final LayoutInflater mInflater;
    private int mResource;

    public QuesListAdapter(Context context, int resource) {
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
            viewHolder.quesIdTV = view.findViewById(R.id.question_id_tv);
            viewHolder.textTV = view.findViewById(R.id.question_text_tv);
            viewHolder.countTV = view.findViewById(R.id.question_count_tv);
            viewHolder.resultTV = view.findViewById(R.id.question_result_tv);
        //}
            if(position%2==1){
                view.setBackgroundColor(Color.parseColor("#AFEEEE"));
            }
        QuesInfo result = getItem(position);

        if(result.isResult()) {
            viewHolder.resultTV.setTextColor(Color.parseColor("#228B22"));
            viewHolder.resultTV.setText("√");
        }else {
            viewHolder.resultTV.setTextColor(Color.parseColor("#E3170D"));
            viewHolder.resultTV.setText("×");
        }

        viewHolder.quesIdTV.setText(result.getQuesId()+"");
        viewHolder.textTV.setText(result.getText());
        viewHolder.countTV.setText(result.getCountTime()+"s");

        return view;
    }

    static class ViewHolder{
        TextView quesIdTV;
        TextView textTV;
        TextView countTV;
        TextView resultTV;
    }
}
