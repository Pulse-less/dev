<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.jdom2.*" %>
<%@ page import="org.jdom2.output.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	String param = request.getParameter("mobile_no")==null?" ":request.getParameter("mobile_no");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "tiger";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, id, pw);
		String sql = "select station_id, station_nm, region_name, mobile_no from station where mobile_no like ?||'%' order by station_nm";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, param);
		rs = pstmt.executeQuery();
		
		Element root = new Element("station_info");
		Document doc = new Document(root);
		int index = 1;
		while(rs.next()){
			//자식노드 생성
			Element data = new Element("station");
			Element station_id = new Element("station_id");
			station_id.setText(rs.getString("station_id"));
			Element station_nm = new Element("station_nm");
			station_nm.setText(rs.getString("station_nm"));
			Element region_name = new Element("region_name");
			region_name.setText(rs.getString("region_name"));
			Element mobile_no = new Element("mobile_no");
			mobile_no.setText(rs.getString("mobile_no"));
			
			data.addContent(station_id);
			data.addContent(station_nm);
			data.addContent(region_name);
			data.addContent(mobile_no);
			
			root.addContent(data);
			System.out.println(index +". ="+rs.getString("station_id"));
			System.out.println(index +". ="+rs.getString("station_nm"));
			System.out.println(index +". ="+rs.getString("region_name"));
			System.out.println(index +". ="+rs.getString("mobile_no"));
			index++;
		}		
		XMLOutputter xout = new XMLOutputter();
		Format f = xout.getFormat();
		f.setEncoding("UTF-8");//인코딩설정
		f.setIndent("\t");//들여쓰기설정
		f.setLineSeparator("\r\n");//줄바꿈설정
		f.setTextMode(Format.TextMode.TRIM);//공백제거 
		xout.setFormat(f);
		//xml문서파일을 저장할 경우
		//xout.output(doc, new FileWriter("d:/jdom.xml"));
		//xout.output(doc,out);
		//xml문서 내용을 스트링으로 바꾸기
		String result = xout.outputString(doc);
		//jsp 출력영역 삭제
		out.clear();
		//웹브라우저에 출력
		out.print(result);
		System.out.println("sendStation xml완성");
		System.out.println("끝!");
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		try{//여기서 종료
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
%>