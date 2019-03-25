import java.util.Scanner;

public class MainMenu {
	
	public static void main (String[] arg) {
		Scanner sc = new Scanner(System.in);
		int sel;
		
		do {
			System.out.println("1.Create Order");
			System.out.println("2.Update/Remove/View Order");
			System.out.println("==================================");
			System.out.println("3.Create Reservation booking");
			System.out.println("4.Update/Remove/View Reservation (Check table availability) booking");
			System.out.println("==================================");
			System.out.println("5.Create/Update/Remove/View menu item.");
			System.out.println("==================================");
			System.out.println("6.Create/Update/Remove/View Promotion item. ");
			System.out.println("==================================");
			System.out.println("7.Create/Update/Remove/View Staff ");
			System.out.println("==================================");
			System.out.println("8.Billing");
			System.out.println("View bill");
			System.out.println("Print bill");
			System.out.println("Sale Revenue Report");
			System.out.println("==================================");
			System.out.println("9. Exit");
			
			sel = sc.nextInt();
			
			switch(sel) {
				case 0:
				default:
					System.out.println("Invalid input. Please enter again.");
			}

		} while (sel != 9);
	}
}
