import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class CustomerApp {

	public static void updateCustomerDetails() throws IOException {
		int sel, memId;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println();
			System.out.println();
			System.out.println("===========================================");
			System.out.println("\t\tMembership");
			System.out.println("===========================================");
			System.out.println("1) View membership details");
			System.out.println("2) Update member's details");
			System.out.println("3) Terminate membership");
			System.out.println("0) Back");
			System.out.println();
			System.out.print("Enter your choice: ");
			
			sel = sc.nextInt();
			
			switch(sel) {
				case 0:
					break;
				case 1:
					System.out.print("Membership ID\t: ");
					memId = sc.nextInt();
					Customer.viewCustomer(memId);
					break;
				case 2:
					System.out.print("Membership ID\t: ");
					memId = sc.nextInt();
					if(Validation.customerExistsDB(memId) < 0) {
						System.out.println("Invalid Membership ID. Please enter a valid ID.");
					}
					else {
						Customer cs = Customer.getCustomer(memId);
						System.out.print("Name\t\t: ");
						cs.setName(sc.next());
						System.out.print("Contact Number\t: ");
						cs.setName(sc.next());
						DBManager.saveCustomerDetails(cs);
						System.out.print("Member's details successfully updated!");
					}
					break;
				case 3:
					System.out.print("Membership ID\t: ");
					memId = sc.nextInt();
					if(Validation.customerExistsDB(memId) < 0) {
						System.out.println("Invalid Membership ID. Please enter a valid ID.");
					}
					else {
						Customer cs = Customer.getCustomer(memId);
						System.out.print("Are you sure to terminate this membership? (Y/N): ");
						String yn = sc.next();
						if(yn.equalsIgnoreCase("y")) {
							cs.setExpiry(LocalDate.now().minusDays(1).toString());
							DBManager.saveCustomerDetails(cs);
							System.out.print("Member " + memId + " has been terminated!");
						}
						else if (!yn.equalsIgnoreCase("n")) {
							System.out.println("Invalid input. Please enter again.\n");
						}
					}
					break;
				default:
					System.out.println("Invalid input. Please enter again.\n");
			}
		} while(sel!=0);
	}
}
