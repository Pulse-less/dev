package com.cookandroid.bus;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
    Button btnBack;
    EditText routeSearch,stationSearch;
    ListView busListView, stopListView;
    RouteAdapter adapter;
    ArrayList<RouteDTO> routelist;
    ArrayList<StationDTO> stationlist;
    String xmlString;
    //핸들러 설정
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter = new RouteAdapter(getApplicationContext(), routelist);
            busListView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);

        btnBack=(Button)findViewById(R.id.btnBack);
        routeSearch = (EditText)findViewById(R.id.routeSearch);
        stationSearch = (EditText)findViewById(R.id.stationSearch);
        busListView = (ListView)findViewById(R.id.busListView);
        stopListView = (ListView)findViewById(R.id.stopListView);

        final TabHost tabHost = getTabHost();

        //버스검색 탭
        final TabHost.TabSpec tab1=tabHost.newTabSpec("TAB1");
        tab1.setIndicator("버스").setContent(R.id.busListView);
        tabHost.addTab(tab1);

        //정류장검색 탭
        TabHost.TabSpec tab2=tabHost.newTabSpec("TAB2");
        tab2.setIndicator("정류장").setContent(R.id.stopListView);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);

        //탭변경시 이벤트처리
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabHost.getCurrentTabTag().equals("TAB1")){
                    routeSearch.setVisibility(View.VISIBLE);
                    routeSearch.setText("");
                    routeSearch.setHint("버스 검색");
                    stationSearch.setVisibility(View.INVISIBLE);
                }else{
                    stationSearch.setVisibility(View.VISIBLE);
                    stationSearch.setText("");
                    stationSearch.setHint("정류장,ID 검색");
                    routeSearch.setVisibility(View.INVISIBLE);
                }
            }
        });

        //검색설정
        //버스검색 설정
        routeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //Log.i("입력확인", v.getText().toString());
                    String url = Common.SERVER_URL+"/busServer/route/sendRoute.jsp";
                    ContentValues values = new ContentValues();
                    values.put("route_nm",v.getText().toString());
                    RouteTask routeTask = new RouteTask(url, values);
                    try{
                        xmlString = routeTask.execute().get();
                        //xml파싱 스레드 작성
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    routelist = new ArrayList<>();
                                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                                    InputStream is = new ByteArrayInputStream(xmlString.getBytes());
                                    parser.setInput(is,"UTF-8");
                                    //이벤트타입
                                    int eventType = parser.getEventType();
                                    String tag;
                                    RouteDTO dto = null;
                                    while(eventType!=XmlPullParser.END_DOCUMENT){
                                        switch(eventType){
                                            case XmlPullParser.START_TAG:
                                                tag = parser.getName();
                                                if(tag.equals("route_id")){
                                                    dto = new RouteDTO();
                                                    dto.setRoute_id(parser.nextText());
                                                }
                                                if(tag.equals("route_nm")){
                                                    dto.setRoute_nm(parser.nextText());
                                                }
                                                if(tag.equals("region_name")){
                                                    dto.setRegion_name(parser.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                tag = parser.getName();
                                                if(tag.equals("route")){
                                                    routelist.add(dto);
                                                    dto=null;
                                                }
                                                break;
                                        }
                                        eventType = parser.next();
                                    }
                                    handler.sendEmptyMessage(0);
                                }catch(Exception e){
                                    //파서에러
                                    e.printStackTrace();
                                }
                            }
                        });
                        th.start();
                    }catch(Exception e){
                        //버스루트 검색실패시
                        e.printStackTrace();
                    }
                    //키보드 감추기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(routeSearch.getWindowToken(),0);
                    return true;
                }
                return true;
            }
        });

        //정류장검색 설정
        stationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //Log.i("입력확인", v.getText().toString());
                    String url = Common.SERVER_URL+"/busServer/station/sendStation.jsp";
                    ContentValues values = new ContentValues();
                    values.put("station_nm",v.getText().toString());
                    RouteTask routeTask = new RouteTask(url, values);
                    try{
                        xmlString = routeTask.execute().get();
                        //xml파싱 스레드 작성
                        Thread th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    stationlist = new ArrayList<>();
                                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                                    InputStream is = new ByteArrayInputStream(xmlString.getBytes());
                                    parser.setInput(is,"UTF-8");
                                    //이벤트타입
                                    int eventType = parser.getEventType();
                                    String tag;
                                    StationDTO dto = null;
                                    while(eventType!=XmlPullParser.END_DOCUMENT){
                                        switch(eventType){
                                            case XmlPullParser.START_TAG:
                                                tag = parser.getName();
                                                if(tag.equals("station_id")){
                                                    dto = new StationDTO();
                                                    dto.setStation_id(parser.nextText());
                                                }
                                                if(tag.equals("station_nm")){
                                                    dto.setStation_nm(parser.nextText());
                                                }
                                                if(tag.equals("region_name")){
                                                    dto.setRegion_name(parser.nextText());
                                                }
                                                if(tag.equals("mobile_no")){
                                                    dto.setMobile_no(parser.nextText());
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                tag = parser.getName();
                                                if(tag.equals("station")){
                                                    stationlist.add(dto);
                                                    dto=null;
                                                }
                                                break;
                                        }
                                        eventType = parser.next();
                                    }
                                    handler.sendEmptyMessage(0);
                                }catch(Exception e){
                                    //파서에러
                                    e.printStackTrace();
                                }
                            }
                        });
                        th.start();
                    }catch(Exception e){
                        //버스루트 검색실패시
                        e.printStackTrace();
                    }
                    //키보드 감추기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(stationSearch.getWindowToken(),0);
                    return true;
                }
                return true;
            }
        });




        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

    }
}