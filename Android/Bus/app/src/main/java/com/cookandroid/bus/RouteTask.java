package com.cookandroid.bus;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

class RouteTask extends AsyncTask<Void, Void, ArrayList<RouteDTO>>{
    private String url;
    private ContentValues values;
    ArrayList<RouteDTO> routelist;

    public RouteTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected ArrayList<RouteDTO> doInBackground(Void... params) {
        String result; //요청결과 저장변수
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url,values);

        try {
            routelist = new ArrayList<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream is = new ByteArrayInputStream(result.getBytes());
            parser.setInput(is,"UTF-8");
            //이벤트타입
            int eventType = parser.getEventType();
            String tag;
            RouteDTO dto = null;
            while(eventType!= XmlPullParser.END_DOCUMENT){
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
                        if(tag.equals("route_tp")){
                            dto.setRoute_tp(parser.nextText());
                        }
                        if(tag.equals("region_name")){
                            dto.setRegion_name(parser.nextText());
                        }
                        if(tag.equals("st_sta_nm")){
                            dto.setSt_sta_nm(parser.nextText());
                        }
                        if(tag.equals("ed_sta_nm")){
                            dto.setEd_sta_nm(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("route")){
                            routelist.add(dto);
                            dto=null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            //handler.sendEmptyMessage(0);
            is.close();
        }catch(Exception e){
            //파서에러
            e.printStackTrace();
        }

        //return result; //onPostExecute로 보냄
        return routelist;
    }
}