package auction_system;

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

	public boolean getActive() {
		return isActive;
	}

	public void getAllBids() { // Printing should include: bid amount, date/time, user name, order descending
							   // based on bid amount
		Set<Map.Entry<Double, Integer>> entrySet = bids.entrySet();
		for (Map.Entry<Double, Integer> currentBid : entrySet) {
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

	public void addBid(User user, double bid) { // show the user name as well as the date/time the bid was placed
												
		if (this.getCurrentBid() < bid) {
			Calendar c = Calendar.getInstance();
			bids.put(bid, user.getID());
			bidDates.put(bid, c.getTime());
			System.out.println("Bid Accepted from " + user.getName() + " at " + bidDates.get(bid));
		} else {
			System.out.println("Bid Denied from " + user.getName() + " Lower than Current Bid");
		}
	}

	@Override
	public String toString() {
		String line1 = this.item.toString();
		String line2 = "\nCurrent and Previous bids: " + bids.keySet();
		String line3 = "\nBuy-it-Now Price: " + this.getbIN();
		String line4 = "\nStarting Date: " + startDate;
		String line5 = "\nEnding Date: " + endDate;
		String line6;
		if (isActive) {
			line6 = "\nStatus: Active";
		} else {
			line6 = "\nStatus: Inactive";
		}
		return line1 + line2 + line3 + line4 + line5 + line6;
	}
}
