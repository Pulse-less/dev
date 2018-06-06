
public class Area {

	private String center_id;
	private String area_id;
	private String area_name;
	
	public Area(String center_id, String area_id, String area_name) {
		this.center_id = center_id;
		this.area_id = area_id;
		this.area_name = area_name;
	}
		
	public String getCenter_id() {
		return center_id;
	}
	public void setCenter_id(String center_id) {
		this.center_id = center_id;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	

}
