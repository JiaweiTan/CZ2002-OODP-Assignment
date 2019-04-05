import java.util.Scanner;
import java.util.ArrayList;

public class ReservationApp {
	
	public static void createReservation() {
		Table tb = new Table();
		TableInfo tbInfo = new TableInfo();
		Reservation res = new Reservation();
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("=============================");
		System.out.println("\tCreating Reservation");
		System.out.println("=============================");
		// 1. Ask user to enter date, session, number of guests
		// 2. Check if there's any available session on that day
		// 3. If yes, proceed
		// 4. If no, ask user to book at a different date/session
		boolean case1 = true;
		while (case1) {
			System.out.println();
			System.out.print("Enter reservation date (yyyy-mm-dd): ");
			String resDate = sc.nextLine();
			//Validate Date
			while(!Validation.isDateValid(resDate)) {
				System.out.println();
				System.out.println("Invalid value, please key in a new value");
				System.out.print("Reservation date (yyyy-mm-dd): ");
				resDate = sc.nextLine();
			}
			System.out.print("Enter session (AM/PM): ");
			String resSession = sc.nextLine();
			//Validate Session
			while(!(resSession.equals("AM") || resSession.equals("PM"))) {
				System.out.println();
				System.out.println("Invalid session, please key in a new session");
				System.out.print("Session Format (AM/PM): ");
				resSession = sc.nextLine();
			}
			System.out.print("Enter pax (0-10): ");
			int resPax = sc.nextInt();
			sc.nextLine();
			System.out.println();
			//Validate Pax
			while(resPax <= 0 || resPax > 10) {
				//System.out.println();
				System.out.println("Invalid pax, please key between 1 to 10 pax");
				System.out.print("Pax (1-10): ");
				resPax = sc.nextInt();
				sc.nextLine();
			}

			boolean tbAvailability = tbInfo.checkAvailableTable(resDate, resSession, resPax);
			//Validate Availability
			if (tbAvailability != true) {
				System.out.println("There's no available table for this current date");
				System.out.println("Would you like to try a different date?");
				System.out.println();
				System.out.println("1. Try again");
				System.out.println("2. Exit");
				System.out.print("Enter choice: ");
				int choice1 = sc.nextInt();
				sc.nextLine();
				//Validate Choice
				while(choice1 != 1 && choice1 != 2) {
					System.out.println();
					System.out.println("Invalid selection, please key either 1 or 2");
					System.out.print("Enter choice: ");
					choice1 = sc.nextInt();
					sc.nextLine();
				}
				if(choice1 == 2) {
					case1 = false;
				}
			} else {
				System.out.println("There's table available.");
				System.out.println();
				System.out.print("Enter name: ");
				String resName = sc.nextLine();
				//Validate Name
				while(resName.equals("")) {
					System.out.println();
					System.out.println("Reservation name cannot be empty");
					System.out.print("Enter name: ");
					resName = sc.nextLine();
					System.out.println();
				}
				System.out.print("Enter contact: ");
				String resContact = sc.nextLine();
				//Validate Contact
				while(!(resContact.matches("^[0-9]*$") && resContact.length() >= 8)) {
					System.out.println();
					System.out.println("Invalid selection, please key numbers & no less than 8 digits");
					System.out.print("Enter contact: ");
					resContact = sc.nextLine();
				}
				System.out.print("Enter arrival time: ");
				String resArrival = sc.nextLine();
				//Validate Arrival Time
				while((res.checkSession(resArrival) == "Invalid")) {
					System.out.println();
					System.out.println("Invalid time, please key in between 11:00-15:00 or 18:00-22:00");
					System.out.print("Enter arrival time (hh:mm): ");
					resArrival = sc.nextLine();		
				}
				
				TableInfo tblItem = new TableInfo();
				tblItem = tbInfo.getTableSession(resDate, resSession);
				int tablesize = tbInfo.checkTableSize(resPax);
				int tableID = tbInfo.assignTable(tblItem, tablesize);
				
				int newReservationId = res.getNewReservationId();
				Reservation myRes = new Reservation(newReservationId, tableID, resDate, resArrival, resPax, resName, resContact);
				res.createReservation(myRes);

				System.out.println();
				System.out.println("=========================================================");
				System.out.println("\t     Reservation successfully created! ");
				System.out.println("=========================================================");
				System.out.println("TableID: " + tableID);
				System.out.println("Date: " + resDate);
				System.out.println("Arrival: " + resArrival);
				System.out.println("Pax: " + resPax);
				System.out.println("Name: " + resName);
				System.out.println("Contact: " + resContact);
				System.out.println("=========================================================");
				System.out.println();
				case1 = false;
			}
		}
	}
	
