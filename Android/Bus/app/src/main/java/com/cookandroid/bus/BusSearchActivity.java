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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BusSearchActivity extends AppCompatActivity {
    ImageButton btnPrevious, btnMain;
    TextView route_info, route_nm, routeline;
    ListView busListView;
    String st_sta_nm, ed_sta_nm,region_name,route_tp,route_id,route_nm_;
    ArrayList<LineDTO> lineList;
    LineAdapter lineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_search);

        btnPrevious=(ImageButton)findViewById(R.id.btnPrevious);
        btnMain=(ImageButton)findViewById(R.id.btnMain);
        busListView = (ListView)findViewById(R.id.busListView);
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
        route_nm_ = intent.getStringExtra("route_nm");

        //윗쪽 레이아웃 설정
        route_info.setText(region_name +" "+ route_tp);
        route_nm.setText(route_nm_);
        routeline.setText(st_sta_nm +" "+ routeline.getText().toString() +" "+ ed_sta_nm);

        //리스트뷰설정
        String url = Common.SERVER_URL+"/busServer/line/sendLine.jsp";
        ContentValues values = new ContentValues();
        values.put("route_id",route_id);
        LineTask lineTask = new LineTask(url, values);
        try{
            lineList = lineTask.execute().get();
            lineAdapter = new LineAdapter(getApplicationContext(),lineList);
            busListView.setAdapter(lineAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }



        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabHostActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Toast.makeText(getApplicationContext(),"뒤로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Toast.makeText(getApplicationContext(),"메인으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
}
