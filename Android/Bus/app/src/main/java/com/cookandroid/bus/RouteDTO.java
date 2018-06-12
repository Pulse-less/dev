package com.cookandroid.bus;

public class RouteDTO {
    private String route_id;
    private String route_nm;
    private String district_cd;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_nm() {
        return route_nm;
    }

    public void setRoute_nm(String route_nm) {
        this.route_nm = route_nm;
    }

    public String getDistrict_cd() {
        return district_cd;
    }

    public void setDistrict_cd(String district_cd) {
        this.district_cd = district_cd;
    }
}
