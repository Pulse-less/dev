package com.cookandroid.bus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Test extends AppCompatActivity{
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdb);

        textView = (TextView)findViewById(R.id.textView);

        BusDBHelper busDBHelper = new BusDBHelper(this, "busDB.db",null,1);
        SQLiteDatabase sqlDB = busDBHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from route where route_nm = '5626';",null);

        String temp = "테스트"+"\n";
        while(cursor.moveToNext()){
            temp+=cursor.getString(0)+"\n";
        }

        textView.setText(temp);

        cursor.close();
        sqlDB.close();

    }
}
