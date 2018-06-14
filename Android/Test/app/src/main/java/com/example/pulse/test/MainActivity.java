package com.example.pulse.test;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends TabActivity {
    EditText input;
    Button btn;
    ListView bus,station;
    TabHost tabHost;
    BusAdapter busAdapter;
    ArrayList<BusDTO> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.btn);
        bus = (ListView) findViewById(R.id.bus);
        station = (ListView)findViewById(R.id.station);

        tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("bus").setIndicator("bus").setContent(R.id.bus);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("station").setIndicator("station").setContent(R.id.station);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabHost.getCurrentTabTag().equals("bus")){
                    input.setText("");
                    input.setHint("버스 검색");

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "http://192.168.0.2:8080/busServer/test/testroute.jsp";
                            ContentValues values = new ContentValues();
                            values.put("route_nm",input.getText().toString());
                            SelectTask task = new SelectTask(url, values);
                            task.execute();
                            //BusTask busTask = new BusTask(url, values);
                            //busTask.execute();
//                            try {
//                                busList = busTask.execute().get();
//                                busAdapter = new BusAdapter(getApplicationContext(),busList);
//                                bus.setAdapter(busAdapter);
//                            }catch(Exception e){
//                                e.printStackTrace();
//                                Log.i("오류체크","버스테스크 오류");
//                            }
                        }
                    });
                    //탭에서 리스트뷰처리
                    input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if(actionId== EditorInfo.IME_ACTION_SEARCH){
                                String url = "http://192.168.0.2:8080/busServer/test/testroute.jsp";
                                ContentValues values = new ContentValues();
                                values.put("route_nm",v.getText().toString());
                                try {
                                    BusTask busTask = new BusTask(url, values);
                                    busList = busTask.execute().get();
                                    busAdapter = new BusAdapter(getApplicationContext(),busList);
                                    bus.setAdapter(busAdapter);
                                }catch(Exception e){
                                    e.printStackTrace();
                                    Log.i("오류체크","버스테스크 오류");
                                }
                            }
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(input.getWindowToken(),0);
                            return true;
                        }
                    });
                }else{
                    input.setText("");
                    input.setHint("정류장,ID 검색");
                    if(input.getText().equals("")){
                        //리스트뷰처리
                    }else{
                        //리스트뷰처리
                    }
                }
            }
        });
    }
}
