import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;

public class SalesRevenueMonth extends SalesRevenue{

	private String dateYear;
	
	public SalesRevenueMonth() 
	{
		
	}
	
	public SalesRevenueMonth(String dateYear, Double netSales, List<Integer> invoiceID) 
	{
		this.dateYear = dateYear;
		setNetSales(netSales);
		setInvoiceID(invoiceID);
	}
	
	public String getDateYear() {
		return dateYear;
	}

	public void setDate(String dateYear) {
		this.dateYear = dateYear;
	}

	public static SalesRevenueMonth createSalesRevenue(List<Invoice> invoiceLst, int month, int year) 
	{
		String dateYear = "";
		double netSales = 0;
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
			netSales += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		SalesRevenue salesRevenue = new SalesRevenueMonth(dateYear, netSales, invoiceIDLst);
		return (SalesRevenueMonth)salesRevenue;
	}
	
	public static SalesRevenueMonth updateSalesRevenue(SalesRevenueMonth salesRevenue, List<Invoice> invoiceLst) 
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
	public static void printSalesRevenue(SalesRevenueMonth salesRevenue) throws IOException
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
		System.out.println("\t     (Monthly)");
		System.out.println("\t      " + salesRevenue.getDateYear());
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
		System.out.println(" Total\t\t\t\t" + String.format("%.2f",totalPrice));
		System.out.println(" Net Sales\t\t\t" + String.format("%.2f", salesRevenue.getNetSales()));
		System.out.println("========================================\n\n");
	}
	
	
}
