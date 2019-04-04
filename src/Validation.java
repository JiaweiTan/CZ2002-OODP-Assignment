import java.io.IOException;
import java.util.List;

public class Validation {
	
	private static final String MENU_FILE = "OutputMenu.txt";
	private static final String PROMOSET_FILE = "PromotionList.txt";
	private static final String STAFF_FILE = "StaffList.txt";

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
			if(staff.getEmployeeId() == staffId) {
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
}
