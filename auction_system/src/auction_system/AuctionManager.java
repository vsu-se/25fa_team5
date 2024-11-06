package auction_system;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class AuctionManager {
	private Map<Integer, Auction> auctionsMap = new TreeMap<>();
	private ArrayList<Auction> auctionsList;
	
	public AuctionManager() {
		
	}
	
	public void addAuction(Auction auction) {
		auctionsMap.put(auction.getItem().getID(), auction);
	}
	
	public int getNumAuctions() {
		return auctionsMap.size();
	}
	
	public Auction getAuction(int id) {
		if(auctionsMap.containsKey(id)) {
			return auctionsMap.get(id);
		}
		return null;
	}
	
	public void clear() {
		auctionsMap.clear();
	}
	
	public boolean containsAuction(int id) {
		return auctionsMap.containsKey(id);
	}
	
	public ArrayList<Auction> listAuctions() {
		auctionsList = new ArrayList<>(auctionsMap.values());
		return auctionsList;
	}
	
	public String toString() {
		String result = "";
		for(Auction auction : auctionsMap.values()) {
			result += auction.toString() + "\n";
		}
		return result;
	}

	public void testingToString() {
		AuctionManager auctionManager = new AuctionManager();
		ArrayList<Auction> auctions;
		Item item1 = new Item(1, "one");
		Item item2 = new Item(2, "two");
		Item item3 = new Item(3, "three");
		Auction one = new Auction(item1, 5.00);
		Auction two = new Auction(item2, 5.00);
		Auction three = new Auction(item3, 5.00);
		auctionManager.addAuction(one);
		auctionManager.addAuction(two);
		auctionManager.addAuction(three);
		System.out.println(auctionManager.toString());
	}
	
	public static void main(String[] args) {
		AuctionManager auctionManager = new AuctionManager();
		auctionManager.testingToString();
	}

}
