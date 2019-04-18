import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Staff extends Person {

	/*private String name;
	private char gender;
	private String contact;
	private String email;
	private String address;
	private int employeeId;*/
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
		this.id=employeeId;
		this.shift=shift;
		this.jobTitle=jobTitle;
		
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
		Validation v= new Validation();
		if(v.staffExistsDB(staffId)==1)
		{
			db.updateStaffItem(staffId, i);
			
		}
		else
		{
			System.out.println("invalid ID");
		}
		
		
	}
	public void remove(int removeID) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		
		Validation v= new Validation();
		if(v.staffExistsDB(removeID)==1)
		{
			db.deleteStaff(removeID);
			System.out.println("Delete successfully");
		}
		else
		{
			System.out.println("Delete unsuccessfully");
		}
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
			return staffList.get(staffList.size()-1).getID() + 1;
		else
			return 8001;
	}
	

}
