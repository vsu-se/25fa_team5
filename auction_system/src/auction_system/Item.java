package auction_system;
import java.util.*;
import java.lang.Double;

public class Item {
    private int id;
    private String name;
    Calendar c = Calendar.getInstance();
    private Date startDate;
    private Date endDate;
    TreeMap<Integer, Double> bids = new TreeMap<>();
    private double bIN = 0.0;

    
      public static void main(String[] args) {
    	  Item item = new Item(12345, "Cool Hat", 15.00);
    	  System.out.println(item.toString());
      }
      
    public Item (int id, String name, double bIN){
        this.id = id;
        this.name = name;
        this.bIN = bIN;
        Calendar c = Calendar.getInstance();
        startDate = c.getTime();
        c.add(Calendar.DATE, 30); 
        // Current auction time is set at 30 days for testing purposes
        endDate = c.getTime();
    }

    public void setStartingBid (double startingBid){
        bids.put(id, startingBid);
        System.out.println("The starting Bid is: " + bids.get(id));
    }
    
    public double getCurrentBid (){
    	double currBid = 0.0;
    	for (int key : bids.keySet()) {
    		currBid = bids.get(key);
    		// A comparable will be needed to sort the treeMap by highest value
    	}
    	return currBid;
    }

    public int getID () {
    	return this.id;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public Date getStartDate() {
    	return this.startDate;
    }
    
    public Date getEndDate() {
    	return this.endDate;
    }
    
    public double getbIN() {
    	return this.bIN;
    }

    public void setBIN (double bIN) {
     	// Not required, but can be set by the seller at any time
    	if(bIN <= 0.0) {
    		System.out.println("The Buy it Now price cannot be at or lower than $0.00");
    	} else {
     	this.bIN = bIN;
    	}
    }
    
    public void setName (String name) {
    	this.name = name;
    }
    
    public void setID (int ID) {
    	this.id = ID;
    }
          
    public void addBid(int userID, double bid) {
    	if(this.getCurrentBid() < bid) {
    		bids.put(userID, bid);
//    		if(bid == this.getbIN()) {
//    			System.out.println("Buy it Now Price Reached");
//    			endAuction();
//    		}
    		System.out.println("Bid Accepted");
    	} else {
    		System.out.println("Bid Denied");
    	}
    }
   
    @Override
    public String toString () {
    	String line1 = "Item ID: " +  id + "\n";
    	String line2 = "Item Name: "+ name + "\n";
    	String line3 = "Auction Start Date: " + startDate + "\n";
    	String line4 = "Auction End Date: " + endDate + "\n";
    	String line5 = null;
    	 if (bIN != 0.0) {
    		String bINFormatted = String.format("%.2f", bIN);
    	 	line5 = "Item Buy-It-Now Price: " + bINFormatted + "\n";
    	 }
    	return line1+line2+line3+line4+line5;
    }

	public void setStartDate(Date date) { // Concept is to end auctions by moving the start date to the end date, probably gonna need some rewrites eventually
		this.startDate = date;
		
	}
}
