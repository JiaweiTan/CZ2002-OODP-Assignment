
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
	

}
