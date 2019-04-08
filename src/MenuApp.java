//package rrpss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** skeleton of all the functions*/

public class MenuApp {
	
	public static void main(String[] args) throws IOException {
		int choice = 0;
		MenuFunc menuFunc = new MenuFunc();
		EditRecord editRecord = new EditRecord();
		Scanner sc = new Scanner(System.in);
		
		//BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt"));

		do {
			System.out.println("Perform the following methods:");
			System.out.println("1: Create a new menu item.");
			System.out.println("2: Update a menu item. ");
			System.out.println("3: Delete a menu item. ");
			System.out.println("4: Display the full menu.");
			System.out.println("5: Back");
			System.out.print("Please enter your choice : ");
			
			choice = sc.nextInt();
			
			switch (choice) {
			case 1: 
				/**System.out.print("Enter foodID to create from :");
				int foodID1 = sc.nextInt();
				sc.nextLine(); */
				menuFunc.createMenu();
				break;
				
			case 2: 
				menuFunc.updateMenu();
				break;
				
			case 3:
				/**System.out.print("Enter foodID to delete from : ");
				int foodID2 = sc.nextInt();*/
				menuFunc.deleteMenu();
				break; 
			
			case 4:
				menuFunc.displayMenu();
				break;
				
			case 5: 
				PromoSetApp.main();
			
			default:
		    	System.out.println("=========================================================");
				System.out.println("\tInvalid input. Please enter again!");
		    	System.out.println("=========================================================");

			}
		 } while (choice < 5 || choice >5);
	
	}
}
