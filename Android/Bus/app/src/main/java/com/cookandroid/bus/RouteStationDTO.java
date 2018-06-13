package com.cookandroid.bus;

public class RouteStationDTO {
    private String route_id;
    private String station_id;
    private String sta_order;
    private String route_nm;
    private String station_nm;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getSta_order() {
        return sta_order;
    }

    public void setSta_order(String sta_order) {
        this.sta_order = sta_order;
    }

    public String getRoute_nm() {
        return route_nm;
    }

    public void setRoute_nm(String route_nm) {
        this.route_nm = route_nm;
    }

    public String getStation_nm() {
        return station_nm;
    }

    public void setStation_nm(String station_nm) {
        this.station_nm = station_nm;
    }
}
