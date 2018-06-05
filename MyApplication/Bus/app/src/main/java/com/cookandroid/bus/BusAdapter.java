package com.cookandroid.bus;

import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BusAdapter extends BaseAdapter {
    Context context;
    ArrayList<Bus> data;

    public BusAdapter(Context context, ArrayList<Bus> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context,R.layout.buslist,null);
            //edittext받아서 db검색하여 뷰보여줄라면..

        }
        TextView txt1 = (TextView)convertView.findViewById(R.id.txt1);
        TextView txt2 = (TextView)convertView.findViewById(R.id.txt2);

        txt1.setText(data.get(position).route_name);
        txt2.setText(data.get(position).route_type);

        return convertView;
    }
}
