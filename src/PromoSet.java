import java.util.ArrayList;

public class PromoSet {

	private int PromoSetId, quota;
	private static int menuInput, OperationInput, UpdateInput;
	private ArrayList<Integer> itemId;
	private double Price;
	private String startDate, endDate,name;
	private static ArrayList<PromoSet> List123;

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

	public void setPromoSetId(int promoSetId) {
		PromoSetId = promoSetId;
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
	


	






}
