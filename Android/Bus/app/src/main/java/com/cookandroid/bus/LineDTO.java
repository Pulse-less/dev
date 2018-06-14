package com.cookandroid.bus;

public class LineDTO {
    //이 DTO는 station과 routestation 테이블을 조인해서 데이터를 가져온다
    private String station_nm;
    private String sta_order;
    private String mobile_no;

    public String getStation_nm() {
        return station_nm;
    }

    public void setStation_nm(String station_nm) {
        this.station_nm = station_nm;
    }

    public String getSta_order() {
        return sta_order;
    }

    public void setSta_order(String sta_order) {
        this.sta_order = sta_order;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
