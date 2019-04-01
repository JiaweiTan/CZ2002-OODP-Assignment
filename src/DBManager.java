import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DBManager {
	public static final String SEPARATOR = "|";

	/** Read the contents of the given file. */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	public static ArrayList<TableInfo> readTableInfo(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) read(filename);

		ArrayList<TableInfo> alr = new ArrayList<TableInfo>();

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			String Date = star.nextToken().trim();
			String Session = star.nextToken().trim();
			int TenSeater = Integer.parseInt(star.nextToken().trim());
			int EightSeater = Integer.parseInt(star.nextToken().trim());
			int FourSeater = Integer.parseInt(star.nextToken().trim());
			int TwoSeater = Integer.parseInt(star.nextToken().trim());
			int TotalCount = Integer.parseInt(star.nextToken().trim());
			TableInfo tbInfo = new TableInfo(Date, Session, TenSeater, EightSeater, FourSeater, TwoSeater, TotalCount);

			alr.add(tbInfo);
		}
		return alr;
	}

	public static void saveNewTableSession(TableInfo tbl) throws IOException {
		String filename = "src/TableInfo.txt";
		ArrayList<TableInfo> tbSession = new ArrayList<TableInfo>();
		tbSession = readTableInfo(filename);
		tbSession.add(tbl);

		List alw = new ArrayList();

		for (int i = 0; i < tbSession.size(); i++) {
			TableInfo tbItem = (TableInfo) tbSession.get(i);
			StringBuilder st = new StringBuilder();
			st.append(tbItem.getDate().trim());
			st.append(SEPARATOR);
			st.append(tbItem.getSession().trim());
			st.append(SEPARATOR);
			st.append(tbItem.getTenSeater());
			st.append(SEPARATOR);
			st.append(tbItem.getEightSeater());
			st.append(SEPARATOR);
			st.append(tbItem.getFourSeater());
			st.append(SEPARATOR);
			st.append(tbItem.getTwoSeater());
			st.append(SEPARATOR);
			st.append(tbItem.getTotalCount());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	public static void UpdateTableSessionCount(TableInfo tblItem) throws IOException {
		String filename = "src/TableInfo.txt";
		List alw = new ArrayList();
		ArrayList<TableInfo> tbRec = new ArrayList<TableInfo>();
		tbRec = readTableInfo(filename);

		TableInfo updatedtbl = new TableInfo();
		for (TableInfo tbl : tbRec) {
			if ((tbl.getDate()).equals(tblItem.getDate()) && (tbl.getSession()).equals(tblItem.getSession())) {
				updatedtbl = tblItem;
			} else {
				updatedtbl = tbl;
			}
			StringBuilder st = new StringBuilder();

			st.append(updatedtbl.getDate().trim());
			st.append(SEPARATOR);
			st.append(updatedtbl.getSession().trim());
			st.append(SEPARATOR);
			st.append(updatedtbl.getTenSeater());
			st.append(SEPARATOR);
			st.append(updatedtbl.getEightSeater());
			st.append(SEPARATOR);
			st.append(updatedtbl.getFourSeater());
			st.append(SEPARATOR);
			st.append(updatedtbl.getTwoSeater());
			st.append(SEPARATOR);
			st.append(updatedtbl.getTotalCount());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	//////////////////////////////////////////////////////////////// Reservation
	//////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////

	public static ArrayList<Reservation> readReservationInfo(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) read(filename);

		ArrayList<Reservation> alr = new ArrayList<Reservation>();

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			int TableId = Integer.parseInt(star.nextToken().trim());
			String Date = star.nextToken().trim();
			String ArrivalTime = star.nextToken().trim();
			int Pax = Integer.parseInt(star.nextToken().trim());
			String Name = star.nextToken().trim();
			int ContactNumber = Integer.parseInt(star.nextToken().trim());

			Reservation resInfo = new Reservation(TableId, Date, ArrivalTime, Pax, Name, ContactNumber);

			alr.add(resInfo);
		}
		return alr;
	}

	public static void saveNewReservation(Reservation res) throws IOException {
		String filename = "src/Reservation.txt";
		ArrayList<Reservation> resItem = new ArrayList<Reservation>();
		resItem = readReservationInfo(filename);
		resItem.add(res);

		List alw = new ArrayList();

		for (int i = 0; i < resItem.size(); i++) {
			Reservation resv = (Reservation) resItem.get(i);
			StringBuilder st = new StringBuilder();

			st.append(resv.getTableId());
			st.append(SEPARATOR);
			st.append(resv.getDate().trim());
			st.append(SEPARATOR);
			st.append(resv.getArrivalTime());
			st.append(SEPARATOR);
			st.append(resv.getPax());
			st.append(SEPARATOR);
			st.append(resv.getName());
			st.append(SEPARATOR);
			st.append(resv.getContactNumber());

			alw.add(st.toString());
		}
		write(filename, alw);
	}

	public static void saveExistingReservation(Reservation res, int contactNumber) throws IOException {
		String filename = "src/Reservation.txt";
		ArrayList<Reservation> resItem = new ArrayList<Reservation>();
		resItem = readReservationInfo(filename);
		Reservation resv = new Reservation();

		List alw = new ArrayList();
		for (int i = 0; i < resItem.size(); i++) {
			StringBuilder st = new StringBuilder();
			if (resItem.get(i).getContactNumber() == contactNumber) {
				resv = res;
			} else {
				resv = resItem.get(i);
			}
			st.append(resv.getTableId());
			st.append(SEPARATOR);
			st.append(resv.getDate().trim());
			st.append(SEPARATOR);
			st.append(resv.getArrivalTime());
			st.append(SEPARATOR);
			st.append(resv.getPax());
			st.append(SEPARATOR);
			st.append(resv.getName());
			st.append(SEPARATOR);
			st.append(resv.getContactNumber());

			alw.add(st.toString());
		}
		write(filename, alw);
	}

	public static void deleteReservation(Reservation res) throws IOException {
		String filename = "src/Reservation.txt";
		ArrayList<Reservation> resItem = new ArrayList<Reservation>();
		resItem = readReservationInfo(filename);
		Reservation resv = new Reservation();

		List alw = new ArrayList();
		for (int i = 0; i < resItem.size(); i++) {
			StringBuilder st = new StringBuilder();
			if (resItem.get(i).getContactNumber() != res.getContactNumber()) {
				st.append(resv.getTableId());
				st.append(SEPARATOR);
				st.append(resv.getDate().trim());
				st.append(SEPARATOR);
				st.append(resv.getArrivalTime());
				st.append(SEPARATOR);
				st.append(resv.getPax());
				st.append(SEPARATOR);
				st.append(resv.getName());
				st.append(SEPARATOR);
				st.append(resv.getContactNumber());
				alw.add(st.toString());
			}
		}
		write(filename, alw);

	}

	// READ orders file
	public static ArrayList readOrders(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList) read(filename);
		ArrayList alr = new ArrayList();// to store Professors data

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer
																		// using delimiter ","

			int orderId = Integer.parseInt(star.nextToken().trim());
			int tableId = Integer.parseInt(star.nextToken().trim());
			int staffId = Integer.parseInt(star.nextToken().trim());

			// split string using space as delimiter
			String[] itemStrArr = star.nextToken().trim().split(" ");
			List<Integer> itemId = new ArrayList<Integer>();
			if (itemStrArr != null) {
				// itemId = new int[itemStrArr.length];
				for (int j = 0; j < itemStrArr.length; j++) {
					itemId.add(j, Integer.parseInt(itemStrArr[j]));
				}
			}

			String[] promoSetStrArr = star.nextToken().trim().split(" ");
			List<Integer> promoSetId = new ArrayList<Integer>();
			if (promoSetStrArr != null) {
				// promoSetId = new int[promoSetStrArr.length];
				for (int j = 0; j < promoSetStrArr.length; j++) {
					promoSetId.add(j, Integer.parseInt(promoSetStrArr[j]));
				}
			}

			double price = Double.parseDouble(star.nextToken().trim());
			String comment = star.nextToken().trim();
			LocalDateTime dateTime = LocalDateTime.parse(star.nextToken().trim());

			// create Order object from file data
			Order od = new Order(orderId, tableId, staffId, itemId, promoSetId, price, comment, dateTime);
			// add to Orders list
			alr.add(od);
		}
		return alr;
	}

	// SAVE to orders file
	public static void saveOrders(String filename, List al) throws IOException {
		List alw = new ArrayList();// to store Professors data

		for (int i = 0; i < al.size(); i++) {
			Order od = (Order) al.get(i);
			StringBuilder st = new StringBuilder();
			st.append(od.getOrderId());
			st.append(SEPARATOR);
			st.append(od.getTableId());
			st.append(SEPARATOR);
			st.append(od.getStaffId());
			st.append(SEPARATOR);
			List<Integer> itemLst = od.getItemId();
			for (int var : itemLst)
				st.append(var + " ");
			st.append(SEPARATOR);
			List<Integer> promoSetLst = od.getPromoSetId();
			for (int var : promoSetLst)
				st.append(var + " ");
			st.append(SEPARATOR);
			st.append(od.getPrice());
			st.append(SEPARATOR);
			String comm = od.getComment();
			if (comm != null)
				st.append(od.getComment().trim());
			else
				st.append("NULL");
			st.append(SEPARATOR);
			st.append(od.getDateTime());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	// READ items file
	/*
	 * public static ArrayList readItems(String filename) throws IOException { //
	 * read String from text file ArrayList stringArray = (ArrayList)read(filename);
	 * ArrayList alr = new ArrayList() ;// to store Professors data
	 * 
	 * for (int i = 0 ; i < stringArray.size() ; i++) { String st =
	 * (String)stringArray.get(i); // get individual 'fields' of the string
	 * separated by SEPARATOR StringTokenizer star = new StringTokenizer(st ,
	 * SEPARATOR); // pass in the string to the string tokenizer using delimiter ","
	 * 
	 * int itemId = Integer.parseInt(star.nextToken().trim()); String name =
	 * star.nextToken().trim(); String type = star.nextToken().trim(); int price =
	 * Integer.parseInt(star.nextToken().trim()); String desc =
	 * star.nextToken().trim(); int quota =
	 * Integer.parseInt(star.nextToken().trim());
	 * 
	 * // create Item object from file data Menu mn = new Menu(itemId, name, type,
	 * price, desc, quota); // add to Items list alr.add(mn); } return alr; }
	 */

	public static ArrayList<PromoSetCon> readPromoSetInfo(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) read(filename);

		ArrayList<PromoSetCon> alr = new ArrayList<PromoSetCon>();

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			int PromoSetId = Integer.parseInt(star.nextToken().trim());

			ArrayList<Integer> itemId = new ArrayList<Integer>();
			String temp = star.nextToken().replace('[', ' ');
			temp = temp.replace(']', ' ');
			temp = temp.trim();
			String[] str = temp.split(",");
			for (int j = 0; j < str.length; j++) {
				str[j] = str[j].trim();
				itemId.add(Integer.parseInt(str[j]));
			}

			// = //Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			double price = Double.parseDouble(star.nextToken().trim());
			String startDate = star.nextToken().trim();
			String endDate = star.nextToken().trim();
			int quota = Integer.parseInt(star.nextToken().trim());

			PromoSetCon PromoSetInfo = new PromoSetCon(PromoSetId, name, itemId, price, startDate, endDate, quota);

			alr.add(PromoSetInfo);
		}
		return alr;
	}

	public static void savePromoItems(PromoSetCon promoSetItemData) throws IOException {
		String filename = "src/promotionList.txt";
		ArrayList<PromoSetCon> promoSession = new ArrayList<PromoSetCon>();
		promoSession = readPromoSetInfo(filename);
		promoSession.add(promoSetItemData);

		List alw = new ArrayList();

		for (int i = 0; i < promoSession.size(); i++) {
			PromoSetCon promoItem = (PromoSetCon) promoSession.get(i);
			StringBuilder st = new StringBuilder();
			st.append(promoItem.getPromoSetId());
			st.append(SEPARATOR);
			st.append(promoItem.getItemId());
			st.append(SEPARATOR);
			st.append(promoItem.getName());
			st.append(SEPARATOR);
			st.append(promoItem.getPrice());
			st.append(SEPARATOR);
			st.append(promoItem.getStartDate().trim());
			st.append(SEPARATOR);
			st.append(promoItem.getEndDate().trim());
			st.append(SEPARATOR);
			st.append(promoItem.getQuota());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	public static void UpdatePromoItem(int promoId, int i) throws IOException {
		Scanner sc = new Scanner(System.in);
		String filename = "src/promotionList.txt";
		List alw = new ArrayList();
		ArrayList<PromoSetCon> promoSetList = new ArrayList<PromoSetCon>();
		promoSetList = readPromoSetInfo(filename);

		PromoSetCon updatedtbl = new PromoSetCon();
		for (PromoSetCon promoSetloop : promoSetList) {
			if (promoSetloop.getPromoSetId() == promoId) {
				updatedtbl = promoSetloop;
				if (i == 1) {
					System.out.println("Name");
					System.out.print("New value:");
					String newName = sc.nextLine();
					promoSetloop.setName(newName);
				}
				if (i == 2) {
					
					System.out.println("======================================");
					System.out.println("\t Update Promotion Set Item");
					System.out.println("======================================");
					System.out.println("1) add new item");
					System.out.println("2) remove specific item");
					System.out.println();
					System.out.print("Current List:");
					
					ArrayList<Integer> tempList = promoSetloop.getItemId();
					for (int k = 0; k < tempList.size(); k++) {
						
						System.out.print(tempList.get(k));
						
						if(k+1!=tempList.size())
						{
							System.out.print(",");
						}
					}
					System.out.print("\nEnter your choice: ");
					int input = sc.nextInt();sc.nextLine();
					switch (input) {

					case 1:
						System.out.print("New item:");
						int  newItem = sc.nextInt();
						sc.nextLine();
						tempList.add(newItem);
						break;
						
					case 2:
						System.out.print("Input the Id to remove:");
						int  currentitem = sc.nextInt();
						sc.nextLine();
						for(int cur = 0; cur < tempList.size(); cur++) {
						if(tempList.get(cur)==currentitem)
						{
							tempList.remove(cur);
						}
						
						}
						break;
					default:

						break;
					
				}

				}
				if (i == 3) {
					System.out.println("Price");
					System.out.print("New value:");
					double newPrice = sc.nextDouble();
					sc.nextLine();
					promoSetloop.setPrice(newPrice);
				} else if (i == 4) {
					System.out.println("Start date Formate:DD/MM/YYYY");
					System.out.print("New value:");
					String newStartDate = sc.nextLine();
					promoSetloop.setStartDate(newStartDate);
				} else if (i == 5) {
					System.out.println("End date Formate:DD/MM/YYYY");
					System.out.print("New value:");
					String newEndDate = sc.nextLine();
					promoSetloop.setEndDate(newEndDate);
				} else if (i == 6) {
					System.out.print("New value:");
					int newQuota = sc.nextInt();
					sc.nextLine();
					promoSetloop.setQuota(newQuota);
				}

			} else {
				updatedtbl = promoSetloop;
			}

			StringBuilder st = new StringBuilder();

			st.append(updatedtbl.getPromoSetId());
			st.append(SEPARATOR);
			st.append(updatedtbl.getItemId());
			st.append(SEPARATOR);
			st.append(updatedtbl.getName());
			st.append(SEPARATOR);
			st.append(updatedtbl.getPrice());
			st.append(SEPARATOR);
			st.append(updatedtbl.getStartDate());
			st.append(SEPARATOR);
			st.append(updatedtbl.getEndDate());
			st.append(SEPARATOR);
			st.append(updatedtbl.getQuota());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	public static void deletePromoSet(int promoId) throws IOException {
		String filename = "src/promotionList.txt";
		ArrayList<PromoSetCon> promoSetList = new ArrayList<PromoSetCon>();
		promoSetList = readPromoSetInfo(filename);
		PromoSetCon resv = new PromoSetCon();

		List alw = new ArrayList();

		for (PromoSetCon promoSetloop : promoSetList) {
			if (promoSetloop.getPromoSetId() != promoId) {

				StringBuilder st = new StringBuilder();

				st.append(promoSetloop.getPromoSetId());
				st.append(SEPARATOR);
				st.append(promoSetloop.getItemId());
				st.append(SEPARATOR);
				st.append(promoSetloop.getName());
				st.append(SEPARATOR);
				st.append(promoSetloop.getPrice());
				st.append(SEPARATOR);
				st.append(promoSetloop.getStartDate());
				st.append(SEPARATOR);
				st.append(promoSetloop.getEndDate());
				st.append(SEPARATOR);
				st.append(promoSetloop.getQuota());
				alw.add(st.toString());
			}
		}
		write(filename, alw);

	}

	public boolean checkIfExistPromoSet(int id) throws IOException {
		Scanner sc = new Scanner(System.in);
		String filename = "src/promotionList.txt";
		boolean check = false;
		List alw = new ArrayList();
		ArrayList<PromoSetCon> tbRec = new ArrayList<PromoSetCon>();
		tbRec = readPromoSetInfo(filename);
		PromoSetCon updatedPromoSetCon = new PromoSetCon();
		for (PromoSetCon tbl : tbRec) {
			if (tbl.getPromoSetId() == id) {
				check = true;
			}
		}
		return check;
	}
	
	public static int updatePromoSetQuota(int promoSetId, int addDel) throws IOException {
		String filename = "src/promotionList.txt";
		ArrayList<PromoSetCon> promoSession = new ArrayList<PromoSetCon>();
		promoSession = readPromoSetInfo(filename);
		
		for(PromoSetCon ps: promoSession) {
			if(promoSetId == ps.getPromoSetId()) {
				if(addDel==1) {
					if(ps.getQuota()>0) {
						ps.setQuota(ps.getQuota()-1);
					}
					else {
						return -1;
					}
				}
				else {
					ps.setQuota(ps.getQuota()+1);
				}
				break;
			}
		}

		List alw = new ArrayList();

		for (int i = 0; i < promoSession.size(); i++) {
			PromoSetCon promoItem = (PromoSetCon) promoSession.get(i);
			StringBuilder st = new StringBuilder();
			st.append(promoItem.getPromoSetId());
			st.append(SEPARATOR);
			st.append(promoItem.getItemId());
			st.append(SEPARATOR);
			st.append(promoItem.getName());
			st.append(SEPARATOR);
			st.append(promoItem.getPrice());
			st.append(SEPARATOR);
			st.append(promoItem.getStartDate().trim());
			st.append(SEPARATOR);
			st.append(promoItem.getEndDate().trim());
			st.append(SEPARATOR);
			st.append(promoItem.getQuota());
			alw.add(st.toString());
		}
		write(filename, alw);
		return 0;
	}
	
	public static ArrayList<Staff> readStaffInfo(String filename) throws IOException {
		ArrayList stringArray = (ArrayList) read(filename);

		ArrayList<Staff> staffSetList = new ArrayList<Staff>();

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			int StaffId = Integer.parseInt(star.nextToken().trim());
			String name = star.nextToken().trim();
			char gender = star.nextToken().trim().charAt(0);
			String contact = star.nextToken().trim();
			String email = star.nextToken().trim();
			String address = star.nextToken().trim();
			String shift = star.nextToken().trim();
			String jobTitle = star.nextToken().trim();
			
			
		

			Staff StaffInfo = new Staff(StaffId, name, gender, contact, email,address, shift, jobTitle);

			staffSetList.add(StaffInfo);
		}
		return staffSetList;
	}

	
	
	public static void saveStaffDetails(Staff Staff) throws IOException {
		String filename = "src/StaffList.txt";
		ArrayList<Staff> StaffSession = new ArrayList<Staff>();
		StaffSession = readStaffInfo(filename);
		StaffSession.add(Staff);

		List alw = new ArrayList();

		for (int i = 0; i < StaffSession.size(); i++) {
			Staff StaffItem = (Staff) StaffSession.get(i);
			StringBuilder st = new StringBuilder();
			st.append(StaffItem.getEmployeeId());
			st.append(SEPARATOR);
			st.append(StaffItem.getName().trim());
			st.append(SEPARATOR);
			st.append(StaffItem.getGender());
			st.append(SEPARATOR);
			st.append(StaffItem.getContact().trim());
			st.append(SEPARATOR);
			st.append(StaffItem.getEmail().trim());
			st.append(SEPARATOR);
			st.append(StaffItem.getAddress().trim());
			st.append(SEPARATOR);
			st.append(StaffItem.getShift().trim());
			st.append(SEPARATOR);
			st.append(StaffItem.getJobTitle().trim());
			alw.add(st.toString());
		}
		write(filename, alw);
	}
	public boolean checkIfExistStaff(int id) throws IOException {
		Scanner sc = new Scanner(System.in);
		String filename = "src/StaffList.txt";
		boolean check = false;
		ArrayList<Staff> StaffSession = new ArrayList<Staff>();
		StaffSession = readStaffInfo(filename);
		
		PromoSetCon updatedPromoSetCon = new PromoSetCon();
		for (Staff Stafflist : StaffSession) {
			if (Stafflist.getEmployeeId() == id) {
				check = true;
			}
		}
		return check;
	}
	
	public static void deleteStaff(int StaffId) throws IOException {
		String filename = "src/StaffList.txt";
		ArrayList<Staff> StaffSession = new ArrayList<Staff>();
		StaffSession = readStaffInfo(filename);
		Staff resv = new Staff();

		List alw = new ArrayList();

		for (Staff Staffloop : StaffSession) {
			if (Staffloop.getEmployeeId() != StaffId) {

				StringBuilder st = new StringBuilder();

				st.append(Staffloop.getEmployeeId());
				st.append(SEPARATOR);
				st.append(Staffloop.getName().trim());
				st.append(SEPARATOR);
				st.append(Staffloop.getGender());
				st.append(SEPARATOR);
				st.append(Staffloop.getContact().trim());
				st.append(SEPARATOR);
				st.append(Staffloop.getEmail().trim());
				st.append(SEPARATOR);
				st.append(Staffloop.getAddress().trim());
				st.append(SEPARATOR);
				st.append(Staffloop.getShift().trim());
				st.append(SEPARATOR);
				st.append(Staffloop.getJobTitle().trim());
				alw.add(st.toString());
			}
		}
		write(filename, alw);

	}
	
	public static void UpdateStaffItem(int StaffId, int i) throws IOException {
		Scanner sc = new Scanner(System.in);
		String filename = "src/StaffList.txt";
		List alw = new ArrayList();
		ArrayList<Staff> staffSesson = new ArrayList<Staff>();
		staffSesson = readStaffInfo(filename);

		Staff updatedStaff = new Staff();
		for (Staff staffloop : staffSesson) {
			if (staffloop.getEmployeeId() == StaffId) {
				updatedStaff = staffloop;
				if (i == 1) {
					System.out.println("Contact");
					System.out.print("New value:");
					String newContact = sc.nextLine();
					staffloop.setContact(newContact);
				}
				if (i == 2) {
					System.out.println("Email");
					System.out.print("New value:");
					String newEmail = sc.nextLine();
					staffloop.setEmail(newEmail);

				}
				if (i == 3) {
					System.out.println("Address");
					System.out.print("New value:");
					String newAddress = sc.nextLine();
					staffloop.setAddress(newAddress);
					
				}if (i == 4) {
					System.out.println("Shift");
					System.out.print("New value:");
					String newShift = sc.nextLine();
					staffloop.setShift(newShift);
					
				}
				else if (i == 5) {
					System.out.println("Job Title");
					System.out.print("New value:");
					String newJobTitle = sc.nextLine();
					staffloop.setJobTitle(newJobTitle);
				} 

			} else {
				updatedStaff = staffloop;
			}

			StringBuilder st = new StringBuilder();

			st.append(updatedStaff.getEmployeeId());
			st.append(SEPARATOR);
			st.append(updatedStaff.getName().trim());
			st.append(SEPARATOR);
			st.append(updatedStaff.getGender());
			st.append(SEPARATOR);
			st.append(updatedStaff.getContact().trim());
			st.append(SEPARATOR);
			st.append(updatedStaff.getEmail().trim());
			st.append(SEPARATOR);
			st.append(updatedStaff.getAddress().trim());
			st.append(SEPARATOR);
			st.append(updatedStaff.getShift().trim());
			st.append(SEPARATOR);
			st.append(updatedStaff.getJobTitle().trim());
			alw.add(st.toString());
		}
		write(filename, alw);
	}

	
	
	
	
	public static void main(String[] aArgs) {

	}
}
