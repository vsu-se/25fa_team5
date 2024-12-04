package auction_system;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.lang.Double;

public class Auction {
	TreeMap<Double, Integer> bids = new TreeMap<>();
	TreeMap<Double, Date> bidDates = new TreeMap<>();
	private double bIN = 0.0;
	private Date startDate;
	private Date endDate;
	private Item item;
	private boolean isActive = false;

	private LocalDate startingDate;
	private LocalDate endingDate;
	private LocalTime startTime;
	private LocalTime endTime;

	private BidManager bidManager = new BidManager();

	public Auction(Item item, LocalDate startingDate, LocalDate endingDate, LocalTime startTime, LocalTime endTime, double bIN) {
		this.item = item;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		this.startTime = startTime.truncatedTo(ChronoUnit.SECONDS);
		this.endTime = endTime.truncatedTo(ChronoUnit.SECONDS);
		this.bIN = bIN;
		this.isActive = true;
    }
	
	
	public Auction(Item item, double startBid) {
		Calendar c = Calendar.getInstance();
		startDate = c.getTime();
		c.add(Calendar.DATE, 30); // Auction time set for 30 days
		endDate = c.getTime();
		bids.put(startBid, item.getID());
		this.item = item;
		this.isActive = true;
		System.out.println("Item #" + item.getID() + " for " + item.getName() + " begins at " + startDate
				+ " with a starting bid of $" + startBid);
	}

	public Auction(Item item, double startBid, double bIN) {
		Calendar c = Calendar.getInstance();
		startDate = c.getTime();
		c.add(Calendar.DATE, 30); // Auction time set for 30 days
		endDate = c.getTime();
		bids.put(startBid, item.getID());
		this.item = item;
		this.isActive = true;
		this.bIN = bIN;
		System.out.println("Item #" + item.getID() + " for " + item.getName() + " begins at " + startDate
				+ " with a starting bid of $" + startBid + " and a Buy-it-Now price of $" + bIN);

	}

	public void setBidManager(BidManager bidManager) {
		this.bidManager = bidManager;
	}
	
	public Item getItem() {
		return this.item;
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

	public void setEndDate(Date date) {
		this.endDate = date;
	}

	public void setbIN(double bIN) {
		this.bIN = bIN;
	}

	public double getCurrentBid() {
		double currBid = 0.0;
		if (bids.isEmpty()) {
			return 0.0;
		}
		if (isActive) {
			currBid = bids.lastKey(); // Tree maps order themselves based on the numerical value of the key, so if the
									  // bid amount is the key instead of userID, it self sorts
		}
		return currBid;
	}
	
	public void getAllBids() {
		Set<Map.Entry<Double, Integer> > entrySet = bids.entrySet();
		for(Map.Entry<Double, Integer> currentBid : entrySet) {
			// Printing the Value (User ID)
			System.out.print("User: " + currentBid.getValue());
			// Printing the Key (Bid amount)
			System.out.println(" Bid: " + currentBid.getKey());
			// Printing the Value of bidDates (Time of Bid)
			System.out.println("Time of Bid Placement: " + bidDates.get(currentBid.getKey()));
		}
	}

	public void endAuction() {
		Calendar c = Calendar.getInstance();
		this.isActive = false;
		System.out.println("Auction #" + item.getID() + " for " + item.getName() + " ended at " + c.getTime());
	}

	public boolean checkDate() {
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		if(!isActive || now.after(endDate)) {
			isActive = false;
			return true;
		}
		return false;
	}

	public LocalDate getLocalStartDate() {
		return startingDate;
	}

	public LocalTime getLocalStartTime() {
		return startTime;
	}

	public LocalDate getLocalEndDate() {
		return endingDate;
	}

	public LocalTime getLocalEndTime() {
		return endTime;
	}

	public LocalDateTime getLocalEndDateAndTime() {
		return LocalDateTime.of(endingDate, endTime);
	}
	
	public void addBid(int userID, double bid) {
		if(this.getCurrentBid() < bid) {
			bids.put(bid, userID);
			System.out.println("Bid Accepted");
		} else {
			System.out.println("Bid Denied from " + user.getName() + " Lower than Current Bid");
		}
	}

	public boolean addBid2(Bid bid) {
		if(bidManager.containsBid(bid)) {
			return false;
		}
		else {
			bidManager.addBid(bid);
			return true;
		}
	}

	public BidManager getBidManager() {
		return bidManager;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Auction auction) {
			return this.getItem().getID() == (auction.getItem().getID());
		}
		else {
			return false;
		}
	}
	
//	@Override
//	public String toString() {
//		String line1 = this.item.toString();
//		String line2 = "\nCurrent and Previous bids: " + bids.keySet();
//		String line3 = "\nBuy-it-Now Price: " + this.getbIN();
//		String line4 = "\nStarting Date: " + startDate;
//		String line5 = "\nEnding Date: " + endDate;
//		String line6;
//		if(isActive) {
//			line6 = "\nStatus: Active";
//		} else {
//			line6 = "\nStatus: Inactive";
//		}
//		return line1 + line2 + line3 + line4 + line5 + line6;
//	}

	@Override
	public String toString() {
		String itemLine = this.item.toString();
	//	String bidLine = "Current and Previous bids: " + bidManager.toString(); //bids.keySet();
		String binLine = "Buy-it-Now Price: " + this.getbIN();
		String startingDateLine = "\nStarting time: " + startingDate.toString() + " at " + startTime.toString();
		String endingDateLine = "\nEnding time: " + endingDate.toString() + " at " + endTime.toString();
		String isActiveLine = "\nActive: " + getActive();
		return itemLine  + binLine + startingDateLine  + endingDateLine
				 + isActiveLine + "\n";
	}
}
