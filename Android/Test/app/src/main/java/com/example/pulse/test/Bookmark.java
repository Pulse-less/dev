package com.example.pulse.test;

public class Bookmark {
    //북마크...즐겨찾기
    //각 항목별 리스트뷰에 추가하고 리스트뷰에서 북마크를 선택했을 시 추가...이후 북마크 리스트뷰에는
    //정류장을 추가해놓고 해당 정류장에 있는 버스목록중에서 즐겨찾기 추가된걸 같이보여주게 한다
    //이러면 북마크를 버스북마크, 정류장북마크 두개로 나눈 뒤 뷰를 보여줄때 같이 보여주게해야한다.
    //북마크를 보여줄때 정류장과 해당정류장에 소속되어있는 버스 중 북마크에 추가된걸 보여주어야 하므로 두 리스트뷰를 연결할 연결고리를 찾아야한다.
    //버스북마크부터 제작
    private String route_id;
    private String route_nm;
    private String predictTime1;
    private String predictTime2;

    //북마크를 객체를 생성할 때 해당 데이터를 뽑아서 DB에 저장...생성자를 이용
    public Bookmark(String route_id, String route_nm, String predictTime1, String predictTime2) {
        this.route_id = route_id;
        this.route_nm = route_nm;
        this.predictTime1 = predictTime1;
        this.predictTime2 = predictTime2;
    }
    //북마크 테이블을 구상해보자
    /*
    * create table route_bookmark
    * */

    //서버로 보내 디비에 저장하자 -> 용량이 크지 않으므로 서버대신에 sqlite로 만들면 편하지않을까?
    //웹서버로보내서 다시받아와서 뿌리려면 소모되는 시간과 자원이 크다.
    //간소화해보자



}
