import java.util.ArrayList;
import java.util.Scanner;

public class TableApp {
	public static void menu() {
		Table tb = new Table();
		TableInfo tbInfo = new TableInfo();
		Reservation res = new Reservation();
		ArrayList<Table> curTb = new ArrayList<Table>();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Date: ");
		String date = sc.nextLine();
		System.out.print("Enter Session: ");
		String session = sc.nextLine();
		ArrayList<Reservation> resList = new ArrayList<Reservation>();
		resList = res.getTodayReservation(date, session);
		curTb = tb.getTableStatus(resList);
		System.out.println();
		System.out.println("=============================");
		System.out.println("\tCurrent Table Availability");
		System.out.println("=============================");
		//
		TableInfo tbInfoItem = new TableInfo();
		tbInfoItem = tbInfo.getTableSession(date, session);
		System.out.println("-----------------------------");
		System.out.println("Remaining table count: ");
		System.out.println("Ten Seater: " + tbInfoItem.getTenSeater() + " , " + "Eight Seater: " + tbInfoItem.getEightSeater());
		System.out.println("Four Seater: " + tbInfoItem.getFourSeater() + " , " + "Two Seater: " + tbInfoItem.getTwoSeater());
		System.out.println("-----------------------------");
		//
		for(int i = 0; i < curTb.size(); i++) {
			System.out.println("Table Id: " + curTb.get(i).tableId + " Status : " + tb.statusText(curTb.get(i).getStatus()) + " Capacity: "+ curTb.get(i).getCapacity());
			System.out.println("-----------------------------");
		}
	}
}
