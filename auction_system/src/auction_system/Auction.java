package auction_system;

import java.time.Duration;
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
	private boolean isBought; // update variable after auction ends and at least one bid

	private LocalDate startingDate;
	private LocalDate endingDate;
	private LocalTime startTime;
	private LocalTime endTime;

	private BidManager bidManager = new BidManager();
	private double winningBid;
	private double shippingCost = 7.99;
	private User user;

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
	
//	public void addBid(int userID, double bid) {
//		if(this.getCurrentBid() < bid) {
//			bids.put(bid, userID);
//			System.out.println("Bid Accepted");
//		} else {
//			System.out.println("Bid Denied from " + user.getName() + " Lower than Current Bid");
//		}
//	}

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

	public boolean findWinningBid() {
		if(!bidManager.checkIfEmpty()) {
			winningBid = bidManager.getWinningBid();
			return true;
		}
		return false;
	}

	public double getWinningBid() {
		return winningBid;
	}

	public Bid getUserBid(String username) {
		return bidManager.getUserBid(username);
	}

	// for use in method below this one, may be replaced later
	public String calculateTimeRemaining() {
		LocalDateTime now = LocalDateTime.now();
		LocalDate date = getLocalEndDate();
		LocalTime time = getLocalStartTime();
		LocalDateTime auctionTime = LocalDateTime.of(date, time);
		Duration duration = Duration.between(now, auctionTime);
		long days = duration.toDays();
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		long seconds = duration.toSeconds();
		String daysStr = "";
		if(days >= 0) {
			daysStr += days + "days, ";
		}
		String hoursStr = "";
		if(hours >= 0) {
			hoursStr += hours + " hours, ";
		}
		String minutesStr = "";
		if(minutes >= 0) {
			minutesStr += minutes + " minutes, ";
		}
		String secondsStr = "";
		if(seconds >= 0) {
			secondsStr += seconds + " seconds";
		}
		String calculatedTime = daysStr + hoursStr + minutesStr + secondsStr;
		return calculatedTime;
	}

	public String showMyBidsData(String username) {
		Bid bid;
		String currentBidLine = "";
		if(findWinningBid()) {
			currentBidLine += "Winning bid: $" + winningBid + "\n";
		}
		else {
			currentBidLine += "Winning bid: none\n";
		}
		String timeLine = "Time remaining: " + calculateTimeRemaining() + "\n";
		bid = getUserBid(username);
		String bidLine = "Bid info: " + bid.toString();
		String binPrice = "";
		if(bIN > 0) {
			binPrice += "BIN: $" + String.format("%.2f", bIN);
		}
		return currentBidLine + timeLine + bidLine + binPrice;
	}

	public User getUser() {
		return user;
	}

	// may remove later, sets user who listed auction
	public void setUser(String username) {
		user = new User(username);
	}

	@Override
	public String toString() {
		String itemLine = this.item.toString();
		String binLine = "";
		if(bIN > 0) {
			binLine += "Buy-it-Now Price: $" + String.format("%.2f", bIN);
		}
		else {
			binLine += "No Buy-it-Now Price";
		}
		String startingDateLine = "\nStarting time: " + startingDate.toString() + " at " + startTime.toString();
		String endingDateLine = "\nEnding time: " + endingDate.toString() + " at " + endTime.toString();
		String bidLine = "\nWinning bid: ";
		if(findWinningBid()) {
			bidLine += String.format("$%.2f", winningBid);
		}
		else {
			bidLine += "none";
		}
		String shipping = "\nShipping: $" + shippingCost;
		String isActiveLine = "\nActive: " + getActive();
		return itemLine  + binLine + startingDateLine  + endingDateLine + bidLine + shipping
				 + isActiveLine + "\n";
	}

	public boolean getActive() {
		return isActive;
	}
}