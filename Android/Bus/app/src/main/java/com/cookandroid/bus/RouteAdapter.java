package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView district_cd = (TextView)convertView.findViewById(R.id.district_cd);

        route_nm.setText(data.get(position).getRoute_nm());
        district_cd.setText(data.get(position).getDistrict_cd());

        return convertView;
    }
}
