
public class RouteStation {
	private String route_Id;
	private String station_Id;
	private String upDown;
	private String sta_Order;
	private String route_Nm;
	private String station_Nm;
	public RouteStation(String route_Id, String station_Id, String upDown, String sta_Order, String route_Nm,
			String station_Nm) {
		super();
		this.route_Id = route_Id;
		this.station_Id = station_Id;
		this.upDown = upDown;
		this.sta_Order = sta_Order;
		this.route_Nm = route_Nm;
		this.station_Nm = station_Nm;
	}
	public String getRoute_Id() {
		return route_Id;
	}
	public void setRoute_Id(String route_Id) {
		this.route_Id = route_Id;
	}
	public String getStation_Id() {
		return station_Id;
	}
	public void setStation_Id(String station_Id) {
		this.station_Id = station_Id;
	}
	public String getUpDown() {
		return upDown;
	}
	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}
	public String getSta_Order() {
		return sta_Order;
	}
	public void setSta_Order(String sta_Order) {
		this.sta_Order = sta_Order;
	}
	public String getRoute_Nm() {
		return route_Nm;
	}
	public void setRoute_Nm(String route_Nm) {
		this.route_Nm = route_Nm;
	}
	public String getStation_Nm() {
		return station_Nm;
	}
	public void setStation_Nm(String station_Nm) {
		this.station_Nm = station_Nm;
	}
}
