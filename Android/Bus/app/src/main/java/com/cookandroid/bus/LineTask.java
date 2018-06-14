package com.cookandroid.bus;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

class LineTask extends AsyncTask<Void, Void, ArrayList<LineDTO>>{
    private String url;
    private ContentValues values;
    ArrayList<LineDTO> lineList;

    public LineTask(String url, ContentValues values){
        this.url = url;
        this.values = values;
    }

    @Override
    protected ArrayList<LineDTO> doInBackground(Void... params) {
        String result; //요청결과 저장변수
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url,values);

        try {
            lineList = new ArrayList<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream is = new ByteArrayInputStream(result.getBytes());
            parser.setInput(is,"UTF-8");
            //이벤트타입
            int eventType = parser.getEventType();
            String tag;
            LineDTO dto = null;
            while(eventType!= XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("mobile_no")){
                            dto = new LineDTO();
                            dto.setMobile_no(parser.nextText());
                        }
                        if(tag.equals("station_nm")){
                            dto.setStation_nm(parser.nextText());
                        }
                        if(tag.equals("sta_order")){
                            dto.setSta_order(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("line")){
                            lineList.add(dto);
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

        return lineList;
    }
}