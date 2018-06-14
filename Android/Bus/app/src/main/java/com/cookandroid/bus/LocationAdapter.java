package com.cookandroid.bus;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LocationAdapter extends BaseAdapter{
    Context context;
    ArrayList<LocationTimeDTO> data;
    String predictTime1;
    String predictTime2;
    String locationNo1, locationNo2;


    public LocationAdapter(Context context, ArrayList<LocationTimeDTO> data){
        this.context = context;
        this.data = data;
        exchangeIdToNm();
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
            convertView = View.inflate(context,R.layout.locationlist,null);
        }
        TextView route_nm = (TextView)convertView.findViewById(R.id.route_nm);
        TextView direction = (TextView)convertView.findViewById(R.id.direction);
        TextView timer1 = (TextView)convertView.findViewById(R.id.timer1);
        TextView timer2 = (TextView)convertView.findViewById(R.id.timer2);

        predictTime1 = data.get(position).getPredictTime1();
        predictTime2 = data.get(position).getPredictTime2();
        locationNo1 = data.get(position).getLocationNo1();
        locationNo2 = data.get(position).getLocationNo2();
        route_nm.setText(data.get(position).getRoute_nm());

        timer2.setText("도착정보없음");
        direction.setText(locationNo1+"정거장 전");

        if(!predictTime1.equals("")){
            timer1.setText(predictTime1+"분 후 도착");
        }else if(predictTime1.equals("0")){
            timer1.setText("곧 도착");
        }else{
            timer1.setText("도착정보없음");
        }


        if(!predictTime2.equals("")) {
            timer2.setText(predictTime2 + "분 후 도착");
        }else if(predictTime2.equals("0")){
            timer2.setText("곧 도착");
        }else{
            timer2.setText("도착정보없음");
        }

        return convertView;
    }

    void exchangeIdToNm() {
        String url = Common.SERVER_URL + "/busServer/location/sendLocation.jsp";
        ContentValues values = new ContentValues();
        for(int i=0;i<data.size();i++) {
            values.put("route_id", data.get(i).getRoute_id());
            LocationTask locationtask = new LocationTask(url, values);
            try {
                data.get(i).setRoute_nm(locationtask.execute().get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
