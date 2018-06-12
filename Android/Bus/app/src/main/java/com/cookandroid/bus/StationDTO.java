package com.cookandroid.bus;

public class StationDTO {
    private String station_id;
    private String station_nm;
    private String region_name;
    private String mobile_no;

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_nm() {
        return station_nm;
    }

    public void setStation_nm(String station_nm) {
        this.station_nm = station_nm;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
