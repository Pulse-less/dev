package com.cookandroid.bus;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

class LocationTimeTask extends AsyncTask<Void, Void, ArrayList<LocationTimeDTO>>{
    ArrayList<LocationTimeDTO> locationTimeList;
    private String station_id;

    public LocationTimeTask(String station_id) {
        this.station_id = station_id;
    }

    @Override
    protected ArrayList<LocationTimeDTO> doInBackground(Void... params) {
        try {
            locationTimeList = new ArrayList<>();
            String serviceKey = Common.SERVICEKEY;
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey=" +serviceKey+ "&stationId=" + station_id);
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            InputStream is = url.openStream();
            parser.setInput(is,"UTF-8");
            int eventType = parser.getEventType();
            String tag;
            LocationTimeDTO dto = null;
            while(eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("locationNo1")){
                            dto = new LocationTimeDTO();
                            dto.setLocationNo1(parser.nextText());
                        }
                        if(tag.equals("predictTime1")){
                            dto.setPredictTime1(parser.nextText());
                        }
                        if(tag.equals("locationNo2")){
                            dto.setLocationNo2(parser.nextText());
                        }
                        if(tag.equals("predictTime2")){
                            dto.setPredictTime2(parser.nextText());
                        }
                        if(tag.equals("routeId")){
                            dto.setRoute_id(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if(tag.equals("busArrivalList")){
                            locationTimeList.add(dto);
                            dto=null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //return result; //onPostExecute로 보냄
        return locationTimeList;
    }
}