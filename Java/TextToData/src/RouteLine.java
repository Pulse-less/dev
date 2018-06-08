
public class RouteLine {
	//ROUTE_ID|LOCATION_SEQ|LOCATION_TP|LOCATION_ID|SUB_LENGTH|SUM_LENGTH|X|Y|LINK_ID
	private String route_Id;
	private String location_Seq;
	private String location_Tp;
	private String location_Id;
	private String sub_Length;
	private String sum_Length;
	private String x;
	private String y;
	private String link_Id;
	public String getRoute_Id() {
		return route_Id;
	}
	public void setRoute_Id(String route_Id) {
		this.route_Id = route_Id;
	}
	public String getLocation_Seq() {
		return location_Seq;
	}
	public void setLocation_Seq(String location_Seq) {
		this.location_Seq = location_Seq;
	}
	public String getLocation_Tp() {
		return location_Tp;
	}
	public void setLocation_Tp(String location_Tp) {
		this.location_Tp = location_Tp;
	}
	public String getLocation_Id() {
		return location_Id;
	}
	public void setLocation_Id(String location_Id) {
		this.location_Id = location_Id;
	}
	public String getSub_Length() {
		return sub_Length;
	}
	public void setSub_Length(String sub_Length) {
		this.sub_Length = sub_Length;
	}
	public String getSum_Length() {
		return sum_Length;
	}
	public void setSum_Length(String sum_Length) {
		this.sum_Length = sum_Length;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getLink_Id() {
		return link_Id;
	}
	public void setLink_Id(String link_Id) {
		this.link_Id = link_Id;
	}
	public RouteLine(String route_Id, String location_Seq, String location_Tp, String location_Id, String sub_Length,
			String sum_Length, String x, String y, String link_Id) {
		super();
		this.route_Id = route_Id;
		this.location_Seq = location_Seq;
		this.location_Tp = location_Tp;
		this.location_Id = location_Id;
		this.sub_Length = sub_Length;
		this.sum_Length = sum_Length;
		this.x = x;
		this.y = y;
		this.link_Id = link_Id;
	}
}
