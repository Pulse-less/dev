package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteAdapter extends BaseAdapter {
    Context context;
    ArrayList<RouteDTO> data;

    public RouteAdapter(Context context, ArrayList<RouteDTO> data){
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
            convertView = View.inflate(context,R.layout.routelist,null);

        }
        TextView route_nm = (TextView)convertView.findViewById(R.id.route_nm);
        TextView region_name = (TextView)convertView.findViewById(R.id.region_name);
        Button bookmark = (Button)convertView.findViewById(R.id.btnBookmark);

        route_nm.setText(data.get(position).getRoute_nm());
        region_name.setText(data.get(position).getRegion_name());

        return convertView;
    }
}
