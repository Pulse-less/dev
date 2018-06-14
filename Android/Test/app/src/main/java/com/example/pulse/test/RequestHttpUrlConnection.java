package com.example.pulse.test;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestHttpUrlConnection {
    public String request(String _url, ContentValues _params){
        HttpURLConnection conn = null;
        StringBuffer sbParams = new StringBuffer();

        /*
         * 1. StringBuffer에 파라미터 연결
         * */
        if(_params == null) sbParams.append("");
        else{
            boolean isAnd = false;
            String key;
            String value;

            for(Map.Entry<String,Object>parameter:_params.valueSet()){
                key = parameter.getKey();
                value = parameter.getValue().toString();

                //파라미터가 두개이상일때
                if(isAnd) sbParams.append("&");
                sbParams.append(key).append("=").append(value);

                //파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙힌다
                if(!isAnd)
                    if(_params.size()>=2) isAnd = true;
            }
        }

        /*
         *  2.httpurlconnection을 통한 web에서 데이터 가져오기
         * */
        try{
            URL url = new URL(_url);
            conn = (HttpURLConnection)url.openConnection();

            //conn설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            //parameter 전달, 데이터 읽기
            String strParams = sbParams.toString(); //sbParams에 저장한 파라미터들 스트링으로 저장
            OutputStream os = conn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); //출력 스트림에 출력
            os.flush();//출력스트림 비우고 버퍼링된 모든 출력 바이트 실행
            os.close();//출력스트림 해제

            //연결요청확인
            //실패시 null 리턴, 종료
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

            //읽어온 결과물 리턴
            //요청한 URL 출력물을 BufferedReader로 받아오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

            //출력물의 라인과 그 합에 대한 변수
            String line;
            String page ="";

            //페이지받아와 합치기
            while((line = br.readLine())!=null){
                page+=line;
            }

            return page;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null) conn.disconnect();
        }
        return null;
    }
}
