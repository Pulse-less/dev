<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>

<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.jdom2.*" %>
<%@ page import="org.jdom2.output.*" %>
<%
	//request.setCharacterEncoding("UTF-8");
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "tiger";
	
	//String param = request.getParameter("route_nm");
	String param = request.getParameter("route_nm")==null?"none":request.getParameter("route_nm");
	System.out.println(param+"체크");
	
/* 	if(param.equals("88")){
		out.println("참 true");
	}else{
		out.println("거짓 false");
	} */
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,id,pw);
		if(!param.equals("")){
			String sql = "select * from route where route_nm like ?||'%' order by route_nm";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,param);
			rs = pstmt.executeQuery();
			
			//xml문서 작성
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
				System.out.println(param);
				System.out.println(index +". "+rs.getString("route_id"));
				System.out.println(index +". "+rs.getString("route_nm"));
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
			String result = xout.outputString(doc);
			//jsp 출력영역 삭제
			out.clear();
			//웹브라우저에 출력
			out.print(result);
			System.out.println("xml완성 끝!");
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		}else{
			out.println("결과값이 없는디요");
			System.out.println("결과값이 없는디요");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>