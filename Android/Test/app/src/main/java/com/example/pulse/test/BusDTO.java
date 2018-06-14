package com.example.pulse.test;

public class BusDTO {
    private String route_id;
    private String route_nm;
    private String route_tp;
    private String region_name;
    private String st_sta_nm;
    private String ed_sta_nm;

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

    public String getRoute_tp() {
        return route_tp;
    }

    public void setRoute_tp(String route_tp) {
        switch (route_tp){
            case "11": this.route_tp = "직행좌석형 시내버스"; break;
            case "12": this.route_tp = "좌석형 시내버스"; break;
            case "13": this.route_tp = "일반형 시내버스"; break;
            case "14": this.route_tp = "광역급행형 시내버스"; break;
            case "15": this.route_tp = "따복형 시내버스"; break;
            case "16": this.route_tp = "경기순환버스"; break;
            case "21": this.route_tp = "직행좌석형 농어촌버스"; break;
            case "22": this.route_tp = "좌석형 농어촌버스"; break;
            case "23": this.route_tp = "일반형 농어촌버스"; break;
            case "30": this.route_tp = "마을버스"; break;
            case "41": this.route_tp = "고속형 시외버스"; break;
            case "42": this.route_tp = "좌석형 시외버스"; break;
            case "43": this.route_tp = "일반형 시외버스"; break;
            case "51": this.route_tp = "리무진형 공항버스"; break;
            case "52": this.route_tp = "좌석형 공항버스"; break;
            case "53": this.route_tp = "일반형 공항버스"; break;
        }
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getSt_sta_nm() {
        return st_sta_nm;
    }

    public void setSt_sta_nm(String st_sta_nm) {
        this.st_sta_nm = st_sta_nm;
    }

    public String getEd_sta_nm() {
        return ed_sta_nm;
    }

    public void setEd_sta_nm(String ed_sta_nm) {
        this.ed_sta_nm = ed_sta_nm;
    }
}
