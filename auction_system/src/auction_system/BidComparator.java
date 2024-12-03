package auction_system;

import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {

    public BidComparator() {

    }

    @Override
    public int compare(Bid one, Bid two) {
        return Double.compare(two.getBidValue(), one.getBidValue());
    }
}
