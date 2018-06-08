
public class Route {
	private String route_Id; //�뼱id
	private String route_Nm; //�뼱��
	private String route_Tp; //�뼱����
	private String st_Sta_Id; //����������id
	private String st_Sta_Nm; //�����������
	private String st_Sta_No; //�����������ȣ
	private String ed_Sta_Id; //����������id
	private String ed_Sta_Nm; //�����������
	private String ed_Sta_No; //�����������ȣ
	private String up_First_Time; //����ù���ð�
	private String up_Last_Time; //���ื���ð�
	private String down_First_Time; //����ù���ð�
	private String down_Last_Time; //���ื���ð�
	private String peek_Alloc; //����ٽ� ��������
	private String npeek_Alloc; //���� ��������
	private String company_Id; //�����id
	private String company_Nm; //������
	private String tel_No; //�������ȭ��ȣ
	private String region_Name; //��������
	private String district_Cd; //���������ڵ� 1.���� 2.��� 3.��õ
	
	public Route(String route_Id, String route_Nm, String route_Tp, String st_Sta_Id, String st_Sta_Nm,
			String st_Sta_No, String ed_Sta_Id, String ed_Sta_Nm, String ed_Sta_No, String up_First_Time,
			String up_Last_Time, String down_First_Time, String down_Last_Time, String peek_Alloc, String npeek_Alloc,
			String company_Id, String company_Nm, String tel_No, String region_Name, String district_Cd) {
		super();
		this.route_Id = route_Id;
		this.route_Nm = route_Nm;
		this.route_Tp = route_Tp;
		this.st_Sta_Id = st_Sta_Id;
		this.st_Sta_Nm = st_Sta_Nm;
		this.st_Sta_No = st_Sta_No;
		this.ed_Sta_Id = ed_Sta_Id;
		this.ed_Sta_Nm = ed_Sta_Nm;
		this.ed_Sta_No = ed_Sta_No;
		this.up_First_Time = up_First_Time;
		this.up_Last_Time = up_Last_Time;
		this.down_First_Time = down_First_Time;
		this.down_Last_Time = down_Last_Time;
		this.peek_Alloc = peek_Alloc;
		this.npeek_Alloc = npeek_Alloc;
		this.company_Id = company_Id;
		this.company_Nm = company_Nm;
		this.tel_No = tel_No;
		this.region_Name = region_Name;
		this.district_Cd = district_Cd;
	}
	
	public String getRoute_Id() {
		return route_Id;
	}
	public void setRoute_Id(String route_Id) {
		this.route_Id = route_Id;
	}
	public String getRoute_Nm() {
		return route_Nm;
	}
	public void setRoute_Nm(String route_Nm) {
		this.route_Nm = route_Nm;
	}
	public String getRoute_Tp() {
		return route_Tp;
	}
	public void setRoute_Tp(String route_Tp) {
		this.route_Tp = route_Tp;
	}
	public String getSt_Sta_Id() {
		return st_Sta_Id;
	}
	public void setSt_Sta_Id(String st_Sta_Id) {
		this.st_Sta_Id = st_Sta_Id;
	}
	public String getSt_Sta_Nm() {
		return st_Sta_Nm;
	}
	public void setSt_Sta_Nm(String st_Sta_Nm) {
		this.st_Sta_Nm = st_Sta_Nm;
	}
	public String getSt_Sta_No() {
		return st_Sta_No;
	}
	public void setSt_Sta_No(String st_Sta_No) {
		this.st_Sta_No = st_Sta_No;
	}
	public String getEd_Sta_Id() {
		return ed_Sta_Id;
	}
	public void setEd_Sta_Id(String ed_Sta_Id) {
		this.ed_Sta_Id = ed_Sta_Id;
	}
	public String getEd_Sta_Nm() {
		return ed_Sta_Nm;
	}
	public void setEd_Sta_Nm(String ed_Sta_Nm) {
		this.ed_Sta_Nm = ed_Sta_Nm;
	}
	public String getEd_Sta_No() {
		return ed_Sta_No;
	}
	public void setEd_Sta_No(String ed_Sta_No) {
		this.ed_Sta_No = ed_Sta_No;
	}
	public String getUp_First_Time() {
		return up_First_Time;
	}
	public void setUp_First_Time(String up_First_Time) {
		this.up_First_Time = up_First_Time;
	}
	public String getUp_Last_Time() {
		return up_Last_Time;
	}
	public void setUp_Last_Time(String up_Last_Time) {
		this.up_Last_Time = up_Last_Time;
	}
	public String getDown_First_Time() {
		return down_First_Time;
	}
	public void setDown_First_Time(String down_First_Time) {
		this.down_First_Time = down_First_Time;
	}
	public String getDown_Last_Time() {
		return down_Last_Time;
	}
	public void setDown_Last_Time(String down_Last_Time) {
		this.down_Last_Time = down_Last_Time;
	}
	public String getPeek_Alloc() {
		return peek_Alloc;
	}
	public void setPeek_Alloc(String peek_Alloc) {
		this.peek_Alloc = peek_Alloc;
	}
	public String getNpeek_Alloc() {
		return npeek_Alloc;
	}
	public void setNpeek_Alloc(String npeek_Alloc) {
		this.npeek_Alloc = npeek_Alloc;
	}
	public String getCompany_Id() {
		return company_Id;
	}
	public void setCompany_Id(String company_Id) {
		this.company_Id = company_Id;
	}
	public String getCompany_Nm() {
		return company_Nm;
	}
	public void setCompany_Nm(String company_Nm) {
		this.company_Nm = company_Nm;
	}
	public String getTel_No() {
		return tel_No;
	}
	public void setTel_No(String tel_No) {
		this.tel_No = tel_No;
	}
	public String getRegion_Name() {
		return region_Name;
	}
	public void setRegion_Name(String region_Name) {
		this.region_Name = region_Name;
	}
	public String getDistrict_Cd() {
		return district_Cd;
	}
	public void setDistrict_Cd(String district_Cd) {
		this.district_Cd = district_Cd;
	}
}
