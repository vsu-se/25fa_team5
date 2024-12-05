package auction_system;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AuctionManager {
    private ArrayList<Auction> auctionList = new ArrayList<>();
    private ArrayList<Auction> activeList = new ArrayList<>();
    private ArrayList<Auction> inactiveList = new ArrayList<>();

    public AuctionManager() {

    }

    public void addAuction(Auction auction) {
    //    auctionList.add(auction);
        if((!auctionList.contains(auction)) && auction.getActive()) {
            auctionList.add(auction);
            activeList.add(auction);
        }
    }

    public Auction getAuctionFromAuctionList(int index) {
        return auctionList.get(index);
    }

    public int getAuctionListLength() {
        return auctionList.size();
    }

    public boolean containsAuction(Auction auction) {
        return auctionList.contains(auction);
    }


    // returns list of all auctions regardless of active or inactive
    public ArrayList<Auction> getAuctionList() {
        return auctionList;
    }

    public ArrayList<Auction> getActiveList() {
        return activeList;
    }

    public void sortBySoonestEndingActiveAuctions() {
        Comparator<Auction> auctionComparator = (Auction one, Auction two) -> one.getLocalEndDateAndTime().compareTo(two.getLocalEndDateAndTime());
        Collections.sort(auctionList, auctionComparator); // replace later with activeauctionlist
    }

    @Override
    public String toString() {
        String str = "";
        for(Auction a : auctionList) {
            str += a.toString() + "\n";
        }
        return str;
    }

    public static void main(String[] arg) {

    }
}
