import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class PromoSetApp {
	




	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int  OperationInput;
		Scanner sc = new Scanner(System.in);
		DBManager db = new DBManager();

		System.out.println();
		System.out.println();
		System.out.println("=============================");
		System.out.println("\t Promtion Set Menu");
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

			System.out.print("Promotion set ID:");
			int tempPromoSetid = sc.nextInt();
			sc.nextLine();
			while (db.checkIfExistPromoSet(tempPromoSetid) == true) {
				System.out.print("This id exists. Please provides another input:");
				tempPromoSetid = sc.nextInt();
				sc.nextLine();
			}

			System.out.print("Name:");
			String tempName = sc.nextLine();

			System.out.println("Item ID:");
			System.out.println("Note: Use comma to separate id e.g 1001,2002,3003");
			String tempItemID = sc.nextLine();
			ArrayList<Integer> tempItemIDList = new ArrayList<Integer>();
			String str[] = tempItemID.split(",");
			for (int i = 0; i < str.length; i++) {
				tempItemIDList.add(Integer.parseInt(str[i]));
			}

			System.out.print("Price:");
			double tempPrice = sc.nextDouble();
			sc.nextLine();

			System.out.print("Start date Formate:YYYY-MM-DD :");
			String tempStartDate = sc.nextLine();

			System.out.print("End date Formate:YYYY-MM-DD :");
			String tempEndDate = sc.nextLine();

			System.out.print("Quota:");
			int tempQuota = sc.nextInt();
			sc.nextLine();

			db.savePromoItems(new PromoSet(tempPromoSetid, tempName, tempItemIDList, tempPrice, tempStartDate,
					tempEndDate, tempQuota));

			PromoSetApp.main(null);
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
			
			System.out.println("Promotion ID:");
			promoId = sc.nextInt();
			sc.nextLine();
			switch (UpdateInput) {

			case 1:
				
				db.UpdatePromoItem(promoId, 1);

				break;
			case 2:
				
				db.UpdatePromoItem(promoId, 2);

				break;
			case 3:
				
				db.UpdatePromoItem(promoId, 3);

				break;
			case 4:
				
				db.UpdatePromoItem(promoId, 4);

				break;
			case 5:
				db.UpdatePromoItem(promoId, 5);
				break;
			case 6:
				db.UpdatePromoItem(promoId, 6);
				break;
			default:

				break;
			}
			PromoSetApp.main(null);
			break;
		case 3:

			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tDeleting Items from Promotion Set");
			System.out.println("=============================================");
			System.out.print("Enter Promotion Set ID: ");
			int removeID = sc.nextInt();
			sc.nextLine();
			db.deletePromoSet(removeID);
			PromoSetApp.main(null);
			break;

		case 4:
			System.out.println();
			System.out.println("=============================================");
			System.out.println("\tViewing Promotion Set");
			System.out.println("=============================================");
			String filename = "src/promotionList.txt";
			ArrayList<PromoSet> resItem = new ArrayList<PromoSet>();
			resItem = db.readPromoSetInfo(filename);
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%5s %10s %30s %40s %15s %15s %5s", "Index", "PromoSet ID", "Name", "PromoSet Item List",
					"Start Data", "End Date", "Quota");
			System.out.println();
			int index = 1;
			for (PromoSet g : resItem) {

				System.out.format("%5s %10s %30s %40s %15s %15s %5s", index, g.getPromoSetId(), g.getName(),
						g.getItemId(), g.getEndDate(), g.getEndDate(), g.getQuota());
				index++;
				System.out.println();

			}
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------");
			PromoSetApp.main(null);
			break;

		default:

			MainMenu.main(null);
			break;
		}

	}

}
