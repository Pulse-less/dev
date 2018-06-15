package com.example.pulse.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class BookmarkDBHelper extends SQLiteOpenHelper {
    public BookmarkDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //두테이블을 연결해야한다. 기본정류장테이블에서 루트아이디를 받아오니 루트아이디를 삽입할까?
        //버스북마크테이블
        //필요한 컬럼은 노선아이디, 노선명, 도착예정시간1, 도착예정시간2
        db.execSQL("create table routeBookmark(route_id char(50) primary key, route_nm char(50), predictTime1 char(50), predictTime2 char(50))");

        //정류장북마크테이블
        //정류장명...그리고 외래키가 될 route_id
        db.execSQL("create table stationBookmark(station_nm char(100), route_id char(50) references routeBookmark(route_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists routeBookmark");
        db.execSQL("drop table if exists stationBookmark");
        onCreate(db);
    }
}
