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
	
}
