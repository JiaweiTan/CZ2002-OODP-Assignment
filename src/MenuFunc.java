//package rrpss;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class MenuFunc extends MenuApp {
	Scanner sc = new Scanner(System.in);
		
	public static void displayMenu()  {
    	MenuFunc menuFunc = new MenuFunc();
    	String filename = "outputMenu.txt" ;
		try {
			// read file containing Professor records.
			ArrayList al = MenuFunc.getMenu(filename) ;
			for (int i = 0 ; i < al.size() ; i++) {
					Menu menuItems = (Menu)al.get(i);
					System.out.print((i+1) +". ID:" + menuItems.getFoodID() );
					System.out.print("  Dish:" + menuItems.getFoodName() );
					System.out.print("  Type:" + menuItems.getFoodType() );
					System.out.print("  Price:$" + menuItems.getFoodPrice() );
					System.out.print("  Description:" + menuItems.getFoodDesc() );
					System.out.print("  Quantity sold per day:" + menuItems.getFoodQuota() );
					System.out.println("");
			}
		}catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
  }
	
	public static final String SEPARATOR = "|";
	String filename = "outputMenu.txt";
	
    // getMenu()
	public static ArrayList getMenu(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				//edit this part 
				int foodID = Integer.parseInt(star.nextToken().trim());	// first token
				String foodName = star.nextToken().trim();	// second token
				String foodType = star.nextToken().trim();	// third token
				int foodPrice = Integer.parseInt(star.nextToken().trim());	// fourth token
				String foodDesc = star.nextToken().trim();	// fifth token
				int foodQuota = Integer.parseInt(star.nextToken().trim());	// sixth token
				
				// create menu object from file data
				Menu menuItems = new Menu(foodID, foodName,foodType, foodPrice, foodDesc, foodQuota);
				
				// add to Menu list
				alr.add(menuItems) ;
			}
			return alr ;
	}

	 /** Read the contents of the given file. */
	public static List read(String fileName) throws IOException {
			List data = new ArrayList() ;
		    Scanner scanner = new Scanner(new FileInputStream(fileName));
		    try {
		      while (scanner.hasNextLine()){
		        data.add(scanner.nextLine());
		      }
		    }
		    finally{
		      scanner.close();
		    }
		    return data;
		  }

		public static void createMenu(int foodID) throws IOException {
			
			File file = new File("outputMenu.txt");
			FileWriter writer = new FileWriter("outputMenu.txt",true);
			BufferedWriter bwriter = new BufferedWriter(writer);
			Scanner sc = new Scanner(System.in);
			int cont;
			
			do {
			System.out.print("Enter the name of the item: ");
			String food_name = sc.nextLine();
			
			System.out.print("Enter the price of the item: ");
			int food_price = sc.nextInt();
			sc.nextLine(); //must do 
			
			System.out.print("Enter the type of the item: ");
			String food_type = sc.nextLine();
			
			System.out.print("Enter the description of the item: ");
			String food_desc = sc.nextLine();
			
			
			System.out.print("Enter the quota of the item: ");
			int food_quota = sc.nextInt();
			sc.nextLine();
			
			System.out.println("\nThe following item has been created: \nItemID|Name|Type|Price|Desc|Quota\n" + foodID +"|"+ food_name +"|"+ food_price +"|"+ food_type +"|"+ food_desc +"|"+ food_quota+ "\n");
			bwriter.write(foodID +"|"+ food_name +"|"+ food_type +"|"+ food_price +"|"+ food_desc +"|"+ food_quota);
			bwriter.newLine();
			//checks if another input is going to be required else breaks
			
			System.out.print("Do you want to write in more information? (Y-1/N-0): ");
			cont = sc.nextInt();
			sc.nextLine(); //must do 
			
			}while(cont != 0);
			
			System.out.print("--------- Main Menu --------\n");
			bwriter.close();
			writer.close();
		}
		
		//3. deletes an item
		public void deleteMenu(int foodID2) {
			String filepath = "outputMenu.txt";
			String removeTerm = Integer.toString(foodID2);
			
			removeRecord(filepath, removeTerm);
		}
		
		private static Scanner scan;
		
		public static void removeRecord(String filepath, String removeTerm) {
			String tempFile = "temp.txt";
			File oldFile = new File(filepath);
			File newFile = new File(tempFile);
			String ID = ""; String name = ""; String type =""; String price = ""; String desc = ""; String quota ="";
			
			try {
				FileWriter fw = new FileWriter(tempFile,true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				scan = new Scanner(new File(filepath));//reads the file
				scan.useDelimiter("[|\n]");
				
				while (scan.hasNext()) {
					ID = scan.next();
					name = scan.next();
					type = scan.next();
					price = scan.next();
					desc = scan.next();
					quota = scan.next();
					
					if (!ID.equals(removeTerm)) {
						pw.println(ID+"|"+name+"|"+type+"|"+price+"|"+desc+"|"+quota);
					}
				}
				
				scan.close();
				pw.flush();
				pw.close();
				oldFile.delete();
				File dump = new File(filepath);
				newFile.renameTo(dump);
				
				System.out.println("---- Item has been deleted! ----");
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error");
			}
		}
		
		//2. updates an item
		public void updateMenu() {
			
			String filepath = "outputMenu.txt";
			
			System.out.println("Enter the ID of the items to be updated: ");
			String editTerm = sc.nextLine();
			
			System.out.println("Enter the new ID: ");
			String newID = sc.nextLine();
			
			System.out.println("Enter the Name: ");
			String newName = sc.nextLine();
			
			System.out.println("Enter the Type: ");
			String newType = sc.nextLine();
			
			System.out.println("Enter the Price");
			String newPrice = sc.nextLine();
			
			System.out.println("Enter the Description: ");
			String newDesc = sc.nextLine();
			
			System.out.println("Enter the Quota");
			String newQuota = sc.nextLine();
			
			editRecord(filepath, editTerm, newID, newName, newType, newPrice, newDesc, newQuota);
		}
		
		public static void editRecord(String filepath,String editTerm,String newID,String newName,String newType,String newPrice, String newDesc, String newQuota) {
			
			String tempFile = "temp.txt";
			File oldFile = new File(filepath);
			File newFile = new File(tempFile);
			String ID = ""; String name = ""; String type =""; String price = ""; String desc = ""; String quota ="";
		
			try
			{
				FileWriter fw = new FileWriter(tempFile,true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				scan = new Scanner(new File(filepath));//reads the file
				scan.useDelimiter("[|\n]");
				
				while (scan.hasNext()) {
					ID = scan.next();
					name = scan.next();
					type = scan.next();
					price = scan.next();
					desc = scan.next();
					quota = scan.next();
					
					if (!ID.equals(editTerm)) {
						
						pw.println(ID+"|"+name+"|"+type+"|"+price+"|"+desc+"|"+quota);
					}
					else
					{
						pw.println(newID+"|"+newName+"|"+newType+"|"+newPrice+"|"+newDesc+"|"+newQuota);
			
					}
				}
				scan.close();
				pw.flush();
				pw.close();
				oldFile.delete();
				File dump = new File(filepath);
				newFile.renameTo(dump);
				
				System.out.println("---- Item has been updated! ----");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error");
			}
		}
		
		public static int updateItemQuota (int itemId, int addDel) throws IOException {
			
			String filepath = "outputMenu.txt";
			List<Menu> mnLst = getMenu(filepath);
			
			for(Menu mn: mnLst) {
				if(itemId == mn.getFoodID()) {
					if(addDel==1) {
						if(mn.getFoodQuota()>0) {
							mn.setFoodQuota(mn.getFoodQuota()-1);
						}
						else {
							return -1;
						}
					}
					else {
						mn.setFoodQuota(mn.getFoodQuota()+1);
					}
					break;
				}
			}
			
			List alw = new ArrayList();

			for (int i = 0; i < mnLst.size(); i++) {
				Menu mn = (Menu) mnLst.get(i);
				StringBuilder st = new StringBuilder();
				st.append(mn.getFoodID());
				st.append(SEPARATOR);
				st.append(mn.getFoodName());
				st.append(SEPARATOR);
				st.append(mn.getFoodType());
				st.append(SEPARATOR);
				st.append(mn.getFoodPrice());
				st.append(SEPARATOR);
				st.append(mn.getFoodDesc());
				st.append(SEPARATOR);
				st.append(mn.getFoodQuota());
				alw.add(st.toString());
			}
			DBManager.write(filepath, alw);
			
			return 0;
		}
}
