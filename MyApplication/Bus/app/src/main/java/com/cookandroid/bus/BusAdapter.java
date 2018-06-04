package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BusAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Movie> mData;

    public BusAdapter(Context mContext, ArrayList<Movie> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.buslist,null);
        }
        TextView txt1 = (TextView)convertView.findViewById(R.id.txt1);
        TextView txt2 = (TextView)convertView.findViewById(R.id.txt2);

        txt1.setText(mData.get(position).title);
        txt2.setText(mData.get(position).genre);

        return convertView;
    }
}
