package com.cookandroid.bus;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
    Button btnBack;
    EditText searchEdit;
    ListView busListView, stopListView;
    RouteAdapter routeAdapter;
    StationAdapter stationAdapter;
    ArrayList<RouteDTO> routelist;
    ArrayList<StationDTO> stationlist;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);

        btnBack=(Button)findViewById(R.id.btnBack);
        searchEdit = (EditText)findViewById(R.id.searchEdit);
        busListView = (ListView)findViewById(R.id.busListView);
        stopListView = (ListView)findViewById(R.id.stopListView);

        //탭호스트 처리
        final TabHost tabHost = getTabHost();

        final TabHost.TabSpec tab1=tabHost.newTabSpec("TAB1");
        tab1.setIndicator("버스").setContent(R.id.busListView);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("TAB2");
        tab2.setIndicator("정류장").setContent(R.id.stopListView);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);

        //탭변경시 이벤트처리
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabHost.getCurrentTabTag().equals("TAB1")){
                    searchEdit.setText("");
                    searchEdit.setHint("버스 검색");
                    searchEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                }else{
                    searchEdit.setText("");
                    searchEdit.setHint("정류장,ID 검색");
                    searchEdit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                }
            }
        });

        //검색설정
        //버스검색 설정
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(tabHost.getCurrentTabTag().equals("TAB1")&&actionId == EditorInfo.IME_ACTION_SEARCH){
                    //Log.i("입력확인", v.getText().toString());
                    url = Common.SERVER_URL+"/busServer/route/sendRoute.jsp";
                    ContentValues values = new ContentValues();
                    if(v.getText().toString().equals("")) {
                        return false;
                    }else{
                        values.put("route_nm",v.getText().toString());
                        RouteTask routeTask = new RouteTask(url, values);
                        try{
                            routelist = routeTask.execute().get();
                            routeAdapter = new RouteAdapter(getApplicationContext(), routelist);
                            busListView.setAdapter(routeAdapter);
                        }catch(Exception e){
                            //버스루트 검색실패시
                            e.printStackTrace();
                        }
                        //키보드 감추기
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(),0);
                        return true;
                    }
                }else{
                    if(v.getText().toString().equals("")) {
                        return false;
                    }else {
                        ContentValues values = new ContentValues();
                        if (isNumber(v.getText().toString())) {
                            //숫자일때
                            url = Common.SERVER_URL + "/busServer/station/sendStation.jsp";
                            values.put("mobile_no", v.getText().toString());
                        } else {
                            url = Common.SERVER_URL + "/busServer/station/sendStationForName.jsp";
                            values.put("station_nm", v.getText().toString());
                        }

                        StationTask stationTask = new StationTask(url, values);
                        try {
                            stationlist = stationTask.execute().get();
                            stationAdapter = new StationAdapter(getApplicationContext(), stationlist);
                            stopListView.setAdapter(stationAdapter);
                        } catch (Exception e) {
                            //검색실패시
                            e.printStackTrace();
                        }
                        //키보드 감추기
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
                        return true;
                    }
                }
            }
        });

        //리스트뷰 선택시 동작처리
        //버스
        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BusSearchActivity.class);
                //intent.putExtra("");
                intent.putExtra("route_id",routeAdapter.data.get(position).getRoute_id());
                intent.putExtra("route_tp",routeAdapter.data.get(position).getRoute_tp());
                intent.putExtra("route_nm", routeAdapter.data.get(position).getRoute_nm());
                intent.putExtra("st_sta_nm",routeAdapter.data.get(position).getSt_sta_nm());
                intent.putExtra("ed_sta_nm",routeAdapter.data.get(position).getEd_sta_nm());
                intent.putExtra("region_name",routeAdapter.data.get(position).getRegion_name());
                startActivity(intent);
            }
        });
        //정류소
        stopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),StopSearchActivity.class);
                intent.putExtra("station_id",stationAdapter.data.get(position).getStation_id());
                intent.putExtra("station_nm",stationAdapter.data.get(position).getStation_nm());
                intent.putExtra("mobile_no",stationAdapter.data.get(position).getMobile_no());
                intent.putExtra("region_name",stationAdapter.data.get(position).getRegion_name());
                startActivity(intent);
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

    //숫자 문자 입력판별 함수
    public static boolean isNumber(String str){
        boolean check = true;
        for(int i=0; i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                check=false;
                break;
            }
        }
        return check;
    }
}