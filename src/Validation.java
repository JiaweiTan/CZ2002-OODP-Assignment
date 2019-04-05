import java.io.IOException;
import java.time.LocalDate;
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

	public static int itemExistsDB (int itemId) throws IOException {
		
		List<Menu> mnLst = MenuFunc.getMenu(MENU_FILE);
		for(Menu mn: mnLst) {
			if(mn.getFoodID() == itemId) {
				return 1;
			}
		}
		return -1;
	}
	
	public static int promoSetExistsDB (int promoSetId) throws IOException {
		
		List<PromoSet> psLst = DBManager.readPromoSetInfo(PROMOSET_FILE);
		for(PromoSet ps: psLst) {
			if(ps.getPromoSetId() == promoSetId) {
				return 1;
			}
		}
		return -1;
	}
	
	public static int staffExistsDB (int staffId) throws IOException {
		
		List<Staff> staffLst = DBManager.readStaffInfo(STAFF_FILE);
		for(Staff staff: staffLst) {
			if(staff.getID() == staffId) {
				return 1;
			}
		}
		return -1;
	}
	
	public static int tableExistsDB (int tableId) {
		if(tableId < 1 || tableId > 30) {
			return -1;
		}
		
		return 1;
	}
	
	public static int customerExistsDB (int customerId) throws IOException {
		List<Customer> customerLst = DBManager.readCustomerInfo(CUSTOMER_FILE);
		for(Customer cs: customerLst) {
			if(cs.getID() == customerId) {
				if(LocalDate.now().isAfter(LocalDate.parse(cs.getExpiry()))) {
					return 0;
				}
				return 1;
			}
		}
		return -1;
	}
	
	public static boolean isDateValid(String dateToValidate){
		
		if(dateToValidate == null){
			return false;
		}
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		
		try {
			
			Date date = dateFormat.parse(dateToValidate);
			//System.out.println(date);
		
		} catch (ParseException e) {
			
			//e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
