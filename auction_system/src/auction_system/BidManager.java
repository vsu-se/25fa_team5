package auction_system;

import java.util.ArrayList;
import java.util.Collections;

public class BidManager {
    ArrayList<Bid> bidList = new ArrayList<>();

    public BidManager() {

    }

    public void addBid(Bid bid) {
        if(!containsBid(bid)) {
            if(getIndexOfUserOldBid(bid) > -1) {
                bidList.remove(getIndexOfUserOldBid(bid));
                bidList.add(bid);
                sortBidsByBidAmount();
            }
            else {
                bidList.add(bid);
                sortBidsByBidAmount();
            }
        }
    }

    // used to get all bids for bid history, used on file manager
    public void addBidForBidHistory(Bid bid) {
        bidList.add(bid);
    }

    public boolean containsBid(Bid bid) {
        if(bidList.contains(bid)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void sortBidsByBidAmount() {
        Collections.sort(bidList, new BidComparator());
    }

    public Bid getWinningBid() {
        Bid highest = new Bid(0);
        for(int i = 0; i < bidList.size(); i++) {
            if(bidList.get(i).getBidValue() > highest.getBidValue()) {
                highest = bidList.get(i);
            }
        }
        return highest;
    }

    // use before getWinningBid
    public boolean checkIfEmpty() {
        return bidList.isEmpty();
    }

    public Bid getBidByIndex(int i) {
        return bidList.get(i);
    }

    public boolean checkIfUserHasBid(User user) {
        for(Bid bid: bidList) {
            if(bid.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public Bid getUserBid(String username) {
        Bid bid;
        User user = new User(username);
        for(int i = 0; i < bidList.size(); i++) {
            bid = bidList.get(i);
            if(bid.getUser().equals(user)) {
                return bid;
            }
        }
        return null;
    }

    public int getIndexOfUserOldBid(Bid bid) {
        int pos = -1;
        for(int i = 0; i < bidList.size(); i++) {
            if(bidList.get(i).getUser().equals(bid.getUser())) {
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public String toString() {
        String line = "";
        for(Bid bid : bidList) {
            line += bid.toString();
        }
        return line;
    }

    public static void main(String[] args) {

    }
}
