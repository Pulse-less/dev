package com.example.pulse.netconntest;

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

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    ListView listView;
    //ScrollView scrollView;
    EditText edt1;
    TextView txt1;
    ArrayList<RouteDTO> list;
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

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        listView = (ListView)findViewById(R.id.listView);
        //scrollView = (ScrollView)findViewById(R.id.scrollView);
        edt1 = (EditText)findViewById(R.id.edt1);
        //txt1 = (TextView)findViewById(R.id.txt1);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String result;
                    MyTask task = new MyTask();
                    //result = task.execute(edt1.getText().toString()).get();
                    result = task.execute(edt1.getText().toString()).get();
                    Log.i("리턴 값 확인: ",result);
                    
                    //xml 파싱
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //로컬변수
                            String str;
                            list = new ArrayList<>();
                            //파라미터 넘긴 후 데이터 요청

                            URL url = new URL("http://192.168.0.2:8080/busServer/route/sendRoute.jsp");
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                            conn.setRequestMethod("POST");
                            String sendMsg = "route_nm="+edt1.getText().toString();
                            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                            osw.write(sendMsg);
                            osw.flush();
                            osw.close();
                            //연결ok
                            if(conn.getResponseCode()==conn.HTTP_OK){
                                InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"euc-kr");
                                BufferedReader br = new BufferedReader(isr);
                                StringBuffer sb = new StringBuffer();
                                while((str=br.readLine())!=null){
                                    sb.append(str);
                                }
                                //receiveMsg = sb.toString();
                                conn.disconnect();
                                br.close();
                                isr.close();

                            }else{
                                Log.i("통신결과확인 : ",conn.getResponseCode()+"에러");
                            }
                            //conn.disconnect();

                            //데이터넘어온후 파싱부분
                            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                            InputStream is = url.openStream();
                            parser.setInput(is,"utf-8");
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
                            Log.i("test","route_list:"+list);
                            //핸들러 메시지전송
                            handler.sendEmptyMessage(0);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                th.start();

            }
        });


    }
    //비동기화 스레드
    class MyTask extends AsyncTask<String, Void, String>{
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                String str;
                URL url = new URL("http://192.168.0.2:8080/busServer/route/sendRoute.jsp");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                sendMsg = "route_nm="+strings[0];

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"euc-kr");
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"euc-kr");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    while((str=br.readLine())!=null){
                        sb.append(str);
                    }
                    receiveMsg = sb.toString();
                    br.close();
                    isr.close();
                    osw.close();
                }else{
                    Log.i("통신결과확인 : ",conn.getResponseCode()+"에러");
                }
                conn.disconnect();
            }catch(Exception e){

            }

            return receiveMsg;
        }
    }

    //어댑터
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
