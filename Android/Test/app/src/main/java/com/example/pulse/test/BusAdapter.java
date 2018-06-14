package com.example.pulse.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class BusAdapter extends BaseAdapter{
    Context context;
    ArrayList<BusDTO> data;

    public BusAdapter(Context context, ArrayList<BusDTO> data) {
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
        }
        TextView busname = (TextView)convertView.findViewById(R.id.busname);
        TextView region = (TextView)convertView.findViewById(R.id.region);

        busname.setText(data.get(position).getRoute_nm());
        region.setText(data.get(position).getRegion_name()+" "+data.get(position).getRoute_tp());

        return convertView;
    }
}
