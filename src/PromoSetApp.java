import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class PromoSetApp {
	final static String filename = "promotionList.txt";
	public static void main() throws IOException, ParseException  {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int  OperationInput;
		Scanner sc = new Scanner(System.in);
		PromoSet promo=new PromoSet();
		Validation inputValidation= new Validation();

		System.out.println();
		System.out.println();
		System.out.println("=============================");
		System.out.println("\t Promotion Set Menu");
		System.out.println("=============================");
		System.out.println("1) Create new promotion item");
		System.out.println("2) Update promotion item");
		System.out.println("3) Delete promotion item");
		System.out.println("4) View promotion items");
		System.out.println("0) Back");
		System.out.print("\nEnter your choice: ");
		
		OperationInput = sc.nextInt();
		sc.nextLine();

		switch (OperationInput) {
		case 1:

			System.out.println();
			System.out.println("=================================");
			System.out.println("\tCreating Promotion Set");
			System.out.println("=================================");

		//	System.out.print("Promotion set ID:");
		//  System.out.print(promo.SystemGeneratedID(filename));		
		//	System.out.println();

			System.out.print("Name:");
			String tempName = sc.nextLine();

			System.out.println("Item ID (Main Course:6001-6299)(Dessert:6300-6599)(Drink:6600-7000): ");
			System.out.println("Enter 0 to move next");
			int tempItemID = sc.nextInt();sc.nextLine();
			ArrayList<Integer> tempItemIDList = new ArrayList<Integer>();
			while(tempItemID!=0)
			{
				if(inputValidation.itemExistsDB(tempItemID)==1) {
				tempItemIDList.add(tempItemID);
				}
				else
				{
					System.out.println("Invalid value");	
				}
				System.out.println("Item ID (Main Course:6001-6299)(Dessert:6300-6599)(Drink:6600-7000): ");
				System.out.println("Enter 0 to move next");
				tempItemID = sc.nextInt();sc.nextLine();
			}
			System.out.print("Price:");
			double tempPrice = sc.nextDouble();
			sc.nextLine();

			System.out.print("Start date Formate:YYYY-MM-DD :");
			String tempStartDate = sc.nextLine();
			while(inputValidation.isDateValid(tempStartDate)==false)
			{
				System.out.println("Invalid value, please key in a new value");
				System.out.print("Start date Formate:YYYY-MM-DD :");
				tempStartDate = sc.nextLine();
			}
			

			System.out.print("End date Formate:YYYY-MM-DD :");
			String tempEndDate = sc.nextLine();
			while(inputValidation.isDateValid(tempStartDate)==false)
			{
				System.out.println("Invalid value, please key in a new value");
				System.out.print("End date Formate:YYYY-MM-DD :");
				tempEndDate = sc.nextLine();
			}
			System.out.print("Quota:");
			int tempQuota = sc.nextInt();
			sc.nextLine();
			PromoSet newitem	= new PromoSet(promo.SystemGeneratedID(filename), tempName, tempItemIDList, tempPrice, tempStartDate,tempEndDate, tempQuota);
			promo.addPromoItems(newitem);
			
			
			System.out.println();
			System.out.println("=========================================================");
			System.out.println("\t     Promotion set successfully created! ");
			System.out.println("=========================================================");
			System.out.println("PromoSet ID: " + newitem.getPromoSetId());
			System.out.println("Name: " + newitem.getName());
			System.out.println("PromoSet Item List: " + newitem.getItemId());
			System.out.println("Price: " + newitem.getPrice());
			System.out.println("Start Data: " + newitem.getStartDate());
			System.out.println("End Date: " + newitem.getEndDate());
			System.out.println("Quota: " + newitem.getQuota());
			System.out.println("=========================================================");
			System.out.println();

			PromoSetApp.main();
			break;
		case 2:

			int  UpdateInput;
			System.out.println();
			System.out.println();
			System.out.println("======================================");
			System.out.println("\t Update Promotion Set Menu");
			System.out.println("======================================");
			System.out.println("1) Name");
			System.out.println("2) Promotion Set Item");
			System.out.println("3) Price");
			System.out.println("4) Start date Formate:DD/MM/YYYY");
			System.out.println("5) End date Formate:DD/MM/YYYY");
			System.out.println("6) Quota");
			System.out.println("0) Back");
			System.out.print("\nEnter your choice: ");

			UpdateInput = sc.nextInt();
			int promoId = 0;
			sc.nextLine();
			if(UpdateInput==0)
			{
				PromoSetApp.main();
				break;
			}
			System.out.println("Promotion ID:");
			promoId = sc.nextInt();
			sc.nextLine();
			switch (UpdateInput) {
			case 1:
				promo.updateInfo(promoId, 1);
				

				break;
			case 2:
				
				promo.updateInfo(promoId, 2);

				break;
			case 3:
				
				promo.updateInfo(promoId, 3);

				break;
			case 4:
				
				promo.updateInfo(promoId, 4);

				break;
			case 5:
				promo.updateInfo(promoId, 5);
				break;
			case 6:
				promo.updateInfo(promoId, 6);
				break;
			default:

				break;
			}
			PromoSetApp.main();
			break;
		case 3:

			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tDeleting Items from Promotion Set");
			System.out.println("=============================================");
			System.out.print("Enter Promotion Set ID: ");
			int removeID = sc.nextInt();
			sc.nextLine();
			promo.remove(removeID);
			
			PromoSetApp.main();
			break;

		case 4:
			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tViewing Promotion Set");
			System.out.println("=============================================");
			
			ArrayList<PromoSet> resItem = new ArrayList<PromoSet>();
			
			resItem = promo.readPromoInfo(filename);
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%5s %10s %30s %40s %15s %15s %15s %5s", "Index", "PromoSet ID", "Name","PromoSet Item List","Price", 
					"Start Data", "End Date", "Quota");
			System.out.println();
			int index = 1;
			for (PromoSet g : resItem) {

				System.out.format("%5s %10s %30s %40s %15s %15s %15s %5s", index, g.getPromoSetId(), g.getName(),
						g.getItemId(),g.getPrice(), g.getEndDate(), g.getEndDate(), g.getQuota());
				index++;
				System.out.println();

			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------");
			PromoSetApp.main();
			break;

		default:

			break;
		}

	}

}
