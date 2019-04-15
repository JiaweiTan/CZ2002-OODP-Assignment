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

		//	System.out.print("Staff ID:");
			
		//	System.out.print(staff.SystemGeneratedID(filename));		
		//	System.out.println();
			System.out.print("Name:");
			String tempName = sc.nextLine();

			System.out.print("Gender(M/F):");
			char tempGender = sc.next().toUpperCase().charAt(0); sc.nextLine();
			
			while(!(tempGender=='M' || tempGender=='F')) {
				System.out.println();
				System.out.println("Invalid input, please key in a new input");
				System.out.print("Gender Format (M/F): ");
				tempGender = sc.next().charAt(0); sc.nextLine();
			}
			

			System.out.print("Contact:");
			String tempContact = sc.nextLine();
			while(!(tempContact.matches("^[0-9]*$") && tempContact.length() >= 8)) {
				System.out.println();
				System.out.println("Invalid input, please key numbers & no less than 8 digits");
				System.out.print("Enter contact: ");
				tempContact = sc.nextLine();
			}
			
			System.out.print("Email:");
			String tempEmail = sc.nextLine();

			System.out.print("Address:");
			String tempAdress = sc.nextLine();

			System.out.print("Shift(AM/PM):");
			String tempShift = sc.nextLine();
			tempShift=tempShift.toUpperCase();
			//Validate Session
			while(!(tempShift.equals("AM") || tempShift.equals("PM"))) {
				System.out.println();
				System.out.println("Invalid session, please key in a new session");
				System.out.print("Session Format (AM/PM): ");
				tempShift = sc.nextLine();
			}
			

			System.out.print("Job Title:");
			String tempJobTitle = sc.nextLine();
			
			Staff newitem=new Staff(staff.SystemGeneratedID(filename), tempName, tempGender, tempContact, tempEmail,tempAdress, tempShift, tempJobTitle);	
			staff.addPromoItems(newitem);
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("\t     Staff successfully created! ");
			System.out.println("=========================================================");
			System.out.println("Staff ID: " + newitem.getID());
			System.out.println("Name: " + newitem.getName());
			System.out.println("Gender: " + newitem.getGender());
			System.out.println("Contact: " + newitem.getContact());
			System.out.println("Email: " + newitem.getEmail());
			System.out.println("Adress: " + newitem.getAddress());
			System.out.println("Shift: " + newitem.getShift());
			System.out.println("Job Title: " + newitem.getShift());
			System.out.println("=========================================================");
			System.out.println();
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
			
			System.out.println("Staff ID:");
			staffId = sc.nextInt();
			sc.nextLine();
			if(UpdateInput==0)
			{
				StaffApp.main();
			}
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
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
												
			System.out.printf("%5s %10s %20s %10s %20s %30s  %50s %20s %20s", "Index", "Staff ID", "Name", "Gender","Contact", "Email", "Adress","Shift","Job Title");
			System.out.println();
			int index = 1;
			for (Staff g : resItem) {

				System.out.format("%5s %10s %20s %10s %20s %30s %50s %20s %20s", index, g.getID(), g.getName(),g.getGender(), g.getContact(), g.getEmail(), g.getAddress(),g.getShift(),g.getJobTitle());
				index++;
				System.out.println();

			}
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
			StaffApp.main();
			break;
		default:
			
			break;
		}
	}
}
