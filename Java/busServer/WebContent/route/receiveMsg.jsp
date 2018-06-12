<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="busServer.ConnectDB" %>  
    
<%
	//안드로이드로부터 파라메터 받기
	String param = request.getParameter("route_nm")==null?" ":request.getParameter("route_nm");
	//디비에 보내기
	//ConnectDB connectDB = ConnectDB.getInstance();
	//connectDB.connectionDB(param);
%>    

<%-- <%
	RequestDispatcher rd = request.getRequestDispatcher("sendRoute.jsp");
%> --%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>