package com.example.pulse.netconntest;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2,btn3;
    ListView listView;
    //ScrollView scrollView;
    EditText edt1;
    TextView txt1;
    ArrayList<RouteDTO> list;

    String xmlString;
    //핸들러
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RouteAdapter adapter = new RouteAdapter(MainActivity.this,list);
            listView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        listView = (ListView) findViewById(R.id.listView);
        edt1 = (EditText) findViewById(R.id.edt1);
        txt1 = (TextView) findViewById(R.id.txt1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.0.2:8080/busServer/route/sendRoute.jsp";
                ContentValues values = new ContentValues();
                values.put("route_nm", edt1.getText().toString());
                TestTask testTask = new TestTask(url, values);
                try {
                    xmlString = testTask.execute().get();
                    //txt1.setText(xmlString);
                    Log.i("xmlString확인:",xmlString);

                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                list = new ArrayList<>();
                                XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                                InputStream is = new ByteArrayInputStream(xmlString.getBytes());
                                parser.setInput(is,"UTF-8");

                                int eventType = parser.getEventType();
                                String tag;
                                RouteDTO dto = null;
                                while(eventType!=XmlPullParser.END_DOCUMENT){
                                    switch (eventType){
                                        case XmlPullParser.START_TAG:
                                            tag = parser.getName();
                                            if(tag.equals("route_id")){
                                                dto = new RouteDTO();
                                                dto.setRoute_id(parser.nextText());
                                            }
                                            if(tag.equals("route_nm")){
                                                dto.setRoute_nm(parser.nextText());
                                            }
                                            if(tag.equals("district_cd")){
                                                dto.setDistrict_cd(parser.nextText());
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            tag = parser.getName();
                                            if(tag.equals("route")){
                                                list.add(dto);
                                                dto=null;
                                            }
                                            break;
                                    }
                                    eventType = parser.next();
                                }
                                Log.i("파싱확인","route_list = "+list);
                                handler.sendEmptyMessage(0);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    th.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //이너클래스로 설정 -> 데이터가져오기
    class TestTask extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public TestTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; //요청결과 저장변수
            RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
            result = requestHttpUrlConnection.request(url,values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    //이너클래스 어댑터
    class RouteAdapter extends BaseAdapter{
        Context context;
        ArrayList<RouteDTO> data;

        public RouteAdapter(Context context, ArrayList<RouteDTO> data) {
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
                convertView = View.inflate(context,R.layout.routelist,null);
            }
            TextView route_id = (TextView)convertView.findViewById(R.id.routd_id);
            TextView routd_nm = (TextView)convertView.findViewById(R.id.routd_nm);
            TextView district_cd = (TextView)convertView.findViewById(R.id.distirct_cd);

            route_id.setText(data.get(position).getRoute_id());
            routd_nm.setText(data.get(position).getRoute_nm());
            district_cd.setText(data.get(position).getDistrict_cd());

            return convertView;
        }
    }
}

