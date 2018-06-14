package com.cookandroid.bus;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StopSearchActivity extends AppCompatActivity {
    Button btnPrevious, btnMain;
    TextView mobile_no,station_nm,routeline;
    String station_id,route_nm;
    ListView stopInfolist;
    ArrayList<LocationTimeDTO> locationTimeList;
    LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_search);

        btnPrevious=(Button)findViewById(R.id.btnPrevious);
        btnMain=(Button)findViewById(R.id.btnMain);
        mobile_no = (TextView)findViewById(R.id.mobile_no);
        station_nm = (TextView)findViewById(R.id.station_nm);
        routeline = (TextView)findViewById(R.id.routeline);
        stopInfolist = (ListView)findViewById(R.id.stopInfoList);

        //인텐트받기
        Intent intent = getIntent();
        //위쪽레이아웃지정
        mobile_no.setText(intent.getStringExtra("mobile_no"));
        station_nm.setText(intent.getStringExtra("station_nm"));
        routeline.setText(intent.getStringExtra("region_name"));

        station_id = intent.getStringExtra("station_id");


        //네트워크는 스레드에서
        LocationTimeTask locationTimeTask = new LocationTimeTask(station_id);
        try{
            locationTimeList = locationTimeTask.execute().get();
            locationAdapter = new LocationAdapter(getApplicationContext(),locationTimeList);
            stopInfolist.setAdapter(locationAdapter);
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
