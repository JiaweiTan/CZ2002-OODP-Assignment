import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;

public class SalesRevenueDay extends SalesRevenue{

	private String date;
	
	public SalesRevenueDay() 
	{
		
	}
	
	public SalesRevenueDay(String date, Double netSales, List<Integer> invoiceID) 
	{
		this.date = date;
		setNetSales(netSales);
		setInvoiceID(invoiceID);
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	 
	public static SalesRevenueDay createSalesRevenue(List<Invoice> invoiceLst, int day, int month, int year) 
	{
		String date = "";
		double netSales = 0;
		List<Integer> invoiceIDLst = new ArrayList<Integer>();
		if(day >= 0 && day <= 9)
		{
			date = "0"+day;
		}
		else
		{
			date += day;
		}
		date = date + "-";
		
		if(month >= 0 && month <= 9) 
		{
			date = date + "0" + month;
		}
		else
		{
			date = date + month;
		}
		date = date + "-" + year;
		
		for(Invoice invoice: invoiceLst) 
		{
			netSales += invoice.getFinalPrice();
			invoiceIDLst.add(invoice.getInvoiceID());
		}
		SalesRevenue salesRevenue = new SalesRevenueDay(date, netSales, invoiceIDLst);
		return (SalesRevenueDay)salesRevenue;
	}
	
	public static SalesRevenueDay updateSalesRevenue(SalesRevenueDay salesRevenue, List<Invoice> invoiceLst) 
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
	public static void printSalesRevenue(SalesRevenueDay salesRevenue) throws IOException
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
		System.out.println("\t     (Daily)");
		System.out.println("\t    " + salesRevenue.getDate());
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