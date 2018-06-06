package com.example.pulse.xmlparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String key = "OyBWm7DZMTSjbfswkxwBOzJUJbDb2g4L50G2zv7hBt2tBUe1f%2F39yPKs8bU5Lg%2FUr8PrffmqYNetK6d9WUsSFQ%3D%3D";//인증키
    String parameter = "200000085";

    TextView textView;
    EditText editText;
    Button btn;

    //StringBuffer sb = new StringBuffer();
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.btn1);

        textView.setText("파싱테스트");

        try {
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/buslocationservice?serviceKey="+key+"&routeId="+parameter);
            //InputStream is = url.openStream();

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            //parser.setInput(new InputStreamReader(is, "UTF-8"));
            parser.setInput(url.openStream(),null);
            //parser.next();
            int parserEvent = parser.getEventType();

            //xml파일이 끝이 아닐때
            while(parserEvent!=XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_DOCUMENT:
                        //sb.append("파싱시작\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        //버스위치정보의 xml상위노드는 busLocationList
                        /*이후 태그 구성
                         * endBus - 막차여부...1이면 막차
                         * lowPlate - 저상버스여부...1이면 저상버스
                         * plateNo - 차량번호
                         * plateType - 차종...0:정보없음, 1:소형승합차, 2:중형승합차, 3:대형승합차, 4:2층버스
                         * remainSeatCnt - 남은좌석...-1:정보없음, 0~:빈자리 수
                         * routeId - 노선ID
                         * stationId - 정류소ID
                         * stationSeq - 정류소순서
                         * */
                        tag = parser.getName();//getName()은 태그이름 받아옴
                        if(tag.equals("busLocationList")) parser.next();
                        if(tag.equals("endBus")){
                            textView.setText("막차여부 : ");
                            //sb.append("막차여부 : ");
                            parser.next();
                            //sb.append(parser.getText()); //getText()는 해당태그의 텍스트를 가져옴
                            if(parser.getText().equals("1")){
                                //sb.append("막차입니다.\n");
                                textView.setText(textView.getText()+"막차입니다.\n");
                            }else{
                                //sb.append("막차가 아닙니다.\n");
                                textView.setText(textView.getText()+"막차가 아닙니다.\n");
                            }
                        }
                        if(tag.equals("lowPlate")){
                            //sb.append("저상버스 여부 : ");
                            textView.setText(textView.getText()+"저상버스 여부 : ");
                            parser.next();
                            if(parser.getText().equals("1")){
                                //sb.append("저상버스\n");
                                textView.setText(textView.getText()+"저상버스\n");
                            }else{
                                //sb.append("일반버스\n");
                                textView.setText(textView.getText()+"일반버스\n");
                            }
                        }
                        if(tag.equals("plateNo")){
                            //sb.append("차량번호 : ");
                            textView.setText(textView.getText()+"차량번호 : "+parser.getText()+"\n");
                            parser.next();
                        }
//                        if(tag.equals("plateType")){
//                            //sb.append("저상버스 여부 : ");
//                            textView.setText(textView.getText()+"차종 : ");
//                            parser.next();
//                            if(parser.getText().equals("1")){
//                                sb.append("저상버스\n");
//                            }else{
//                                sb.append("일반버스\n");
//                            }
//                        }
                        if(tag.equals("remainSeatCnt")){
                            //sb.append("비어있는 좌석 : ");
                            textView.setText(textView.getText()+"비어있는 좌석 : ");
                            parser.next();
                            if(parser.getText().equals("-1")){
                                //sb.append("좌석정보가 없습니다.\n");
                                textView.setText(textView.getText()+"좌석정보가 없습니다.\n");
                            }else{
                                //sb.append("남은좌석 수 : "+parser.getText()+"\n");
                                textView.setText(textView.getText()+parser.getText()+"\n");
                            }
                        }
//                        if(tag.equals("routeId")){
//                            sb.append("노선아이디 : ");
//                            parser.next();
//                            sb.append(parser.getText()+"\n");
//                        }
//                        if(tag.equals("stationId")){
//                            sb.append("정류소아이디 : ");
//                            parser.next();
//                            sb.append(parser.getText()+"\n");
//                        }
//                        if(tag.equals("stationSeq")){
//                            sb.append("정류소순서(테스트) : ");
//                            parser.next();
//                            sb.append(parser.getText()+"\n");
//                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName(); //종료태그이름가져오기
                        if(tag.equals("busLocationList")){
                            //목록1개 출력 끝. 개행하기
                            //sb.append("\n");
                            textView.setText(textView.getText()+"\n");
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //textView.setText(sb);
    }

        //버스위치정보기능
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                parameter = editText.getText().toString();
//
//                try {
//                    URL url = new URL("http://openapi.gbis.go.kr/ws/rest/buslocationservice?serviceKey="+key+"&routeId="+parameter);
//                    //InputStream is = url.openStream();
//
//                    XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
//                    XmlPullParser parser = parserFactory.newPullParser();
//
//                    //parser.setInput(new InputStreamReader(is, "UTF-8"));
//                    parser.setInput(url.openStream(),null);
//                    //parser.next();
//                    int parserEvent = parser.getEventType();
//
//                    //xml파일이 끝이 아닐때
//                    while(parserEvent!=XmlPullParser.END_DOCUMENT){
//                        switch(parserEvent){
//                            case XmlPullParser.START_DOCUMENT:
//                                //sb.append("파싱시작\n\n");
//                                break;
//                            case XmlPullParser.START_TAG:
//                                //버스위치정보의 xml상위노드는 busLocationList
//                                /*이후 태그 구성
//                                 * endBus - 막차여부...1이면 막차
//                                 * lowPlate - 저상버스여부...1이면 저상버스
//                                 * plateNo - 차량번호
//                                 * plateType - 차종...0:정보없음, 1:소형승합차, 2:중형승합차, 3:대형승합차, 4:2층버스
//                                 * remainSeatCnt - 남은좌석...-1:정보없음, 0~:빈자리 수
//                                 * routeId - 노선ID
//                                 * stationId - 정류소ID
//                                 * stationSeq - 정류소순서
//                                 * */
//                                tag = parser.getName();//getName()은 태그이름 받아옴
//                                if(tag.equals("busLocationList")) parser.next();
//                                if(tag.equals("endBus")){
//                                    sb.append("막차여부 : ");
//                                    parser.next();
//                                    //sb.append(parser.getText()); //getText()는 해당태그의 텍스트를 가져옴
//                                    if(parser.getText().equals("1")){
//                                        sb.append("막차입니다.\n");
//                                    }else{
//                                        sb.append("막차가 아닙니다.\n");
//                                    }
//                                }
//                                if(tag.equals("lowPlate")){
//                                    sb.append("저상버스 여부 : ");
//                                    parser.next();
//                                    if(parser.getText().equals("1")){
//                                        sb.append("저상버스\n");
//                                    }else{
//                                        sb.append("일반버스\n");
//                                    }
//                                }
//                                if(tag.equals("plateType")){
//                                    sb.append("저상버스 여부 : ");
//                                    parser.next();
//                                    if(parser.getText().equals("1")){
//                                        sb.append("저상버스\n");
//                                    }else{
//                                        sb.append("일반버스\n");
//                                    }
//                                }
//                                if(tag.equals("remainSeatCnt")){
//                                    sb.append("비어있는 좌석 : ");
//                                    parser.next();
//                                    if(parser.getText().equals("-1")){
//                                        sb.append("좌석정보가 없습니다.\n");
//                                    }else{
//                                        sb.append("남은좌석 수 : "+parser.getText()+"\n");
//                                    }
//                                }
//                                if(tag.equals("routeId")){
//                                    sb.append("노선아이디 : ");
//                                    parser.next();
//                                    sb.append(parser.getText()+"\n");
//                                }
//                                if(tag.equals("stationId")){
//                                    sb.append("정류소아이디 : ");
//                                    parser.next();
//                                    sb.append(parser.getText()+"\n");
//                                }
//                                if(tag.equals("stationSeq")){
//                                    sb.append("정류소순서(테스트) : ");
//                                    parser.next();
//                                    sb.append(parser.getText()+"\n");
//                                }
//                                break;
//                            case XmlPullParser.TEXT:
//                                break;
//                            case XmlPullParser.END_TAG:
//                                tag = parser.getName(); //종료태그이름가져오기
//                                if(tag.equals("busLocationList")){
//                                    //목록1개 출력 끝. 개행하기
//                                    sb.append("\n");
//                                }
//                                break;
//                        }
//                        parserEvent = parser.next();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                textView.setText(sb);
//            }
//        });
}

