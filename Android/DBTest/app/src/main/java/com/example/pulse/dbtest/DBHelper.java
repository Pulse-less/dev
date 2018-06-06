package com.example.pulse.dbtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table route(ROUTE_ID char(20), ROUTE_NM char(20), ROUTE_TP char(20),ST_STA_ID char(20),ST_STA_NM char(20),ST_STA_NO char(20),ED_STA_ID char(20),ED_STA_NM char(20),ED_STA_NO char(20),UP_FIRST_TIME char(20),UP_LAST_TIME char(20),DOWN_FIRST_TIME char(20),DOWN_LAST_TIME char(20),PEEK_ALLOC char(20),NPEEK_ALLOC char(20),COMPANY_ID char(20),COMPANY_NM char(20),TEL_NO char(20),REGION_NAME char(20),DISTRICT_CD char(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists route");
        onCreate(db);
    }
}
