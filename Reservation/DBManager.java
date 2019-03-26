import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
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
	
	public static void main(String[] aArgs) {

	}
}
