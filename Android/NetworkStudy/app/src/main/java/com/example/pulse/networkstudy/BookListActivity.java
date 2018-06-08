package com.example.pulse.networkstudy;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends ListActivity {
    ArrayList<BookDTO> list;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BookAdapter adapter = new BookAdapter(getApplicationContext(), R.layout.book_row, list);
            setListAdapter(adapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        //웹서버접속 스레드 추가
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    list = new ArrayList<>();
                    URL url = new URL(Common.SERVER_URL + "/busServer/book/xml.jsp");
                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    InputStream is = url.openStream();
                    parser.setInput(is,"utf-8");

                    int eventType = parser.getEventType();
                    String tag;
                    BookDTO dto = null;
                    while(eventType!=XmlPullParser.END_DOCUMENT){
                        switch(eventType){
                            case XmlPullParser.START_TAG://여는태그
                                tag = parser.getName();
                                if(tag.equals("book_name")){
                                    dto = new BookDTO();
                                    dto.setBook_name(parser.nextText());
                                    parser.next();
                                }
                                if(tag.equals("press")){
                                    dto.setPress(parser.nextText());
                                    parser.next();
                                }
                                break;
                            case XmlPullParser.END_TAG://닫는태그
                                tag = parser.getName();
                                if(tag.equals("book")){
                                    list.add(dto);
                                    dto=null;
                                }
                                break;
                        }
                        eventType = parser.next(); //다음요소
                        handler.sendEmptyMessage(0);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        th.start();

    }

    //어댑터
    class BookAdapter extends ArrayAdapter<BookDTO>{
        public BookAdapter(Context context, int textViewResourceId, ArrayList<BookDTO> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v==null){
                LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.book_row,null);
            }
            BookDTO dto = list.get(position);
            if(dto!=null){
                TextView book_name = (TextView)findViewById(R.id.book_name);
                TextView press = (TextView)findViewById(R.id.press);

                book_name.setText(dto.getBook_name());
                press.setText(dto.getPress());
            }
            return v;
        }
    }
}

