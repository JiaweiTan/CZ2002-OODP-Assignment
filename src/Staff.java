
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
	public void setName(String name) {
		this.name = name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
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
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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
	

}
