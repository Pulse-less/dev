package com.cookandroid.bus;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
    Button btnBack1;
    EditText editSearch;
    ListView busListView, stopListView;
    RouteAdapter busAdapter;
    ArrayList<RouteDTO> mArray, mArray2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        btnBack1=(Button)findViewById(R.id.btnBack1);
        editSearch = (EditText)findViewById(R.id.editSearch);
        busListView = (ListView)findViewById(R.id.busListView);
        stopListView = (ListView)findViewById(R.id.stopListView);

        final TabHost tabHost = getTabHost();

        //버스검색 탭
        final TabHost.TabSpec tab1=tabHost.newTabSpec("TAB1");
        tab1.setIndicator("버스",getResources().getDrawable(R.drawable.busicon)).setContent(R.id.busListView);
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
                    editSearch.setHint("버스 검색");
                }else{
                    editSearch.setHint("정류장 검색");
                }
            }
        });

        //뒤로가기
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

    }
}