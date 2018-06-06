package com.example.pulse.dbtest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text;
    Button btn1;
    EditText edit1,edit2,edit3;

    DBHelper dbHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);
        btn1 = (Button)findViewById(R.id.btn1);
        edit1 = (EditText)findViewById(R.id.edit1);
        edit2 = (EditText)findViewById(R.id.edit2);
        edit3 = (EditText)findViewById(R.id.edit3);

        dbHelper = new DBHelper(this, "busDB.db",null,1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = dbHelper.getWritableDatabase();
                Cursor cursor;

                //sqlDB.execSQL("insert into route(route_id, route_nm) values ('11','test');");
                String temp1 = "확인\n";

                cursor = sqlDB.rawQuery("select route_nm||station_nm from routestation where route_id = '"+edit1.getText().toString()+"';",null);

                while(cursor.moveToNext()){
                    temp1 += (cursor.getString(0)+"\n");

                }
                text.setText(temp1);

                cursor.close();
                sqlDB.close();
            }
        });


    }
}
