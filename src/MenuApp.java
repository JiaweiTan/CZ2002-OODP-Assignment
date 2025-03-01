//package rrpss;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/** skeleton of all the functions*/

public class MenuApp {
	
	public static void main(String[] args) throws IOException, ParseException {
		int choice = -1;
		MenuFunc menuFunc = new MenuFunc();
		Scanner sc = new Scanner(System.in);
		
		do {
		System.out.println("=================================");
        System.out.println("\t Menu Options");
		System.out.println("=================================");
		System.out.println("1) Create a new menu item.");
		System.out.println("2) Update a menu item. ");
		System.out.println("3) Delete a menu item. ");
		System.out.println("4) Display the full menu.");
		System.out.println("5) Back");
			
			do {
				try {
					System.out.print("Please enter your choice : ");
					choice = sc.nextInt();
					
				}catch(InputMismatchException e) {
					//System.out.println("");
					System.out.println("=================================");
					System.out.println("Invalid Entry has been entered. ");
					System.out.println("Please enter numbers only. ");
					System.out.println("=================================");
			        System.out.println("\t Menu Options");
					System.out.println("=================================");
					System.out.println("1) Create a new menu item.");
					System.out.println("2) Update a menu item. ");
					System.out.println("3) Delete a menu item. ");
					System.out.println("4) Display the full menu.");
					System.out.println("5) Back");
					System.out.println("");
				}
				sc.nextLine();
			}while (choice == -1);


			switch (choice) {
			case 1: 
				/**System.out.print("Enter foodID to create from :");
				int foodID1 = sc.nextInt();
				sc.nextLine(); */
				menuFunc.createMenu();
				break;
				
			case 2: 
				System.out.println("=================================");
		        System.out.println("\t Update Menu");
				System.out.println("=================================");
				menuFunc.updateMenu();
				break;
				
			case 3:
				/**System.out.print("Enter foodID to delete from : ");
				int foodID2 = sc.nextInt();*/
				System.out.println("=================================");
		        System.out.println("\t Delete Menu");
				System.out.println("=================================");
				menuFunc.deleteMenu();
				break; 
			
			case 4:
				menuFunc.displayMenu();
				break;
				
			case 5: 
				MainMenu.main(null);
				break;
			
			default:
		    	System.out.println("=========================================================");
				System.out.println("\tInvalid input. Please enter again!");
		    	System.out.println("=========================================================");

			}
		}while (choice <5||choice>5);
	}
}
