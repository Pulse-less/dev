package com.cookandroid.bus;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
    Button btnBack1;
    EditText editSearch;
    ListView busListView, stopListView;
    BusAdapter busAdapter;
    BusDBHelper busDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;

    int index;

    ArrayList<Bus> mArray, mArray2,mArray3,mArray4;
    Bus mItem, mItem2, mItem3, mItem4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        btnBack1=(Button)findViewById(R.id.btnBack1);
        editSearch = (EditText)findViewById(R.id.editSearch);
        busListView = (ListView)findViewById(R.id.busListView);
        stopListView = (ListView)findViewById(R.id.stopListView);
        mArray = new ArrayList<Bus>();
        mArray2 = new ArrayList<Bus>();
        mArray3 = new ArrayList<Bus>();
        mArray4 = new ArrayList<Bus>();

        busDBHelper = new BusDBHelper(this, "busDB.db",null,1);
        sqlDB = busDBHelper.getWritableDatabase();

        final String[] titles={"버스1","버스2","버스3","버스4","버스5","버스6","버스7","버스8","버스9","버스10"};
        final String[] genre={"test","test","test","test","test","test","test","test","test","test"};

        final String[] titles2={"정류장1","정류장2","정류장3","정류장4","정류장5","정류장6","정류장7","정류장8","정류장9","정류장10"};
        final String[] genre2={"test","test","test","test","test","test","test","test","test","test"};

        final List<String> route_nm = new ArrayList<String>();
        final List<String> route_tp = new ArrayList<String>();

        //커서 인덱싱용

        //테스트객체 생성될 곳에는 최근 검색리스트가 나오게끔 -> 즐겨찾기 추가도 이곳에서 가능하게끔 -> 사실상 즐겨찾기 추가기능을 처리하기위해선 즐겨찾기용 코드분리필요
        //테스트객체생성
        for(int i=0;i<titles.length;i++){
            mItem = new Bus(titles[i],genre[i]);
            mArray.add(mItem);
        }

        //검색처리를 어떻게 할것인가? 어플 디비내에 있는 버스를 보여주나? 아니면 파싱받아서 파싱데이터만 보여주나?
        //애초에 버스검색시에는 파싱받을데이터가 따로 필요없음. DB내에 있는 버스데이터만 쿼리문으로 작성해서 보여주면 된다.
        //그럼 edittext로 입력받은 route_nm만 보이게끔하면 된다. -> select route_nm from route where route_id = ?
        //select * from route where route_id = ? 가 맞는 쿼리가 될듯.
        //저번에 만든 DB부터 추가하자
        busAdapter = new BusAdapter(this, mArray);
        busListView.setAdapter(busAdapter);

        //정류장테스트객체생성
        for(int i=0;i<titles.length;i++) {
            mItem2 = new Bus(titles2[i], genre2[i]);
            mArray2.add(mItem2);
        }

        busAdapter = new BusAdapter(this, mArray2);
        stopListView.setAdapter(busAdapter);

        final TabHost tabHost = getTabHost();

        //버스검색 탭
        final TabHost.TabSpec tab1=tabHost.newTabSpec("TAB1");
        tab1.setIndicator("버스",getResources().getDrawable(R.drawable.busicon)).setContent(R.id.busListView);
        tabHost.addTab(tab1);
        //버스검색탭 동작 정의
        //Intent busIn = new Intent(getApplicationContext(), )


        //정류장검색 탭
        TabHost.TabSpec tab2=tabHost.newTabSpec("TAB2");
        tab2.setIndicator("정류장").setContent(R.id.stopListView);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);

        //탭변경시 이벤트처리
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabHost.getCurrentTabTag().equals("TAB1")){
                    editSearch.setHint("버스 검색");
                    //검색입력받았을때 동작도 정의해야한다.
//                    if(!editSearch.getText().toString().equals("")){
//                        //디비에서 버스 검색해야한다
//                        //sqlDB.execSQL("select route_id from route where route_nm = ?");
//                        //String sql = "select route_id from route where route_nm = ?";
//                        //Object[] bindArgs = {editSearch.getText().toString()};
//                        //커서로 쿼리문 작성
//                        cursor = sqlDB.rawQuery("select route_id,route_tp from route where route_nm="+editSearch.getText().toString(),null);
//                        String[] bus_nm ={};
//                        String[] bus_tp ={};
//                        //커서동작
//                        while(cursor.moveToNext()){
//                            bus_nm[index]=cursor.getString(0);
//                            bus_tp[index]=cursor.getString(1);
//                            index++;
//                        }
//                        //검색한결과로 버스객체생성
//                        for(int i=0;i<bus_nm.length;i++){
//                            mItem3 = new Bus(bus_nm[i],bus_tp[i]);
//                            mArray3.add(mItem3);
//                        }
//                        //리스트뷰에 보이기
//                        busAdapter = new BusAdapter(TabHostActivity.this,mArray3);
//                        busListView.setAdapter(busAdapter);
//                    }
                    //에딧텍스트의 리스너를 정의해서 써야할듯?
                    editSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            //텍스트 입력하기 전에
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //텍스트에 변화중일때

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            //텍스트 입력이 끝났을때
                            cursor = sqlDB.rawQuery("select route_id,route_tp from route where route_nm="+s.toString(),null);

                            //커서동작
                            while(cursor.moveToNext()){
                                route_nm.add(cursor.getString(0));
                                route_tp.add(cursor.getString(1));
                            }
                            //검색한결과로 버스객체생성
                            for(int i=0;i<route_nm.size();i++){
                                mItem3 = new Bus(route_nm.get(i).toString(),route_tp.get(i).toString());
                                mArray3.add(mItem3);
                            }
                            //리스트뷰에 보이기
                            busAdapter = new BusAdapter(TabHostActivity.this,mArray3);
                            busListView.setAdapter(busAdapter);
                        }
                    });
                }else{
                    editSearch.setHint("정류장 검색");
                }
            }
        });

        //탭별 동작정의
//        if(tabHost.getCurrentTabView()==busListView){
//            editSearch.setHint("버스 검색");
//        }else{
//            editSearch.setHint("정류장 검색");
//        }
        //위쪽 테스트객체에서 리스트뷰는 잘 동작하지만 각 컨버트뷰를 구성할 아이템으로 최근검색한 히스토리를 볼수있도록 만들자.
        //히스토리 노출시 최근검색데이터들을 공유자원에서 가져올것인지를 생각해야할듯하다.
        //에디트텍스트에 검색어 입력시 데이터베이스에서 또는 파서를 이용해서 데이터를 받아와 리스트뷰에 뿌려주도록하자
        //이때 해당항목의 리스트를 누르면 인텐트를 넘겨서 busSearch or stopsearch 액티비티로 넘어가도록하자


        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

    }
}