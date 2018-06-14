package com.cookandroid.bus;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

class StationTask extends AsyncTask<Void, Void, ArrayList<StationDTO>>{
    private String url;
    private ContentValues values;
    ArrayList<StationDTO> stationlist;

    public StationTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected ArrayList<StationDTO> doInBackground(Void... params) {
        String result; //요청결과 저장변수
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url,values);

        try {
            stationlist = new ArrayList<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream is = new ByteArrayInputStream(result.getBytes());
            parser.setInput(is,"UTF-8");
            //이벤트타입
            int eventType = parser.getEventType();
            String tag;
            StationDTO dto = null;
            while(eventType!= XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("station_id")){
                            dto = new StationDTO();
                            dto.setStation_id(parser.nextText());
                        }
                        if(tag.equals("station_nm")){
                            dto.setStation_nm(parser.nextText());
                        }
                        if(tag.equals("region_name")){
                            dto.setRegion_name(parser.nextText());
                        }
                        if(tag.equals("mobile_no")){
                            dto.setMobile_no(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("station")){
                            stationlist.add(dto);
                            dto=null;
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

        return stationlist;
    }
}