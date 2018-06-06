package com.cookandroid.bus;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownHtmlActivity extends AppCompatActivity {
    String html;
    TextView result;
    //핸들러 선언
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            result.setText(html);
        }
    };
    //네트워크 관련 작업 시 백그라운드 스레드에서 처리 thread or asynctask
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_html);

        result = (TextView)findViewById(R.id.result);
        Button btn = (Button)findViewById(R.id.down);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //result.setText(downloadHtml(Common.SERVER_URL+"/busServer/main.jsp"));
                //스레드에서 처리하자
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        html = downloadHtml(Common.SERVER_URL+"/busServer/main.jsp");
                        //result.setText(downloadHtml(Common.SERVER_URL+"/busServer/main.jsp"));
                        //핸들러가 처리해야하므로 요청
                        handler.sendEmptyMessage(0);
                    }
                });
                th.start();
            }
        });
    }

    String downloadHtml(String addr){
        StringBuilder html = new StringBuilder();
        try{
            URL url = new URL(addr);//url객체생성
            //url접속처리객체
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn!=null){//url형식에 맞으면
                //타임아웃 시간 설정, 10초
                conn.setConnectTimeout(10000);
                //캐쉬사용여부
                conn.setUseCaches(false);
                //url접속에 성공했을때
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                    while(true){
                        String line = br.readLine();//한줄읽기
                        if(line == null) break;//내용없으면 종료
                        html.append(line+"\n");
                    }
                    br.close();
                }
                conn.disconnect();//웹서버연결종료
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return html.toString();
    }
}
