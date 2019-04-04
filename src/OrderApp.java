import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import org.apache.commons.lang3.ArrayUtils;

public class OrderApp {
	
	public static void createOrder() throws IOException {
		
		int tableId, staffId, it, ps, index = 1;
		String comment = null, cmt;
		List<Integer> itemLst = new ArrayList<>();
		List<Integer> psLst = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("=============================");
		System.out.println("\tCreating Order");
		System.out.println("=============================");
		System.out.print("Enter Table No.: ");
		tableId = sc.nextInt();
		if (Validation.tableExistsDB(tableId) < 0) {
			do {
				System.out.print("Table No. is invalid. Please enter a valid ID.");
				System.out.println();
				System.out.print("Enter Table No.: ");
				tableId = sc.nextInt();
			} while (Validation.tableExistsDB(tableId) < 0);
		}
		System.out.print("Enter Staff ID: ");
		staffId = sc.nextInt();
		if ((Validation.staffExistsDB(staffId) < 0)) {
			do {
				System.out.print("Table No. is invalid. Please enter a valid ID.");
				System.out.println();
				System.out.print("Enter Staff ID: ");
				staffId = sc.nextInt();
			} while (Validation.staffExistsDB(staffId) < 0);
		}
		
		index = 1;
		do {
			// Can display out all items??
			System.out.print("Enter ID for Item " + index +" (Enter 0 to move next): ");
			it = sc.nextInt();
			if (it > 0) {
				if(Order.updateItemQuota(it, 1) < 0) {
					System.out.println("Item is sold out. Please order another item.");
				}
				else if(Validation.itemExistsDB(it) < 0) {
					System.out.println("Invalid item ID. Please enter a valid ID.");
				}
				else {
					itemLst.add(it);
					index++;
				}
			}
		} while (it > 0);
		if(itemLst.size()==0) {
			itemLst.add(0);
		}
		index=1;
		do {
			// Can display out all PromoSets??
			System.out.print("Enter ID for PromoSet " + index +" (Enter 0 to move next): ");
			ps = sc.nextInt();
			if (ps > 0) 
				if(Order.updatePromoSetQuota(ps, 1) < 0) {
					System.out.println("Promo Set is sold out. Please order another Promo Set.");
				}
				else if(Validation.promoSetExistsDB(ps) < 0) {
					System.out.println("Invalid Promo Set ID. Please enter a valid ID.");
				}
				else {
					psLst.add(ps);
					index++;
				}
		} while (ps > 0);
		if(psLst.size()==0)
			psLst.add(0);
		
		do {
			System.out.print("Any comment (Y/N): ");
			cmt = sc.next();
			if(cmt.equals("Y") || cmt.equals("y")) {
				System.out.print("Enter comment : ");
				comment = sc.next();
			}
			else if(cmt.equals("N") || cmt.equals("n")) {
				comment = "No comment";
			}
			else
				cmt = "invalid";
		}while(cmt.equals("invalid"));
		//itemLst.stream().mapToInt(i->i).toArray()
		Order newOrder = new Order(0, tableId, staffId, itemLst, psLst, 0, comment, LocalDateTime.now());
		System.out.println("Order created successfully. Order ID: " + Order.createOrder(newOrder));
	}
	
