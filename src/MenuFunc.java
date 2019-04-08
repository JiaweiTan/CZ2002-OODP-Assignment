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
	Scanner sc = new Scanner(System.in);
		
	public static void displayMenu()  {
    	MenuFunc menuFunc = new MenuFunc();
    	String filename = "OutputMenu.txt" ;
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
	String filename = "OutputMenu.txt";
	
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


	public static void createMenu() throws IOException 
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=================================");
       		System.out.println("\t Creating Menu Item");
		System.out.println("=================================");
		
		System.out.print("Enter the ID of the item: ");
		int food_ID = sc.nextInt(); sc.nextLine(); //must do
		
		System.out.print("Enter the name of the item: ");
		String food_name = sc.nextLine();
		
		System.out.print("Enter the price of the item: ");
		double food_price = sc.nextInt(); sc.nextLine(); //must do
		
		System.out.print("Enter the type of the item: ");
		String food_type = sc.nextLine();
		
		System.out.print("Enter the description of the item: ");
		String food_desc = sc.nextLine();
		
		
		System.out.print("Enter the quota of the item: ");
		int food_quota = sc.nextInt(); sc.nextLine(); //must do
		
		String filepath = "menuRecord.txt";
		
		saveRecord(food_ID, food_name, food_price, food_type, food_desc, food_quota, filepath);
		
	}
	
	public static void saveRecord(int food_ID, String food_name, double food_price,String food_type,String food_desc,int food_quota, String filepath) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int cont;

		try {
			FileWriter writer = new FileWriter(filepath,true);
			BufferedWriter bw = new BufferedWriter(writer);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(food_ID+"|"+ food_name+"|"+ food_type+"|"+ food_price+"|"+ food_desc+"|"+ food_quota);
			pw.flush();
			pw.close();
			
			System.out.println("=========================================================");
	        System.out.println("\t Item has been created successfully");
	    	System.out.println("=========================================================");
			System.out.println("The following item has been created:");
			System.out.println("Food ID:     " + food_ID);
			System.out.println("Name:        " + food_name);
			System.out.println("Type:        " + food_type);
			System.out.println("Price:      $" + food_price);
			System.out.println("Description: " + food_desc);
			System.out.println("Quota:       " + food_quota);
			System.out.println("=========================================================");
			
			System.out.print("Do you want to write in more information? (Y-1/N-0): ");
			cont = sc.nextInt();
			sc.nextLine(); //must do
			
			if (cont == 1) {
				createMenu();
			}
			
		}
		catch(Exception E) {
			JOptionPane.showConfirmDialog(null, "Record not saved message");
		}
	}
		
	//3. deletes an item
	public void deleteMenu() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the term to remove of the item: ");
		String removeTerm = sc.nextLine();
		String removeTerm = "6009";
		removeRecord(filepath,removeTerm);
	}
		
	public static void removeRecord(String filepath, String removeTerm) throws IOException {
		String tempFile = "temp.txt";
		
		File oldFile = new File(filepath); //original file is made old
		File newFile = new File(tempFile); //temp file is made new
		Scanner checker;
		
		String ID = ""; String name = ""; String type =""; 
		String price = ""; String desc = ""; String quota ="";

		try {
			FileWriter fw = new FileWriter(tempFile,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
				
			checker = new Scanner(new File(filepath));//reads the file
			checker.useDelimiter("[|\n]");
				
			while (checker.hasNext()) {
				ID = checker.next();
				name = checker.next();
				type = checker.next();
				price = checker.next();
				desc = checker.next();
				quota = checker.next();
					
				if (!ID.equals(removeTerm)) {
					pw.println(ID + "|"+name+"|"+type+"|"+price+"|"+desc+"|"+quota);
				}else {
					System.out.println(ID + "|"+name+"|"+type+"|"+price+"|"+desc+"|"+quota);
				}
			}
					
			pw.flush();
			pw.close();
			checker.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);

			System.out.println("=========================================================");
		    System.out.println("\t Item has been deleted successfully");
		    System.out.println("=========================================================");
		
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
		
	public void updateMenu(){
		String filepath = "menuRecord.txt";
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the targetID of the item: ");
		String targetTerm = sc.nextLine();
		
		System.out.print("Enter the name of the item: ");
		String newName = sc.nextLine();
		
		System.out.print("Enter the type of the item: ");
		String newType = sc.nextLine();
		
		System.out.print("Enter the price of the item: ");
		String newPrice = sc.nextLine();
		
		System.out.print("Enter the description of the item: ");
		String newDesc = sc.nextLine();
		
		System.out.print("Enter the quota of the item: ");
		String newQuota = sc.nextLine();
		
		System.out.print("Enter the ID of the item: ");
		String newID = sc.nextLine();

		editRecord (filepath, targetTerm, newID, newType,newName,newPrice, newDesc, newQuota);
	}

	
	public static void editRecord(String filepath, String targetTerm, String newID, String newName, String newType, String newPrice, String newDesc, String newQuota){
		String tempFile = "temp.txt";
		File oldFile = new File (filepath);
		File newFile = new File (tempFile);
		Scanner check;
		
		String ID = ""; String name = ""; String type = ""; String price =""; String desc =""; String quota="";
		
		try{
				FileWriter fw = new FileWriter(tempFile,true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
					
				check = new Scanner(new File(filepath));//reads the file
				check.useDelimiter("[|\n]"); 
				
				while (check.hasNext()) {
					ID = check.next();
					name = check.next();
					type = check.next();
					price = check.next();
					desc = check.next();
					quota = check.next();
						
					if (ID.equals(targetTerm)){ 
						pw.println(newID + "|"+newName+"|"+newType + "|"+newPrice+ "|"+ newDesc+ "|"+newQuota);
					}else{
						pw.println(ID + "|"+name+"|"+type+"|"+price+ "|"+desc+ "|"+quota);
					}
						
				}
					check.close();
					pw.flush();
					pw.close();
					oldFile.delete();
					File dump = new File(filepath);
					newFile.renameTo(dump);
					System.out.println("=========================================================");
			        System.out.println("\t Item has been edited successfully");
			    	System.out.println("=========================================================");

				}
				catch (Exception e) {
					System.out.println("=========================================================");
					JOptionPane.showMessageDialog(null, "\tItem was not able to update");
					System.out.println("=========================================================");
				}
	}
}

		public static int updateItemQuota (int itemId, int addDel) throws IOException {
			
			String filepath = "OutputMenu.txt";
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
