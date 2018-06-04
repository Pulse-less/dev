package com.cookandroid.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnMenu, btnSearchChange;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu=(Button)findViewById(R.id.btnMenu);
        btnSearchChange = (Button)findViewById(R.id.btnSearchChange);

        btnSearchChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),TabHostActivity.class);
                startActivity(in);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(in);
            }
        });

    }

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
