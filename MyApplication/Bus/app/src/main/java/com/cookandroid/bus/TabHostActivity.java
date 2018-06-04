package com.cookandroid.bus;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {
    Button btnBack1;
    EditText editSearch;
    ListView busListView, stopListView;
    BusAdapter busAdapter;
    ArrayList<Movie> mArray, mArray2;
    Movie mItem, mItem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        btnBack1=(Button)findViewById(R.id.btnBack1);
        editSearch = (EditText)findViewById(R.id.editSearch);
        busListView = (ListView)findViewById(R.id.busListView);
        stopListView = (ListView)findViewById(R.id.stopListView);
        mArray = new ArrayList<Movie>();
        mArray2 = new ArrayList<Movie>();

        final String[] titles={"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"};
        final String[] genre={"test","test","test","test","test","test","test","test","test","test"};

        final String[] titles2={"11","22","33","44","55","66","77","88","99","10"};
        final String[] genre2={"test","test","test","test","test","test","test","test","test","test"};
        //테스트객체생성
        for(int i=0;i<titles.length;i++){
            mItem = new Movie(titles[i],genre[i]);
            mArray.add(mItem);
        }

        busAdapter = new BusAdapter(this, mArray);
        busListView.setAdapter(busAdapter);

        //정류장테스트객체생성
        for(int i=0;i<titles.length;i++) {
            mItem2 = new Movie(titles2[i], genre2[i]);
            mArray2.add(mItem2);
        }

        busAdapter = new BusAdapter(this, mArray2);
        stopListView.setAdapter(busAdapter);

        final TabHost tabHost = getTabHost();

        //버스검색 탭
        TabHost.TabSpec tab1=tabHost.newTabSpec("TAB1");
        tab1.setIndicator("버스",getResources().getDrawable(R.drawable.busicon)).setContent(R.id.busListView);
        tabHost.addTab(tab1);
        //버스검색탭 동작 정의
        //Intent busIn = new Intent(getApplicationContext(), )

        //정류장검색 탭
        TabHost.TabSpec tab2=tabHost.newTabSpec("TAB2");
        tab2.setIndicator("정류장").setContent(R.id.stopListView);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);

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