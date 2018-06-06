package com.cookandroid.bus;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBTask extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {
        return null;

        //jsp로 mysql을 연결할 내부클래스 하나를 생성, AsyncTask를 상속
//        class MyDBTask extends AsyncTask<String, Void, String>{
//            //연결확인용 변수
//            String sendMsg, receiveMsg;
//
//            @Override
//            protected String doInBackground(String... strings) {
//                try{
//                    String str;
//                    URL url = new URL("http://경로");
//                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                    conn.setRequestProperty("POST");
//                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//                    sendMsg = "~";
//
//                }
//
//                return null;
//            }
//        }
    }
}
