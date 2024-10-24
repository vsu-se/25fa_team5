package auction_system;
import java.util.Date;
import java.util.LocalDateTime;
import java.util.DateTime;
import java.util.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class Item {
    private int id;
    private String name;
    private LocalDateTime startDate = LocalDateTime.now();
    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    String startDateFormatted = startDate.format(myFormat);
    private Date endDate = new Date(year, month, day, hours, minutes);
    private TreeMap<int, double> bids = new TreeMap<>();
    private double bIN = null;

    public Item (int id, String name, Date startDate, Date endDate){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setStartingBid (double startingBid){
        bids.put(id, startingBid);
        System.out.println("The starting Bid is: " + bids.get(id));
    }

    // Include basic getters and setters
    public int getID () {
    	return this.id;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public LocalDateTime getStartDate() {
    	return this.startDate;
    }
    
    public Date
    
     public void setBIN (double bIN) {
     	// Not required, but can be set by the seller at any time
     	this.bIN = bIN;
     }
    
    @Override
    public String toString () {
    	private String line1 = "Item ID: " +  id + "\n";
    	private String line2 = "Item Name: "+ name + "\n";
    	private String line3 = "Auction Start Date: " + startDateFormatted + "\n";
    	private String line4 = "Auction End Date: " + endDate + "\n";
    	 private String line5 = null;
    	 if (bIN != null) {
    	 	line5 = "Item Buy-It-Now Price: " + bIN + "\n";
    	 }
    	return line1+line2+line3+line4+line5;
    }
}
