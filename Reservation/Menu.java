import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
	public static void main(String args[]) {
		Table tb = new Table();
		TableInfo tbInfo = new TableInfo();
		Reservation res = new Reservation();
		boolean start = true;
		while (start) {
			Scanner sc = new Scanner(System.in);
			System.out.println("==============================================");
			System.out.println("   Reservation System");
			System.out.println("   ------------------");
			System.out.println("1. Create new reservation");
			System.out.println("2. Retrieve reservation");
			System.out.println("3. Update reservation");
			System.out.println("4. Remove reservation");		
			System.out.println("5. Exit");
			System.out.println();
			System.out.println("==============================================");
			System.out.print("Enter choice: ");
			int select = sc.nextInt();
			sc.nextLine();
			System.out.println();
			switch (select) {
			case 1:
				// 1. Ask user to enter date, session, number of guests
				// 2. Check if there's any available session on that day
				// 3. If yes, proceed
				// 4. If no, ask user to book at a different date/session
				boolean case1 = true;
				while (case1) {
					System.out.println();
					System.out.print("Enter reservation date (yyyy-mm-dd): ");
					String resDate = sc.nextLine();
					System.out.print("Enter session (AM/PM): ");
					String resSession = sc.nextLine();
					System.out.print("Enter pax (0-10): ");
					int resPax = sc.nextInt();
					sc.nextLine();

					boolean tbAvailability = tbInfo.checkAvailableTable(resDate, resSession, resPax);

					if (tbAvailability != true) {
						System.out.println("There's no available table for this current date");
						System.out.println("Would you like to try a different date?");
						System.out.println();
						System.out.println("1. Try again");
						System.out.println("2. Exit");
						System.out.println();
						System.out.print("Enter choice: ");
						int choice1 = sc.nextInt();
						sc.hasNextLine();
						if (choice1 == 2) {
							case1 = false;
						}
					} else {
						System.out.println("There's table available.");
						System.out.println();
						System.out.print("Enter name: ");
						String resName = sc.nextLine();
						System.out.print("Enter contact number: ");
						int resContact = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter arrival time: ");
						String resArrival = sc.nextLine();

						TableInfo tblItem = new TableInfo();
						tblItem = tbInfo.getTableSession(resDate, resSession);
						int tablesize = tbInfo.checkTableSize(resPax);
						int tableID = tbInfo.assignTable(tblItem, tablesize);

						Reservation myRes = new Reservation(tableID, resDate, resArrival, resPax, resName, resContact);
						res.createReservation(myRes);

						System.out.println();
						System.out.println("==============================================");
						System.out.println("Reservation successfully created!");
						System.out.println("==============================================");
						System.out.println("TableID: " + tableID);
						System.out.println("Date: " + resDate);
						System.out.println("Arrival: " + resArrival);
						System.out.println("Pax: " + resPax);
						System.out.println("Name: " + resName);
						System.out.println("Contact: " + resContact);
						System.out.println("==============================================");
						System.out.println();
						case1 = false;
					}
				}
				System.out.println();
				break;
			case 2:
				// Find reservation by contact
				// If true, display reservation
				// If false, exit or try again
				boolean case2 = true;
				while (case2) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					int contact = sc.nextInt();
					sc.nextLine();
					Reservation myReservation = new Reservation();
					myReservation = res.getReservation(contact);
					if (myReservation != null) {
						System.out.println();
						System.out.println("Reservation Record:");
						System.out.println("==============================================");
						System.out.println("Table Id: " + myReservation.getTableId());
						System.out.println("Date: " + myReservation.getDate());
						System.out.println("Time: " + myReservation.getArrivalTime());
						System.out.println("Pax: " + myReservation.getPax());
						System.out.println("Name: " + myReservation.getName());
						System.out.println("Contact: " + myReservation.getContactNumber());
						System.out.println("==============================================");
						System.out.println();
						case2 = false;
					} else {
						System.out.println("Contact number not found.");
						System.out.println();
						System.out.println("1. Search again");
						System.out.println("2. Exit");
						System.out.print("Choice: ");
						int choice2 = sc.nextInt();
						sc.nextLine();
						if (choice2 == 2) {
							case2 = false;
						}
					}
				}
				break;
			case 3:
				//Get Reservation by contact
				//Check if it exist
				//Allow the user to update
				//Update the Reservation and Table accordingly
				boolean case3 = true;
				while (case3) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					int contact = sc.nextInt();
					sc.nextLine();
					Reservation resItem = new Reservation();
					resItem = res.getReservation(contact);
					if(!resItem.equals(null)) {
						System.out.print("Enter new date: ");
						String newDate = sc.nextLine();
						System.out.print("Enter new time: ");
						String newTime = sc.nextLine();
						System.out.print("Enter new pax: ");
						int newPax = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter new name: ");
						String newName = sc.nextLine();
						System.out.print("Enter new contact: ");
						int newContact = sc.nextInt();
						sc.nextLine();
						System.out.println();
						int tableId = resItem.getTableId();
						Reservation updatedResItem = new Reservation(tableId,newDate,newTime,newPax,newName,newContact);
						res.updateReservation(resItem, updatedResItem);
						System.out.println("Reservation with contact: " + resItem.getContactNumber() + " have been updated");
						System.out.println();
						case3 = false;
					}
					else {
						System.out.println("Contact number not found.");
						System.out.println();
						System.out.println("1. Search again");
						System.out.println("2. Exit");
						System.out.print("Choice: ");
						int choice2 = sc.nextInt();
						sc.nextLine();
						if (choice2 == 2) {
							case3 = false;
						}
					}
				}
				break;
			case 4:
				//Get Reservation by contact
				//Check if it exist
				//Delete Reservation and Table
				boolean case4 = true;
				while(case4) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					int contact = sc.nextInt();
					sc.nextLine();
					
					Reservation resItem = new Reservation();
					resItem = res.getReservation(contact);					
					if(resItem != null) {
						res.deleteReservation(resItem);
						System.out.println("Reservation successfully deleted!");
						case4 = false;
					}
					else {
						System.out.println("Contact number not found.");
						System.out.println();
						System.out.println("1. Search again");
						System.out.println("2. Exit");
						System.out.print("Choice: ");
						int choice2 = sc.nextInt();
						sc.nextLine();
						if (choice2 == 2) {
							case4 = false;
						}	
					}					
				}
				break;
			case 5:
				System.out.println();
				System.out.println("System exiting...");
				start = false;
				break;
			}
		}
}}
