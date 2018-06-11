package com.cookandroid.bus;

import android.os.AsyncTask;
import com.cookandroid.bus.Common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBTask extends AsyncTask<String, Void, String> {

    //jsp로 오라클 연결
    public static String ip = Common.SERVER_URL+"/busServer/main.jsp";
    String sendMsg, receiveMsg;

    DBTask(String sendMsg){
        this.sendMsg = sendMsg;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            String str;
            URL url = new URL(ip);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn!=null) {//url형식에 맞으면
                //타임아웃 시간 설정, 10초
                conn.setConnectTimeout(10000);
                //캐쉬사용여부
                conn.setUseCaches(false);
                //url접속에 성공했을때
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    while (true) {
                        String line = br.readLine();//한줄읽기
                        if (line == null) break;//내용없으면 종료
                        //html.append(line + "\n");
                    }
                    br.close();
                }
                conn.disconnect();//웹서버연결종료
            }
        }catch(Exception e){

        }




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
