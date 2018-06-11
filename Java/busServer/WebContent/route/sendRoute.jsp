<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="org.jdom2.*" %>
<%@ page import="org.jdom2.output.*" %>
<%
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "scott";
	String pw = "tiger";
	String param = request.getParameter("route_nm");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,id,pw);
		String sql = "select * from route where route_nm = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,param);
		rs = pstmt.executeQuery();
		//xml문서 작성
		Element root = new Element("routes");
		Document doc = new Document(); 
		while(rs.next()){
			//자식노드 생성
			Element data = new Element("route");
			Element route_id = new Element("route_id");
			//book_code태그에 들어갈 데이터
			route_id.setText(rs.getString("route_id"));
			//태그의 속성도 정의 가능함
			//book_code.setAttribute("num", "5");
			Element route_nm = new Element("route_nm");
			route_nm.setText(rs.getString("route_nm"));
			//<book>(Element data로 만든것)노드의 하위노드추가
			root.addContent(data);
			data.addContent(route_id);
			data.addContent(route_nm);
			//루트에 book노드 추가
			//root.addContent(data);
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
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		while(rs.next()){
			System.out.println(rs.getString("route_id"));
			System.out.println(rs.getString("route_nm"));
		}
		//db 종료
		try{
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

%>