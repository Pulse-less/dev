package com.cookandroid.bus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
* 클래스 작성전에 생각해야할것...
* 북마크 디비는 여러 액티비티에서 열려야 한다. 헌데 액티비티마다 헬퍼를 생성하고 하려면 디비 열고 닫고의 문제가 생긴다..
*
* 0613 -> 헬퍼클래스를 싱글톤으로 하자 -> 인스턴스화해서 시키면 메모리 누수도 해결가능
* */


class BookmarkDBHelper extends SQLiteOpenHelper {
    //사용할 변수들 선언
    //먼저 디비용 변수
    public static BookmarkDBHelper bookmarkDBHelper = null;
    public static final String DB_NAME = "bookmarkDB"; //bookmarkDB.db->?
    //테이블은 두개로...
    public static final String ROUTE_BOOKMARK_TABLE = "routeBookmark";
    public static final String STATION_BOOKMARK_TABLE ="stationBookmark";
    public static final int DB_VERSION = 1;

    //버스북마크 변수
    public static final String ROUTE_COL_0 ="route_id";
    public static final String ROUTE_COL_1 ="route_nm";
    public static final String ROUTE_COL_2 ="predictTime1";
    public static final String ROUTE_COL_3 ="predictTime2";
    //정류장북마크 변수
    public static final String STATION_COL_0 ="station_nm"; //버스북마크의 외래키
    //public static final String STATION_COL_1 ="route_id"; //외래키로 지정하자

    private SQLiteDatabase sqlDB;

    //세팅함수
    public static BookmarkDBHelper getInstance(Context context){ //싱글톤으로 구현
        if(bookmarkDBHelper == null){ //인스턴스화 했는지 확인.. 없으면 새로 만들고 있으면 자기자신 반환
            bookmarkDBHelper = new BookmarkDBHelper(context);
        }

        return bookmarkDBHelper;
    }

    //디비 생성자 및 권한설정
    private BookmarkDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        sqlDB = this.getWritableDatabase();
    }

    //동작정의
    //테이블생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        //두테이블을 연결해야한다. 기본정류장테이블에서 루트아이디를 받아오니 루트아이디를 삽입할까?

        //정류장북마크테이블
        //정류장명...그리고 외래키가 될 route_id
        //정류장밑에 버스가오므로 정류장에서 외래키를 제공해줘야함
        //db.execSQL("create table stationBookmark(station_nm char(100), route_id char(50) references routeBookmark(route_id))");
        /*db.execSQL("create table " + STATION_BOOKMARK_TABLE + "(" + STATION_COL_0 + " char(100), " + STATION_COL_1 + " char(50) references "+
        ROUTE_BOOKMARK_TABLE +"("+ROUTE_COL_0+"))");*/
        db.execSQL("create table " + STATION_BOOKMARK_TABLE + "(" + STATION_COL_0 + " char(100) primary key);");

        //버스북마크테이블
        //필요한 컬럼은 노선아이디, 노선명, 도착예정시간1, 도착예정시간2, 외래키용 station_nm
        //db.execSQL("create table routeBookmark(route_id char(50) primary key, route_nm char(50), predictTime1 char(50), predictTime2 char(50))");
        db.execSQL("create table " + ROUTE_BOOKMARK_TABLE + "(" + ROUTE_COL_0 + " char(50) primary key," + ROUTE_COL_1 + " char(50), "
        + ROUTE_COL_2 + " char(50), " + ROUTE_COL_3 + " char(50), "+STATION_COL_0+" char(100) references "+STATION_BOOKMARK_TABLE+"("+STATION_COL_0+"))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ROUTE_BOOKMARK_TABLE);
        db.execSQL("drop table if exists " + STATION_BOOKMARK_TABLE);
        onCreate(db);
    }

    //북마크가 있는지 확인
    public boolean isBookmark(){
        boolean isData = false;
        //정류장이 있어야 버스 즐겨찾기도 확인가능 -> 일단 정류장만 확인하면 된다..?
        /*String sql = "select * from " + STATION_BOOKMARK_TABLE +" s, "+ROUTE_BOOKMARK_TABLE+" r where "+STATION_BOOKMARK_TABLE+"."+
                STATION_COL_0+"="+ROUTE_BOOKMARK_TABLE+"."+STATION_COL_0+";";*/
        String sql = "select * from " + STATION_BOOKMARK_TABLE +";";
        Cursor cursor = sqlDB.rawQuery(sql,null);

        //북마크가 있으면 true 없으면 false
        if(cursor.moveToNext()){
            isData = true;
        }

        cursor.close();
        return isData;
    }


    //정류장 insert
    public boolean insertStation(StationBookmarkDTO dto){
        ContentValues values = new ContentValues();
        values.put(STATION_COL_0,dto.getStation_nm());

        long result = sqlDB.insert(STATION_BOOKMARK_TABLE,null,values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //버스 insert
    public boolean insertRoute(RouteBookmarkDTO dto){
        //contentValues사용
        ContentValues values = new ContentValues();
        values.put(ROUTE_COL_0,dto.getRoute_id());
        values.put(ROUTE_COL_1,dto.getRoute_nm());
        values.put(ROUTE_COL_2,dto.getPredictTime1());
        values.put(ROUTE_COL_3,dto.getPredictTime2());
        values.put(STATION_COL_0,dto.getStation_nm());

        long result = sqlDB.insert(ROUTE_BOOKMARK_TABLE,null,values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //station select
    public ArrayList<StationBookmarkDTO> selectStation(){
        ArrayList<StationBookmarkDTO> sbList=null;
        StationBookmarkDTO sbDTO = null;

        String sql = "select * from "+STATION_BOOKMARK_TABLE+";";
                //" where "+STATION_COL_0+" = '"+station_nm+"';";
        Cursor cursor = sqlDB.rawQuery(sql, null);

        if(cursor.getCount()!=0){ //결과가 있으면
            sbList = new ArrayList<>();
            //반복해서 리스트에 담기
            for(int i=0; i<cursor.getCount();i++) {
                cursor.moveToNext();
                sbDTO = new StationBookmarkDTO();
                sbDTO.setStation_nm(cursor.getString(1));
                sbDTO.setRbList(selectRoute(cursor.getString(1)));

                sbList.add(sbDTO);
            }
            cursor.close();
            return sbList;
        }else{
            cursor.close();
            return sbList;
        }
    }

    //route select
    public ArrayList<RouteBookmarkDTO> selectRoute(String station_nm){
        RouteBookmarkDTO rbDTO = null;
        ArrayList<RouteBookmarkDTO> rbList = null;

        String sql = "select * from "+ROUTE_BOOKMARK_TABLE+" where "+STATION_COL_0+" = '"+station_nm+"';";
        Cursor cursor = sqlDB.rawQuery(sql, null);

        if(cursor.getCount()!=0) {
            rbList = new ArrayList<>();
            for(int i=0; i<cursor.getCount(); i++) {
                cursor.moveToNext();

                rbDTO = new RouteBookmarkDTO();
                rbDTO.setRoute_id(cursor.getString(1));
                rbDTO.setRoute_nm(cursor.getString(2));
                rbDTO.setPredictTime1(cursor.getString(3));
                rbDTO.setPredictTime2(cursor.getString(4));
                rbDTO.setStation_nm(cursor.getString(5));

                rbList.add(rbDTO);
            }

            cursor.close();
            return rbList;
        }else{
            cursor.close();
            return rbList;
        }
    }

    //delete
    public void deleteData(){
        sqlDB.delete(STATION_BOOKMARK_TABLE, null, null);
        sqlDB.delete(ROUTE_BOOKMARK_TABLE,null,null);
    }
}
