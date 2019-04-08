import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalesRevenueYear {
	private int dateYear;
	private Double netSales;
	private List<Integer> invoiceID;
	
	public SalesRevenueYear() 
	{
		
	}
	
	public SalesRevenueYear(int year, Double netSales, List<Integer> invoiceID) 
	{
		this.dateYear = year;
		this.netSales = netSales;
		this.invoiceID = invoiceID;
	}
	
	public int getYear() {
		return dateYear;
	}

	public void setYear(int dateYear) {
		this.dateYear = dateYear;
	}

	public Double getNetSales() {
		return netSales;
	}

	public void setNetSales(Double netSales) {
		this.netSales = netSales;
	}

	public List<Integer> getInvoiceID() {
		return this.invoiceID;
	}

	public void setInvoiceID(List<Integer> invoiceID) {
		this.invoiceID = invoiceID;
	}
	
	public static SalesRevenueYear createSalesRevenue(List<Invoice> invoiceLst, int year) 
	{
		double netSales = 0;
		List<Integer> invoiceIDLst = new ArrayList<Integer>();
		for(Invoice invoice: invoiceLst) 
		{
			netSales += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		SalesRevenueYear salesRevenue = new SalesRevenueYear(year, netSales, invoiceIDLst);
		return salesRevenue;
	}
	
	public static SalesRevenueYear updateSalesRevenue(SalesRevenueYear salesRevenue, List<Invoice> invoiceLst) 
	{
		double netSales = 0;
		List<Integer> invoiceIDLst = new ArrayList<Integer>();
		
		for(Invoice invoice: invoiceLst) 
		{
			netSales += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		
		salesRevenue.setNetSales(netSales);
		salesRevenue.setInvoiceID(invoiceIDLst);
		return salesRevenue;
	}
	public static void printSalesRevenue(SalesRevenueYear salesRevenue) throws IOException
	{
		boolean print = false;
		int multiplier = 1;
		int i = 0;
		double price = 0, totalPrice = 0;
		
		List<PromoSet> promoLst = DBManager.readPromoSetInfo("PromotionList.txt");
		List<Menu> menuLst = MenuFunc.getMenu("OutputMenu.txt");
		
		List<Integer> invoiceIDLst = salesRevenue.getInvoiceID();
		List<Invoice> invoiceLst = DBManager.readInvoice("Invoice.txt");
		
		List<Integer> orderIDLst = new ArrayList<Integer>();
		List<Order> orderLst = DBManager.readOrders("Orders.txt");
		
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
		System.out.println("========================================");
		System.out.println("\tSales Revenue Report");
		System.out.println("\t      (Yearly)");
		System.out.println("\t       " + salesRevenue.getYear());
		System.out.println("========================================");
	
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
						totalPrice += price;
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
						totalPrice += price;
						break;
					}
				}
				System.out.println("\t\t\t\t" + (price));
				price = 0;
				multiplier = 1;
			}
			i++;
		}
		System.out.println("========================================");
		System.out.println(" Total\t\t\t\t" + totalPrice);
		System.out.println(" Net Sales\t\t\t" + String.format("%.2f", salesRevenue.getNetSales()));
		System.out.println("========================================\n\n");
	}
}
