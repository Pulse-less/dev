package com.cookandroid.bus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnMenu, btnSearchChange;
    private long pressedTime;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //공유레퍼런스 메인에 가져오기 -> 공유레퍼런스가져오는것을 따로 코드로 분리할것인가?
        pref = getSharedPreferences("Bookmark",0);
        editor = pref.edit();

        btnMenu=(Button)findViewById(R.id.btnMenu);
        btnSearchChange = (Button)findViewById(R.id.btnSearchChange);

        //공유프리퍼런스 값 가져오기
        if(pref.getBoolean("Bring_My_Bookmark",false)){
            //이곳에 설정
        }

        //디비설정
        //dbHelper = new BusDBHelper(this, "busDB.db",null,1);

        //검색액티비티로 전환
        btnSearchChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),TabHostActivity.class);
                startActivity(in);
            }
        });

        //메뉴 -> 액티비티전환대신 따로 처리하는것으로 변경
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //뒤로가기 버튼 두번누를시 종료
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(pressedTime == 0){
            Toast.makeText(getApplicationContext(),"어플을 종료하려면 한번 더 눌러주세요!",Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        }else{
            int seconds = (int)(System.currentTimeMillis() - pressedTime);
            if(seconds > 2000){
                pressedTime = 0;
            }else{
                finish();
            }
        }
    }
}
