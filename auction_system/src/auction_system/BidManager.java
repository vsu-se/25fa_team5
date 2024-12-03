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
            bidList.add(bid);
            sortBidsByBidAmount();
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
