package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StationAdapter extends BaseAdapter {
    Context context;
    ArrayList<StationDTO> data;

    public StationAdapter(Context context, ArrayList<StationDTO> data) {
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
            convertView = View.inflate(context,R.layout.stationlist,null);

        }
        TextView station_nm = (TextView)convertView.findViewById(R.id.station_nm);
        TextView mobile_no = (TextView)convertView.findViewById(R.id.mobile_no);

        station_nm.setText(data.get(position).getStation_nm());
        mobile_no.setText(data.get(position).getMobile_no());

        return convertView;
    }
}
