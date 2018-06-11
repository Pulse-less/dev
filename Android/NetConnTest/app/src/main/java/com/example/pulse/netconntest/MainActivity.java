package com.example.pulse.netconntest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    //ListView listView;
    ScrollView scrollView;
    EditText edt1;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button)findViewById(R.id.btn1);
        //listView = (ListView)findViewById(R.id.listView);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        edt1 = (EditText)findViewById(R.id.edt1);
        txt1 = (TextView)findViewById(R.id.txt1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToServer task = new SendToServer();
                task.execute();
            }
        });


    }

    //디비로 보낼것들
    class SendToServer extends AsyncTask<Void, String, Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String addr = "http://192.168.0.2/busServer/route/sendRoute.jsp";
                String body = edt1.getText().toString();
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn!=null){//url형식에 맞으면
                    //타임아웃 시간 설정, 10초
                    conn.setConnectTimeout(10000);
                    //캐쉬사용여부
                    conn.setUseCaches(false);
                    //url접속에 성공했을때
                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                        conn.setRequestMethod("POST");
                        //파라미터 설정 및 담기
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestProperty("ROUTE_NM", "route_nm");
                        OutputStream os = conn.getOutputStream();
                        //인코딩설정
                        os.write(body.getBytes("UTF-8"));
                        //버퍼클리어
                        os.flush();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                        while(true){
                            String line = br.readLine();//한줄읽기
                            txt1.setText(line);
                            if(line == null) break;//내용없으면 종료
                        }
                        os.close();
                        br.close();
                    }
                    conn.disconnect();//웹서버연결종료
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
