package auction_system;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class AuctionPersistence {
    private AuctionManager auctionManager;
    private File auctionFile = new File("C:\\Users\\kathl\\IdeaProjects\\25fa_team5\\auction_system\\src\\auction_system\\Files\\auctions.txt");
	// path name must be changed
    public AuctionPersistence() {

    }
    
    // auction controller class will use auction persistence class to save and read
   
    // will read file and return auction manager holding auctions
    public AuctionManager buildAuctionManager() {
    	auctionManager = new AuctionManager();
    	Item item = null;
    	Auction auction = null;
    	String line = "";
    	String name = "";
    	int id = 0;
    	double bIN = 0.0; // fix later
		// dates saved as strings for now
    	String startDate = "";
    	String endDate = "";
    	String status = "";
    	try {
    		Scanner scan = new Scanner(auctionFile); // .useDelimiter(",\\s+");
    		while(scan.hasNext()) {
    			line = scan.nextLine();
    			String[] test = line.split(",");
    			if(test.length == 6) {
    				name = test[0];
    				id = Integer.valueOf(test[1]);
    				bIN = Double.valueOf(test[2]);
    				startDate = test[3];
    				endDate = test[4];
    				status = test[5];
    				item = new Item(id, name);
    				auction = new Auction(item, bIN); // auction class will need more constructors
    				auctionManager.addAuction(auction);
    			}
    		}
    		scan.close();
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    	return auctionManager;
    }
    
    // appends record of auction after being given auction to record from controller class
    public void recordAuction(Auction auction) {
    	try {
			FileWriter fW = new FileWriter(auctionFile, true);
			PrintWriter pW = new PrintWriter(fW);
			
			// records name, id, bIN, start/end dates, status
			// will need category and other data
			String sep = ",";
			pW.println(auction.getItem().getName() + sep + auction.getItem().getID() + sep + auction.getbIN()
			+ sep + auction.getStartDate() + sep + auction.getEndDate() + sep + "active");
			System.out.println("Printed successfully!");
			System.out.println(auction.getbIN());
			
			pW.close();
    		fW.close();
    	}
    	catch(IOException e) {
    		System.out.println(e);
    	}
    }
    
    public void testRecordAuction() {
        Item item1 = new Item(1, "one");
        Item item2 = new Item(2, "two");
        Item item3 = new Item(3, "three");
        Auction one = new Auction(item1, 5.00);
        Auction two = new Auction(item2, 5.00);
        Auction three = new Auction(item3, 5.00);
        recordAuction(one);
        recordAuction(two);
        recordAuction(three);
    }
    
    public static void main(String[] args) {
        AuctionPersistence auctionPersistence = new AuctionPersistence();
  //      auctionPersistence.testRecordAuction();
        AuctionManager auctionManager = auctionPersistence.buildAuctionManager();
        System.out.println(auctionManager.toString());
           
    }
}
