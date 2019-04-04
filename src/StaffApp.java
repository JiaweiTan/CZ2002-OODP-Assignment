import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffApp {
	final static  String filename = "StaffList.txt";
	public static void main() throws IOException {
		int  OperationInput;
		Scanner sc = new Scanner(System.in);
		Staff staff=new Staff();
		Validation inputValidation= new Validation();

		System.out.println();
		System.out.println();
		System.out.println("=============================");
		System.out.println("\t Staff Menu");
		System.out.println("=============================");
		System.out.println("1) Create new Staff");
		System.out.println("2) Update Staff");
		System.out.println("3) Delete Staff");
		System.out.println("4) View Staff");
		System.out.println("0) Back");
		System.out.print("\nEnter your choice: ");

		OperationInput = sc.nextInt();
		sc.nextLine();
		switch (OperationInput) {
		case 1:
			
			
			System.out.println();
			System.out.println("=================================");
			System.out.println("\tCreating Staff");
			System.out.println("=================================");

			System.out.print("Staff ID:");
			
			System.out.print(staff.SystemGeneratedID(filename));		
			System.out.println();
			System.out.print("Name:");
			String tempName = sc.nextLine();

			System.out.print("Gender(M/F):");
			char tempGender = sc.next().charAt(0); sc.nextLine();

			System.out.print("Contact:");
			String tempContact = sc.nextLine();
			
			System.out.print("Email:");
			String tempEmail = sc.nextLine();

			System.out.print("Address:");
			String tempAdress = sc.nextLine();

			System.out.print("Shift(AM/PM):");
			String tempShift = sc.nextLine();

			System.out.print("Job Title:");
			String tempJobTitle = sc.nextLine();
			
			
			staff.addPromoItems(new Staff(staff.SystemGeneratedID(filename), tempName, tempGender, tempContact, tempEmail,tempAdress, tempShift, tempJobTitle));
	
			
			StaffApp.main();
			break;
		case 2:
		
			int UpdateInput=0;
			int staffId = 0;
			
			System.out.println();
			System.out.println();
			System.out.println("======================================");
			System.out.println("\t Update Staff Menu");
			System.out.println("======================================");
			System.out.println("1) Contact");
			System.out.println("2) Email");
			System.out.println("3) Address");
			System.out.println("4) Shift");
			System.out.println("5) Job Title");
			System.out.println("0) Back");
			System.out.print("\nEnter your choice: ");
			
			UpdateInput = sc.nextInt();
			
			sc.nextLine();
			
			System.out.println("Promotion ID:");
			staffId = sc.nextInt();
			sc.nextLine();
			switch (UpdateInput) {

			case 1:
				staff.updateInfo(staffId, 1);
				
				break;
			case 2:
				staff.updateInfo(staffId, 2);
				break;
			case 3:
				staff.updateInfo(staffId, 3);
				break;
			case 4:	
				staff.updateInfo(staffId, 4);
				break;
			case 5:
				staff.updateInfo(staffId, 5);
				break;
			default:

				break;
			}		
			
			StaffApp.main();
			break;
		case 3:
			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tDeleting Items from Staff");
			System.out.println("=============================================");
			System.out.print("Enter Staff ID: ");
			int removeID = sc.nextInt();
			sc.nextLine();
			staff.remove(removeID);
			
			
			StaffApp.main();
			break;
		case 4: 
			
			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tViewing Staff");
			System.out.println("=============================================");
			
			ArrayList<Staff> resItem = new ArrayList<Staff>();
			resItem = staff.readStaffInfo(filename);
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
												
			System.out.printf("%5s %10s %20s %10s %20s %30s  %50s %20s %10s", "Index", "Staff ID", "Name", "Gender","Contact", "Email", "Adress","Shift","Job Title");
			System.out.println();
			int index = 1;
			for (Staff g : resItem) {

				System.out.format("%5s %10s %20s %10s %20s %30s %50s %20s %10s", index, g.getEmployeeId(), g.getName(),g.getGender(), g.getContact(), g.getEmail(), g.getAddress(),g.getShift(),g.getJobTitle());
				index++;
				System.out.println();

			}
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
			StaffApp.main();
			break;
		default:
			
			break;
		}
	}
}
