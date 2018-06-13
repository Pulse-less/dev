package com.cookandroid.bus;

import android.content.ContentValues;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LocationDTO {
    //버스도착정보 데이터 받아와 저장하는 클래스
    private String route_id; //차량노선id
    private String locationNo1; //첫번째차량 위치정보
    private String predictTime1; //첫번째차량 도착예정시간
    private String locationNo2; //두번째차량 위치정보
    private String predictTime2; //두번째차량 도착예정시간
    private String route_nm;
    private String xmlString;

    //정류장검색 리스트를 구현하려면 버스노선이름에 대한 정보가 필요한데 파싱이 개인이라 받아올 방법이 없다...여기서 요청해서 받아놓자
    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getLocationNo1() {
        return locationNo1;
    }

    public void setLocationNo1(String locationNo1) {
        this.locationNo1 = locationNo1;
    }

    public String getPredictTime1() {
        return predictTime1;
    }

    public void setPredictTime1(String predictTime1) {
        this.predictTime1 = predictTime1;
    }

    public String getLocationNo2() {
        return locationNo2;
    }

    public void setLocationNo2(String locationNo2) {
        this.locationNo2 = locationNo2;
    }

    public String getPredictTime2() {
        return predictTime2;
    }

    public void setPredictTime2(String predictTime2) {
        this.predictTime2 = predictTime2;
    }

    public String getRoute_nm() {
        return route_nm;
    }

    public void setRoute_nm(String route_nm){
        this.route_nm = route_nm;
    }
}

