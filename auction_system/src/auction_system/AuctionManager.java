package auction_system;

import java.util.ArrayList;

public class AuctionManager {
    private ArrayList<Auction> auctionList = new ArrayList<>();
    private ArrayList<Auction> inactiveAuctionList = new ArrayList<>();
    private ArrayList<Auction> activeAuctionList = new ArrayList<>();

    public AuctionManager() {

    }

    public void addAuction(Auction auction) {
        // auctionList.add(auction);
        if((!auctionList.contains(auction)) && auction.getActive()) {
            auctionList.add(auction);
            activeAuctionList.add(auction);
        }
    }
    
    public void endAuction(Auction auction) {
    	activeAuctionList.remove(auction);
    	inactiveAuctionList.add(auction);
    	auction.endAuction();
    }

    public boolean containsAuction(Auction auction) {
        return auctionList.contains(auction);
    }

    @Override
    public String toString() {
        String str = "";
        for(Auction a : auctionList) {
            str += a.toString() + "\n";
        }
        return str;
    }
}
