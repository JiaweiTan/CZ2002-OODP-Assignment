import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class MainMenu {
	
	public static void main (String[] arg) throws IOException, ParseException {
		Scanner sc = new Scanner(System.in);
		int sel;
		
		do {
			System.out.println();
			System.out.println("\t Welcome to 4 Star Michelin Restaurant");
			System.out.println("=========================================================");
			System.out.println("1. Create Order");
			System.out.println("2. Update/Remove/View Order");
			System.out.println("---------------------------------------------------------");
			System.out.println("3. Create Reservation booking");
			System.out.println("4. Update/Remove/View Reservation booking");
			System.out.println("---------------------------------------------------------");
			System.out.println("5. View table availability");
			System.out.println("---------------------------------------------------------");			
			System.out.println("6. Create/Update/Remove/View menu item.");
			System.out.println("---------------------------------------------------------");
			System.out.println("7. Create/Update/Remove/View Promotion item. ");
			System.out.println("---------------------------------------------------------");
			System.out.println("8. Create/Update/Remove/View Staff ");
			System.out.println("---------------------------------------------------------");
			System.out.println("9. Billing");
			System.out.println("---------------------------------------------------------");
			System.out.println("10. Update Membership");
			System.out.println("---------------------------------------------------------");
			System.out.println("0. Exit");
			System.out.println("=========================================================");
			System.out.println();
			System.out.print("Enter your choice: ");
			sel = sc.nextInt();
			
			switch(sel) {
				case 0:
					break;
				case 1:
					OrderApp.createOrder();
					break;
				case 2:
					OrderApp.updateOrder();
					break;
				case 3:
					ReservationApp.createReservation();
					break;
				case 4:
					ReservationApp.updateReservation();
					break;
				case 5:
					TableApp.menu();
					break;
				case 7:
					PromoSetApp.main();
					break;
				case 8:
					StaffApp.main();
					break;
				case 9: 
					BillingApp.main(null);
					break;
				case 10:
					CustomerApp.updateCustomerDetails();
					break;
				default:
					System.out.println("Invalid input. Please enter again.\n");
					break;
			}

		} while (sel != 9);
	}
}
