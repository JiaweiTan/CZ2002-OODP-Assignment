import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Validation {

	private static final String MENU_FILE = "OutputMenu.txt";
	private static final String PROMOSET_FILE = "PromotionList.txt";
	private static final String STAFF_FILE = "StaffList.txt";
	private static final String CUSTOMER_FILE = "CustomerList.txt";
	private static final String RESERVATION_FILE = "Reservation.txt";
	private static final String TABLEINFO_FILE = "TableInfo.txt";
	private static final String ORDER_FILE = "Orders.txt";
	private static final String INVOICE_FILE = "Invoice.txt";

	public static int itemExistsDB(int itemId) throws IOException {

		List<Menu> mnLst = MenuFunc.getMenu(MENU_FILE);
		for (Menu mn : mnLst) {
			if (mn.getFoodID() == itemId) {
				return 1;
			}
		}
		return -1;
	}

	public static int promoSetExistsDB(int promoSetId) throws IOException {

		List<PromoSet> psLst = DBManager.readPromoSetInfo(PROMOSET_FILE);
		for (PromoSet ps : psLst) {
			if (ps.getPromoSetId() == promoSetId) {
				return 1;
			}
		}
		return -1;
	}

	public static int staffExistsDB(int staffId) throws IOException {

		List<Staff> staffLst = DBManager.readStaffInfo(STAFF_FILE);
		for (Staff staff : staffLst) {
			if (staff.getID() == staffId) {
				return 1;
			}
		}
		return -1;
	}

	public static int tableExistsDB(int tableId) {
		if (tableId < 1 || tableId > 30) {
			return -1;
		}

		return 1;
	}

	public static int customerExistsDB(int customerId) throws IOException {
		List<Customer> customerLst = DBManager.readCustomerInfo(CUSTOMER_FILE);
		for (Customer cs : customerLst) {
			if (cs.getID() == customerId) {
				if (LocalDate.now().isAfter(LocalDate.parse(cs.getExpiry()))) {
					return 0;
				}
				return 1;
			}
		}
		return -1;
	}

	public static int reservationExistsDB(String contact) throws IOException {
		List<Reservation> reservationLst = DBManager.readReservationInfo(RESERVATION_FILE);
		for (Reservation res : reservationLst) {
			if ((res.getContactNumber()).equals(contact)) {
				return 1;
			}
		}
		return 0;
	}

	public static boolean dateRange(String resdate) {
		// Reservation can only be made at most 1 month in advance
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date today = new Date();
		LocalDate currentDate = LocalDate.now().minusDays(1);
		LocalDate futureDate = LocalDate.now().plusMonths(1);
		LocalDate resDate = LocalDate.parse(resdate);
		if (currentDate.isBefore(resDate) && resDate.isBefore(futureDate)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDateValid(String dateToValidate) {
		if (dateToValidate == null) {
			return false;
		}
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);

		try {
			Date date = dateFormat.parse(dateToValidate);
			// System.out.println(date);
		} catch (ParseException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean orderExistDB (int orderId) throws IOException
	{
		List<Order> orderLst = DBManager.readOrders(ORDER_FILE);
		for(Order ord: orderLst)
		{
			if(ord.getOrderId() == orderId)
				return true;
		}
		return false;
	}
	
	public static boolean isBilled(int orderId) throws IOException
	{
		List<Invoice >invoiceLst = DBManager.readInvoice("Invoice.txt");
		for(Invoice inv: invoiceLst) 
		{
			if(inv.getOrderID() == orderId)
			{
				return false;
			}
		}
		return true;
	}
	public static boolean isSalesRevenueDateValid(int year, int month) {
		LocalDateTime date = LocalDateTime.now();
		if(year > date.getYear() || (year == date.getYear() && month > date.getMonthValue()) || year <= 0 || month <= 0) 
			return false;
		else
			return true;
	}
}
