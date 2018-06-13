package com.cookandroid.bus;

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
    TextView mobile_no,station_nm;
    String station_id, region_name,route_nm;
    ListView stopInfolist;
    ArrayList<LocationDTO> locationlist;
    LocationAdapter locationAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for(int i=0; i<locationlist.size();i++){
                locationlist.get(i).setRoute_nm("");
            }

            locationAdapter = new LocationAdapter(getApplicationContext(),locationlist);
            stopInfolist.setAdapter(locationAdapter);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_search);

        btnPrevious=(Button)findViewById(R.id.btnPrevious);
        btnMain=(Button)findViewById(R.id.btnMain);
        mobile_no = (TextView)findViewById(R.id.mobile_no);
        station_nm = (TextView)findViewById(R.id.station_nm);
        stopInfolist = (ListView)findViewById(R.id.stopInfoList);

        //인텐트받기
        Intent intent = getIntent();
        mobile_no.setText(intent.getStringExtra("mobile_no"));
        station_nm.setText(intent.getStringExtra("station_nm"));
        station_id = intent.getStringExtra("station_id");
        region_name = intent.getStringExtra("region_name");

        //openapi에 연결해서 xml데이터 받아가지고 버스도착예정시간 리스트뷰 만들기
        //버스도착xml url은 http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey= ? &stationId= ? 형식
        //xml태그 확인잘할것
        //네트워크는 스레드에서
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    String serviceKey = Common.SERVICEKEY;
                    try {
                        URL url = new URL("http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey=" +serviceKey+ "&stationId=" + station_id.toString());
                        locationlist = new ArrayList<>();
                        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                        InputStream is = url.openStream();
                        parser.setInput(is,"UTF-8");
                        int eventType = parser.getEventType();
                        String tag;
                        LocationDTO dto=null;
                        while(eventType!=XmlPullParser.END_DOCUMENT){
                            switch (eventType){
                                case XmlPullParser.START_TAG:
                                    tag = parser.getName();
                                    if(tag.equals("locationNo1")){
                                        dto = new LocationDTO();
                                        dto.setLocationNo1(parser.nextText());
                                    }
                                    if(tag.equals("predictTime1")){
                                        dto.setPredictTime1(parser.nextText());
                                    }
                                    if(tag.equals("locationNo2")){
                                        dto.setLocationNo2(parser.nextText());
                                    }
                                    if(tag.equals("predictTime2")){
                                        dto.setPredictTime2(parser.nextText());
                                    }
                                    if(tag.equals("route_id")) {
                                        dto.setRoute_id(parser.nextText());
                                    }
                                    break;
                                case XmlPullParser.END_TAG:
                                    tag = parser.getName();
                                    if(tag.equals("busArrivalList")){
                                        locationlist.add(dto);
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
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,0,60000); //1분에한번 실행



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
