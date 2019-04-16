//package rrpss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class MenuFunc extends MenuApp {
	Scanner sc = new Scanner(System.in);
	static int indexCounter = 6004;	
	
	public void displayMenu()  {
    	String filepath = "outputMenu.txt";
    	
    	//checks if file is empty and returns an error
        File file = new File(filepath);
        if (file.length() == 0) {
			System.out.println("=================================");
            System.out.println("File is empty, please add items into the Menu");
			System.out.println("=================================");
        }else {
        	new MenuFunc();
        	int index = 1;
    		try {
			    System.out.println("===================================================================================================================================");
				System.out.println("\t\t\t\t\t\t Displaying all items");
			    System.out.println("===================================================================================================================================");
				System.out.printf("%5s %10s %25s %15s %15s %45s %10s", 
						"Index", "Food ID", "Name", "Type", "Price", "Description", "Quota");
 
				ArrayList al = MenuFunc.getMenu(filepath) ;
    			for (int i = 0 ; i < al.size() ; i++) {
    					Menu menuItems = (Menu)al.get(i);
    			    	System.out.println("");
    			    	System.out.format("%5s %10s %25s %15s %15s %45s %10s", index,
    			    			menuItems.getFoodID(), 
    			    			menuItems.getFoodName() ,menuItems.getFoodType(),menuItems.getFoodPrice(), 
    			    			menuItems.getFoodDesc(), menuItems.getFoodQuota());
    					index++;
    					System.out.println();

    			}
    	    	System.out.println("===================================================================================================================================");
    			System.out.println("\t\t\t\t\t\t All items have been displayed!");
    	    	System.out.println("===================================================================================================================================");

    		}catch (IOException e) {
    			System.out.println("IOException > " + e.getMessage());
    		}
        }
	}
	
	public static final String SEPARATOR = "|";
	
	public static ArrayList getMenu(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList arrayList = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				//edit this part 
				int foodID = Integer.parseInt(star.nextToken().trim());	// first token
				String foodName = star.nextToken().trim();	// second token
				String foodType = star.nextToken().trim();	// third token
				double foodPrice = Double.parseDouble(star.nextToken().trim());	// fourth token
				String foodDesc = star.nextToken().trim();	// fifth token
				int foodQuota = Integer.parseInt(star.nextToken().trim());	// sixth token
				
				// create menu object from file data
				Menu menuItems = new Menu(foodID, foodName,foodType, foodPrice, foodDesc, foodQuota);
				
				// add to Menu list
				arrayList.add(menuItems) ;
			}
			return arrayList ;
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
        
 
	public static void createMenu() throws IOException{
		int indexCounter = 6004;
		Scanner sc = new Scanner(System.in);
		boolean create = true;

		int cont = 0;
		
		System.out.println("=================================");
			System.out.println("\t Creating Menu Item");
		System.out.println("=================================");

		int food_ID = indexCounter;

		//do {
			System.out.print("Enter the name of the item: ");
			String food_name = sc.nextLine();			
			
			double food_price = 0;
			do {
				try {
					System.out.print("Enter the price of the item: $");
					food_price = sc.nextDouble();//must do
					
				}catch(InputMismatchException e) {
					System.out.println("=================================");
					System.out.println("Invalid price has been entered.");
					System.out.println("Please enter numbers only. ");
					System.out.println("=================================");
				}
				sc.nextLine();
			}while (food_price == 0);
			
			int type_choice = -1;		
			String food_type = null;
			do {
				try {
					System.out.println("Enter the type of the item : ");
					System.out.println("1) Main Course ");
					System.out.println("2) Drinks ");
					System.out.println("3) Desserts ");
					System.out.print("Option selected: ");
					type_choice = sc.nextInt();
					
					switch(type_choice) {
					case 1: 
						food_type = "Main Course";
						break;
					case 2:
						food_type = "Drinks";
						food_ID +=200;
						break;
					case 3: 
						food_type = "Desserts";
						food_ID +=100;
						break;
					default:
						System.out.println("=================================");
						System.out.println("Invalid Entry has been entered. ");
						System.out.println("Please enter 1/2/3 only. ");
						System.out.println("=================================");
					}
					
				}catch(InputMismatchException e) {
					//System.out.println("");
					System.out.println("=================================");
					System.out.println("Invalid quantity has been entered. ");
					System.out.println("Please enter 1/2/3 only. ");
					System.out.println("=================================");
					System.out.println("");
				}
				sc.nextLine();
			}while (type_choice == -1 ||type_choice >3);
			
			System.out.print("Enter the description of the item: ");
			String food_desc = sc.nextLine();
			
			int food_quota = -1;		
				do {
					try {
						System.out.print("Enter the quota of the item: ");
						food_quota = sc.nextInt();
						
					}catch(InputMismatchException e) {
						//System.out.println("");
						System.out.println("=================================");
						System.out.println("Invalid quantity has been entered. ");
						System.out.println("Please enter numbers only. ");
						System.out.println("=================================");
					}
					sc.nextLine();
				}while (food_quota == -1);
			
			
				String tempFile = "temp.txt";
				String filepath ="menuRecord.txt";
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
							
						System.out.println(Integer.valueOf(ID));
						//Integer.valueOf(ID) == food_ID-1
						if(type.equals(food_type) && Integer.valueOf(ID) == food_ID-1) {
							pw.println(ID + "|"+name+"|"+type+"|"+price+ "|"+desc+ "|"+quota);
							pw.println(food_ID + "|"+food_name+"|"+food_type+"|"+food_price+"|"+food_desc+"|"+food_quota);
						}else if (type.equals(food_type)) {
							pw.println(ID + "|"+name+"|"+type+"|"+price+ "|"+desc+ "|"+quota);
						}else {
							pw.println(ID + "|"+name+"|"+type+"|"+price+ "|"+desc+ "|"+quota);
						}
					}
					
					pw.flush(); 
					pw.close(); 
					checker.close();
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "\tIThere is no item with the ID: removeTerm in the database");
				}
				
				oldFile.delete();
				File dump = new File(filepath);
				newFile.renameTo(dump);
				
			      Scanner file1;
				   PrintWriter writer1;

					    try {

					        file1 = new Scanner(new File("menuRecord.txt"));
					        writer1 = new PrintWriter("outputMenu.txt");

					        while (file1.hasNext()) {
					            String line = file1.nextLine();
					            if (!line.isEmpty()) {
					                writer1.write(line);
					                writer1.println("");
					            }
					        }

					        file1.close();
					        writer1.close();

					    } catch (FileNotFoundException ex) {
							JOptionPane.showMessageDialog(null, "\tItem was not able to update");
					    }
				
				System.out.println("=========================================================");
			   	System.out.println("\t Item has been created successfully");
				System.out.println("=========================================================");
				System.out.println("The following item has been created:");
				System.out.println("Food ID:     " + food_ID);
				System.out.println("Name:        " + food_name);
				System.out.println("Type:        " + food_type);
				System.out.println("Price:       $" + food_price);
				System.out.println("Description: " + food_desc);
				System.out.println("Quota:       " + food_quota);
				
				indexCounter++; //increases the value of the index
				}


	public void deleteMenu() throws IOException {
		String filesearch = "outputMenu.txt";
		System.out.print("Enter the targetID of the item: ");
		String removeTerm = sc.nextLine();

		Scanner checker;
		
		String ID = ""; String name = ""; String type =""; 
		String price = ""; String desc = ""; String quota ="";
		
		boolean found = false;
		int confirmation = -1;
		
		try {
			checker = new Scanner(new File(filesearch));//reads the file
			checker.useDelimiter("[|\n]");
				
			while (checker.hasNext()) {
				ID = checker.next();
				name = checker.next();
				type = checker.next();
				price = checker.next();
				desc = checker.next();
				quota = checker.next();
					
				if (!ID.equals(removeTerm)) {
					continue;
				}else {
					found = true;

					do {
						try {
							System.out.print("Are you sure you want to delete "+ removeTerm +"? (Y-1/N-0): ");
							confirmation = sc.nextInt();

						}catch(InputMismatchException e) {
							//System.out.println("");
							System.out.println("=================================");
							System.out.println("Invalid Entry has been entered. ");
							System.out.println("Please enter (1 - Yes / 0 - No) only. ");
							System.out.println("=================================");
							System.out.print("Are you sure you want to delete "+removeTerm+"? (Y-1/N-0): ");
							System.out.println("");
						}
						sc.nextLine();
						
						switch(confirmation) {
						
						case 1: 
							String filepath = "menuRecord.txt";
							removeRecord(filepath,removeTerm);
							break;
						case 2:
							break;
						default:
							System.out.println("=================================");
							System.out.println("Invalid Entry has been entered. ");
							System.out.println("Please enter (1 - Yes / 0 - No) only. ");
							System.out.println("=================================");
							break;
						}
						
					}while (confirmation == -1);
				}
			}
			checker.close();
			if (found == false) {
			System.out.println("=================================");
			System.out.println("Record does not exist.");
			System.out.println("Please enter a valid ID. ");
			//System.out.println("=================================");
			}
			

		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "\tError");
		}
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
				}
			}
			
			pw.flush(); 
			pw.close(); 
			checker.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "\tIThere is no item with the ID: removeTerm in the database");
		}
		
		oldFile.delete();
		File dump = new File(filepath);
		newFile.renameTo(dump);
		
        Scanner file;
        PrintWriter writer;

        try {

            file = new Scanner(new File("menuRecord.txt"));
            writer = new PrintWriter("outputMenu.txt");

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty()) {
                    writer.write(line);
                    writer.println("");
                }
            }
            
            file.close();
            writer.close();
            
			System.out.println("=========================================================");
	        System.out.println("\t Item has been deleted successfully");
	    	System.out.println("=========================================================");
	    	

        } catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "\tItem was not able to delete");
        }
	}
		

	public void updateMenu(){
		String filepath = "menuRecord.txt";
		Scanner sc = new Scanner(System.in);
		
		String ID = ""; String name = ""; String type =""; 
		String price = ""; String desc = ""; String quota ="";
		String filesearch = "outputMenu.txt";
		Scanner checker;
		String targetTerm = null;
		boolean found = false;
		
		do {
		try {
			checker = new Scanner(new File(filesearch));//reads the file
			checker.useDelimiter("[|\n]");
			
			System.out.print("Enter the targetID of the item: ");
			targetTerm = sc.nextLine();
			
			while (checker.hasNext() && !found) {
				
				ID = checker.next();
				name = checker.next();
				type = checker.next();
				price = checker.next();
				desc = checker.next();
				quota = checker.next();
					
				if (!ID.equals(targetTerm)) {
					found = false;
					continue;
				}else {
					System.out.print("Enter the name of the item: ");
					String newName = sc.nextLine();
					
					found = true;
					
					int type_choice = -1;		
					String newType = null;
					do {
						try {
							System.out.println("Enter the type of the item : ");
							System.out.println("1) Main Course ");
							System.out.println("2) Drinks ");
							System.out.println("3) Desserts ");
							System.out.print("Option selected: ");
							type_choice = sc.nextInt();
							
							switch(type_choice) {
							case 1: 
								newType = "Main Course";
								break;
							case 2:
								newType = "Drinks";
								break;
							case 3: 
								newType = "Desserts";
								break;
							default:
								System.out.println("=================================");
								System.out.println("Invalid Entry has been entered. ");
								System.out.println("Please enter 1/2/3 only. ");
								System.out.println("=================================");
							}
							
						}catch(InputMismatchException e) {
							//System.out.println("");
							System.out.println("=================================");
							System.out.println("Invalid quantity has been entered. ");
							System.out.println("Please enter 1/2/3 only. ");
							System.out.println("=================================");
							System.out.println("");
						}
						sc.nextLine();
					}while (type_choice == -1 ||type_choice >3);


					String newPrice = null;
			        boolean numeric = true;
			        
			        while (numeric) {
						System.out.print("Enter the price of the item: $");
						newPrice = sc.nextLine();
						
				        try {
				            Double num = Double.parseDouble(newPrice);
				            numeric = false;
				        } catch (NumberFormatException e) {
				            numeric = true;
							System.out.println("=================================");
							System.out.println("Invalid quantity has been entered. ");
							System.out.println("Please enter number only. ");
							System.out.println("=================================");
				        }
			        }

					System.out.print("Enter the description of the item: ");
					String newDesc = sc.nextLine();
					
					String newQuota = null;
			        boolean number = true;
			        while (number) {
						System.out.print("Enter the quota of the item: ");						
						newQuota = sc.nextLine();
						
				        try {
				            Double num = Double.parseDouble(newQuota);
				            number = false;
				        } catch (NumberFormatException e) {
				            number = true;
							System.out.println("=================================");
							System.out.println("Invalid quantity has been entered. ");
							System.out.println("Please enter number only. ");
							System.out.println("=================================");
				        }
			        }

					String newID = null;
			        boolean valid = true;
			        while (valid) {
						System.out.print("Enter the ID of the item: ");						
						newID = sc.nextLine();
						
				        try {
				            Double num = Double.parseDouble(newQuota);
				            valid = false;
				        } catch (NumberFormatException e) {
				            valid = true;
							System.out.println("=================================");
							System.out.println("Invalid quantity has been entered. ");
							System.out.println("Please enter number only. ");
							System.out.println("=================================");
				        }
			        }
					
			        
					editRecord (filepath, targetTerm, newID, newType,newName,newPrice, newDesc, newQuota);
					
				}
				
			}
			checker.close();
			if (found == false) {
			System.out.println("=================================");
			System.out.println("Record does not exist.");
			System.out.println("Please enter a valid ID. ");
			System.out.println("=================================");
			}
			
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "\tError");
			}
		
		}while(found == false);
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
			    	
			        Scanner file;
			        PrintWriter writer;

			        try {

			            file = new Scanner(new File("menuRecord.txt"));
			            writer = new PrintWriter("outputMenu.txt");

			            while (file.hasNext()) {
			                String line = file.nextLine();
			                if (!line.isEmpty()) {
			                    writer.write(line);
			                    writer.println("");
			                }
			            }

			            file.close();
			            writer.close();

			        } catch (FileNotFoundException ex) {
						JOptionPane.showMessageDialog(null, "\tItem was not able to update");
			        }
			    	

				}
				catch (Exception e) {
					System.out.println("=========================================================");
					JOptionPane.showMessageDialog(null, "\tItem was not able to update");
					System.out.println("=========================================================");
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

