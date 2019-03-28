import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class PromoSet {
	
	private int promoSetId;
	private String name;
	private int[] itemId;
	private double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private int quota;
	
	public PromoSet() {};
	
	public PromoSet(int promoSetId, String name, int[] itemId, double price, LocalDate startDate, LocalDate endDate, int quota) {
		this.promoSetId = promoSetId;
		this.name = name;
		this.itemId = itemId;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.quota = quota;
	}
	
	public int getPromoSetId () {
		return this.promoSetId;
	}
	
	public String getName () {
		return this.name;
	}
	
	public double getPrice () {
		return this.price;
	}
}
