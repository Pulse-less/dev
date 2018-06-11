package busServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConnectDB {
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public ConnectDB() {}
	
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "tiger";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//버스검색
	public void connectionDB(String param) {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * from route where route_nm like ?||'%' order by route_nm";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("route_id"));
				System.out.println(rs.getString("route_nm"));
			}
			
			makeXml();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeXml() {
		try {
			Element root = new Element("route_info");
			Document doc = new Document(root);
			int index = 1;
			while(rs.next()){
				//자식노드 생성
				Element data = new Element("route");
				Element route_id = new Element("route_id");
				route_id.setText(rs.getString("route_id"));
				Element route_nm = new Element("route_nm");
				route_nm.setText(rs.getString("route_nm"));
				
				data.addContent(route_id);
				data.addContent(route_nm);
				
				root.addContent(data);
				System.out.println(index +". ="+rs.getString("route_id"));
				System.out.println(index +". ="+rs.getString("route_nm"));
				index++;
			}		
			XMLOutputter xout = new XMLOutputter();
			Format f = xout.getFormat();
			f.setEncoding("utf-8");//인코딩설정
			f.setIndent("\t");//들여쓰기설정
			f.setLineSeparator("\r\n");//줄바꿈설정
			f.setTextMode(Format.TextMode.TRIM);//공백제거 
			xout.setFormat(f);
			//xml문서파일을 저장할 경우
			//xout.output(doc, new FileWriter("d:/jdom.xml"));
			//xout.output(doc,out);
			//xml문서 내용을 스트링으로 바꾸기
			//String result = xout.outputString(doc);
			//jsp 출력영역 삭제
			//out.clear();
			//웹브라우저에 출력
			//out.print(result);
			System.out.println("xml완성 끝!");
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					//여기서 종료
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}