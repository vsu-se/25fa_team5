package auction_system;

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
            if(!auction.checkEndDateTimeIsBeforeNow()) {
                activeList.add(auction);
                sortBySoonestEndingActiveAuctions();
            }
            else {
                inactiveList.add(auction);
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

    public void sortByReverseChronologicalOrder(ArrayList<Auction> auctions) {
        Comparator<Auction> auctionComparator = (Auction one, Auction two) -> two.getLocalEndDateAndTime().compareTo(one.getLocalEndDateAndTime());
        Collections.sort(auctions, auctionComparator);
    }

    public ArrayList<Auction> getUserWonAuctions(String username) {
        ArrayList<Auction> userWonAuctions = new ArrayList<>();
        User user = new User(username);
        for(int i = 0; i < inactiveList.size(); i++) {
            if(inactiveList.get(i).findWinningBid()) {
                if(inactiveList.get(i).getWinningBid().getUser().equals(user)) {
                    userWonAuctions.add(inactiveList.get(i));
                }
            }
        }
        sortByReverseChronologicalOrder(userWonAuctions);
        return userWonAuctions;
    }

    public ArrayList<Auction> getUserSoldAuctions(String username) {
        ArrayList<Auction> userSoldAuctions = new ArrayList<>();
        User user = new User(username);
        for(int i = 0; i < inactiveList.size(); i++) {
            if(inactiveList.get(i).getUser().equals(user) && inactiveList.get(i).isBought()) {
                userSoldAuctions.add(inactiveList.get(i));
            }
        }
        sortByReverseChronologicalOrder(userSoldAuctions);
        return userSoldAuctions;
    }

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
        for(int i = 0; i < activeList.size(); i++) {
            if(activeList.get(i).getBidManager().checkIfUserHasBid(user)) {
                userBidOnAuctions.add(activeList.get(i));
            }
        }
        return userBidOnAuctions;
    }

    public void sortBySoonestEndingUserAuctions(ArrayList<Auction> userListedAuctions) {
        Comparator<Auction> auctionComparator = (Auction one, Auction two) ->
                one.getLocalEndDateAndTime().compareTo(two.getLocalEndDateAndTime());
        Collections.sort(userListedAuctions, auctionComparator);
    }

    public boolean isEmpty() {
        return auctionList.isEmpty();
    }

    public int getActiveListLength() {
        return activeList.size();
    }

    public boolean activeListIsEmpty() {
        return activeList.isEmpty();
    }

    public Auction getInactiveAuctionAtIndex(int i) {
        return inactiveList.get(i);
    }

    public ArrayList<Auction> getInactiveList() {
        return inactiveList;
    }

    // called when auctions ends
    public void endAuction(Auction auction) {
        if (activeList.contains(auction)) {
            activeList.remove(auction);
        }
        if (!inactiveList.contains(auction)) {
            inactiveList.add(auction);
        }
    }

    public void checkDates() {
        for(Auction auction : auctionList) {
            if(auction.checkEndDateTimeIsBeforeNow()) {
                endAuction(auction);
            }
        }
    }

    @Override
    public String toString() {
        String str = "";
        for(Object a : auctionList) {
            str += a.toString() + "\n";
        }
        return str;
    }

    public static void main(String[] arg) {

    }
}