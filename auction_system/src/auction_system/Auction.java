package auction_system;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.lang.Double;

public class Auction {
	private double bIN = 0.0;
	private Date endDate; // will delete later
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

	public void setBidManager(BidManager bidManager) {
		this.bidManager = bidManager;
	}
	
	public Item getItem() {
		return this.item;
	}

	public double getbIN() {
		return this.bIN;
	}

	public void setbIN(double bIN) {
		this.bIN = bIN;
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
		LocalTime time = getLocalEndTime();
		LocalDateTime auctionTime = LocalDateTime.of(date, time);
		Duration duration = Duration.between(now, auctionTime);
		long days = duration.toDays();
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		long seconds = duration.toSeconds();
		String daysStr = "";
		if(days >= 0) {
			daysStr += days + " days, ";
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
		if(days >= 0 & hours >= 0 & minutes >= 0 & seconds >= 0) {
			return daysStr + hoursStr + minutesStr + secondsStr;
		}
		else return " none";
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

	public double getShippingCost() {
		return shippingCost;
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