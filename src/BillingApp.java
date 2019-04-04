import java.util.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BillingApp {
	public static void main(String [] args) throws IOException 
	{
		int choice = 0;
		int orderID;
		int invoiceID;
		double totalRevenue;
		boolean validOrderID, validDate, entryFound;
		String [] salesRevenueDate;
		
		LocalDateTime date;
		int month, year;
		
		List<Invoice> invoiceLst = new ArrayList<Invoice>();
		List<Order> orderLst = new ArrayList<Order>();
		List<SalesRevenue> salesRevenueLst = new ArrayList<SalesRevenue>();
		
		Scanner sc = new Scanner(System.in);
		do 
		{
			System.out.println("  Billing");
			System.out.println("  ----------------");
			System.out.println("1: Print bill invoice");
			System.out.println("2: Print sale revenue report");
			System.out.println("3: Exit");
			
			validOrderID = false;
			validDate = false;
			entryFound = false;
			choice = sc.nextInt();
			
			switch(choice) 
			{
				case 1: 
						orderLst = DBManager.readOrders("Orders.txt");
						//Check if there are any orders
						if(orderLst.size() <= 0)
						{
							System.out.println("There are no orders to bill.");
							break;
						}else
						{
							//List of Orders
							System.out.println("List of Order: ");
							for(Order od: orderLst) 
							{
								System.out.println(od.getOrderId());	
							}
							//Checks for valid ID
							do
							{
								System.out.println("Enter orderID: ");
								orderID = sc.nextInt();
								for(Order od: orderLst) 
								{
									if(od.getOrderId() == orderID)
									{
										validOrderID = true;
										break;
									}
								}
								if(!validOrderID) 
								{
									System.out.println("OrderID not found. Please try again.");
								}
							}while (!validOrderID);
							
							//Check if order has been billed before
							invoiceLst = DBManager.readInvoice("Invoice.txt");
							for(Invoice inv: invoiceLst) 
							{
								if(inv.getOrderID() == orderID)
								{
									validOrderID = false;
									break;
								}
							}
							if(!validOrderID)
							{
								System.out.println("Order has previously been billed.\n");
								break;
							}

							Order order = new Order();
							order = Order.getOrder(orderID);
							Customer cst = new Customer();
							int memberId = 0;
							do {
								System.out.println("Membership ID (Enter 0 to skip): ");
								memberId = sc.nextInt();
								if(memberId == 0)
									break;
								//Check if expired
								int memStatus = Validation.customerExistsDB(memberId);
								if(memStatus < 0) {
									System.out.println("Invalid Membership ID. Please enter a valid ID.");
									memberId = -1;
								}
								else if(memStatus == 0) {
									if(order.getPrice() >= 100) {
										List<Customer> csLst = DBManager.readCustomerInfo("CustomerList.txt");
										for(Customer cs: csLst) {
											if(cs.getID() == memberId) {
												cst = cs;
												cst.setExpiry(LocalDate.now().plusYears(1).toString());
												order.setIsMember(true);
												DBManager.saveCustomerDetails(cst);
											}
										}
									}
									else {
										System.out.println("The membership has expired. Please spend above $100 to renew membership.");
										break;
									}
								}
								else {
									order.setIsMember(true);
								}
							} while(memberId < 0);
							
							if(order.getPrice() >= 100 && memberId < 1) {
								System.out.println("Do you want to be a member? (Y/N)");
								String sel = sc.next();
								if(sel.equalsIgnoreCase("y")) {
									//create customer record
									boolean invalid = true;
									do {
										System.out.println("Enter Reservation Number (Enter 0 to skip): ");
										int resNo = sc.nextInt();
										if(resNo == 0) {
											//Ask for details
											cst.setID(0);
											System.out.print("Enter Name\t: ");
											sc.next();
											cst.setName(sc.next());
											System.out.print("Enter Contact No.\t: ");
											cst.setContact(sc.next());
											cst.setExpiry(LocalDate.now().toString());
											invalid = false;
										}
										else {
											//get details from reservation
											List<Reservation> rsLst = DBManager.readReservationInfo("Reservation.txt");
											for(Reservation rs: rsLst) {
												if(resNo == rs.getReservationId()) {
													cst.setID(0);
													cst.setName(rs.getName());
													cst.setContact(Integer.toString(rs.getContactNumber()));
													cst.setExpiry(LocalDate.now().toString());
													invalid = false;
													break;
												}
											}
										}
										if(invalid) {
											System.out.println("Invalid Reservation ID.");
										}
									} while (invalid);
									DBManager.saveCustomerDetails(cst);
									order.setIsMember(true);
								}
							}
							
							//Create new invoice and print
							Invoice invoice = Invoice.createInvoice(order);
							Invoice.printBillInvoice(order, invoice);
							System.out.println("\nPayment Success!\n");
							break;
						}
				case 2: /*List<SalesRevenue> salesRevenueLst = DBManager.readSalesRevenue("src/SalesRevenue");
						for(SalesRevenue salesrevenue: salesRevenueLst)
						{
							System.out.println(salesrevenue.getDateYear());
							System.out.println(salesrevenue.getTotalRevenue());
							System.out.println(salesrevenue.getInvoiceID());
						}
						List<Integer> invoiceId = new ArrayList<Integer>();
						invoiceId.add(10003);
						invoiceId.add(10004);
						SalesRevenue salesRevenue = new SalesRevenue("022019", (double)20000, invoiceId);
						salesRevenueLst.add(salesRevenue);*/
						//Check for valid date (i.e. date <= currentDate)
						do 
						{
							System.out.println("Enter Year(YYYY)): ");
							year = sc.nextInt();
							System.out.println("Enter Month(MM): ");
							month = sc.nextInt();
							date = LocalDateTime.now();
							if(year > date.getYear() || (year == date.getYear() && month > date.getMonthValue()) || year <= 0 || month <= 0) 
								validDate = false;
							else
								validDate = true;
							if(!validDate)
							{
								System.out.println("Date entered not valid. Please try again.");
							}
						} while (!validDate);
						
						//Check the invoices of the same Date
						invoiceLst = DBManager.readInvoice("Invoice.txt");
						List<Invoice> tempInvoiceLst = new ArrayList<Invoice>();
						for(Invoice invoice: invoiceLst) 
						{
							if(month == invoice.getDateTime().getMonthValue() && year == invoice.getDateTime().getYear())
								tempInvoiceLst.add(invoice);
						}
						//Exits loop if there are no invoices in the date
						if(tempInvoiceLst.size() <= 0)
						{
							System.out.println("There were no sales in " + month + "-" + year + ".");
							break;
						} 
						else
						{
							//Check for existing entry in SalesRevenue
							salesRevenueLst = DBManager.readSalesRevenue("SalesRevenue.txt");
							SalesRevenue salesRevenue = new SalesRevenue();
							if(salesRevenueLst.size() > 0)
							{
								for(SalesRevenue salesRev: salesRevenueLst) 
								{
									salesRevenueDate = salesRev.getDateYear().split("-");
									if(Integer.parseInt(salesRevenueDate[0]) == month && Integer.parseInt(salesRevenueDate[1]) == year)
									{
										//updates the entry
										salesRevenue = SalesRevenue.updateSalesRevenue(salesRev, tempInvoiceLst);
										DBManager.saveSalesRevenue("SalesRevenue.txt", salesRevenueLst);
										entryFound = true;
										break;
									}								
								}
							}
							//creates a new SalesRevenue entry
							if(!entryFound)
							{
								salesRevenue = SalesRevenue.createSalesRevenue(tempInvoiceLst,month,year);
								salesRevenueLst.add(salesRevenue);
								DBManager.saveSalesRevenue("SalesRevenue.txt",salesRevenueLst);
							}
							SalesRevenue.printSalesRevenue(salesRevenue);
						}
						break;
						/*List<SalesRevenue> salesRevenueLst = DBManager.readSalesRevenue("src/SalesRevenue.txt");
						
						if(salesRevenueLst.size() <= 0)
						{
							System.out.println("There are no orders to bill.");
							break;
						}
						break;*/
						
				case 3: 
					break;	
			}
			
			
		}while (choice < 3);
	}
}
