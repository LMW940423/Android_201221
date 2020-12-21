package com.android.quiz_201221;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAdapter extends BaseAdapter {

    final static String TAG = "MemberAdapter";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<JsonMember> data = null;
    private LayoutInflater inflater = null;

    public MemberAdapter(Context mContext, int layout, ArrayList<JsonMember> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.v(TAG, "data size : " + data.size());
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG, "구간 1---------");
        if(convertView == null){ // 초기 생성
            Log.v(TAG, "구간 1---------");
            convertView = inflater.inflate(this.layout, parent, false);
        }
        Log.v(TAG, "구간 2---------");
        TextView tv_code = convertView.findViewById(R.id.custom_code);
        TextView tv_name = convertView.findViewById(R.id.custom_name);
        TextView tv_dept = convertView.findViewById(R.id.custom_dept);
        TextView tv_phone = convertView.findViewById(R.id.custom_phone);


        tv_code.setText("code : " + data.get(position).getCode());
        tv_name.setText("name : " + data.get(position).getName());
        tv_dept.setText("dept : " + data.get(position).getDept());
        tv_phone.setText("phone : " + data.get(position).getPhone());

        Log.v(TAG, "구간 3---------");
        if((position % 2) == 1){
            convertView.setBackgroundColor(0x5000ff00);
        }else{
            convertView.setBackgroundColor(0x500000ff);
        }
        return convertView;
    }
}
