package com.cookandroid.bus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Switch;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LodingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(3000); // 대기 초 설정
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //디비를 로딩 페이지에서 만들어서 인텐트로 넘겨볼까?
        //-> 로딩페이지에서 만든 후 디비를 다시 읽거나 할때는 커서를 이용하자!
        //먼저 웹서버와 연결한 후 연결체크에 따라 메인을 동작하게 하자
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}