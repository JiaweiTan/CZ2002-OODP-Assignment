import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PromoSet {

	private int PromoSetId, quota;
	private ArrayList<Integer> itemId;
	private double Price;
	private String startDate, endDate,name;

	public PromoSet()
	{
		
	}
	
	public PromoSet(int PromoSetId,String name, ArrayList<Integer> itemId, double Price, String startDate, String endDate,
			int quota) {
		// TODO Auto-generated constructor stub
		this.PromoSetId = PromoSetId;
		this.itemId = itemId;
		this.name= name;
		this.Price = Price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.quota = quota;

	}

	public int getPromoSetId() {
		return PromoSetId;
	}


	public ArrayList<Integer> getItemId() {
		return itemId;
	}

	public void setItemId(ArrayList<Integer> itemId) {
		this.itemId = itemId;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}
	public String  getName()
	{
		return name ;
		
	}
	public void setName(String name)
	{
		this.name= name ;
	}

	public void addPromoItems(PromoSet promoSet) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		db.savePromoItems(promoSet);
	}

	public void updateInfo(int promoId, int i) throws IOException, ParseException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		db.UpdatePromoItem(promoId, i);
	}

	public void remove(int removeID) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		Validation v= new Validation();
		if(v.promoSetExistsDB(removeID)==1)
		{
			db.deletePromoSet(removeID);
			System.out.println("Delete successfully");
		}
		else
		{
			System.out.println("Delete unsuccessfully");
		}
		
	}

	public ArrayList<PromoSet> readPromoInfo(String filename) throws IOException {
		// TODO Auto-generated method stub
		DBManager db = new DBManager();
		return  db.readPromoSetInfo(filename);
	}
	public int SystemGeneratedID(String filename) throws IOException
	{
		List<PromoSet> PromoSetList = DBManager.readPromoSetInfo(filename);
		if(PromoSetList.size()>0)
			return PromoSetList.get(PromoSetList.size()-1).getPromoSetId() + 1;
		else
			return 7001;
	}


	






}
