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
		
		for(int i = 0; i < tbSession.size(); i++) {
			TableInfo tbItem = (TableInfo)tbSession.get(i);
			StringBuilder st =  new StringBuilder();
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
			if((tbl.getDate()).equals(tblItem.getDate()) && (tbl.getSession()).equals(tblItem.getSession())) {
				updatedtbl = tblItem;
			}
			else {
				updatedtbl = tbl;
			}
			StringBuilder st =  new StringBuilder();
			
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
	
	//////////////////////////////////////////////////////////////// Reservation /////////////////////////////////////////////////////////////////////
	
	
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
		
		for(int i = 0; i < resItem.size(); i++) {
			Reservation resv = (Reservation)resItem.get(i);
			StringBuilder st =  new StringBuilder();
			
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
		for(int i = 0; i < resItem.size(); i++) {
			StringBuilder st =  new StringBuilder();
			if(resItem.get(i).getContactNumber() == contactNumber) {
				resv = res;
			}
			else {
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
		for(int i = 0; i < resItem.size(); i++) {
			StringBuilder st =  new StringBuilder();
			if(resItem.get(i).getContactNumber() != res.getContactNumber()) {
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
	
	//READ orders file
		public static ArrayList readOrders(String filename) throws IOException {
			// read String from text file
			ArrayList stringArray = (ArrayList)read(filename);
			ArrayList alr = new ArrayList() ;// to store Professors data

			for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
				
				int orderId = Integer.parseInt(star.nextToken().trim());
				int staffId = Integer.parseInt(star.nextToken().trim());
				
				// split string using space as delimiter
		        String[] itemStrArr = star.nextToken().trim().split(" ");
		        List<Integer> itemId = new ArrayList<Integer>();
		        if(itemStrArr != null) {
		        	//itemId = new int[itemStrArr.length];
			        for (int j = 0; j < itemStrArr.length; j++) 
			        { 
			            itemId.add(j, Integer.parseInt(itemStrArr[j]));
			        }
		        }
		        
				String[] promoSetStrArr = star.nextToken().trim().split(" ");
				List<Integer> promoSetId = new ArrayList<Integer>();
		        if(promoSetStrArr != null) {
		        	//promoSetId = new int[promoSetStrArr.length];
			        for (int j = 0; j < promoSetStrArr.length; j++) 
			        { 
			        	promoSetId.add(j, Integer.parseInt(promoSetStrArr[j]));
			        }
		        }

				double price = Double.parseDouble(star.nextToken().trim());
				String comment = star.nextToken().trim();
				LocalDateTime dateTime = LocalDateTime.parse(star.nextToken().trim());
				
				// create Order object from file data
				Order od = new Order(orderId, staffId, itemId, promoSetId, price, comment, dateTime);
				// add to Orders list
				alr.add(od) ;
			}
			return alr ;
		}
		
		//SAVE to orders file
		public static void saveOrders(String filename, List al) throws IOException {
			List alw = new ArrayList() ;// to store Professors data

		    for (int i = 0 ; i < al.size() ; i++) {
				Order od = (Order)al.get(i);
				StringBuilder st =  new StringBuilder();
				st.append(od.getOrderId());
				st.append(SEPARATOR);
				st.append(od.getStaffId());
				st.append(SEPARATOR);
				List<Integer> itemLst = od.getItemId();
				for (int var: itemLst)
					st.append(var + " ");
				st.append(SEPARATOR);
				List<Integer> promoSetLst = od.getPromoSetId();
				for (int var: promoSetLst)
					st.append(var + " ");
				st.append(SEPARATOR);
				st.append(od.getPrice());
				st.append(SEPARATOR);
				String comm = od.getComment();
				if(comm!=null)
					st.append(od.getComment().trim());
				else
					st.append("NULL");
				st.append(SEPARATOR);
				st.append(od.getDateTime());
				alw.add(st.toString()) ;
			}
			write(filename,alw);
		}
		
		//READ items file
		/*public static ArrayList readItems(String filename) throws IOException {
			// read String from text file
			ArrayList stringArray = (ArrayList)read(filename);
			ArrayList alr = new ArrayList() ;// to store Professors data

			for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
				
				int itemId = Integer.parseInt(star.nextToken().trim());
				String name = star.nextToken().trim();
				String type = star.nextToken().trim();
				int price = Integer.parseInt(star.nextToken().trim());
				String desc = star.nextToken().trim();
				int quota = Integer.parseInt(star.nextToken().trim());
				
				// create Item object from file data
				Menu mn = new Menu(itemId, name, type, price, desc, quota);
				// add to Items list
				alr.add(mn);
			}
			return alr;
		}*/
		
		//READ PromoSets file
			public static ArrayList readPromoSets(String filename) throws IOException {
				// read String from text file
				ArrayList stringArray = (ArrayList)read(filename);
				ArrayList alr = new ArrayList() ;// to store Professors data

				for (int i = 0 ; i < stringArray.size() ; i++) {
					String st = (String)stringArray.get(i);
					// get individual 'fields' of the string separated by SEPARATOR
					StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
					
					int promoSetId = Integer.parseInt(star.nextToken().trim());
					String name = star.nextToken().trim();
					String[] itemStrArr = star.nextToken().trim().split(" ");
			        int[] itemId = new int[itemStrArr.length];
			        for (int j = 0; j < itemStrArr.length; j++) 
			        { 
			            itemId[j] = Integer.parseInt(itemStrArr[j]);
			        }
					double price = Double.parseDouble(star.nextToken().trim());
					DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				    LocalDate startDate = LocalDate.parse(star.nextToken().trim(), DATEFORMATTER);
				    LocalDate endDate = LocalDate.parse(star.nextToken().trim(), DATEFORMATTER);
					int quota = Integer.parseInt(star.nextToken().trim());
					
					// create Item object from file data
					PromoSet ps = new PromoSet(promoSetId, name, itemId, price, startDate, endDate, quota);
					// add to Items list
					alr.add(ps);
				}
				return alr;
			}
	
	public static void main(String[] aArgs) {

	}
}
