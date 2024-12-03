package auction_system;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AuctionManager {
    private ArrayList<Auction> auctionList = new ArrayList<>();

    public AuctionManager() {

    }

    public void addAuction(Auction auction) {
    //    auctionList.add(auction);
        if((!auctionList.contains(auction)) && auction.getActive()) {
            auctionList.add(auction);
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

    public void getSoonestEndingActiveAuctions() {
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
        Item one = new Item(1, "name");
        Auction auctionOne = new Auction(one, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 40);
        Item two = new Item(2, "name");
        Auction auctionTwo = new Auction(two, LocalDate.now(), LocalDate.parse("2024-01-06"), LocalTime.now(), LocalTime.now(), 40);
        Item three = new Item(3, "name");
        Auction auctionThree = new Auction(three, LocalDate.now(), LocalDate.parse("2030-01-06"), LocalTime.now(), LocalTime.now(), 40);
        AuctionManager auctionManager = new AuctionManager();
        auctionManager.addAuction(auctionOne);
        auctionManager.addAuction(auctionThree);
        auctionManager.addAuction(auctionTwo);
        auctionManager.getSoonestEndingActiveAuctions();
        System.out.println(auctionManager.toString());
    }
}
