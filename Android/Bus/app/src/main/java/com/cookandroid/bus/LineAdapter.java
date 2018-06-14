package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LineAdapter extends BaseAdapter {
    Context context;
    ArrayList<LineDTO> data;

    public LineAdapter(Context context, ArrayList<LineDTO> data) {
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
            convertView = View.inflate(context,R.layout.linelist,null);
        }
        TextView route_nm_text = (TextView)convertView.findViewById(R.id.route_nm_text);
        TextView mobile_no_text = (TextView)convertView.findViewById(R.id.mobile_no_text);

        route_nm_text.setText(data.get(position).getStation_nm());
        mobile_no_text.setText(data.get(position).getMobile_no());

        return convertView;
    }
}
