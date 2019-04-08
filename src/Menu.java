//package rrpss; //referenced from PlaneSeat 

import java.util.List;
import java.util.Scanner;

public class Menu {
    private int foodID;
    private String foodName;
    private String foodType;
    private double foodPrice;
    private String foodDesc;
    private int foodQuota;
    
	Scanner sc = new Scanner(System.in);
	
	public Menu() {};
    
	public Menu(int id, String name, String type, double foodPrice2, String desc, int quota)  {
		this.foodID = id ;
		this.foodName = name;
		this.foodType = type ;
		this.foodPrice = foodPrice2 ;	
		this.foodDesc = desc ;
		this.foodQuota = quota ;
	}
    
    //@return the id of the item.
    public int getFoodID() {
    	return foodID;
    }
    
    //@return name of the item.
    public String getFoodName(){
        return foodName;
    }
    
    //@return the price of the item.
    public double getFoodPrice(){
        return  foodPrice;
    }
    
    public String getFoodType() {
    	return foodType;
    }
    
    public String getFoodDesc() {
    	return foodDesc;
    }
    
    public int getFoodQuota() {
    	return foodQuota;
    }
    
    public void setFoodQuota(int foodQuota) {
    	this.foodQuota = foodQuota;
    }

}

