import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Load driver success");
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Pulse\\Desktop\\busDB.db");
			
			String query = "INSERT INTO routestation VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement pstm = connection.prepareStatement(query);
			//Route a = new Route(routd_Id, route_Nm, route_Tp, st_Sta_Id, st_Sta_Nm, st_Sta_No, ed_Sta_Id, ed_Sta_Nm, ed_Sta_No, up_First_Time, up_Last_Time, down_First_Time, down_Last_Time, peek_Alloc, npeek_Alloc, company_Id, company_Nm, tel_No, region_Name, district_Cd)
			ArrayList<RouteStation> listObjects = getListObjects("C:\\Users\\Pulse\\Desktop\\routestation.txt");
			
			for(int i=0;i<listObjects.size();i++) {
				pstm.setString(1, listObjects.get(i).getRoute_Id());
				pstm.setString(2, listObjects.get(i).getStation_Id());
				pstm.setString(3, listObjects.get(i).getUpDown());
				pstm.setString(4, listObjects.get(i).getSta_Order());
				pstm.setString(5, listObjects.get(i).getRoute_Nm());
				pstm.setString(6, listObjects.get(i).getStation_Nm());
//				pstm.setString(7, listObjects.get(i).getRegion_Name());
//				pstm.setString(8, listObjects.get(i).getMobile_No());
//				pstm.setString(9, listObjects.get(i).getDistrict_Cd());
				
				pstm.executeUpdate();
				System.out.println("Insert success record"+(i+1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<RouteStation> getListObjects(String filePath){
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ArrayList<RouteStation> listResult = new ArrayList<RouteStation>();
		
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
						listResult.add(new RouteStation(SecondSplit[0],SecondSplit[1],SecondSplit[2],SecondSplit[3],SecondSplit[4],SecondSplit[5]));
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
