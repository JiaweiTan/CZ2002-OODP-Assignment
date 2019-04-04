import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Staff {

	private String name;
	private char gender;
	private String contact;
	private String email;
	private String address;
	private int employeeId;
	private String shift;
	private String jobTitle;
	public Staff()
	{
		
	}
	public Staff(int employeeId,String name,char gender, String contact, String email, String address,String shift,String jobTitle) {
		this.name=name;
		this.gender=gender;
		this.contact=contact;
		this.email=email;
		this.address=address;
		this.employeeId=employeeId;
		this.shift=shift;
		this.jobTitle=jobTitle;
		
	}
	public String getName() {
		return name;
	}

	public char getGender() {
		return gender;
	}

	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getEmployeeId() {
		return employeeId;
	}

	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public void addPromoItems(Staff staff) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		db.saveStaffDetails(staff);		
		
	}
	public void updateInfo(int staffId, int i) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		db.UpdateStaffItem(staffId, i);
		
	}
	public void remove(int removeID) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		db.deleteStaff(removeID);
	}
	public ArrayList<Staff> readStaffInfo(String filename) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		return db.readStaffInfo(filename);
	}
	public int SystemGeneratedID(String filename) throws IOException
	{
		List<Staff> staffList = DBManager.readStaffInfo(filename);
		if(staffList.size()>0)
			return staffList.get(staffList.size()-1).getEmployeeId() + 1;
		else
			return 1001;
	}
	

}
