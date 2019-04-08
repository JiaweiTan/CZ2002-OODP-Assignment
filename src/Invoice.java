import java.util.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private String remarks;
	private LocalDateTime dateTime;
	
	
	public Invoice(int invoiceID, String paymentType, int orderID, double originalPrice, double finalPrice, double serviceCharge, double GST, double discount, String remarks,LocalDateTime dateTime) 
	{
		this.invoiceID = invoiceID;
		this.paymentType = paymentType;
		this.orderID = orderID;
		this.originalPrice = originalPrice;
		this.finalPrice = finalPrice;
		this.serviceCharge = serviceCharge;
		this.GST = GST;
		this.discount = discount;
		this.remarks = remarks;
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
	
	public String getRemarks() 
	{
		return remarks;
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
		String paymentType = "", remarks = "";
		int choice;
		
		do 
		{
			System.out.println();
			System.out.println("Select Payment Method:");
			System.out.println("1. Cash");
			System.out.println("2. Nets");
			System.out.println("3. Visa");
			System.out.println("4. MasterCard");
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
		
		invoiceID = Invoice.SystemGeneratedID("Invoice.txt");
		
		do 
		{
		System.out.println("Remarks with maximum 35 characters:");
		System.out.println("(Enter 0 to skip)");
		remarks = sc.next();
		if(remarks.length() > 35)
			System.out.println("Too many characters. Please try again.");
		} while(remarks.length() > 35);
		
		Invoice invoice = new Invoice(invoiceID, paymentType, order.getOrderId(),  order.getPrice(), finalPrice, serviceCharge, GST, discount, remarks ,dateTime);
		
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
		List<Invoice> invoiceLst = DBManager.readInvoice("Invoice.txt");
		invoiceLst.add(invoice);
		DBManager.saveInvoice("Invoice.txt", invoiceLst);
		if(customer.getExpiry() != null) {
			DBManager.saveCustomerDetails(customer);
		}
		
		return invoice;
	}
	public static int SystemGeneratedID(String filename) throws IOException
	{
		List<Invoice> invoiceLst = DBManager.readInvoice(filename);
		if(invoiceLst.size()>0)
			return invoiceLst.get(invoiceLst.size()-1).getInvoiceID() + 1;
		else
			return 9001;
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
		
		System.out.println("========================================");
		System.out.println("       4 Star Michelin Restaurant");
		System.out.println("========================================");
		System.out.println("   Invoice ID\t\t\t" + invoice.getInvoiceID());
		System.out.println("   Order ID\t\t\t" + invoice.getOrderID());
		System.out.println("   Table\t\t\t" + order.getTableId());
		System.out.println("   DateTime"+ invoice.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println("========================================");
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
		
		System.out.println("========================================");
		System.out.println("   SubTotal\t\t\t" + String.format("%.2f", (invoice.getOriginalPrice() + invoice.getDiscount())));
		if(order.getIsMember()) {
			//System.out.println("   SubTotal\t\t\t" + String.format("%.2f", invoice.getOriginalPrice() + invoice.getDiscount()));
			System.out.println("   Member Discount 5%\t\t-" + String.format("%.2f", invoice.getDiscount()));
		}
		System.out.println("   Service Charge 10%\t\t" + String.format("%.2f", invoice.getServiceCharge()));
		System.out.println("   GST 7%\t\t\t" + String.format("%.2f", invoice.getGST()));
		System.out.println("   Total\t\t\t" + String.format("%.2f", invoice.getFinalPrice()));
		System.out.println("   Payment By " + invoice.getPaymentType().toUpperCase());
		if(!invoice.getRemarks().equals("0"))
		{
			System.out.println(" Remarks:");
			System.out.println(" " + invoice.getRemarks());
		}
		System.out.println("========================================\n\n");
		
		
		/* Note: In order for this to work, you have to create the reservation using today date and time */
		// As this checks for today reservation and deletes it. 
		//Restaurant opening hours -> 11:00 to 1500 or 1800-2200
		//Change your own computer time to test if it's over the restaurant opening hours.
		Reservation res = new Reservation();
		boolean isReservation = false;
		//Get current date, session and tableid
		int tableid = order.getTableId();
		String orderdate = order.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String ordertime = order.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		ArrayList<Reservation> resList = new ArrayList<Reservation>();
		//Get all the reservations for this date and session
		//Find reservation by table id
		resList = res.getTodayReservation(orderdate, res.checkSession(ordertime));
		Reservation resv = new Reservation();
		for(Reservation resitem: resList)
		{
			if(resitem.getTableId() == tableid) {
				isReservation = true;
				resv = resitem;
			}
		}
		//If yes, delete reservation and release table space
		if(isReservation) {
			res.deleteReservation(resv);
			System.out.println("Table ID: " + resv.getTableId() + " released and Reservation ID: " + resv.getReservationId() + " deleted!");
		}
	}
}
