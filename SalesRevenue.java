import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;

public class SalesRevenue {

	private String dateYear;
	private Double totalRevenue;
	private List<Integer> invoiceID;
	
	public SalesRevenue() 
	{
		
	}
	
	public SalesRevenue(String dateYear, Double totalRevenue, List<Integer> invoiceID) 
	{
		this.dateYear = dateYear;
		this.totalRevenue = totalRevenue;
		this.invoiceID = invoiceID;
	}
	
	public String getDateYear() {
		return dateYear;
	}

	public void setDate(String dateYear) {
		this.dateYear = dateYear;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public List<Integer> getInvoiceID() {
		return this.invoiceID;
	}

	public void setInvoiceID(List<Integer> invoiceID) {
		this.invoiceID = invoiceID;
	}
	
	public static SalesRevenue createSalesRevenue(List<Invoice> invoiceLst, int month, int year) 
	{
		String dateYear = "";
		double totalRevenue = 0;
		List<Integer> invoiceIDLst = new ArrayList<Integer>();
		
		if(month >= 0 && month <= 9) 
		{
			dateYear = "0"+month;
		}
		else
		{
			dateYear += month;
		}
		dateYear = dateYear + "-" + year;
		
		for(Invoice invoice: invoiceLst) 
		{
			totalRevenue += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		SalesRevenue salesRevenue = new SalesRevenue(dateYear, totalRevenue, invoiceIDLst);
		return salesRevenue;
	}
	
	public static SalesRevenue updateSalesRevenue(SalesRevenue salesRevenue, List<Invoice> invoiceLst) 
	{
		double totalRevenue = 0;
		List<Integer> invoiceIDLst = new ArrayList<Integer>();
		
		for(Invoice invoice: invoiceLst) 
		{
			totalRevenue += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		
		salesRevenue.setTotalRevenue(totalRevenue);
		salesRevenue.setInvoiceID(invoiceIDLst);
		return salesRevenue;
	}
	public static void printSalesRevenue(SalesRevenue salesRevenue) throws IOException
	{
		boolean print = false;
		int multiplier = 1;
		int i = 0;
		double price = 0;
		
		List<PromoSet> promoLst = DBManager.readPromoSets("promosets.txt");
		List<Menu> menuLst = MenuFunc.getMenu("outputMenu.txt");
		
		List<Integer> invoiceIDLst = salesRevenue.getInvoiceID();
		List<Invoice> invoiceLst = DBManager.readInvoice("src/Invoice.txt");
		
		List<Integer> orderIDLst = new ArrayList<Integer>();
		List<Order> orderLst = DBManager.readOrders("orders.txt");
		
		List<Integer> itemLst = new ArrayList<Integer>();
		List<Integer> promoItemLst = new ArrayList<Integer>();
		
		for(int invoiceID: invoiceIDLst)
		{
			for(Invoice invoice: invoiceLst) 
			{
				if(invoiceID == invoice.getInvoiceID())
				{
					orderIDLst.add(invoice.getOrderID());
					break;
				}
			}
		}	
		for(int orderID: orderIDLst) 
		{
			for(Order order: orderLst)
			{
				if(orderID == order.getOrderId())
				{
					itemLst.addAll(order.getItemId());
					promoItemLst.addAll(order.getPromoSetId());
					break;
				}
			}
		}
		Collections.sort(itemLst);
		Collections.sort(promoItemLst);
		
		System.out.println("\tSales Revenue Report");
		System.out.println("\t      " + salesRevenue.getDateYear());
		System.out.println("------------------------------------");
	
		for(int item: itemLst) 
		{
			print = false;
			if(i != itemLst.lastIndexOf(item))
			{
				print = false;
				multiplier++;
			}
			else 
			{
				for(Menu menu: menuLst)
				{
					if(item == menu.getFoodID()) 
					{
						System.out.println(" x" + multiplier + "\t" + menu.getFoodName());
						price = multiplier * menu.getFoodPrice();
						break;
					}
				}
				System.out.println("\t\t\t\t" + (price));
				price = 0;
				multiplier = 1;
			}
			i++;
		}
		
		i = 0;
		
		for(int promoItem: promoItemLst) 
		{
			print = false;
			if(i != promoItemLst.lastIndexOf(promoItem))
			{
				print = false;
				multiplier++;
			}
			else 
			{
				for(PromoSet promoMenu: promoLst)
				{
					if(promoItem == promoMenu.getPromoSetId()) 
					{
						System.out.println(" x" + multiplier + "\t" + promoMenu.getName());
						price = multiplier * (int)promoMenu.getPrice();
						break;
					}
				}
				System.out.println("\t\t\t\t" + (price));
				price = 0;
				multiplier = 1;
			}
			i++;
		}
		
		System.out.println("----------------------------------------");
		System.out.println("   Total Revenue\t\t" + String.format("%.2f", salesRevenue.getTotalRevenue()));
		System.out.println("----------------------------------------\n");
	}
	
	
}
