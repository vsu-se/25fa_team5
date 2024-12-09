package auction_system;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public double getWinningBid() {
        Bid highest = new Bid(0);
        for(int i = 0; i < bidList.size(); i++) {
            if(bidList.get(i).getBidValue() > highest.getBidValue()) {
                highest = bidList.get(i);
            }
        }
        return highest.getBidValue();
    }

    // use before getWinningBid
    public boolean checkIfEmpty() {
        return bidList.isEmpty();
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
