package com.cookandroid.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnSearchChange;
    ImageButton btnMenu;
    private long pressedTime;
    ListView listView;
    ArrayList<StationBookmarkDTO> sbList;
    //ArrayList<RouteBookmarkDTO> rbList;
    BookmarkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu=(ImageButton)findViewById(R.id.btnMenu);
        btnSearchChange = (Button)findViewById(R.id.btnSearchChange);
        listView = (ListView)findViewById(R.id.listView);

        //이때 디비가 없으면 생성된다.
        BookmarkDBHelper bookmarkDBHelper = BookmarkDBHelper.getInstance(getApplicationContext());

        //북마크가 있는지 확인 후 있으면 메인엑티비티에 있는 리스트에 북마크 뿌림 없으면 아무동작 안함
        if(bookmarkDBHelper.isBookmark()){
            Log.i("널체크",bookmarkDBHelper.isBookmark()+"이거뭐임?");
            //있으면리스트뷰에 전개
            sbList = bookmarkDBHelper.selectStation();//정류장북마크 데이터를 가져와 리스트에 넣기
            adapter = new BookmarkAdapter(getApplicationContext(),sbList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }else{
            //없으면 아무것도 안하는데 이미지뷰에다 뭐라도 보여줄까..? 등록한 즐겨찾기가 없습니다...같은거
        }


        //검색액티비티로 전환
        btnSearchChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TabHostActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        //메뉴 -> 액티비티전환대신 따로 처리하는것으로 변경
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.item1:
                                break;
                            case R.id.item2:
                                break;
                            case R.id.item3:
                                break;
                            case R.id.item4:
                                break;
                            case R.id.item5:
                                break;
                            case R.id.item6:
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
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
