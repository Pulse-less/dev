package com.example.pulse.test;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class BusTask extends AsyncTask<Void, Void, ArrayList<BusDTO>> {
    private String url;
    private ContentValues values;
    private String result;
    ArrayList<BusDTO> busList;

    public BusTask(String url, ContentValues values) {
        this.url = url;
        this.values = values;
    }

    @Override
    protected ArrayList<BusDTO> doInBackground(Void... voids) {
        RequestHttpUrlConnection requestHttpUrlConnection = new RequestHttpUrlConnection();
        result = requestHttpUrlConnection.request(url, values);

//        try {
//            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
//            InputStream is = new ByteArrayInputStream(result.getBytes());
//            parser.setInput(is,"UTF-8");
//            int eventType = parser.getEventType();
//            String tag;
//            BusDTO dto = new BusDTO();
//            busList = new ArrayList<>();
//            while(eventType!=XmlPullParser.END_DOCUMENT){
//                switch(eventType){
//                    case XmlPullParser.START_TAG:
//                        tag = parser.getName();
//                        if(tag.equals("route_id")){
//                            dto.setRoute_id(parser.nextText());
//                        }
//                        if(tag.equals("route_nm")){
//                            dto.setRoute_nm(parser.nextText());
//                        }
//                        if(tag.equals("route_tp")){
//                            dto.setRoute_tp(parser.nextText());
//                        }
//                        if(tag.equals("region_name")){
//                            dto.setRegion_name(parser.nextText());
//                        }
//                        if(tag.equals("st_sta_nm")){
//                            dto.setSt_sta_nm(parser.nextText());
//                        }
//                        if(tag.equals("ed_sta_nm")){
//                            dto.setEd_sta_nm(parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        tag = parser.getName();
//                        if(tag.equals("route")){
//                            busList.add(dto);
//                            dto=null;
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//            is.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        return busList;
    }

    @Override
    protected void onPostExecute(ArrayList<BusDTO> busList) {
        super.onPostExecute(busList);
    }
}
