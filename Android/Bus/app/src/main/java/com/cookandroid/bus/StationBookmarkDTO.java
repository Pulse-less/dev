package com.cookandroid.bus;

import java.util.ArrayList;

public class StationBookmarkDTO {
    private String station_nm;
    private ArrayList<RouteBookmarkDTO> rbList;

    public String getStation_nm() {
        return station_nm;
    }

    public void setStation_nm(String station_nm) {
        this.station_nm = station_nm;
    }

    public ArrayList<RouteBookmarkDTO> getRbList() {
        return rbList;
    }

    public void setRbList(ArrayList<RouteBookmarkDTO> rbList) {
        this.rbList = rbList;
    }
}