	public static void updateReservation() {
		Reservation res = new Reservation();
		boolean start = true;
		while (start) {
			Scanner sc = new Scanner(System.in);
			System.out.println("=============================");
			System.out.println("\t Reservation Menu");
			System.out.println("=============================");
			System.out.println("1) View reservation");
			System.out.println("2) Update reservation");
			System.out.println("3) Delete reservation");
			System.out.println("0) Back");
			System.out.print("Enter choice: ");
			int select = sc.nextInt();
			sc.nextLine();
			System.out.println();
			switch (select) {
			case 0:
				start = false;
				break;
			case 1:
				// Find reservation by contact
				// If true, display reservation
				// If false, exit or try again
				boolean case1 = true;
				while (case1) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					String contact = sc.nextLine();
					//Validate Contact
					while(!(contact.matches("^[0-9]*$") && contact.length() >= 8)) {
						System.out.println();
						System.out.println("Invalid selection, please key numbers & no less than 8 digits");
						System.out.print("Enter contact: ");
						contact = sc.nextLine();
						System.out.println();
					}
					Reservation myReservation = new Reservation();
					myReservation = res.getReservation(contact);
					if (myReservation != null) {
						System.out.println();
						System.out.println("=============================");
						System.out.println("\tReservation Info");
						System.out.println("=============================");
						System.out.println("Table Id: " + myReservation.getTableId());
						System.out.println("Date: " + myReservation.getDate());
						System.out.println("Time: " + myReservation.getArrivalTime());
						System.out.println("Pax: " + myReservation.getPax());
						System.out.println("Name: " + myReservation.getName());
						System.out.println("Contact: " + myReservation.getContactNumber());
						System.out.println();
						case1 = false;
					} else {
						case1 = subMenu();
					}
				}
				break;
			case 2:
				//Get Reservation by contact
				//Check if it exist
				//Allow the user to update
				//Update the Reservation and Table accordingly
				boolean case2 = true;
				while (case2) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					String contact = sc.nextLine();
					//Validate Contact
					while(!(contact.matches("^[0-9]*$") && contact.length() >= 8)) {
						System.out.println();
						System.out.println("Invalid selection, please key numbers & no less than 8 digits");
						System.out.print("Enter contact: ");
						contact = sc.nextLine();
						System.out.println();
					}
					Reservation resItem = new Reservation();
					resItem = res.getReservation(contact);
					if(resItem != null) {		
						System.out.println();
						System.out.println("======================================");
						System.out.println("\tUpdate Reservation");
						System.out.println("======================================");
						System.out.println("1) Name");
						System.out.println("2) Date");
						System.out.println("3) Arrival Time");
						System.out.println("4) Pax");
						System.out.println("5) Contact Number");
						System.out.println("0) Back");
						System.out.print("\nEnter your choice: ");
						int updateselect = sc.nextInt();
						sc.nextLine();
						boolean case2a = true;
						System.out.println();
						Reservation updatedResItem = new Reservation();
						while(case2a) {
						switch (updateselect) {
						case 0:
							case2a = false;
							break;
						case 1:
							System.out.print("Enter new name: ");
							String newName = sc.nextLine();
							while(newName.equals("")) {
								System.out.println();
								System.out.println("Reservation name cannot be empty");
								System.out.print("Enter name: ");
								newName = sc.nextLine();
								System.out.println();
							}
							updatedResItem = new Reservation(resItem.getReservationId(), resItem.getTableId(), resItem.getDate(), resItem.getArrivalTime(), resItem.getPax(), newName, resItem.getContactNumber());
							res.updateReservation(resItem, updatedResItem);
							System.out.println("Name is successfully added!");
							case2a = false;
							break;
						case 2:	
							System.out.print("Enter reservation date (yyyy-mm-dd): ");
							String resDate = sc.nextLine();
							while(!Validation.isDateValid(resDate)) {
								System.out.println();
								System.out.println("Invalid value, please key in a new value");
								System.out.print("Reservation date (yyyy-mm-dd): ");
								resDate = sc.nextLine();
							}
							updatedResItem = new Reservation(resItem.getReservationId(), resItem.getTableId(), resDate, resItem.getArrivalTime(), resItem.getPax(), resItem.getName(), resItem.getContactNumber());
							res.updateReservation(resItem, updatedResItem);
							System.out.println("Date is successfully added!");
							case2a = false;
							break;
						case 3:
							System.out.print("Enter arrival time: ");
							String resArrival = sc.nextLine();
							//Validate Arrival Time
							while((res.checkSession(resArrival) == "Invalid")) {
								System.out.println();
								System.out.println("Invalid time, please key in between 11:00-15:00 or 18:00-22:00");
								System.out.print("Enter arrival time (hh:mm): ");
								resArrival = sc.nextLine();		
							}
							updatedResItem = new Reservation(resItem.getReservationId(), resItem.getTableId(), resItem.getDate(), resArrival, resItem.getPax(), resItem.getName(), resItem.getContactNumber());
							res.updateReservation(resItem, updatedResItem);
							System.out.println("Arrival time is successfully added!");
							case2a = false;
							break;
						case 4:
							System.out.print("Enter pax (0-10): ");
							int resPax = sc.nextInt();
							sc.nextLine();
							System.out.println();
							//Validate Pax
							while(resPax <= 0 || resPax > 10) {
								//System.out.println();
								System.out.println("Invalid pax, please key between 1 to 10 pax");
								System.out.print("Pax (1-10): ");
								resPax = sc.nextInt();
								sc.nextLine();
							}
							updatedResItem = new Reservation(resItem.getReservationId(), resItem.getTableId(), resItem.getDate(), resItem.getArrivalTime(), resPax, resItem.getName(), resItem.getContactNumber());
							res.updateReservation(resItem, updatedResItem);
							System.out.println("Pax is successfully added!");
							case2a = false;
							break;
						case 5:
							System.out.print("Enter reservation contact: ");
							String contactNum = sc.nextLine();
							//Validate Contact
							while(!(contactNum.matches("^[0-9]*$") && contactNum.length() >= 8)) {
								System.out.println();
								System.out.println("Invalid selection, please key numbers & no less than 8 digits");
								System.out.print("Enter contact: ");
								contactNum = sc.nextLine();
								System.out.println();
							}
							updatedResItem = new Reservation(resItem.getReservationId(), resItem.getTableId(), resItem.getDate(), resItem.getArrivalTime(), resItem.getPax(), resItem.getName(), contactNum);
							res.updateReservation(resItem, updatedResItem);
							System.out.println("Contact Number is successfully added!");
							case2a = false;
							break;
						default:
							System.out.println("Invalid input. Please enter between 0-5. ");
							System.out.print("\nEnter your choice: ");
							updateselect = sc.nextInt();
							sc.nextLine();
							break;
							}
						}
						case2 = false;
						System.out.println();
					}
					else {
						case2 = subMenu();
					}
				}
				break;
			case 3:
				//Get Reservation by contact
				//Check if it exist
				//Delete Reservation and Table
				boolean case3 = true;
				System.out.println("=========================================");
				System.out.println("\tDeleting Reservation");
				System.out.println("=========================================");
				while(case3) {
					System.out.println();
					System.out.print("Enter reservation contact: ");
					String contact = sc.nextLine();
					Reservation resItem = new Reservation();
					resItem = res.getReservation(contact);			
					if(resItem != null) {
						res.deleteReservation(resItem);
						System.out.println("Reservation successfully removed!");
						case3 = false;
					}
					else {
						case3 = subMenu();
					}					
				}
				break;
			default:
				System.out.println("Invalid input. Please enter again. ");
				break;
			}
		}		
	}
	
	public static boolean subMenu(){
		boolean result = true;
		Scanner sc = new Scanner(System.in);
		System.out.println("Contact number not found.");
		System.out.println();
		System.out.println("1. Search again");
		System.out.println("2. Exit");
		System.out.print("Choice: ");
		int select = sc.nextInt();
		sc.nextLine();
		if(select == 1) {
			result = true;
		}
		else if(select == 2) {
			result = false;
		}
		return result;
	}
}