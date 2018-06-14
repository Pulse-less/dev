<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.jdom2.*" %>
<%@ page import="org.jdom2.output.*" %>
<%@ page import="busServer.ConnectDB" %>

<%
	request.setCharacterEncoding("UTF-8");
	String param = request.getParameter("route_nm")==null?" ":request.getParameter("route_nm");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "tiger";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, id, pw);
		String sql = "select route_id, route_nm, route_tp, st_sta_nm, ed_sta_nm, region_name from route where route_nm like ?||'%' order by route_nm";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, param);
		rs = pstmt.executeQuery();
		
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
			Element route_tp = new Element("route_tp");
			route_tp.setText(rs.getString("route_tp"));
			Element region_name = new Element("region_name");
			region_name.setText(rs.getString("region_name"));
			Element st_sta_nm = new Element("st_sta_nm");
			st_sta_nm.setText(rs.getString("st_sta_nm"));
			Element ed_sta_nm = new Element("ed_sta_nm");
			ed_sta_nm.setText(rs.getString("ed_sta_nm"));
			
			data.addContent(route_id);
			data.addContent(route_nm);
			data.addContent(route_tp);
			data.addContent(region_name);
			data.addContent(st_sta_nm);
			data.addContent(ed_sta_nm);
			
			root.addContent(data);
			System.out.println(index +". ="+rs.getString("route_id"));
			System.out.println(index +". ="+rs.getString("route_nm"));
			System.out.println(index +". ="+rs.getString("route_tp"));
			System.out.println(index +". ="+rs.getString("region_name"));
			System.out.println(index +". ="+rs.getString("st_sta_nm"));
			System.out.println(index +". ="+rs.getString("ed_sta_nm"));
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
		System.out.println("sendRoute xml완성");
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