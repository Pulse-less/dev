import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainForStation {

	public static void main(String[] args) {
		//오라클연동
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pw = "tiger";
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		//rs = pstmt.executeQuery();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Load driver success");
			conn = DriverManager.getConnection(url, id, pw);	
			String sql = "INSERT INTO station VALUES(?, ?, ?, ?, ?, ?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			//ArrayList<RouteStation> listObjects = getListObjects("C:\\Users\\Pulse\\Desktop\\route.txt");
			//ArrayList<Route> listObjects = getListObjects("C:\\Users\\Pulse\\Desktop\\route1.txt");
			ArrayList<Station> listObjects = getListObjects("station.txt파일위치");
			//ArrayList<RouteStation> listObjects = getListObjects("C:\\Users\\Pulse\\Desktop\\routestation1.txt");
			//ArrayList<RouteLine> listObjects = getListObjects("C:\\Users\\Pulse\\Desktop\\routeline.txt");
			for(int i=0;i<listObjects.size();i++) {
				pstmt.setString(1, listObjects.get(i).getStation_Id());
				pstmt.setString(2, listObjects.get(i).getStation_Nm());
				pstmt.setString(3, listObjects.get(i).getCenter_Id());
				pstmt.setString(4, listObjects.get(i).getCenter_Yn());
				pstmt.setString(5, listObjects.get(i).getX());
				pstmt.setString(6, listObjects.get(i).getY());
				pstmt.setString(7, listObjects.get(i).getRegion_Name());
				pstmt.setString(8, listObjects.get(i).getMobile_No());
				pstmt.setString(9, listObjects.get(i).getDistrict_Cd());
//				pstmt.setString(10, listObjects.get(i).getUp_First_Time());
//				pstmt.setString(11, listObjects.get(i).getUp_Last_Time());
//				pstmt.setString(12, listObjects.get(i).getDown_First_Time());
//				pstmt.setString(13, listObjects.get(i).getDown_Last_Time());
//				pstmt.setString(14, listObjects.get(i).getPeek_Alloc());
//				pstmt.setString(15, listObjects.get(i).getNpeek_Alloc());
//				pstmt.setString(16, listObjects.get(i).getCompany_Id());
//				pstmt.setString(17, listObjects.get(i).getCompany_Nm());
//				pstmt.setString(18, listObjects.get(i).getTel_No());
//				pstmt.setString(19, listObjects.get(i).getRegion_Name());
//				pstmt.setString(20, listObjects.get(i).getDistrict_Cd());
				pstmt.executeUpdate();
				System.out.println("Insert success record"+(i+1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			//db 종료
			try{
				//if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}	
	public static ArrayList<Station> getListObjects(String filePath){
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ArrayList<Station> listResult = new ArrayList<Station>();
		
		try {
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String line = null;
			String[] firstSplit = null;
			
			while(true) {
				line = br.readLine();
				
				if(line == null) {
					break;
				}else {
					//일단 캐럿으로 짤라서 배열에 삽입
					firstSplit = line.split("\\^");
					//짜른걸 어레이리스트에 삽입시킨다
					for(int i=0;i<firstSplit.length;i++) {
						String[] SecondSplit = firstSplit[i].split("\\|");
						listResult.add(new Station(SecondSplit[0],SecondSplit[1],SecondSplit[2],SecondSplit[3],SecondSplit[4],SecondSplit[5], SecondSplit[6], SecondSplit[7], SecondSplit[8]));
					}
				}
			}		
		}catch(Exception e) {
			System.out.println("Read file error");
			e.printStackTrace();
		}finally {
			try {
				br.close();
				isr.close();
				fis.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return listResult;
	}
}
