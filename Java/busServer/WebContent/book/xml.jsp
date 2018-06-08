<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,id,pw);
		String sql = "select * from book order by book_name";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		//XML태그설정
		//루트노드만들기
		Element root = new Element("books");
		Document doc = new Document(root);
		//doc.setRootElement(root);
		while(rs.next()){
			//자식노드 생성
			Element data = new Element("book");
			Element book_code = new Element("book_code");
			//book_code태그에 들어갈 데이터
			book_code.setText(rs.getString("book_code"));
			//태그의 속성도 정의 가능함
			//book_code.setAttribute("num", "5");
			Element book_name = new Element("book_name");
			book_name.setText(rs.getString("book_name"));
			Element press = new Element("press");
			press.setText(rs.getString("press"));
			Element price = new Element("price");
			price.setText(rs.getString("price"));
			Element amount = new Element("amount");
			amount.setText(rs.getString("amount"));
			//<book>(Element data로 만든것)노드의 하위노드추가
			root.addContent(data);
			data.addContent(book_code);
			data.addContent(book_name);
			data.addContent(press);
			data.addContent(price);
			data.addContent(amount);
			//루트에 book노드 추가
			//root.addContent(data);
		}
		//xml문서를 화면 또는 파일로 출력하기 위한 객체
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>