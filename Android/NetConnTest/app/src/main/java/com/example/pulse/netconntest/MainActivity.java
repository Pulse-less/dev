package com.example.pulse.netconntest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
                String sendMsg = "vision_write";
                String result = edt1.getText().toString();
                try{
                    String rst = new SendToServer(sendMsg).execute(result,"vision_write").get();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    //디비로 보낼것들
    class SendToServer extends AsyncTask<String, Void, String>{
        String sendMsg, receiveMsg;

        SendToServer(String sendMsg){
            this.sendMsg = sendMsg;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String addr = "http://192.168.0.2/busServer/route/sendRoute.jsp";
                String str;
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                if(sendMsg.equals("vision_write")){
                    sendMsg = "vision_write="+strings[0]+"&type"+strings[1];
                }else if(sendMsg.equals("vision_list")){
                    sendMsg = "&type="+strings[0];
                }

                osw.write(sendMsg);
                osw.flush();
                if(conn!=null){//url형식에 맞으면
                    //타임아웃 시간 설정, 10초
                    conn.setConnectTimeout(10000);
                    //캐쉬사용여부
                    conn.setUseCaches(false);
                    //url접속에 성공했을때
                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                        InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        StringBuffer buffer = new StringBuffer();
                        str = br.readLine();
                        while(str!=null){
//                            String line = br.readLine();//한줄읽기
//                            txt1.setText(line);
//                            if(line == null) break;//내용없으면 종료
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
//                        os.close();
                        br.close();
                    }else{
                        Log.i("통신결과",conn.getResponseCode()+"에러");
                    }
                    conn.disconnect();//웹서버연결종료
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }
    }
}
