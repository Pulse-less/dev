package com.cookandroid.bus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BusDBHelper extends SQLiteOpenHelper {

    public BusDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //일단 route에 관련된 디비...인데 기존에 만든 디비먼저 사용할수있나?
        //검색에 필요한 테이블을 생성해야하므로 필요한테이블을 생각해야한다.
        //버스검색엔 route table...
        //노선테이블의 경우 검색파라메터를 위한 route_id가 필요하고 사용자가 노선번호로 검색을 하기 위한 route_nm이 필요하다.
        //정류소테이블은 정류소명이나 정류소번호가 필요하다. station table
        //정류소검색을 위한 파라메터로는 string형식의 station_id 또는 stationName이 필요하다. 이름으로 넘길때는 인코딩에 조심
        //db.execSQL("create table route(route_id char(20), route_nm char(20))");
        //db.execSQL("create table route(route_id char(20), route_nm char(20)) ");
        //db.execSQL("create table station(station_id char(20), station_nm char(50))");
        db.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists route");
        onCreate(db);
    }
}
