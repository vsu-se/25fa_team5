package auction_system;

import java.util.ArrayList;

public class AuctionManager {
    private ArrayList<Auction> auctionList = new ArrayList<>();

    public AuctionManager() {

    }

    public void addAuction(Auction auction) {
        auctionList.add(auction);
        if((!auctionList.contains(auction)) && auction.getActive()) {
            auctionList.add(auction);
        }
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
