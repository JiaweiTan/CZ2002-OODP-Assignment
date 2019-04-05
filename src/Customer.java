import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Customer extends Person{
	
	private String expiry;
	private boolean status;
	private List<Integer> invoiceId;
	
	public Customer () {};
	
	public Customer (int customerId, String name, String contact, String expiry, boolean status, List<Integer> invoiceId) {
		this.id = customerId;
		this.name = name;
		this.contact = contact;
		this.expiry = expiry;
		this.status = status;
		this.invoiceId = invoiceId;
	}
	
	public String getExpiry() {
		return this.expiry;
	}
	
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public List<Integer> getInvoiceId(){
		return this.invoiceId;
	}
	
	public void setInvoiceId(List<Integer> invoiceId){
		this.invoiceId = invoiceId;
	}
	
	public static Customer getCustomer(int customerId) throws IOException {
		List<Customer> cstLst = DBManager.readCustomerInfo("CustomerList.txt");
		for(Customer cs: cstLst) {
			if(customerId == cs.getID()) {
				return cs;
			}
		}
		return null;
	}
	

	
	public static void viewCustomer(int memId) throws IOException {
		
		if(Validation.customerExistsDB(memId) < 0) {
			System.out.println("Invalid Membership ID. Please enter a valid ID.");
		}
		else {
			Customer cs = Customer.getCustomer(memId);
			System.out.println("Membership ID\t\t: " + cs.getID());
			System.out.println("Name\t\t\t: " + cs.getName());
			System.out.println("Contact Number\t\t: " + cs.getContact());
			List<Integer> invIdLst = cs.getInvoiceId();
			List<Invoice> invLst = DBManager.readInvoice("Invoice.txt");
			double totalSpent = 0;
			int visits = 0;
			if(invIdLst != null) {
				for(int id: invIdLst) {
					for(Invoice in: invLst) {
						if(in.getInvoiceID() == id) {
							totalSpent += in.getFinalPrice();
							visits++;
							break;
						}
					}
				}
			}
			System.out.println("Total Visits\t\t: " + visits);
			System.out.println("Total Spent\t\t: " + String.format("%.2f", totalSpent));
			System.out.println("Membership Expiry Date\t: " + cs.getExpiry());
		}
	}
	
}
