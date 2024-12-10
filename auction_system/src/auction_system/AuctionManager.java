package auction_system;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        if((!auctionList.contains(auction))) {
            auctionList.add(auction);
            if(auction.getActive()) {
                activeList.add(auction);
                sortBySoonestEndingActiveAuctions();
            }
        }
    }

    public Auction getAuctionFromAuctionList(int index) {
        return auctionList.get(index);
    }

    public Auction getAuctionFromActiveList(int index) {
        return activeList.get(index);
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
        Collections.sort(activeList, auctionComparator);
    }

//    public ArrayList<Auction> getUserWonAuctions(User user) {
//        return null;
//    }
//
    public ArrayList<Auction> getUserListedAuctions(String username) {
        sortBySoonestEndingActiveAuctions();
        ArrayList<Auction> userListedAuctions = new ArrayList<>();
        User user = new User(username);
        for(int i = 0; i < auctionList.size(); i++) {
            if(auctionList.get(i).getUser().equals(user)) {
                userListedAuctions.add(auctionList.get(i));
                sortBySoonestEndingUserAuctions(userListedAuctions);
            }
        }
        return userListedAuctions;
    }

    public ArrayList<Auction> getUserBidOnAuctions(String username) {
        ArrayList<Auction> userBidOnAuctions = new ArrayList<>();
        User user = new User(username);
        for(int i = 0; i < auctionList.size(); i++) {
            if(auctionList.get(i).getBidManager().checkIfUserHasBid(user)) {
                userBidOnAuctions.add(auctionList.get(i));
            }
        }
        return userBidOnAuctions;
    }

    public void sortBySoonestEndingUserAuctions(ArrayList<Auction> userListedAuctions) {
        Comparator<Auction> auctionComparator = (Auction one, Auction two) ->
                one.getLocalEndDateAndTime().compareTo(two.getLocalEndDateAndTime());
        Collections.sort(userListedAuctions, auctionComparator);
    }

    // called when auctions ends
    public void endAuction(Auction auction) {
        activeList.remove(auction);
        inactiveList.add(auction);
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