	public static void updateOrder() throws IOException {
		
		int sel, staffId, orderId, it, ps, index = 1, valid = 0;
		String comment = null, cmt;
		List<Integer> itemLst = new ArrayList<>();
		List<Integer> psLst = new ArrayList<>();
		Order od = new Order();
		
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println();
			System.out.println();
			System.out.println("=============================");
			System.out.println("\t Order Menu");
			System.out.println("=============================");
			System.out.println("1) View Order");
			System.out.println("2) Add Items to Order");
			System.out.println("3) Delete Items from order");
			System.out.println("0) Back");
			System.out.print("\nEnter your choice: ");
			sel = sc.nextInt();
			
			switch(sel) {
				case 0:
					break;
				case 1:
					System.out.println();
					System.out.println("=============================");
					System.out.println("\tViewing Order");
					System.out.println("=============================");
					Order.viewOrders();
					break;
				case 2:
					System.out.println();
					System.out.println("======================================");
					System.out.println("\tUpdating Items to Order");
					System.out.println("======================================");
					System.out.print("Enter Order ID: ");
					orderId = sc.nextInt();
					od = Order.getOrder(orderId);
					if(od == null) {
						System.out.println("The Order ID " + orderId + " is invalid.");
						break;
					}
					System.out.print("Enter Staff ID: "); //Validation??
					staffId = sc.nextInt();
					if ((Validation.staffExistsDB(staffId) < 0)) {
						do {
							System.out.print("Table No. is invalid. Please enter a valid ID.");
							System.out.println();
							System.out.print("Enter Staff ID: ");
							staffId = sc.nextInt();
						} while (Validation.staffExistsDB(staffId) < 0);
					}
					int addSel;
					index = 1;
					do {
						System.out.println();
						System.out.println("Choose an item to update: ");
						System.out.println("1) Add Item");
						System.out.println("2) Add Promo Set");
						System.out.println("3) Add Comment");
						System.out.println("0) Back");
						System.out.print("\nEnter your choice: ");
						addSel = sc.nextInt();
						sc.nextLine();
						switch(addSel) {
							case 0: 
								break;
							case 1:
								index = 1;
								do {
									// Can display out all items??
									System.out.print("Enter ID for Item " + index +" (Enter 0 to move next): ");
									it = sc.nextInt();
									if (it > 0) 
										if(Order.updateItemQuota(it, 1) < 0) {
											System.out.println("Item is sold out. Please order another item.");
										}
										else if(Validation.itemExistsDB(it) < 0) {
											System.out.println("Invalid item ID. Please enter a valid ID.");
										}
										else {
											itemLst.add(it);
											index++;
										}
									index++;
								} while (it > 0);
								if(itemLst.size() > 0) {
									List<Integer> tmpItemLst = new ArrayList<Integer>(od.getItemId());
									if(od.getItemId().get(0) == 0) {
										tmpItemLst.remove(0);
									}
									od.setItemId(Stream.concat(tmpItemLst.stream(), itemLst.stream()).collect(Collectors.toList()));
								}
								Order.updateOrder(od);
								System.out.println("Item(s) added to order successfully.");
								break;
							case 2:
								index = 1;
								do {
									// Can display out all items??
									System.out.print("Enter ID for Promo Set " + index +" (Enter 0 to move next): ");
									ps = sc.nextInt();
									if (ps > 0) 
										if(Order.updatePromoSetQuota(ps, 1) < 0) {
											System.out.println("Promo Set is sold out. Please order another Promo Set.");
										}
										else if(Validation.promoSetExistsDB(ps) < 0) {
											System.out.println("Invalid Promo Set ID. Please enter a valid ID.");
										}
										else {
											psLst.add(ps);
											index++;
										}
									index++;
								} while (ps > 0);
								if(psLst.size() > 0) {
									List<Integer> tmpPsLst = new ArrayList<Integer>(od.getPromoSetId());
									if(od.getPromoSetId().get(0) == 0) {
										tmpPsLst.remove(0);
									}
									od.setPromoSetId(Stream.concat(tmpPsLst.stream(), psLst.stream()).collect(Collectors.toList()));
								}
								Order.updateOrder(od);
								System.out.println("Promo Set(s) added to order successfully.");
								break;
							case 3:
								System.out.print("Enter comment to update: ");
								comment = sc.nextLine();
								if(od.getComment().trim().equalsIgnoreCase("No comment"))
									od.setComment(comment);
								else
									od.setComment(od.getComment() + "\\n" + comment);
								Order.updateOrder(od);
								System.out.println("Comment added to order successfully.");
								break;
							default:
								System.out.println("Invalid input. Please enter again. ");
								break;
						}
					} while (addSel > 0);
					break;
				case 3: 
					System.out.println();
					System.out.println("=========================================");
					System.out.println("\tDeleting Items from Order");
					System.out.println("=========================================");
					System.out.print("Enter Order ID: ");
					orderId = sc.nextInt();
					od = Order.getOrder(orderId);
					if(od == null) {
						System.out.println("The Order ID " + orderId + " is invalid.");
						break;
					}
					System.out.print("Enter Staff ID: "); //Validation??
					staffId = sc.nextInt();
					if ((Validation.staffExistsDB(staffId) < 0)) {
						do {
							System.out.print("Table No. is invalid. Please enter a valid ID.");
							System.out.println();
							System.out.print("Enter Staff ID: ");
							staffId = sc.nextInt();
						} while (Validation.staffExistsDB(staffId) < 0);
					}
					int delSel;
					index = 1;
					do {
						System.out.println();
						System.out.println("Choose an item to remove: ");
						System.out.println("1) Remove Item");
						System.out.println("2) Remove Promo Set");
						System.out.println("3) Remove Comment");
						System.out.println("0) Back");
						System.out.print("\nEnter your choice: ");
						delSel = sc.nextInt();
						sc.nextLine();
						switch(delSel) {
							case 0:
								break;
							case 1:
								if(od.getItemId().size() == 1 && od.getItemId().get(0) == 0) {
									System.out.println("No items in this order. ");
									break;
								}
								else {
									System.out.println("Items in order: ");
									for(int var: od.getItemId()) {
										System.out.println(var);
									}
									System.out.println("Select an item to remove: ");
									it = sc.nextInt();
									itemLst = od.getItemId();
									valid = 0;
									for(int i = 0; i < itemLst.size(); i++) {
										if(it == itemLst.get(i)) {
											Order.updateItemQuota(it, 0);
											itemLst.remove(i);
											valid = 1;
											break;
										}
									}
									if(valid == 0) {
										System.out.println("Item ID " + it + " not found in the order.");
									}
									else
										System.out.println("Item ID " + it + " is removed from the order.");
								}
								od.setItemId(itemLst);
								Order.updateOrder(od);
								break;
							case 2:
								if(od.getPromoSetId().size() == 1 && od.getPromoSetId().get(0) == 0) {
									System.out.println("No promo set in this order. ");
									break;
								}
								else {
									System.out.println("Promo Sets in order: ");
									for(int var: od.getPromoSetId()) {
										System.out.println(var);
									}
									System.out.println("Select a promo set to remove: ");
									ps = sc.nextInt();
									psLst = od.getPromoSetId();
									valid = 0;
									for(int i = 0; i < psLst.size(); i++) {
										if(ps == psLst.get(i)) {
											Order.updatePromoSetQuota(ps, 0);
											psLst.remove(i);
											valid = 1;
											break;
										}
									}
									if(valid == 0) {
										System.out.println("Promo Set ID " + ps + " not found in the order.");
									}
									else
										System.out.println("Promo Set ID " + ps + " is removed from the order.");
								}
								od.setPromoSetId(psLst);
								Order.updateOrder(od);
								break;
							case 3:
								System.out.println("Comment is removed from the order. ");
								od.setComment("No comment");
								Order.updateOrder(od);
								break;
							default:
								System.out.println("Invalid input. Please enter again. ");
								break;
						}
					} while (delSel > 0);
					break;
				default:
					System.out.println("Invalid input. Please enter again. ");
					break;
			}
		} while (sel > 0);
		
	}
}
