import java.util.List;

public class SalesRevenue {

	private Double netSales;
	private List<Integer> invoiceID;
	
	public Double getNetSales() {
		return netSales;
	}
	public void setNetSales(Double netSales) {
		this.netSales = netSales;
	}
	public List<Integer> getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(List<Integer> invoiceID) {
		this.invoiceID = invoiceID;
	}
}
