import java.util.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BillingApp {
	public static void main(String [] args) throws IOException, ParseException 
	{
		int choice = 0;
		int orderID;
		int invoiceID;
		double totalRevenue;
		boolean validOrderID, validDate, entryFound;
		String [] salesRevenueDate;
		int day, month, year;
		
		List<Invoice> invoiceLst = new ArrayList<Invoice>();
		List<Order> orderLst = new ArrayList<Order>();
		List<SalesRevenue> salesRevenueLst = new ArrayList<SalesRevenue>();
		
		Scanner sc = new Scanner(System.in);
		do 
		{
			System.out.println("========================================");
			System.out.println("\t      Billing Menu");
			System.out.println("========================================");
			System.out.println("1. Print Bill Invoice");
			System.out.println("2. Print Sales Revenue Report");
			System.out.println("0. Back");
			
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
							//Checks for valid OrderID
							do
							{
								System.out.print("\nEnter orderID: ");
								orderID = sc.nextInt();
								validOrderID = Validation.orderExistDB(orderID);
								if(!validOrderID) 
								{
									System.out.println("OrderID not found. Please try again.");
								}
							}while (!validOrderID);
							
							//Check if order has been billed before
							
							validOrderID = Validation.isBilled(orderID);
							if(!validOrderID)
							{
								System.out.println("Order has previously been billed.\n");
								System.out.println("========================================");
								System.out.println("\t     Reprinting Bill");
								System.out.println("========================================\n");
								invoiceLst = DBManager.readInvoice("Invoice.txt");
								for(Invoice inv: invoiceLst)
								{
									if(inv.getOrderID() == orderID)	
									{
										Invoice.printBillInvoice(Order.getOrder(orderID), inv);
										break;
									}
								}
								break;
							}

							Order order = new Order();
							order = Order.getOrder(orderID);
							Customer cst = new Customer();
							int memberId = 0;
							do {
								System.out.print("Membership ID (Enter 0 to skip): ");
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
									cst = Customer.getCustomer(memberId);
								}
							} while(memberId < 0);
							
							if(order.getPrice() >= 100 && memberId < 1) {
								System.out.print("Do you want to be a member? (Y/N)");
								String sel = sc.next();
								if(sel.equalsIgnoreCase("y")) {
									//create customer record
									boolean invalid = true;
									do {
										System.out.print("Enter Reservation Number (Enter 0 to skip): ");
										int resNo = sc.nextInt();
										sc.nextLine();
										if(resNo == 0) {
											//Ask for details
											cst.setID(0);
											System.out.print("Enter Name\t\t: ");
											String name = sc.nextLine();
											cst.setName(name);
											System.out.print("Enter Contact No.\t: ");
											String contact = sc.nextLine();
											cst.setContact(contact);
											cst.setExpiry(LocalDate.now().toString());
											System.out.println();
											System.out.println("=======================================================");
											System.out.println("\tMembership created successfully!");
											System.out.println("=======================================================");
											invalid = false;
										}
										else {
											//get details from reservation
											List<Reservation> rsLst = DBManager.readReservationInfo("Reservation.txt");
											for(Reservation rs: rsLst) {
												if(resNo == rs.getReservationId()) {
													cst.setID(0);
													cst.setName(rs.getName());
													cst.setContact(rs.getContactNumber());
													cst.setExpiry(LocalDate.now().toString());
													System.out.println("Name\t\t: " + cst.getName());
													System.out.println("Contact No.\t: " + cst.getContact());
													System.out.println();
													System.out.println("=======================================================");
													System.out.println("\tMembership created successfully!");
													System.out.println("=======================================================");
													invalid = false;
													break;
												}
											}
										}
										if(invalid) {
											System.out.println("Invalid Reservation ID.");
										}
									} while (invalid);
									int membershipId = DBManager.saveCustomerDetails(cst);
									System.out.println("\tMembership ID\t: " + membershipId);
									System.out.println("=======================================================");
									order.setIsMember(true);
								}
							}
							
							//Create new invoice and print
							Invoice invoice = Invoice.createInvoice(order, cst);
							Invoice.printBillInvoice(order, invoice);
							System.out.println("\n\n\n");
							break;
						}
				case 2: List<Invoice> tempInvoiceLst;
						do {
							System.out.println("Print Sales Revenue By:");
							System.out.println("1. Day");
							System.out.println("2. Month");
							System.out.println("3. Year");
							choice = sc.nextInt();
							switch(choice) 
							{
								case 1: 
										do 
										{
											System.out.println("Enter Year(YYYY)): ");
											year = sc.nextInt();
											System.out.println("Enter Month(MM): ");
											month = sc.nextInt();
											System.out.println("Enter Day(DD):");
											day = sc.nextInt();
											validDate = Validation.isSalesRevenueDayDateValid(year,month,day);
											if(!validDate)
											{
												System.out.println("Date entered not valid. Please try again.");
											}
										} while (!validDate);
										//Check the invoices of the same Date
										invoiceLst = DBManager.readInvoice("Invoice.txt");
										tempInvoiceLst = new ArrayList<Invoice>();
										for(Invoice invoice: invoiceLst) 
										{
											if(month == invoice.getDateTime().getMonthValue() && year == invoice.getDateTime().getYear() && day == invoice.getDateTime().getDayOfMonth())
												tempInvoiceLst.add(invoice);
										}
										//Exits loop if there are no invoices in the date
										if(tempInvoiceLst.size() <= 0)
										{
											System.out.println("\nThere were no sales in " + day + "-" + month + "-" + year + ".\n");
											break;
										} 
										else
										{
											//Check for existing entry in SalesRevenue
											salesRevenueLst = DBManager.readSalesRevenueDay("SalesRevenueDay.txt");
											SalesRevenue salesRevenue = new SalesRevenueDay();
											if(salesRevenueLst.size() > 0)
											{
												for(SalesRevenue salesRev: salesRevenueLst) 
												{
													SalesRevenueDay salesRevenueDay = (SalesRevenueDay) salesRev;
													salesRevenueDate = salesRevenueDay.getDate().split("-");
													if(Integer.parseInt(salesRevenueDate[0]) == day && Integer.parseInt(salesRevenueDate[1]) == month && Integer.parseInt(salesRevenueDate[2]) == year)
													{
														//updates the entry
														salesRevenue = SalesRevenueDay.updateSalesRevenue(salesRevenueDay, tempInvoiceLst);
														DBManager.saveSalesRevenueDay("SalesRevenueDay.txt", salesRevenueLst);
														entryFound = true;
														break;
													}								
												}
											}
											//creates a new SalesRevenue entry
											if(!entryFound)
											{
												salesRevenue = SalesRevenueDay.createSalesRevenue(tempInvoiceLst,day,month,year);
												salesRevenueLst.add(salesRevenue);
												DBManager.saveSalesRevenueDay("SalesRevenueDay.txt",salesRevenueLst);
											}
											SalesRevenueDay.printSalesRevenue((SalesRevenueDay)salesRevenue);
										}
										break;
								case 2:	do 
										{
											System.out.println("Enter Year(YYYY)): ");
											year = sc.nextInt();
											System.out.println("Enter Month(MM): ");
											month = sc.nextInt();
											validDate = Validation.isSalesRevenueMonthDateValid(year,month);
											if(!validDate)
											{
												System.out.println("Date entered not valid. Please try again.");
											}
										} while (!validDate);
										
										//Check the invoices of the same Date
										invoiceLst = DBManager.readInvoice("Invoice.txt");
										tempInvoiceLst = new ArrayList<Invoice>();
										for(Invoice invoice: invoiceLst) 
										{
											if(month == invoice.getDateTime().getMonthValue() && year == invoice.getDateTime().getYear())
												tempInvoiceLst.add(invoice);
										}
										//Exits loop if there are no invoices in the date
										if(tempInvoiceLst.size() <= 0)
										{
											System.out.println("\nThere were no sales in " + month + "-" + year + ".\n");
											break;
										} 
										else
										{
											//Check for existing entry in SalesRevenue
											salesRevenueLst = DBManager.readSalesRevenueMonth("SalesRevenueMonth.txt");
											SalesRevenue salesRevenue = new SalesRevenueMonth();
											if(salesRevenueLst.size() > 0)
											{
												for(SalesRevenue salesRev: salesRevenueLst) 
												{
													SalesRevenueMonth salesRevenueMonth = (SalesRevenueMonth)salesRev;
													salesRevenueDate = salesRevenueMonth.getDateYear().split("-");
													if(Integer.parseInt(salesRevenueDate[0]) == month && Integer.parseInt(salesRevenueDate[1]) == year)
													{
														//updates the entry
														salesRevenue = SalesRevenueMonth.updateSalesRevenue(salesRevenueMonth, tempInvoiceLst);
														DBManager.saveSalesRevenueMonth("SalesRevenueMonth.txt", salesRevenueLst);
														entryFound = true;
														break;
													}								
												}
											}
											//creates a new SalesRevenue entry
											if(!entryFound)
											{
												salesRevenue = SalesRevenueMonth.createSalesRevenue(tempInvoiceLst,month,year);
												salesRevenueLst.add(salesRevenue);
												DBManager.saveSalesRevenueMonth("SalesRevenueMonth.txt",salesRevenueLst);
											}
											SalesRevenueMonth.printSalesRevenue((SalesRevenueMonth)salesRevenue);
										}
									
										break;
								case 3: 
										do{
											System.out.println("Enter Year(YYYY)): ");
											year = sc.nextInt();
											validDate = Validation.isSalesRevenueYearDateValid(year);
											if(!validDate)
											{
												System.out.println("Date entered not valid. Please try again.");
											}
										} while (!validDate);
										invoiceLst = DBManager.readInvoice("Invoice.txt");
										tempInvoiceLst = new ArrayList<Invoice>();
										for(Invoice invoice: invoiceLst) 
										{
											if(year == invoice.getDateTime().getYear())
												tempInvoiceLst.add(invoice);
										}
										if(tempInvoiceLst.size() <= 0)
										{
											System.out.println("There were no sales in " + year);
											break;
										} else 
										{
											//Check for existing entry in SalesRevenue
											salesRevenueLst = DBManager.readSalesRevenueYear("SalesRevenueYear.txt");
											SalesRevenue salesRevenue = new SalesRevenueYear();
											if(salesRevenueLst.size() > 0)
											{
												int tempYear;
												for(SalesRevenue salesRev: salesRevenueLst) 
												{
													SalesRevenueYear salesRevenueYear = (SalesRevenueYear)salesRev;
													tempYear = salesRevenueYear.getYear();
													if(tempYear == year)
													{
														//updates the entry
														salesRevenue = SalesRevenueYear.updateSalesRevenue(salesRevenueYear, tempInvoiceLst);
														DBManager.saveSalesRevenueYear("SalesRevenueYear.txt", salesRevenueLst);
														entryFound = true;
														break;
													}								
												}
											}
											//creates a new SalesRevenue entry
											if(!entryFound)
											{
												salesRevenue = SalesRevenueYear.createSalesRevenue(tempInvoiceLst, year);
												salesRevenueLst.add(salesRevenue);
												DBManager.saveSalesRevenueYear("SalesRevenueYear.txt",salesRevenueLst);
											}
											SalesRevenueYear.printSalesRevenue((SalesRevenueYear)salesRevenue);
										}
										break;
								default: System.out.println("Invalid input. Please enter again.\n");
							} 
						}while(choice < 1 || choice > 3);
						
						
				case 0: break;
				default: System.out.println("Invalid input. Please enter again.\n");
			}
			
			
		}while (choice != 0);
		if(choice==0)
		{
			
			MainMenu.main(null);
		}
	}
}
