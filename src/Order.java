import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {
	
	private static final String ORDER_FILE = "Orders.txt";
	private static final String ITEM_FILE = "OutputMenu.txt";
	private static final String PROMOSET_FILE = "PromotionList.txt";
	private int orderId;
	private int tableId;
	private int staffId;
	private List<Integer> itemId;
	private List<Integer> promoSetId;
	private double price;
	private String comment;
	private LocalDateTime dateTime;
	
	public Order() {};
	
	public Order(int orderId, int tableId, int staffId, List<Integer> itemId, List<Integer> promoSetId, double price, String comment, LocalDateTime dateTime) {
		this.orderId = orderId;
		this.tableId = tableId;
		this.staffId = staffId;
		this.itemId = itemId;
		this.promoSetId = promoSetId;
		this.price = price;
		this.comment = comment;
		this.dateTime = dateTime;
	}
	
	public int getOrderId () {
		return this.orderId;
	}
	
	public int getTableId() {
		return this.tableId;
	}
	
	public int getStaffId () {
		return this.staffId;
	}
	
	public List<Integer> getItemId () {
		Collections.sort(this.itemId);
		return this.itemId;
	}
	
	public void setItemId (List<Integer> itemId) {
		this.itemId = itemId;
	}
	
	public List<Integer> getPromoSetId () {
		Collections.sort(this.promoSetId);
		return this.promoSetId;
	}
	
	public void setPromoSetId (List<Integer> promoSetId) {
		this.promoSetId = promoSetId;
	}
	
	public double getPrice () {
		return this.price;
	}
	
	public String getComment () {
		return this.comment;
	}
	
	public void setComment (String comment) {
		this.comment = comment;
	}
	
	public LocalDateTime getDateTime() {
		return this.dateTime;
	}
	
	public static int createOrder(Order od) throws IOException { //static??
		
		LocalDateTime dateTime = LocalDateTime.now();

		Order newOrder = new Order(0, od.tableId, od.staffId, od.itemId, od.promoSetId, 0, od.comment, dateTime);
		
		List<Menu> menuLst = MenuFunc.getMenu(ITEM_FILE);
		for(int id: newOrder.itemId) {
			for(Menu mn: menuLst) {
				if(id==mn.getFoodID()) {
					newOrder.price += (double)mn.getFoodPrice();
					break;
				}
			}
		}
		
		List<PromoSet> psLst = DBManager.readPromoSetInfo(PROMOSET_FILE);
		for(int id: newOrder.promoSetId) {
			for(PromoSet ps: psLst) {
				if(id==ps.getPromoSetId()) {
					newOrder.price += ps.getPrice();
					break;
				}
			}
		}
		
		List<Order> orderLst = DBManager.readOrders(ORDER_FILE);;
		if(orderLst.size()>0)
			newOrder.orderId = orderLst.get(orderLst.size()-1).getOrderId() + 1;
		else
			newOrder.orderId = 1001;
		orderLst.add(newOrder);
		
		DBManager.saveOrders(ORDER_FILE, orderLst);
		
		return newOrder.orderId;
	}
	
	public static void viewOrders() throws IOException{
		
		List<Order> orderLst = DBManager.readOrders(ORDER_FILE);
		List<Menu> menuLst = MenuFunc.getMenu(ITEM_FILE);
		List<PromoSet> psLst = DBManager.readPromoSetInfo(PROMOSET_FILE);
		List<String> tmpCmtLst = new ArrayList<String>();
		int sel, valid=0;
		
		Scanner sc = new Scanner(System.in);
		String tmpCmt;
		
		if(orderLst.size()>0) {
			System.out.println("List of Order: ");
			for(Order od: orderLst) {
				System.out.println(od.getOrderId());
			}
			
			System.out.print("\nEnter a order ID: ");
			sel = sc.nextInt();
			
			for(Order od: orderLst) {
				if(sel == od.getOrderId()) {
					System.out.println("\nOrder ID\t\t: " + od.getOrderId());
					System.out.println("Table No.\t\t: " + od.getTableId());
					System.out.println("Staff ID\t\t: " + od.getStaffId());
					List<Integer> odIt = od.getItemId();
					
					int multiplier = 1, j = 0, first = 0;
					for(int it: odIt) 
					{
						if(j != odIt.lastIndexOf(it))
						{
							multiplier++;
						}
						else 
						{
							for(Menu mn: menuLst)
							{
								if(it == mn.getFoodID()) 
								{
									if(first > 0) {
										System.out.println("\t\t\t  " + multiplier + "x\t" + odIt.get(j) + " " + mn.getFoodName());
									}
									else {
										System.out.println("Items\t\t\t: " + multiplier + "x\t" + odIt.get(j) + " " + mn.getFoodName());
										first++;
									}
									break;
								}
							}
							multiplier = 1;
						}
						j++;
					}
					
					/*for(int i = 0; i < odIt.size(); i++) {
						if(i == 0) {
							for(Menu mn: menuLst) {
								if(odIt.get(i)==mn.getFoodID()) {
									System.out.println("Items\t\t\t: " + odIt.get(i) + " " + mn.getFoodName());
									break;
								}
							}
						}
						else {
							for(Menu mn: menuLst) {
								if(odIt.get(i)==mn.getFoodID()) {
									System.out.println("\t\t\t  " + odIt.get(i) + " " + mn.getFoodName());
									break;
								}
							}
						}
					}*/
					
					multiplier = 1;
					j = 0;
					first = 0;
					
					List<Integer> odPs = od.getPromoSetId();
					for(int ps: odPs) 
					{
						if(j != odPs.lastIndexOf(ps))
						{
							multiplier++;
						}
						else 
						{
							for(PromoSet pst: psLst)
							{
								if(ps == pst.getPromoSetId()) 
								{
									if(first > 0) {
										System.out.println("\t\t\t  " + multiplier + "x\t" + odPs.get(j) + " " + pst.getName());
									}
									else {
										System.out.println("Promo Set\t\t: " + multiplier + "x\t" + odPs.get(j) + " " + pst.getName());
										first++;
									}
									break;
								}
							}
							multiplier = 1;
						}
						j++;
					}
					
					/*List<Integer> odPs = od.getPromoSetId();
					for(int i = 0; i < odPs.size(); i++) {
						if(i == 0) {
							for(PromoSet ps: psLst) {
								if(odPs.get(i)==ps.getPromoSetId()) {
									System.out.println("Promo Set\t\t: " + odPs.get(i) + " " + ps.getName());
									break;
								}
							}
						}
						else {
							for(PromoSet ps: psLst) {
								if(odPs.get(i)==ps.getPromoSetId()) {
									System.out.println("\t\t\t  " + odPs.get(i) + " " + ps.getName());
									break;
								}
							}
						}
					}*/
					System.out.println("Price\t\t\t: " + od.getPrice());
					tmpCmt=od.getComment();
					if(tmpCmt.contains("\\n")) {
						tmpCmtLst = Arrays.asList(tmpCmt.split("\\\\n"));
						for(int i = 0; i < tmpCmtLst.size(); i++) {
							if(i == 0) {
								System.out.println("Comment\t\t\t: " + tmpCmtLst.get(i));
							}
							else {
								System.out.println("\t\t\t  " + tmpCmtLst.get(i));
							}
						}
					}
					else
						System.out.println("Comment\t\t\t: " + tmpCmt);
					System.out.println("Date & Time\t\t: " + od.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					valid = 1;
					break;
				}
			}
			if(valid == 0) {
				System.out.println("Order ID " + sel + " not found.");
			}
		}
		else {
			System.out.println("The order list empty. ");
		}
		
	}

	public static List<Order> updateOrder(Order od) throws IOException{
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		od.price = 0;
		if(od.getItemId().size() == 0) {
			List<Integer> tmpItemLst = od.getItemId();
			tmpItemLst.add(0);
			od.setItemId(tmpItemLst);
		}
		else {
			List<Menu> menuLst = MenuFunc.getMenu(ITEM_FILE);
			for(int id: od.itemId) {
				for(Menu mn: menuLst) {
					if(id==mn.getFoodID()) {
						od.price += (double)mn.getFoodPrice();
						break;
					}
				}
			}
		}
		
		if(od.getPromoSetId().size() == 0) {
			List<Integer> tmpPsLst = od.getPromoSetId();
			tmpPsLst.add(0);
			od.setPromoSetId(tmpPsLst);
		}
		else {
			List<PromoSet> psLst = DBManager.readPromoSetInfo(PROMOSET_FILE);
			for(int id: od.promoSetId) {
				for(PromoSet ps: psLst) {
					if(id==ps.getPromoSetId()) {
						od.price += ps.getPrice();
						break;
					}
				}
			}
		}
		
		List<Order> orderLst = DBManager.readOrders(ORDER_FILE);
		
		for(Order var: orderLst) {
			if(od.orderId==var.getOrderId()) {
				var.staffId=od.staffId;
				var.itemId=od.itemId;
				var.promoSetId=od.promoSetId;
				var.price=od.price;
				var.comment=od.comment;
				var.dateTime=dateTime;
				
				break;
			}
		}
		
		DBManager.saveOrders(ORDER_FILE, orderLst);
		
		return orderLst;
	}
	
	public static Order getOrder(int orderId) throws IOException {
		
		List<Order> orderLst = DBManager.readOrders(ORDER_FILE);
		
		for(Order od: orderLst) {
			if(orderId == od.getOrderId()) {
				return od;
			}
		}
		
		return null;
	}
	
	public static int updateItemQuota (int itemId, int addDel) throws IOException {
		//addDel = 1 -- add item, quota -1
		//addDel = 0 -- delete item, quota +1
		
		return MenuFunc.updateItemQuota(itemId, addDel);
	}
	
	public static int updatePromoSetQuota (int promoSetId, int addDel) throws IOException {
		
		return DBManager.updatePromoSetQuota(promoSetId, addDel);
	}
}
