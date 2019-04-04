import java.util.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
	
	private int invoiceID;
	private String paymentType;
	private int orderID;
	private double originalPrice;
	private double finalPrice;
	private double serviceCharge;
	private double GST;
	private double discount;
	private LocalDateTime dateTime;
	
	
	public Invoice(int invoiceID, String paymentType, int orderID, double originalPrice, double finalPrice, double serviceCharge, double GST, double discount, LocalDateTime dateTime) 
	{
		this.invoiceID = invoiceID;
		this.paymentType = paymentType;
		this.orderID = orderID;
		this.originalPrice = originalPrice;
		this.finalPrice = finalPrice;
		this.serviceCharge = serviceCharge;
		this.GST = GST;
		this.discount = discount;
		this.dateTime = dateTime;
	}
	
	public int getInvoiceID()
	{
		return this.invoiceID;
	}
	
	public String getPaymentType() 
	{
		return this.paymentType;
	}
	
	public int getOrderID() 
	{
		return orderID;
	}

	public double getOriginalPrice() 
	{
		return originalPrice;
	}

	public double getFinalPrice() 
	{
		return finalPrice;
	}
	
	public double getServiceCharge() 
	{
		return serviceCharge;
	}

	public double getGST() {
		return GST;
	}

	public LocalDateTime getDateTime() 
	{
		return dateTime;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public static Invoice createInvoice(Order order, Customer customer) throws IOException 
	{ 
		Scanner sc = new Scanner(System.in);
		int invoiceID;
		double GST = 0, serviceCharge = 0, finalPrice = 0, discount = 0;
		String paymentType = "";
		int choice;
		
		do 
		{
			System.out.println();
			System.out.println("Select Payment Method:");
			System.out.println("1. Cash");
			System.out.println("2. Nets");
			System.out.println("3. Visa");
			System.out.println("4. Credit Card");
			choice = sc.nextInt();
			switch(choice)
			{
				case 1: paymentType = "Cash";
						break;
				case 2:	paymentType = "Nets";
						break;
				case 3:	paymentType = "Visa";
						break;
				case 4: paymentType = "MasterCard";
						break;
				default: System.out.println("Invalid input. Please try again.");
			}
		} while (choice < 1 || choice > 4);
		
		if(order.getIsMember()) {
			discount = computerDiscount(order.getPrice());
			order.setPrice(order.getPrice() - discount);
		}
		serviceCharge = computeServiceCharge(order.getPrice());
		GST = computeGST(order.getPrice(), serviceCharge);
		finalPrice = order.getPrice() + GST + serviceCharge;
		LocalDateTime dateTime = LocalDateTime.now();
		
		List<Invoice> invoiceLst = DBManager.readInvoice("Invoice.txt");
		if(invoiceLst.size()>0)
			invoiceID = invoiceLst.get(invoiceLst.size()-1).getInvoiceID() + 1;
		else
			invoiceID = 10001;
		
		Invoice invoice = new Invoice(invoiceID, paymentType, order.getOrderId(),  order.getPrice(), finalPrice, serviceCharge, GST, discount, dateTime);
		
		if(customer.getInvoiceId() == null) {
			List<Integer> invLst = new ArrayList<Integer>();
			invLst.add(invoiceID);
			customer.setInvoiceId(invLst);
		}
		else {
			List<Integer> invLst = customer.getInvoiceId();
			invLst.add(invoiceID);
			customer.setInvoiceId(invLst);
		}
		invoiceLst.add(invoice);
		DBManager.saveInvoice("Invoice.txt", invoiceLst);
		if(customer.getExpiry() != null) {
			DBManager.saveCustomerDetails(customer);
		}
		
		return invoice;
	}
	
	public static double computerDiscount(double originalPrice) 
	{
		return originalPrice * 0.05;
	}
	
	public static double computeServiceCharge(double originalPrice) 
	{
		return originalPrice * 0.10;
	}
	
	public static double computeGST(double originalPrice, double serviceCharge) 
	{
		return (originalPrice + serviceCharge) * 0.07;
	}
	
	public static void printBillInvoice(Order order, Invoice invoice) throws IOException 
	{
		List<PromoSet> promoLst = DBManager.readPromoSetInfo("PromotionList.txt");
		List<Menu> menuLst = MenuFunc.getMenu("OutputMenu.txt");
		
		System.out.println("\t\t  RRPSS");
		System.out.println("----------------------------------------");
		System.out.println("\t\t\t\t"+ invoice.getInvoiceID());
		System.out.println("   "+ invoice.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\t\t"+ invoice.getOrderID());
		System.out.println("----------------------------------------");
		System.out.println(" Qty\tItem\n");
		List<Integer> itemLst = order.getItemId();
		Collections.sort(itemLst);
		int multiplier = 1;
		int price = 0;
		boolean print;
		int i = 0;
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
		
		List<Integer> promoItemLst = order.getPromoSetId();
		Collections.sort(promoItemLst);
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
		System.out.println("   SubTotal\t\t\t" + String.format("%.2f", (invoice.getOriginalPrice() + invoice.getDiscount())));
		if(order.getIsMember()) {
			//System.out.println("   SubTotal\t\t\t" + String.format("%.2f", invoice.getOriginalPrice() + invoice.getDiscount()));
			System.out.println("   Member Discount 5%\t\t-" + String.format("%.2f", invoice.getDiscount()));
		}
		System.out.println("   Service Charge 10%\t\t" + String.format("%.2f", invoice.getServiceCharge()));
		System.out.println("   GST 7%\t\t\t" + String.format("%.2f", invoice.getGST()));
		System.out.println("   Total\t\t\t" + String.format("%.2f", invoice.getFinalPrice()));
		System.out.println("   Payment By " + invoice.getPaymentType().toUpperCase());
		System.out.println("----------------------------------------");
		
	}
}
