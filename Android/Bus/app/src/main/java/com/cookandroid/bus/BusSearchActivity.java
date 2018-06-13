package com.cookandroid.bus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class BusSearchActivity extends AppCompatActivity {
    Button btnPrevious, btnMain;
    TextView route_info, route_nm, routeline;
    ListView busInfoList;
    String st_sta_nm, ed_sta_nm,region_name,route_tp,route_id;
    ArrayList<RouteStationDTO> rslist;
    String xmlString;
    BusAdapter adapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new BusAdapter(getApplicationContext(),rslist);
            busInfoList.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_search);

        btnPrevious=(Button)findViewById(R.id.btnPrevious);
        btnMain=(Button)findViewById(R.id.btnMain);
        busInfoList = (ListView)findViewById(R.id.busInfoList);
        route_info = (TextView)findViewById(R.id.route_info);
        route_nm = (TextView)findViewById(R.id.route_nm);
        routeline = (TextView)findViewById(R.id.routeline);

        //액티비티 받아오기
        Intent intent = getIntent();
        //일단 스트링부터
        st_sta_nm = intent.getStringExtra("st_sta_nm");
        ed_sta_nm = intent.getStringExtra("ed_sta_nm");
        region_name = intent.getStringExtra("region_name");
        route_tp = intent.getStringExtra("route_tp");
        route_id = intent.getStringExtra("route_id");
        //텍스트뷰설정
        route_info.setText(region_name +" "+ route_tp);
        route_nm.setText(intent.getStringExtra("route_nm"));
        routeline.setText(st_sta_nm +" "+ routeline.getText().toString() +" "+ ed_sta_nm);

        //리스트뷰설정
        //먼저 디비 routeline에서 route_nm, route_id로 검색하기
        String url = Common.SERVER_URL+"/busServer/routestation/sendRouteStation.jsp";
        ContentValues values = new ContentValues();
        values.put("route_nm", route_nm.getText().toString());
        values.put("route_id", route_id);
        SelectTask rsTask = new SelectTask(url,values);
        try{
            xmlString = rsTask.execute().get();
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        rslist = new ArrayList<>();
                        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                        InputStream is = new ByteArrayInputStream(xmlString.getBytes());
                        parser.setInput(is, "UTF-8");
                        int eventType = parser.getEventType();
                        String tag;
                        RouteStationDTO dto = null;
                        while(eventType!=XmlPullParser.END_DOCUMENT){
                            switch(eventType){
                                case XmlPullParser.START_TAG:
                                    tag = parser.getName();
                                    if(tag.equals("route_id")){
                                        dto = new RouteStationDTO();
                                        dto.setRoute_id(parser.nextText());
                                    }
                                    if(tag.equals("station_id")){
                                        dto.setStation_id(parser.nextText());
                                    }
                                    if(tag.equals("sta_order")){
                                        dto.setSta_order(parser.nextText());
                                    }
                                    if(tag.equals("route_nm")){
                                        dto.setRoute_nm(parser.nextText());
                                    }
                                    if(tag.equals("station_nm")){
                                        dto.setStation_nm(parser.nextText());
                                    }
                                    break;
                                case XmlPullParser.END_TAG:
                                    tag = parser.getName();
                                    if(tag.equals("routestation")){
                                        rslist.add(dto);
                                        dto=null;
                                    }
                                    break;
                            }
                            eventType = parser.next();
                        }
                        handler.sendEmptyMessage(0);
                        is.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            });
            th.start();
        }catch(Exception e){
            e.printStackTrace();
        }

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), TabHostActivity.class);
                startActivity(in);
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

    }
}
