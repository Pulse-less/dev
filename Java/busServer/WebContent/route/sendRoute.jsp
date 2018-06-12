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
	System.out.println(param+"üũ");
	
/* 	if(param.equals("88")){
		out.println("�� true");
	}else{
		out.println("���� false");
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
			
			//xml���� �ۼ�
			Element root = new Element("route_info");
			Document doc = new Document(root);
			int index = 1;
			while(rs.next()){
				//�ڽĳ�� ����
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
			f.setEncoding("utf-8");//���ڵ�����
			f.setIndent("\t");//�鿩���⼳��
			f.setLineSeparator("\r\n");//�ٹٲ޼���
			f.setTextMode(Format.TextMode.TRIM);//�������� 
			xout.setFormat(f);
			//xml���������� ������ ���
			//xout.output(doc, new FileWriter("d:/jdom.xml"));
			//xout.output(doc,out);
			//xml���� ������ ��Ʈ������ �ٲٱ�
			String result = xout.outputString(doc);
			//jsp ��¿��� ����
			out.clear();
			//���������� ���
			out.print(result);
			System.out.println("xml�ϼ� ��!");
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		}else{
			out.println("������� ���µ��");
			System.out.println("������� ���µ��");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>