package com.cookandroid.bus;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

class LocationTask extends AsyncTask<Void, Void, String>{
    private String url;
    private ContentValues values;
    private String route_nm;

    public LocationTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result; //요청결과 저장변수
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url,values);

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream is = new ByteArrayInputStream(result.getBytes());
            parser.setInput(is,"UTF-8");
            //이벤트타입
            int eventType = parser.getEventType();
            String tag;
            while(eventType!= XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("route_nm")){
                            route_nm = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("location")) {

                        }
                        break;
                }
                eventType = parser.next();
            }
            is.close();
        }catch(Exception e){
            //파서에러
            e.printStackTrace();
        }

        return route_nm;
    }
}